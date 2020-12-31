![GitHub](https://img.shields.io/github/license/studio42gmbh/jara)
![GitHub top language](https://img.shields.io/github/languages/top/studio42gmbh/jara)
![GitHub last commit](https://img.shields.io/github/last-commit/studio42gmbh/jara)
![GitHub issues](https://img.shields.io/github/issues/studio42gmbh/jara)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/studio42gmbh/jara/Java%20CI%20with%20Maven)

# JARA

JARA is a educational 100% Java based ray tracer.

The primary focus of this package is to teach and help explore path tracing graphics not performance.
Altough certain systemic performance measures like spatial partitioning are put in place to teach these concepts.

If you are interested in some fundamentals about path tracing you can download this presentation:
[Basic Path Tacing PDF](https://github.com/studio42gmbh/jara/blob/main/docs/documents/s42-basics-pathtracing.pdf)

If you like JARA or have constructive critique dont hesitate to write us directly on info@s42m.de. 
We are always happy for qualified feedback!

"Look up to the stars not down on your feet. Be curious!" _Stephen Hawking 1942 - 2018_

Have a great day!
Benjamin Schiller

## Features

* PBR Materials with Textures
* Transparent Materials
* Custom Procedural Materials
* Load OBJ Files (using own lightweight [ANTLR](https://github.com/antlr/antlr4) based parser)
* Camera with depth of field
* Spatial Optimization (ongoing)
* Primitives: Sphere, Plane, Triangle, Disc
* Lighting by either ambient and directional light (creating algorithmic background) or 360 backgrounds

## Future Plans

* Support for .HDR file format for backgrounds
* Optimize spatial optimization
* Add configurable denoising
* Add configurable post FX
* Allow dynamic settings and camera in app
* Provide file based scenes either using default file format or little own format
* Allow dynamic texture handling for supporting more materials in the same scene
* Split materials (data) from shaders (alogrithms) to allow easier customization
* Add tutorials

## Examples

![Example Car](https://studio42gmbh.github.io/jara/images/jara-carstill-2020-12-30-14-05-21.jpg)
Car loaded as OBJ exported from Blender

![Example Spheres](https://studio42gmbh.github.io/jara/images/jara-spheres-2020-12-31-11-11-08.jpg)
Example showing different materials

![Example House](https://studio42gmbh.github.io/jara/images/jara-meshtest2-2020-07-26-15-28-20.jpg)
House loaded as OBJ exported from Blender

![Example Chair](https://studio42gmbh.github.io/jara/images/jara-chairstill-2020-12-27-22-29-50.jpg)
Chair loaded as OBJ exported from Blender
