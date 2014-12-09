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
 * Would have included this file as part of main.js for optimization; 
 * however there exists an issue with arbor where it, I think, tries to perform 
 * loading of some content and it blows up. 
 */
define(["jquery", "thirdParty/jQuery/plugins/arbor", "thirdParty/jQuery/plugins/arbor-tween", 
        "thirdParty/jQuery/plugins/slides.min.jquery", "domReady-min!"], function($) {
	
	var arborExample = function() {
        
        /*
         * Later write a simple code for finding paths and etcetera
         */
        var pSystem = arbor.ParticleSystem(1000, 800, 0.5); // create the system with sensible repulsion/stiffness/friction
        pSystem.renderer = (function() {
            
        	/*
        	 * Ported from the example page, but a bit of a shock that one has to supply all the functionality per 
        	 * implementation
        	 */
            var domNode = $("#arborMousing"),
            	canvas = domNode.get(0),
                ctx = canvas.getContext("2d"),
                particleSystem = null;
            
            ctx.strokeStyle = "#FFA500";
            ctx.lineWidth = 3; 
            ctx.lineCap = 'round';
            ctx.lineJoin = 'bevel';
            ctx.font = "bold 20px Trebuchet MS, Tahoma, Verdana";
            ctx.textAlign = "center";
            ctx.textBaseline = "middle";
            ctx.shadowColor = "rgba(0,0,0,0.5)";
            ctx.shadowOffsetX = 4;
            ctx.shadowOffsetY = 4;
            ctx.shadowBlur = 5;
            
            var content = {
                /*
                 * the particle system will call the init function once, right before the
                 * first frame is to be drawn. it's a good place to set up the canvas and
                 * to pass the canvas size to the particle system
                 */
                init: function(system) {
                    particleSystem = system;
                    
                    /*
                     * inform the system of the screen dimensions so it can map coords for us.
                     * if the canvas is ever resized, screenSize should be called again with
                     * the new dimensions
                     */
                    particleSystem.screenSize(canvas.width, canvas.height); 
                    particleSystem.screenPadding(80); // leave an extra 80px of whitespace per side
                    content._initMouseHandling();
                },
                
                _initMouseHandling: function(){
                	// no-nonsense drag and drop (thanks springy.js)
                	var nearest,
                		dragged,
                		mouseP,
                		pos;
                	/*
                	 * Wow, so if you want such support you need to implement below like functionality 
                	 * everytime? Try to modularize later for playing around. 
                	 */
                	var handler = {
                			moved: function(e) {
                				pos = $(canvas).offset(),
                					mouseP = arbor.Point(e.pageX-pos.left, e.pageY-pos.top),
                					nearest = particleSystem.nearest(mouseP);
                				
                				if(!nearest.node) return false;
                			},
                			
                			clicked: function(e) {
                				pos = $(canvas).offset(),
                					mouseP = arbor.Point(e.pageX-pos.left, e.pageY-pos.top),
                					nearest = dragged = particleSystem.nearest(mouseP);
            					
            					if(dragged && dragged.node !== null){ 
            						dragged.node.fixed = true;
            					}
            					$(canvas).unbind('mousemove', handler.moved);
            					$(canvas).bind('mousemove', handler.dragged);
            					$(window).bind('mouseup', handler.dropped);
            					return false;
            				},
            				
            				dragged:function(e){
            					pos = $(canvas).offset();
            					var s = arbor.Point(e.pageX-pos.left, e.pageY-pos.top);
            					if(!nearest) {
            						return;
            					}
            					if(dragged !== null && dragged.node !== null) {
	            					var p = particleSystem.fromScreen(s);
	            					dragged.node.p = p;
            					}
            					return false;
            				},
            				
            				dropped:function(e){
            					if(dragged === null || typeof(dragged.node) === "undefined") { 
            						return
            					}
            					
            					if(dragged.node !== null) { 
            						dragged.node.fixed = false;
            					}
            					dragged = null;
            					$(canvas).unbind('mousemove', handler.dragged);
            					$(window).unbind('mouseup', handler.dropped);
            					$(canvas).bind('mousemove', handler.moved);
            					mouseP = null;
            					return false;
            				}
            			};
            		$(canvas).mousedown(handler.clicked);
            		$(canvas).mousemove(handler.moved);
                },
                
                /*
                 * redraw will be called repeatedly during the run whenever the node positions
                 * change. the new positions for the nodes can be accessed by looking at the
                 * .p attribute of a given node. however the p.x & p.y values are in the coordinates
                 * of the particle system rather than the screen. you can either map them to
                 * the screen yourself, or use the convenience iterators .eachNode (and .eachEdge)
                 * which allow you to step through the actual node objects but also pass an
                 * x,y point in the screen's coordinate system
                 */
                redraw: function() {
                    
                    ctx.clearRect(0,0, canvas.width, canvas.height);
                    particleSystem.eachEdge(function(edge, pt1, pt2){
                        /*
                         * edge: {source:Node, target:Node, length:#, data:{}}
                         * pt1:  {x:#, y:#}  source position in screen coords
                         * pt2:  {x:#, y:#}  target position in screen coords
                         * 
                         * draw a line from pt1 to pt2
                         */
                        ctx.beginPath();
                        ctx.moveTo(pt1.x, pt1.y);
                        ctx.lineTo(pt2.x, pt2.y);
                        ctx.stroke();
                    });
                    
                    particleSystem.eachNode(function(node, pt){
                    	/*
                         * node: {mass:#, p:{x,y}, name:"", data:{}}
                         * pt:   {x:#, y:#}  node position in screen coords
                         * 
                         * draw a rectangle centered at pt
                         */
                        var w = 40;
                        ctx.fillStyle = "#E6E6FA";
                        ctx.fillRect(pt.x-w/2, pt.y-w/2, w,w);
                        ctx.fillStyle = "#400000";
                        ctx.fillText(node.data.label, pt.x, pt.y);
                    });
                }
                
            };
            
            return content;
        })();
        
        pSystem.graft({
            nodes: {
                A: {label: "A"},
                B: {label: "B"},
                C: {label: "C"},
                D: {label: "D"},
                E: {label: "E"}
            },
            
            edges: {
                A: {
                    B: {},
                    C: {}
                },
                B: {
                    C: {},
                    D: {}
                },
                C: {
                    B: {},
                    D: {},
                    E: {}
                },
                D: {
                    E: {}
                },
                E: {
                    D: {}
                }
            }
        });
    };
	
	$(function() {

    	$(".gwSlides").slides({
            preload: true,
            //preloadImage: '${jQueryPluginCssUrl}/images/slider/loading.gif',
            container: "gwSlidesContainer",
            next: "gwSlidesNext",
            prev: "gwSlidesPrev",
            generatePagination: true,
            //play: 5000,
            //pause: 2500,
            hoverPause: true
        });
    	
    	arborExample();
    	
    });
	
});