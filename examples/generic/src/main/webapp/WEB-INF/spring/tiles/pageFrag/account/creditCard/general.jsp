<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />

<fieldset class="padDirectChildren5b">
    <legend><fmt:message key="CREDIT_CARD_FIELDS" /></legend>
	<div class="propertyContainer">
		<div>
			<div><fmt:message key="TYPE" /></div>
			<div><input type="text" list="ccType" required autocomplete data-fieldName="type" />
                    <datalist id="ccType" class="hidden">
                        <option label="AMEX" value="AMEX" />
                        <option label="DISCOVER" value="DISCOVER" />
                        <option label="MASTERCARD" value="MASTERCARD" />
                        <option label="VISA" value="VISA" />
                    </datalist></div>
		</div>
		<div>
			<div><fmt:message key="EXPIRATION_DATE" /></div>
			<div><input type="text" pattern="[0-9]{2}" data-fieldName="expMonth" />/<input type="text" pattern="[0-9]{4}" data-fieldName="expYear" /></div>
		</div>
	</div>
</fieldset>

<div id="ccAddressContainer" data-fieldContainer="billingAddress">
</div>

<div class="hidden" id="ccAddressTemplate">
	<div>
		<tiles:insertDefinition name="address.general" />
	</div>
</div>