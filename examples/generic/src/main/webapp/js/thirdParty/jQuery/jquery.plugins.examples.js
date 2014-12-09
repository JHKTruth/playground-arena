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

(function($){
    
    var arborExample = function() {
        
        /*
         * Later write a simple code for finding paths and etcetera
         */
        var pSystem = arbor.ParticleSystem(1000, 800, 0.5); // create the system with sensible repulsion/stiffness/friction
        pSystem.renderer = (function() {
            
            var canvas = $("#arbor").get(0),
                ctx = canvas.getContext("2d"),
                particleSystem = null;
                
            return {
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
                        ctx.strokeStyle = "black";
                        ctx.lineWidth = 1 + 4*edge.data.weight;
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
                        var w = 10;
                        ctx.fillStyle = "red";
                        ctx.fillRect(pt.x-w/2, pt.y-w/2, w,w);
                    });
                }
                
            };
        })();
        
        pSystem.graft({
            nodes: {
                A: {},
                B: {},
                C: {},
                D: {},
                E: {}
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
    
    var slidesExample = function() {
        
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
        
    };
    
    var bValidatorExample = function() {
        $("#gwValidator").bValidator({
            validateOn: 'keyup'
        });
    };
    
    var galleriaExample = function() {
        $('#gwGalleria').galleria();
    };
    
    $(document).ready(function() {
        arborExample();
        slidesExample();
        bValidatorExample();
        galleriaExample();
        
    });
})(jQuery);