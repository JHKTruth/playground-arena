<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
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
        <title><tiles:getAsString name="title"/></title>
        
        <tiles:insertAttribute name="generalPageUrlDefinitions" />
        
        <spring:url value="/controller/mySQL/viewUserInfoView?userId=" var="userInfoView" />
        
        <spring:url value="/controller/js-{applicationVersion}/mySQLMain.js" var="mainJsUrl" scope="session">
            <spring:param name="applicationVersion" value="${applicationVersion}"/>
        </spring:url>
        
        <style type="text/css">
        nav {
            width: 15%;
        }
        
        section {
            width: 80%;
        }
        
        li button {
        	font-size: 0.8em !important;
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
                    <li><button id="mySQLCreateUserButton" class="inlineBlock"><fmt:message key="CREATE_USER" /></button>
                    </li>
                    <li id="mySQLCurrentUserIdsContainer"><div><input id="mySQLUserIdAutoComplete" /> <button id="mySQLUserIdSubmit" class="inlineBlock"><fmt:message key="SUBMIT_REQUEST" /></button></div>
                    </li>
                </ul>
            </nav>
            
            <section class="hProduct genericBorderRadius genericBoxShadow" itemscope itemtype="http://www.data-vocabulary.org/Product/">
                <details>
                    <summary class="panelHeader" itemprop="name"><fmt:message key="SCREENSHOTS" /></summary>
                    <article class="screenShotArticle">
                    
                        <div>
                            <figure>
                                <img data-img-summary="MySQL Example" itemprop="image" src="" alt="MySQLExample" />
                                <figcaption><fmt:message key="CERTAIN_TASK_EXAMPLE" /></figcaption>
                            </figure>
                        </div>
                    </article>
                </details>
            </section>
        </div>
        
        <div class="clear">
            <section class="genericBorderRadius genericBoxShadow">
                <details>
                    <summary class="panelHeader"><fmt:message key="TASK_CONTENT" /></summary>
                </details>
            </section>
        </div>
        
        <div id="mySQLCreateUserDialog" title="Create User">
        	<tiles:insertDefinition name="user.general" />
        </div>
        
        <script type="text/javascript" data-main="${mainJsUrl}" src="${requireJQueryJsUrl}"></script>
        <script type="text/javascript">
            require(["jquery", "jquery.jpa.main", "domReady-min!"], function($) {
                var userIdChosen,
                	userIds = [];

            	<c:forEach var="user" items="${requestScope.USER_LIST}">
            	userIds.push({label: "${user.firstName}" });
				</c:forEach>

				if(userIds.length > 0) {
	                $("#mySQLUserIdAutoComplete").autocomplete({
	                    source: userIds,
	                    select: function(e, ui) {
	                       userIdChosen = ui.item.value;
	                    }
	                });
				}else {
					$("#mySQLCurrentUserIdsContainer").addClass("hidden");
				}

                $("#mySQLCreateUserButton").button().click(function() {
                	var d = $("#mySQLCreateUserDialog");
                    if(!d.dialog("isOpen")) {
                        d.dialog("open");
                    }
                });
                
                $("#mySQLUserIdSubmit").button().click(function() {
                    if(userIdChosen) {
                        window.location.href = "${userInfoView}" + userIdChosen;
                    }
                });
                
                $(["#userAddressContainer", "#userCCContainer", "#userBAContainer"]).each(function(index, entry){
                    $(entry).jpa("createDialogWithCopyTemplating");
                });
                
                $("#mySQLCreateUserDialog").dialog({
                    autoOpen: false,
                    modal: true,
                    hide: true,
                    width: 700,
                    height: 500,
                    minWidth: 300,
                    minHeight: 300, 
                    buttons: {
                    	"<fmt:message key='SUBMIT' />"        :   function() {
                        					
				                    		var data = $("#mySQLCreateUserDialog").jpa("retrieveInputParameters");
                    						console.info("Data ", data[0]);
                    						
                            				$.ajax({
                                				type: "POST",
                                				url: "/jpa-generic-examples/controller/mySQL/createUser/",
                                				contentType: "application/json",
                                				data: JSON.stringify(data[0]), // $.toJSON(), $.evalJSON()
                                				dataType: "json",
                                				success: function() {
                                    					console.info("With Success ", arguments); 
                                    				 },
                                    			error: function() {
                                    					console.info("With Error ", arguments); 
                                        			 } 
                                				 });
                        				},
                        
                        "<fmt:message key='CLOSE' />"        :   function() {
                                            $("#mySQLCreateUserDialog").dialog("close");
                                        }
                    }
                });
                
            });
        </script>
    </body>
</html>