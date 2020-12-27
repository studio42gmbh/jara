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
grammar ObjParser;

meshes : ( command )* EOF ;
command : ( mtllib | object | position | normal | textureposition | group | usematerial | smooth | face )? NEWLINE ;

mtllib : KEYWORD_MTLLIB SYMBOL ;
object : KEYWORD_OBJECT SYMBOL ;
position : KEYWORD_POSITION vector3 ;
normal : KEYWORD_NORMAL vector3;
textureposition : KEYWORD_TEXTUREPOSITION vector2;
group : KEYWORD_GROUP SYMBOL ;
usematerial : KEYWORD_USEMATERIAL SYMBOL ;
smooth : KEYWORD_SMOOTH (KEYWORD_OFF | INTEGER | FLOAT );
face : KEYWORD_FACE vertex vertex vertex;

vector2 : FLOAT FLOAT;
vector3 : FLOAT FLOAT FLOAT;
vertex : positionIndex SLASH textureIndex SLASH normalIndex;
positionIndex : INTEGER;
textureIndex : INTEGER;
normalIndex : INTEGER;

KEYWORD_MTLLIB : 'mtllib' ;
KEYWORD_OBJECT : 'o' ;
KEYWORD_GROUP : 'g' ;
KEYWORD_POSITION : 'v' ;
KEYWORD_NORMAL : 'vn' ;
KEYWORD_TEXTUREPOSITION : 'vt' ;
KEYWORD_USEMATERIAL : 'usemtl' ;
KEYWORD_SMOOTH : 's' ;
KEYWORD_FACE : 'f' ;
KEYWORD_OFF : 'off' ;

SLASH : '/';

INTEGER : [-]? [0-9]+ ;
FLOAT : [-]? [0-9]+ '.' [0-9]+ ;
SYMBOL : [a-zA-Z0-9] [a-zA-Z0-9_\-.]* ;
COMMENT : '#' ~[\n]+ NEWLINE -> skip ;
NEWLINE : [\n] ;
WS : [ \t\r]+ -> skip ;