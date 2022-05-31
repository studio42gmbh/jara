/*
 * The MIT License
 *
 * Copyright 2020 Studio 42 GmbH (https://www.s42m.de).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.s42.jara;

import de.s42.jara.assets.AssetManager;
import de.s42.jara.enitites.Scene;
import de.s42.jara.tracer.RenderBuffer;
import de.s42.jara.tracer.Raytracer;
import de.s42.jara.util.FileHelper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Benjamin Schiller
 */
public class App extends JFrame
{
	private final static Logger log = LogManager.getLogger(App.class.getName());

	protected final static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	protected final static DecimalFormat NUMBER_FORMAT = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

	public Raytracer raytracer;
	public AssetManager assets;
	private boolean rendering = true;
	private boolean shallSave = false;
	private final long startTime;
	private BufferedImage checkerBoard;

	private class DrawPanel extends JPanel
	{
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			assert g != null;

			Graphics2D g2d = (Graphics2D) g;

			int tileWidth = checkerBoard.getWidth();
			int tileHeight = checkerBoard.getHeight();
			for (int y = 0; y < getHeight(); y += tileHeight) {
				for (int x = 0; x < getWidth(); x += tileWidth) {
					g2d.drawImage(checkerBoard, x, y, this);
				}
			}

			paintRendering(g2d, true);
		}
	}

	protected void paintRendering(Graphics2D g2D, boolean scaleToWindow)
	{
		assert g2D != null;

		//g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		//blit the rendering
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		
		int w;
		int h;
		
		if (scaleToWindow) {
			
			w = getContentPane().getWidth();
			h = getContentPane().getHeight() - 15;
			
			g2D.drawImage(raytracer.buffer.getBuffer(), 0, 0, w, h, this);
		}
		else {
			
			w = Configuration.getWidth();
			h = Configuration.getHeight();
			
			g2D.drawImage(raytracer.buffer.getBuffer(), 0, 0, this);
		}

		//add version and state info
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setColor(Color.black);
		g2D.fillRect(0, h, w, 15);

		g2D.setColor(Color.lightGray);

		//infos
		String infoString
			= raytracer.scene.name
			+ "  HDRScale: " + NUMBER_FORMAT.format(raytracer.getHdrScale())
			+ "  Duration: " + (int) ((double) (System.nanoTime() - startTime) * 0.000000001) + " sec."
			+ "  Pass: " + (int) raytracer.passesRendered;
		g2D.drawString(infoString, 5, h + 12);

		//version
		//Rectangle2D bounds = g2D.getFontMetrics().getStringBounds(Jara.VERSION, g2D);
		//g2D.drawString(Jara.VERSION, Configuration.getWidth() - (int) bounds.getWidth() - 5, Configuration.getHeight() + 12);
	}

	public App()
	{
		startTime = System.nanoTime();

		initRaytracer();
		initComponent();
	}

	private void initRaytracer()
	{
		assets = new AssetManager();

		Scene scene = Configuration.createScene(assets);

		RenderBuffer buffer = new RenderBuffer(Configuration.getWidth(), Configuration.getHeight());

		raytracer = new Raytracer(
			buffer,
			scene
		);
	}

	private void initComponent()
	{
		checkerBoard = FileHelper.getFileAsImage(Configuration.getBasePath() + File.separator + "images/checkerboard.png");

		setTitle("Jara");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.darkGray);
		setContentPane(new DrawPanel());
		getContentPane().setPreferredSize(new Dimension(Configuration.getWidth(), Configuration.getHeight() + 15));
		pack();
		setLocationRelativeTo(null);

		final SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash != null) {
			splash.close();
		}

		setVisible(true);

		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				//exit on escape
				if (isRendering() && (e.getKeyCode() == Configuration.KEY_EXIT)) {
					setRendering(false);
					log.info("Stopping Rendering");
				}
				else if ((e.getKeyCode() == Configuration.KEY_EXPOSURE_MINUS)) {
					raytracer.setHdrScale(raytracer.getHdrScale() - 0.1);
					update();
					log.info("Changed HDR Scale to " + raytracer.getHdrScale());
				}
				else if ((e.getKeyCode() == Configuration.KEY_EXPOSURE_PLUS)) {
					raytracer.setHdrScale(raytracer.getHdrScale() + 0.1);
					update();
					log.info("Changed HDR Scale to " + raytracer.getHdrScale());
				}
				else if ((e.getKeyCode() == Configuration.KEY_SAVE_IMAGE)) {
					log.info("Image will get saved after finishing this pass ...");
					saveRenderingAsImage();
				}
			}

		});
	}

	public void render()
	{
		raytracer.render();

		update();

		if (shallSave) {
			shallSave = false;
			saveImage();
		}
	}

	public void saveRenderingAsImage()
	{
		shallSave = true;
	}

	protected void saveImage()
	{
		try {
			//create a complete screenshot with menuoptions etc.
			int w = getContentPane().getPreferredSize().width;
			int h;

			if (Configuration.getShowFooterInFiles()) {
				h = getContentPane().getPreferredSize().height;
			}
			//@improvement assumes the image to be drawn top in frame component
			else {
				h = raytracer.buffer.getHeight();
			}

			//PNG
			if (Configuration.getSaveFormat() == Configuration.ImageSaveFormat.PNG
				|| Configuration.getSaveFormat() == Configuration.ImageSaveFormat.BOTH) {

				BufferedImage saveImage = new BufferedImage(
					w,
					h,
					BufferedImage.TYPE_INT_ARGB);

				Graphics2D g2D = (Graphics2D) saveImage.getGraphics();
				paintRendering(g2D, false);
				g2D.dispose();

				Path outputFilePng = FileHelper.getUserHome().resolve("jara-" + Configuration.getSceneLoader().getClass().getSimpleName().toLowerCase() + "-" + DATETIME_FORMAT.format(new Date()) + ".png");
				FileHelper.saveImageAsFilePng(saveImage, outputFilePng);

				log.info("Saved rendering to png " + outputFilePng.toString());

				saveImage.flush();
			}

			//JPG
			if (Configuration.getSaveFormat() == Configuration.ImageSaveFormat.JPG
				|| Configuration.getSaveFormat() == Configuration.ImageSaveFormat.BOTH) {

				BufferedImage saveImage = new BufferedImage(
					w,
					h,
					BufferedImage.TYPE_INT_RGB);

				Graphics2D g2D = (Graphics2D) saveImage.getGraphics();
				paintRendering(g2D, false);
				g2D.dispose();

				Path outputFileJpg = FileHelper.getUserHome().resolve("jara-" + Configuration.getSceneLoader().getClass().getSimpleName().toLowerCase() + "-" + DATETIME_FORMAT.format(new Date()) + ".jpg");
				FileHelper.saveImageAsFileJpg(saveImage, outputFileJpg, Configuration.getJpgQuality());

				log.info("Saved rendering to jpg " + Configuration.getJpgQuality() + " " + outputFileJpg.toString());

				saveImage.flush();
			}

		}
		catch (IOException ex) {
			log.error("Error saving file - " + ex.getMessage(), ex);
		}
	}

	public void update()
	{
		validate();
		repaint();
	}

	public boolean isRendering()
	{
		return rendering;
	}

	public void setRendering(boolean rendering)
	{
		this.rendering = rendering;
	}
}
