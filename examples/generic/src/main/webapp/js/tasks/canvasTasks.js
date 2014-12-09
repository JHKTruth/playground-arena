/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * For now will contain content from HTML5 Canvas book with a bit of clean up 
 * here and there with different syntax, later read upon the documentation and write own 
 * functionality.
 */
define("tasks/canvasTasks", ["jquery", "order-min!thirdParty/sylvester", "order-min!thirdParty/glUtils"], function($) {
	
	var MatrixHelper = function() {
		this.stack = [];
		this.matrix = null;
	};
	
	MatrixHelper.prototype.push = function _push(matrix) {
		
		if(matrix) {
			var dup = matrix.dup();
			this.stack.push(dup);
			this.matrix = dup;
		}else {
			this.stack.push(this.matrix.dup());
		}
	};
	
	MatrixHelper.prototype.pop = function _pop(matrix) {
		return this.stack.pop();
	};
	
	MatrixHelper.prototype.loadIdentity = function _loadIdentity() {
		this.matrix = Matrix.I(4);
	};
	
	MatrixHelper.prototype.multiply = function _multiply(matrix) {
		this.matrix = this.matrix.x(matrix);
	};
	
	MatrixHelper.prototype.translate = function _translate(vector) {
		this.multiply( Matrix.Translation($V([vector[0], vector[1], vector[2]])).ensure4x4() );
	};
	
	MatrixHelper.prototype.rotate = function _rotate(angle, vector) {
		var radians = angle * Math.PI / 180.0,
			matrix = Matrix.Rotation(radians, $V([vector[0], vector[1], vector[2]])).ensure4x4();
		
		this.multiply(matrix);
	};
	
	var CanvasTasks = function(canvasId, shaderFSId, shaderVSId) {
		this.canvas = $(canvasId)[0];
		this.shaderFS = $(shaderFSId)[0];
		this.shaderVS = $(shaderVSId)[0];
		this.context = this.canvas.getContext("experimental-webgl");
		this.pMatrix = null;
		this.shaderProgram = null;
		this.matrixContent = new MatrixHelper();
		
		this.rotateCube = 0;
		this.vertexPositionBuffer = null; 
		this.vertexColorBuffer = null;
		this.vertexIndexBuffer = null;
		this.vertices = null;
		
		this.context.viewportWidth = this.canvas.width;
		this.context.viewportHeight = this.canvas.height;
		
		this.context.clearColor(0.0, 0.0, 0.0, 1.0);
		this.context.clearDepth(1.0);
		this.context.enable(this.context.DEPTH_TEST);
		this.context.depthFunc(this.context.LEQUAL);
		
		return this;
	};
	
	CanvasTasks.prototype.init = function _init() {
		this.initShaders();
		this.initBuffers();
		
		var _this = this;
		
		setInterval(function() { _this.drawCube.call(_this); }, 33);
	};
	
	CanvasTasks.prototype.initShaders = function _initShader() {
		var fragmentShader = this.getShader(this.shaderFS), 
			vertexShader = this.getShader(this.shaderVS),
			context = this.context;
		
		this.shaderProgram = context.createProgram();
		var shaderProgram = this.shaderProgram;
		
		context.attachShader(shaderProgram, vertexShader);
		context.attachShader(shaderProgram, fragmentShader);
		context.linkProgram(shaderProgram);
		
		if(!context.getProgramParameter(shaderProgram, context.LINK_STATUS)) {
			throw new Error("Could not initialize shaders");
		}
		
		context.useProgram(shaderProgram);
		
		shaderProgram.vertexPositionAttribute = context.getAttribLocation(shaderProgram, "aVertexPosition");
		context.enableVertexAttribArray(shaderProgram.vertexPositionAttribute);
		
		shaderProgram.vertexColorAttribute = context.getAttribLocation(shaderProgram, "aVertexColor");
		context.enableVertexAttribArray(shaderProgram.vertexColorAttribute);
		
		shaderProgram.pMatrixUniform = context.getUniformLocation(shaderProgram, "uPMatrix");
		shaderProgram.mvMatrixUniform = context.getUniformLocation(shaderProgram, "uMVMatrix");
	};
	
	CanvasTasks.prototype.initBuffers = function _initBuffers() {
		
		var context = this.context;
		
		/*
		 * Buffers refer to space in the video card's memory that we set aside to hold the geometry 
		 * describing our 3D objects.
		 */
		this.vertexPositionBuffer = context.createBuffer();
		context.bindBuffer(context.ARRAY_BUFFER, this.vertexPositionBuffer);
		
		context.bufferData(context.ARRAY_BUFFER, new Float32Array([
			                        //Front
			                        -1.0, -1.0, 1.0,
			                        1.0, -1.0, 1.0,
			                        1.0, 1.0, 1.0,
			                        -1.0, 1.0, 1.0,
			                        
			                        //Back
			                        -1.0, -1.0, -1.0,
			                        -1.0, 1.0, -1.0,
			                        1.0, 1.0, -1.0,
			                        1.0, -1.0, -1.0,
			                        
			                        //Top
			                        -1.0, 1.0, -1.0,
			                        -1.0, 1.0, 1.0,
			                        1.0, 1.0, 1.0,
			                        1.0, 1.0, -1.0,
			                        
			                        //Bottom
			                        -1.0, -1.0, -1.0,
			                        1.0, -1.0, -1.0,
			                        1.0, -1.0, 1.0,
			                        -1.0, -1.0, 1.0,
			                        
			                        //Right
			                        1.0, -1.0, -1.0,
			                        1.0, 1.0, -1.0,
			                        1.0, 1.0, 1.0,
			                        1.0, -1.0, 1.0,
			                        
			                        //Left
			                        -1.0, -1.0, -1.0,
			                        -1.0, -1.0, 1.0,
			                        -1.0, 1.0, 1.0,
			                        -1.0, 1.0, -1.0
			                        ]), context.STATIC_DRAW);
		
		this.vertexPositionBuffer.itemSize = 3;
		this.vertexPositionBuffer.numItems = 24;
		
		this.vertexColorBuffer = context.createBuffer();
		context.bindBuffer(context.ARRAY_BUFFER, this.vertexColorBuffer);
		
		var colors = [
		              [1.0, 1.0, 1.0, 1.0],	//Front
		              [0.9, 0.0, 0.0, 1.0], //Back
		              [0.6, 0.6, 0.6, 1.0],	//Top
		              [0.6, 0.0, 0.0, 1.0],	//Bottom
		              [0.3, 0.0, 0.0, 1.0],	//Right
		              [0.3, 0.3, 0.3, 1.0]	//Left
		              ];
		
		var unpackedColors = [];
		for(var i=0, colorsMainLength=colors.length; i < colorsMainLength; i++) {
			var color = colors[i];
			for(var j=0, colorsInnerLength=color.length; j < colorsInnerLength; j++) {
				unpackedColors = unpackedColors.concat(color);
			}
		}
		
		context.bufferData(context.ARRAY_BUFFER, new Float32Array(unpackedColors), context.STATIC_DRAW);
		this.vertexColorBuffer.itemSize = 4;
		this.vertexColorBuffer.numItems = 24;
		
		this.vertexIndexBuffer = context.createBuffer();
		
		context.bindBuffer(context.ELEMENT_ARRAY_BUFFER, this.vertexIndexBuffer);
		context.bufferData(context.ELEMENT_ARRAY_BUFFER, new Uint16Array([
			                                                                  0, 1, 2,		0, 2, 3,	//Front
			                                                                  4, 5, 6,		4, 6, 7,	//Back
			                                                                  8, 9, 10,		8, 10, 11,	//Top
			                                                                  12, 13, 14,	12, 14, 15,	//Bottom
			                                                                  16, 17, 18,	16, 18, 19,	//Right
			                                                                  20, 21, 22,	20, 22, 23	//Left
			                                                                  ]), context.STATIC_DRAW);
		
		this.vertexIndexBuffer.itemSize = 1;
		this.vertexIndexBuffer.numItems = 36;
	};
	
	CanvasTasks.prototype.getShader = function(shaderScript) {
		var shaderScriptChild = shaderScript.firstChild,
			context = this.context,
			content = "";
		while(shaderScriptChild) {
			if(shaderScriptChild.nodeType === 3) {
				content += shaderScriptChild.textContent;
			}
			shaderScriptChild = shaderScriptChild.nextSibling;
		}
		
		var shader;
		if(shaderScript.type === "x-shader/x-fragment") {
			shader = context.createShader(context.FRAGMENT_SHADER);
		}else if(shaderScript.type === "x-shader/x-vertex") {
			shader = context.createShader(context.VERTEX_SHADER);
		}else {
			console.error("Invalid shaderScript type: ", shaderScript.type);
		}
		
		context.shaderSource(shader, content);
		context.compileShader(shader);
		
		if(!context.getShaderParameter(shader, context.COMPILE_STATUS)) {
			console.error("Compile Status doesn't exist for the context: ", context.getShaderInfoLog(shader));
		}
		
		return shader;
	};
	
	CanvasTasks.prototype.setMatrixUniforms = function() {
		var context = this.context,
			shaderProgram = this.shaderProgram;
		
		context.uniformMatrix4fv(shaderProgram.pMatrixUniform, false, new Float32Array(this.pMatrix.flatten()));
		context.uniformMatrix4fv(shaderProgram.mvMatrixUniform, false, new Float32Array(this.matrixContent.matrix.flatten()));
	};
	
	CanvasTasks.prototype.drawCube = function() {
		var context = this.context,
			shaderProgram = this.shaderProgram,
			matrixContent = this.matrixContent;
		
		context.viewport(0, 0, context.viewportWidth, context.viewportHeight);
		context.clear(context.COLOR_BUFFER_BIT | context.DEPTH_BUFFER_BIT);
		
		this.pMatrix = makePerspective(25, (context.viewportWidth / context.viewportHeight), 0.1, 100.0);
		
		matrixContent.loadIdentity();
		
		matrixContent.translate([0, 0.0, -10.0]);
		
		matrixContent.push();
		
		matrixContent.rotate(this.rotateCube, [0, .5, .5]);
		
		context.bindBuffer(context.ARRAY_BUFFER, this.vertexPositionBuffer);
		context.vertexAttribPointer(shaderProgram.vertexPositionAttribute, this.vertexPositionBuffer.itemSize, context.FLOAT, false, 0, 0);
		
		context.bindBuffer(context.ARRAY_BUFFER, this.vertexColorBuffer);
		context.vertexAttribPointer(shaderProgram.vertexColorAttribute, this.vertexColorBuffer.itemSize, context.FLOAT, false, 0, 0);
		
		context.bindBuffer(context.ELEMENT_ARRAY_BUFFER, this.vertexIndexBuffer);
		this.setMatrixUniforms();
		context.drawElements(context.TRIANGLES, this.vertexIndexBuffer.numItems, context.UNSIGNED_SHORT, 0);
		
		matrixContent.pop();
		this.rotateCube += 2;
		
	};
	
	return CanvasTasks;
});