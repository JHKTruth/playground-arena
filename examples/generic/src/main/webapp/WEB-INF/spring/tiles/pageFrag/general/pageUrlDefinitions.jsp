<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval expression="@applicationProps['application.version']"
	var="applicationVersion" scope="session" />
<spring:url value="/" var="root" scope="session" />

<spring:url
	value="/controller/js-{applicationVersion}/require-jquery.js"
	var="requireJQueryJsUrl" scope="session">
	<spring:param name="applicationVersion" value="${applicationVersion}" />
</spring:url>

<spring:url value="/controller/css-{applicationVersion}/all.css"
	var="allCssUrl" scope="session">
	<spring:param name="applicationVersion" value="${applicationVersion}" />
</spring:url>

<link href="${allCssUrl}" rel="stylesheet"></link>