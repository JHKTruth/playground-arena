<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />

<div class="gwSlidesContent">
    <div class="gwSlidesExample">
        <div class="gwSlides">
            <div class="gwSlidesContainer">
                <div class="gwSlide">
                    <h1><fmt:message key="ARBOR_EXAMPLE" /></h1>
                    <p><fmt:message key="ARBOR_EXAMPLE_DESC" /></p>
                    <div>
                        <canvas id="arbor" width="730" height="460"></canvas>
                    </div>
                </div>
                
                <div class="gwSlide">
                    <h1><fmt:message key="B_VALIDATOR_EXAMPLE" /></h1>
                    <p><fmt:message key="B_VALIDATOR_EXAMPLE_DESC" /></p>
                    
                    <fieldset id="gwValidator" class="padDirectChildren5b">
                        <legend><fmt:message key="VARIOUS_HTML_ELEMENTS" /></legend>
                        <div>
                            <fmt:message key="EMAIL" />: <input type="text" data-bvalidator="required,email"/>
                        </div>
                        <div>
                            <fmt:message key="URL" />: <input type="text" data-bvalidator="url"/>
                        </div>
                    </fieldset>
                </div>
                
                <div class="gwSlide">
                    <h1><fmt:message key="REVEAL_EXAMPLE" /></h1>
                    <p><fmt:message key="REVEAL_EXAMPLE_DESC" /></p>
                    
                    <ul>
                        <li><a href="#" data-reveal-id="myModal"><fmt:message key="FADE_AND_POP" /></a></li>
                        <li><a href="#" data-reveal-id="myModal" data-animation="fade"><fmt:message key="FADE" /></a></li>
                        <li><a href="#" data-reveal-id="myModal" data-animation="none"><fmt:message key="NONE" /></a></li>
                    </ul>
                    
                    <div id="myModal" class="reveal-modal">
                        <h1><fmt:message key="REVEAL_MODAL_GOODNESS" /></h1>
                        <p><fmt:message key="REVEAL_MODAL_GOODNESS_DESC" /></p>
                        <a class="close-reveal-modal">&#215;</a>
                    </div>            
                </div>
                
                <div class="gwSlide">
                    <h1><fmt:message key="GALLERIA_EXAMPLE" /></h1>
                    <p><fmt:message key="GALLERIA_EXAMPLE_DESC" /></p>
                    
                    <style>
                        /* This rule is read by Galleria to define the gallery height: */
                        #gwGalleria{height:320px}
                    </style>
                    
                    <div id="gwGalleria">
                        <a href="${apacheImageUrl}">
                            <img title="Apache"
                                 alt="Apache"
                                 src="${apacheImageUrl}">
                        </a>
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

<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/arbor.js" var="arborJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/arbor-tween.js" var="arborTweenJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/galleria-1.2.6.min.js" var="galleriaJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/jquery.bvalidator.js" var="bvalidatorJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/jquery.bvalidator-yc.js" var="bvalidatorycJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/jquery.masonry.min.js" var="masonryJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/jquery.reveal.js" var="revealJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/jquery.splatter.js" var="splatterJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>
<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/plugins/slides.min.jquery.js" var="slidesMinJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>

<spring:url value="/controller/themes-{applicationVersion}/plugins/galleria/classic/galleria.classic.min.js" var="galleriaClassicMinJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>

<spring:url value="/controller/js-{applicationVersion}/thirdParty/jQuery/jquery.plugins.examples.js" var="jqueryPluginExamplesJsUrl" scope="session">
    <spring:param name="applicationVersion" value="${applicationVersion}"/>
</spring:url>

<script type="text/javascript" src="${arborJsUrl}"></script>
<script type="text/javascript" src="${arborTweenJsUrl}"></script>
<script type="text/javascript" src="${galleriaJsUrl}"></script>
<script type="text/javascript" src="${bvalidatorJsUrl}"></script>
<script type="text/javascript" src="${bvalidatorycJsUrl}"></script>
<script type="text/javascript" src="${masonryJsUrl}"></script>
<script type="text/javascript" src="${revealJsUrl}"></script>
<script type="text/javascript" src="${splatterJsUrl}"></script>
<script type="text/javascript" src="${slidesMinJsUrl}"></script>

<script>
    Galleria.loadTheme("${galleriaClassicMinJsUrl}");
</script>

<script type="text/javascript" src="${jqueryPluginExamplesJsUrl}"></script>