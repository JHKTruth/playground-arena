<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />

<fieldset class="padDirectChildren5b">
    <legend><fmt:message key="BANK_ACCOUNT_FIELDS" /></legend>
	<div class="propertyContainer">
		<div>
			<div><fmt:message key="BANK_NAME" /></div>
			<div><input type="text" data-fieldName="bankName"></input></div>
		</div>
		<div>
			<div><fmt:message key="ROUTING_NUMBER" /></div>
			<div><input type="text" data-fieldName="routingNumber" pattern="[0-9]{9}"></input></div>
		</div>
	</div>
</fieldset>

<div id="baAddressContainer" data-fieldContainer="billingAddress">
</div>

<div class="hidden" id="baAddressTemplate">
	<div>
		<tiles:insertDefinition name="address.general" />
	</div>
</div>