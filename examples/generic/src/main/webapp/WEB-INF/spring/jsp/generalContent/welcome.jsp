<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />
<!DOCTYPE html>
<html lang=en>
<!--
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
//-->
    <head>
        <meta charset="utf-8">
        <spring:eval expression="@applicationProps['application.version']" var="applicationVersion" scope="session" />
        <spring:eval expression="@applicationProps['jQuery.main']" var="jQueryMain" scope="session" />
        <spring:eval expression="@applicationProps['jQuery.ui']" var="jQueryUi" scope="session" />
        <spring:eval expression="@applicationProps['jQuery.ui.i18n']" var="jQueryUiI18n" scope="session" />
        <spring:url value="/" var="root" scope="session" />
        
        <spring:url value="/controller/css-{applicationVersion}/main.css" var="mainCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/html5css3specific.css" var="html5css3specificCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/bvalidator.css" var="bvalidatorCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/reveal.css" var="revealCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/sliderGlobal.css" var="sliderGlobalCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <spring:url value="/controller/themes-{applicationVersion}/redmond/jquery-ui-1.8.17.custom.css" var="jQueryUIThemesCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/themes-{applicationVersion}/plugins/bValidator/bvalidator.theme.gray2.css" var="bValidatorThemesCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/themes-{applicationVersion}/plugins/galleria/classic/galleria.classic.css" var="galleriaThemesCssUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <spring:url value="/controller/images-{applicationVersion}/apache.png" var="apacheImageUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/images/slider/arrow-prev.png" var="arrowPrevImageUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/images/slider/arrow-next.png" var="arrowNextImageUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/css-{applicationVersion}/thirdParty/jQuery/plugins/images/slider/example-frame.png" var="exampleFrameImageUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/{jQueryMain}" var="jQueryMainJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
            <spring:param name="jQueryMain" value="${jQueryMain}"/>
        </spring:url>
        <spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/{jQueryUi}" var="jQueryUiJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
            <spring:param name="jQueryUi" value="${jQueryUi}"/>
        </spring:url>
        <spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/{jQueryUiI18n}" var="jQueryUiI18nJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
            <spring:param name="jQueryUiI18n" value="${jQueryUiI18n}"/>
        </spring:url>
        <spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/jquery.tpl_layout1.1.6.js" var="jQueryTPLJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        <spring:url value="/controller/js-{applicationVersion}/genericScript.js" var="genericScriptJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <link href="${mainCssUrl}" rel="stylesheet"></link>
        <link href="${html5css3specificCssUrl}" rel="stylesheet"></link>
        <link href="${bvalidatorCssUrl}" rel="stylesheet"></link>
        <link href="${revealCssUrl}" rel="stylesheet"></link>
        <link href="${sliderGlobalCssUrl}" rel="stylesheet"></link>
        <link href="${jQueryUIThemesCssUrl}" rel="stylesheet"></link>
        <link href="${bValidatorThemesCssUrl}" rel="stylesheet"></link>
        <link href="${galleriaThemesCssUrl}" rel="stylesheet"></link>
        <style type="text/css">
        #gwSliderTip {
            display: inline;
            font: bold 11px Verdana;
            padding: 5px 0;
            position: absolute;
            width: 50px;
            text-align: center;
        }
        
        #gwSortables, #gwSortables2 {
            width: 300px;
        }
        #gwSortables span, #gwSortables2 span {
            float: right;
            margin: 2px 2px 0 0;
        }
        #gwSortables div, #gwSortables2 div {
            margin-bottom: 8px;
            padding: 2px 0 2px 4px;
        }
        
        .resize-ghost-helper {
            background-color: #FFFF99;
        }
        
        .status-message {
            background-color: white;
            border: 1px solid #aaa;
            color: #D2691E;
            font-size: 11px;
            position: absolute;
            right: 10px
        }
        </style>
    </head>
    <body>
        <div class="clear">
            <nav class="clear links genericBorderRadius genericBoxShadow">
                <div class="linksHeader" id="toTransform"><fmt:message key="PAGE_LINKS" /></div>
                <ul>
                    <li><a data-example-link href="${root}index.html"><fmt:message key="MAIN_INDEX" /></a>
                    </li>
                </ul>
            </nav>
            
            <section id="gwDraggable" class="hProduct genericBorderRadius genericBoxShadow floatR" itemscope itemtype="http://www.data-vocabulary.org/Product/">
                <details>
                    <summary id="gwDraggableHandle" class="panelHeader" itemprop="name" style="cursor:move;"><fmt:message key="GENERAL_OVERVIEW" /></summary>
                    <article class="pad10">
                        <div id="gwTab">
                            <ul>
                                <li><a href="#description"><fmt:message key="DESCRIPTION" /></a></li>
                                <li><a href="#jQueryExamples"><fmt:message key="JQUERY_EXAMPLES" /></a></li>
                                <li><a href="#etcetera"><fmt:message key="ETCETERA" /></a></li>
                            </ul>
                            <div id="description">
                                <div id="gwAccordion">
                                    <h2>
                                        <a href="#"><fmt:message key="BASIC_JQUERY_EXAMPLE_DESC" /></a>
                                    </h2>
                                    <div>
                                        
                                        <div class="gwSlidesContent">
                                            <div class="gwSlidesExample">
                                                <div class="gwSlides">
                                                    <div class="gwSlidesContainer">
                                                        <div class="gwSlide">
                                                            <h1><fmt:message key="DATE_PICKER_WITH_LOCALE_SELECTION" /></h1>
                                                            <p><fmt:message key="DATE_PICKER_WITH_LOCALE_SELECTION_DESC" /></p>
                                                            <div class="pad10b">
                                                                <label><fmt:message key="LOCALE_SELECTION" />:</label>
                                                                <input id="gwAutoComplete" />
                                                            </div>
                                                            <div>
                                                                <label for="gwDatePicker"><fmt:message key="DATE" />:</label>
                                                                <input id="gwDatePicker"/>
                                                                <input id="gwDatePickerInTimeStamp" />
                                                            </div>
                                                            <p><a href="#3" class="link"><fmt:message key="CHECK_OUT_THE_THIRD_SLIDE" /> &rsaquo;</a></p>
                                                        </div>
                                                        
                                                        <div class="gwSlide padDirectChildren5b">
                                                            <h1><fmt:message key="SAMPLE_ANIMATIONS" /></h1>
                                                            <div>
                                                                <fmt:message key="CLICK_TO_EXPLODE" />: 
                                                                <div>
                                                                    <img id="gwImageBomb" src="${apacheImageUrl}"></img>
                                                                </div>
                                                            </div>
                                                            <div>
                                                                <fmt:message key="CLICK_TO_PUFF" />: 
                                                                <div>
                                                                    <img id="gwImagePuff" src="${apacheImageUrl}"></img>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="gwSlide">
                                                            <h1><fmt:message key="SLIDER_WITHIN_SLIDES" /></h1>
                                                            <div id="gwSlider" style="margin: 30px auto 0;"></div>
                                                        </div>
                                                        
                                                        <div class="gwSlide padDirectChildren5b">
                                                            <h1></h1>
                                                            <p><fmt:message key="EXAMPLES_OF_JQUERY_STANDARD_UI_WIDGETS" /></p>
                                                            <div>
                                                                <button id="gwSelectAll"><fmt:message key="SELECT_ALL" /></button>
                                                                <button id="gwDeSelectAll"><fmt:message key="DESELECT_ALL" /></button>
                                                            </div>
                                                            <div id="gwButtonSet">
                                                                <fmt:message key="BUTTON_SET" />:
                                                                <label for="firstBS"><fmt:message key="FIRST" /></label>
                                                                <input id="firstBS" type="checkbox" />
                                                                <label for="secondBS"><fmt:message key="SECOND" /></label>
                                                                <input id="secondBS" type="checkbox" />
                                                            </div>
                                                            
                                                            <div>
                                                                <button id="gwButton"><fmt:message key="OPEN_DIALOG" /></button>
                                                            </div>
                                                        </div>
                                                        
                                                        <div class="gwSlide">
                                                            <h1><fmt:message key="EXAMPLES_OF_SORTABLES" /></h1>
                                                            <p class="pad10b"><fmt:message key="EXAMPLES_OF_SORTABLES_DESC" /></p>
                                                            <div class="clear">
                                                                <div class="floatL">
                                                                    <div id="gwSortables" class="ui-widget clear">
                                                                        <!-- later play around with the css helper tool that allows content from other class to be applied to an another class -->
                                                                        <div class="ui-widget-header ui-corner-all unsortable"><fmt:message key="UNSORTABLE" />
                                                                        </div>
                                                                        <div class="ui-widget-header ui-corner-all"><fmt:message key="FIRST" />
                                                                            <span class="ui-icon ui-icon-triangle-2-n-s"></span>
                                                                        </div>
                                                                        <div class="ui-widget-header ui-corner-all"><fmt:message key="SECOND" />
                                                                            <span class="ui-icon ui-icon-triangle-2-n-s"></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="floatL">
                                                                    <div id="gwSortables2" class="ui-widget clear">
                                                                        <div class="ui-widget-header ui-corner-all">1
                                                                            <span class="ui-icon ui-icon-triangle-2-n-s"></span>
                                                                        </div>
                                                                        <div class="ui-widget-header ui-corner-all">2
                                                                            <span class="ui-icon ui-icon-triangle-2-n-s"></span>
                                                                        </div>
                                                                        <div class="ui-widget-header ui-corner-all">3
                                                                            <span class="ui-icon ui-icon-triangle-2-n-s"></span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                                                                                            
                                                        </div>
                                                    </div>
                                                    <a href="#" class="gwSlidesPrev"><img src="${arrowPrevImageUrl}" width="24" height="43" alt="Arrow Prev"></a>
                                                    <a href="#" class="gwSlidesNext"><img src="${arrowNextImageUrl}" width="24" height="43" alt="Arrow Next"></a>
                                                </div>
                                                <img src="${exampleFrameImageUrl}" width="739" height="341" alt="Example Frame" class="gwSlidesFrame">
                                            </div>
                                            
                                            <div class="gwSlidesFooter">
                                            	<fmt:message key="SLIDES_REFERENCE" />
                                            </div>
                                            
                                        </div>
                                        
                                    </div>
                                    <h2>
                                        <a href="#"><fmt:message key="INFO" /></a>
                                    </h2>
                                    <div>
                                    	<fmt:message key="GENERAL_PAGE_DESC" />
                                    </div>
                                </div>
                            </div>
                            
                            
                            <div id="jQueryExamples">
                                <script type="text/javascript" src="${jQueryMainJsUrl}"></script>
                                <script type="text/javascript" src="${jQueryUiJsUrl}"></script>
                                <script type="text/javascript" src="${jQueryUiI18nJsUrl}"></script>
                                <script type="text/javascript" src="${jQueryTPLJsUrl}"></script>
                                
                                <c:import url="jQueryDemo.jsp" />
                            </div>
                            <div id="etcetera">
                                <c:import url="jQueryPluginExamples.jsp" />
                            </div>
                            
                        </div>
                    </article>
                </details>
            </section>
            
            <section class="genericBorderRadius genericBoxShadow floatR">
                <details>
                    <summary class="panelHeader" itemprop="name"><fmt:message key="DROP_SNAPPER_LOCATION" /></summary>
                    <article id="gwDropContainer" class="pad10">
                        <div id="gwDropSnapper" class="ui-widget-content ui-corner-bottom textCenter" style="height: 100px;">
                            <fmt:message key="DROP_SECTION" />
                        </div>
                    </article>
                </details>
            </section>
        </div>
        
        <div id="gwDialog" title="General Dialog">
        	<fmt:message key="RANDOM_MESSAGE" />
        </div>
    </body>
    
    <script type="text/javascript" src="${genericScriptJsUrl}"></script>
    <script type="text/javascript">
        (function($){
            
            /*
             * Usually, but not always, callback functions used with the bind() method are executed after the event has been 
             * fired, while callbacks specified using configuration options are executed directly before the event is fired.
             * 
             */
            
            $("#gwTab").tabs({
                /*
                 * The closing effect of the currently open content panel is contained within an object 
                 * in the first item of the array, and the opening animation of the new tab is the second.
                 */
                fx: [
                    {
                        opacity: "toggle",
                        duration: "slow"
                    }, 
                    null
                ],
                collapsible: true,
                select: function(e, tab) {
                    $("<div></div>", {
                        text: "Selected tab at index " + tab.index,
                        "class": "status-message pad5 ui-corner-all"
                    }).appendTo(".ui-tabs-nav", "#gwTab").fadeOut(5000, function() {
                                                                                            $(this).remove();
                                                                                        })
                }
            }
            );
            
            $("#gwAccordion").accordion({
                animated: "bounceslide",
                collapsible: true,
                change: function(e, ui) {
                    $("#gwAccordion").accordion("resize");
                }
            });
            
            $("#gwDialog").dialog({
                autoOpen: false,
                modal: true,
                hide: true,
                width: 400,
                height: 400,
                minWidth: 300,
                minHeight: 300,
                maxWidth: 700,
                maxHeight: 700, 
                buttons: {
                    "Close"        :   function() {
                                        $("#gwDialog").dialog("close");
                                    }
                }
            });
            
            $("#gwButton").button().click(function() {
                    var d = $("#gwDialog");
                    if(!d.dialog("isOpen")) {
                        d.dialog("open");
                    }
                    
                });
            
            $("#gwSlider").slider({
                min: 300,
                max: 600,
                range: true,
                values: [400, 500],
                animate: true,
                start: function() {
                    $("#gwSliderTip").fadeOut(function() {
                        $(this).remove();
                    });
                },
                change: function(e, ui) {
                    $("<div></div>", {
                        id: "gwSliderTip",
                        "class": "ui-widget-header ui-corner-all",
                        text: ui.value,
                        css: {
                            top: -40,
                            left: e.pageX
                        }
                    }).appendTo("#gwSlider");
                }
            });
            
            /*
             * Note that dates returned programmatically through the getDate method are in the 
             * default GMT date-and-time standard. In order to change the format of the date returned 
             * by the API, the $.datepicker.formatDate() utility method should be used.
             */
            $("#gwDatePicker").datepicker({
                appendText: "&nbsp;&nbsp;MM/DD/YYYY",
                changeMonth: true,
                changeYear: true,
                dateFormat: $.datepicker.W3C,
                altField: "#gwDatePickerInTimeStamp",
                altFormat: $.datepicker.TIMESTAMP,
                //dateFormat: "Selecte'd' mm/dd/yy", need to escape d or any other character that is part of the format
                showOtherMonths: true,
                showButtonPanel: true,
                beforeShow: function() {
                    if(chosenLocale){
                        $.datepicker.setDefaults($.datepicker.regional[chosenLocale]);
                    }
                    //$.datepicker.setDefaults($.datepicker.regional[$(":selected", $("#locale")).attr("value")]);
                },
                beforeShowDay: $.datepicker.noWeekends
            });
            
            //To set it to default
            $.datepicker.setDefaults($.datepicker.regional['']);
            
            $("#gwSelectAll").button().click(function() {
                $("#gwButtonSet").find("input").attr("checked", true).button("refresh");
            });
            $("#gwDeSelectAll").button().click(function() {
                $("#gwButtonSet").find("input").attr("checked", false).button("refresh");
            });
            
            $("#gwButtonSet").buttonset();
            
            var chosenLocale = null;
            $("#gwAutoComplete").autocomplete({
                source: [
                         {locale: "en-GB", label: "English"}, {locale: "ar", label: "Arabic"}, {locale: "ar-DZ", label: "Algerian"}, {locale: "az", label: "Azerbaijani"},
                         {locale: "bg", label: "Bulgarian"}, {locale: "bs", label: "Bosnian"}, {locale: "ca", label: "Catalan"}, {locale: "cs", label: "Czech"},
                         {locale: "da", label: "Danish"}, {locale: "de", label: "German"}, {locale: "el", label: "Greek"}, {locale: "en-AU", label: "English/Australia"},
                         {locale: "en-NZ", label: "English/New Zealand"}, {locale: "en-US", label: "English/United States"}, {locale: "eo", label: "Esperanto"},
                         {locale: "es", label: "Spanish"}, {locale: "et", label: "Estonian"}, {locale: "eu", label: "Euskarako"}, {locale: "fa", label: "Farsi"},
                         {locale: "fi", label: "Finnish"}, {locale: "fo", label: "Faroese"}, {locale: "fr", label: "French"}, {locale: "fr-CH", label: "Swiss-French"},
                         {locale: "gl", label: "Galician"}, {locale: "he", label: "Hebrew"}, {locale: "hr", label: "Croatian"}, {locale: "hu", label: "Hungarian"},
                         {locale: "hy", label: "Armenian"}, {locale: "id", label: "Indonesian"}, {locale: "is", label: "Icelandic"}, {locale: "it", label: "Italian"},
                         {locale: "ja", label: "Japanese"}, {locale: "ko", label: "Korean"}, {locale: "kz", label: "Kazakh"}, {locale: "lt", label: "Lithuanian"},
                         {locale: "lv", label: "Latvian"}, {locale: "ml", label: "Malayalam"}, {locale: "ms", label: "Malaysian"}, {locale: "nl", label: "Dutch"},
                         {locale: "no", label: "Norwegian"}, {locale: "pl", label: "Polish"}, {locale: "pl-BR", label: "Brazillian"}, {locale: "pt", label: "Portuguese"},
                         {locale: "rm", label: "Romansh"}, {locale: "ro", label: "Romaniam"}, {locale: "ru", label: "Russian"}, {locale: "sk", label: "Slovakian"},
                         {locale: "sl", label: "Slovenian"}, {locale: "sq", label: "Albanian"}, {locale: "sr-SR", label: "Serbian"}, {locale: "sw", label: "Swedish"},
                         {locale: "ta", label: "Tamil"}, {locale: "th", label: "Thai"}, {locale: "tr", label: "Turkish"}, {locale: "uk", label: "Ukrainian"},
                         {locale: "vi", label: "Vietnamese"}, {locale: "zh-CN", label: "Chinese"}, {locale: "zh-HK", label: "Chinese"}, {locale: "zh-TW", label: "Taiwanese"}
                         ],
                select: function(e, ui) {
                    chosenLocale = ui.item.locale;
                }
            });

            $("#gwDraggable").draggable({
                axis: "y",
                cursor: "move",
                handle: "#gwDraggableHandle",
                snap: "#gwDropSnapper",
                snapMode: "inner",
                snapTolerance: 50
            });

            $("#gwDropSnapper").resizable({
                animate: true,
                containment: "#gwDropContainer",
                helper: "resize-ghost-helper",
                minHeight: 100,
                minWidth: 100,
                ghost: true
            });

            $("#gwSortables, #gwSortables2").sortable({
                connectWith: ["#gwSortables", "#gwSortables2"],
                cursor: "ns-resize",
                handle: "span",
                items: ">:not(.unsortable)",
                opacity: 0.5
            });

            $("#gwTab").tabs().sortable({
                axis: "x",
                items: "li"
            });

            $("#gwImageBomb").click(function() {
                $(this).hide("explode").show("fold");
            });

            $("#gwImagePuff").click(function() {
                $(this).hide("puff").show("pulsate");
            });
            
        })(jQuery);
    </script>
</html>