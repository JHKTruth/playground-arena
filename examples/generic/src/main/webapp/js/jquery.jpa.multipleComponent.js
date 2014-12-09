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
 * Basically using static fields of function w/o subclassing and etcetera for now. 
 * Clean up code later.
 */
define(["jquery", "jquery.jpa.generic"], function($, generic) {
	
	function TemplatingDialog(_container, _textToDisplay, _templateId) {
		
		this.dialogId = this.getNextId();
		this.container = _container;
		this.container.data("data-actualNodeId", this.dialogId);
		this.template = $(_templateId);
		this.dialog = null;
		
		this.constructControls(_textToDisplay);
		this.constructDialog(_textToDisplay);
	};
	
	TemplatingDialog.prototype.getNextId = (function() {
		var idCounter = 0;
		
		return function() { return "TemplatingDialog_" + idCounter++; };
	})();
	
	TemplatingDialog.prototype.constructControls = function _constructControls(_textToDisplay) { 
		
		var container = $('<div class="ui-state-default ui-corner-all margin10t margin10b" style="width: 200px;"></div>'),
			textContainer = $('<div class="inlineBlock">' + _textToDisplay + '</div>'),
			displayLink = $('<span class="floatR ui-icon ui-icon-newwin pointer"></span>'),
			addLink = $('<span class="floatR ui-icon ui-icon-plus pointer"></span>'),
			_this = this;
		
		addLink.click(function(e) { _this.addTemplate() });
		displayLink.click(function(e) { _this.showDialog() });
		
		$([textContainer, displayLink, addLink]).each(function(index, ele){
			container.append(ele);
		});
		
		this.container.append(container);
		
	};
	
	TemplatingDialog.prototype.constructDialog = function _constructDialog(_textToDisplay) { 
		
		var dialog = $('<div title="' + _textToDisplay + '" id="' + this.dialogId + '"></div>'),
			_this = this;
		
		this.templateContainer = $('<div></div>');
		dialog.append(this.templateContainer);
		
		this.dialog = $(dialog).dialog({
			autoOpen: false,
            modal: false,
            hide: true,
            width: 500,
            height: 400,
            minWidth: 300,
            minHeight: 300,
            hide: "explode",
            buttons: {
                    	"Close":	function() {
                                    	_this.dialog.dialog("close");
                    				}
                    }
		});
		
	};
	
	TemplatingDialog.prototype.showDialog = function _showDialog() { 
		
		if(!this.dialog.dialog("isOpen")) {
			this.dialog.dialog("open");
			$(this.templateContainer).accordion("resize");
        }
	};
	
	TemplatingDialog.prototype.addTemplate = function _addTemplate() { 
		$(this.templateContainer).jpa("copyTemplate", this.template);
	};
	
	/*
	 * Don't clutter the jQuery.fn object with more than one namespace per plugin
	 */
	var methods = { 
			
			rootNS: {},
			
			fnNS: {
				
				/*
				 * Clean up the code later and impl better
				 * 
				 * The CSS Selector node should have: 
				 * 	data-dialogTitle -	will be used for Dialog's title as well as the text displayed alongside ui-icon-newwin and ui-icon-plus icons
				 * 	data-templateId	-	Id of the template to copy into the dialog when ui-icon-newwin is clicked
				 * 
				 */
				createDialogWithCopyTemplating: function _createDialogWithCopyTemplating(textToDisplay, templateId) { 
					
					var templateDialogs = [];
					
					this.each(function() { 
						var wrapped = $(this);
						
						templateDialogs.push(new TemplatingDialog(wrapped, wrapped.attr("data-dialogTitle"), wrapped.attr("data-templateId")));
					});
					
					return templateDialogs;
				},
				
				allowMultipleStoreOfInputValues: function _allowMultipleStoreOfInputValues() { 
					
					function createIconContainer(inputComponent, type) { 
						
						var floatContainer = $("<div class='floatL'></div>");
							containerNode = $("<div class='ui-state-default ui-corner-all margin5l margin5t'></div>"),
							contentNode = $(type.divTemplate);
						
						containerNode.append(contentNode);
						floatContainer.append(containerNode);
						
						contentNode.click(function(e) { type.clickCallBack.call(inputComponent, e) });
						
						return floatContainer;
					};
					
					createIconContainer.TYPES = { 
						ADD_CONTENT: { 
							divTemplate: "<div class='ui-icon ui-icon-plus pointer'></div>",
							clickCallBack: function(e) { 
								
								var data = this.data("val") || [], 
									val = this.val(),
									types = createIconContainer.TYPES;
								
								if($.inArray(val, data) === -1) { 
									types.OPEN_CONTENT.CONTAINER.addValue.call(this, val);
								}else { 
									types.DELETE_CONTENT.DELETE_ELEMENT.call(this, val);
								}
								
								data = this.data("val") || [];
								data.push(val);
								this.data("val", data);
							}
						},
						
						DELETE_CONTENT: { 
							divTemplate: "<div class='ui-icon ui-icon-minus pointer'></div>",
							
							DELETE_ELEMENT: function(val) { 
								
								var data = this.data("val");
								
								if(!data) { 
									data = [];
								}else { 
									data = $(data).filter(function(index, ele) { return  val !== ele});
								}
								
								this.data("val", data);
							},
							
							clickCallBack: function(e) { 
								
								var val = this.val(),
									types = createIconContainer.TYPES;
								
								types.DELETE_CONTENT.DELETE_ELEMENT.call(this, val);
								types.OPEN_CONTENT.CONTAINER.removeValue.call(this, val);
							}
						},
						
						OPEN_CONTENT: { 
							divTemplate: "<div class='ui-icon ui-icon-newwin pointer'></div>",
							
							CONTAINER: { 
								
								getContentContainerElements: function() { 
									var contentContainerIds = $(this).data("contentContainerIds");
									
									return {MAIN_CONTAINER: $("#" + contentContainerIds.mainId)[0], CONTENT_CONTAINER: $("#" + contentContainerIds.contentId)[0]};
								},
								
								setContentContainerIds: (function() { 
									var count = 0;
									
									return function() { 
										
										var identifier = count++;
										
										$(this).data("contentContainerIds", {mainId: "main_" + identifier, contentId: "content_" + identifier});
									};
								})(),
								
								addValue: function _addValue(val) { 
									var types = createIconContainer.TYPES,
										container = types.OPEN_CONTENT.CONTAINER.getContentContainerElements.call(this).CONTENT_CONTAINER;
									
									if(container) { 
										
										var contentHolder = $("<div class='content genericBorderRadius margin5b relative clear'></div>"),
											contentRemoverContainer = $("<div class='floatR'></div>"),
											contentRemover = $("<span class='ui-icon ui-icon-closethick pointer'></span>"),
											content = $("<div class='floatL' data-heldValue='" + val + "'>" + val + "</div>"),
											_this = this;
										
										contentRemoverContainer.append(contentRemover);
										
										$([content, contentRemoverContainer]).each(function(index, ele){
											contentHolder.append(ele);
										});
										
										contentRemover.click(
											(function() {
												var dataValue = val,
													element = contentHolder;
												
												return	function() { 
													types.DELETE_CONTENT.DELETE_ELEMENT.call(_this, dataValue);
													element.slideUp("slow", function() { 
														element.remove();
													});
												}
											})());
										
										$(container).append(contentHolder);
									}
								},
								
								removeValue: function _removeValue(val) { 
									var container = createIconContainer.TYPES.OPEN_CONTENT.CONTAINER.getContentContainerElements.call(this).CONTENT_CONTAINER;
									
									if(container) { 
										var removeNode = $("div[data-heldValue='" + val + "']", container).parent();
										
										removeNode.fadeOut(function(){ 
											removeNode.remove();
										});
									}
								}
							},
							
							clickCallBack: function(e) { 
								var container = createIconContainer.TYPES.OPEN_CONTENT.CONTAINER,
									contentContainerIds = $(this).data("contentContainerIds"),
									contentContainerElements = container.getContentContainerElements.call(this);
								
								if(typeof(contentContainerElements.MAIN_CONTAINER) === "undefined") { 
									
									var mainContainer = $("<div class='absolute genericBorderRadius contentDisplayContainer pad5b' style='width: 300px; height: 200px;'></div>"),
										headerContainer = $("<div class='margin5b relative contentHeaderContainer move' style='height: 20px;'></div>"),
										headerMainContent = $("<div class='sphericalGradient floatL' style='width: 20px; height: 20px;'></div><div class='pad5l floatL letterPress' style='line-height: 20px;'>Content</div>"),
										closeIconDivContainer = $("<div class='floatR ui-state-default ui-corner-all pointer'></div>"),
										closeIconContainer = $("<span class='ui-icon ui-icon-closethick'></span>"),
										contentContainer = $("<div class='relative contentContainer' style='height:180px;'></div>");
									
									headerContainer.append(headerMainContent);
									closeIconDivContainer.append(closeIconContainer);
									headerContainer.append(closeIconDivContainer);
									
									$([headerContainer, contentContainer]).each(function(index, ele){
										mainContainer.append(ele);
									});
									$(mainContainer).hide();
									mainContainer = mainContainer.appendTo("body");
									
									$(closeIconContainer).click(function() { 
										$(mainContainer).slideUp();
									});
									
									$(mainContainer).draggable({
						                cursor: "move",
						                handle: $(headerContainer)
						            });
									
									$(mainContainer).attr("id", contentContainerIds.mainId);
									$(contentContainer).attr("id", contentContainerIds.contentId);
									
									contentContainerElements.MAIN_CONTAINER = mainContainer;
									contentContainerElements.CONTENT_CONTAINER = contentContainer;
								}
								
								var contentContainer = contentContainerElements.CONTENT_CONTAINER,
									data = this.data("val") || [],
									helperFunction = 
									_this = this;
								
								$(contentContainer).children().remove();
								for(var i=0, j=data.length; i < j; i++) { 
									
									container.addValue.call(this, data[i]);
								}
								
								$(contentContainerElements.MAIN_CONTAINER).css({top: e.pageY, left: e.pageX});
								$(contentContainerElements.MAIN_CONTAINER).show("slow");
							}
						}
					};
					
					this.each(function(index, element) {
						
						createIconContainer.TYPES.OPEN_CONTENT.CONTAINER.setContentContainerIds.call(this);
						
						var container = $("<div class='relative'></div>"),
							inputWrapper = $("<div class='floatL'></div>"),
							types = createIconContainer.TYPES,
							popUpContainer = createIconContainer($(this), types.OPEN_CONTENT),
							addContainer = createIconContainer($(this), types.ADD_CONTENT),
							deleteContainer = createIconContainer($(this), types.DELETE_CONTENT);
						
						/*
						 * First add the fragment to the location and reset the container to the actual element, 
						 * so to allow addition of popUp, add, and delete fragment containers.
						 */
						$(this).wrap(container);
						container = $(this).parent();
						$(this).wrap(inputWrapper);
						
						$([addContainer, deleteContainer, popUpContainer]).each(function(index, ele){
							container.append(ele);
						});
					});
					
				},
				
				retrieveInputParameters: function _retrieveInputParameters() { 
					
					var inputParameterValues = [];
					
					this.each(function() { 
						
						/*
						 * Should only have one main content element within the selector
						 */
						var mainContentElement = $("div:not([data-fieldContainer])", this),
							inputParameter = {};
						
						appendInputParameters(mainContentElement[0], this, inputParameter);
						
						/*
						 * Bad programming style, but just a note regarding hoisting, note that w/ function 
						 * expression only the variable gets hoisted not the impl.
						 */
						function appendInputParameters(element, origNode, objectParameter) { 
							
							var fields = $("input[data-fieldName]", element), 
								subContentElement = $("> div:data(fieldContainer)", origNode);
							
							for(var i=0, j=fields.length; i < j; i++) { 
								var fieldName = $.attr(fields[i], "data-fieldname"),
									fieldType = $.attr(fields[i], "data-fieldType");
								
								if(fieldType === "Array") {
									/*
									 * Using jQuery isArray function, but note that 
									 * ECMAScript 5 defins Array.isArray() and otherwise to use 
									 * Object.prototype.toString.call(arg) === “[object Array]” invocation 
									 * [especially when passed around through frames]
									 */
									
									objectParameter[fieldName] = ($(fields[i]).data("val") && $($(fields[i]).data("val")).toArray()) || [];
								}else { 
									objectParameter[fieldName] = retrieveValue(fields[i]);
								}
							}
							
							for(var k=0, l=subContentElement.length; k < l; k++) { 
								
								/*
								 * One can figure out that it is an Array type if that field already exists within 
								 * the objectParameter that it exists in 
								 */
								var subInputParameter = {}, 
									dataFieldContainerActualNodeId = $(subContentElement[k]).data("data-actualNodeId"),
									dataFieldContainerActualNode = dataFieldContainerActualNodeId ? $("#" + dataFieldContainerActualNodeId)[0] : subContentElement[k],
									dataFieldContainerType = $.attr(subContentElement[k], "data-fieldType"),
									dataFieldContainer = $.attr(subContentElement[k], "data-fieldContainer");
								
								if(dataFieldContainerType === "Array") { 
									
									if(typeof(objectParameter[dataFieldContainer]) === "undefined") { 
										objectParameter[dataFieldContainer] = [];
									}
									
									var arrayContainer = objectParameter[dataFieldContainer];
									arrayContainer.push(subInputParameter);
									
								}else { 
									
									objectParameter[dataFieldContainer] = subInputParameter;
								}
								
								appendInputParameters(dataFieldContainerActualNode, dataFieldContainerActualNode, subInputParameter);
							}
							
							function retrieveValue(element) { 
								return $(element).val();
							};
						};
						
						inputParameterValues.push(inputParameter);
					});
					
					return inputParameterValues;
				},
				
				copyTemplate: function _copyTemplate(templateSelector) { 
					
					var accordionContainer = $("<div><h3><a href='#'></a></h3><div></div></div>");
					
					$("> div", accordionContainer).append($("> div", templateSelector).clone());
					
					this.append(accordionContainer);
					
					this.accordion("destroy").accordion({ 
			            header: "h3",
			            collapsible: true,
			            change: function(e, ui) {
			                $(this).accordion("resize");
			            } 
					});
				}
			}
	};
	
	return methods;
	
});