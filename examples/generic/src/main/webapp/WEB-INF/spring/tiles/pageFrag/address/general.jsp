<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />

<fieldset class="padDirectChildren5b">
    <legend><fmt:message key="ADDRESS_FIELDS" /></legend>
	<div class="propertyContainer">
		<div>
			<div><fmt:message key="NAME" /></div>
			<div><input type="text" data-fieldName="name"></input></div>
		</div>
		<div>
			<div><fmt:message key="STREET" /></div>
			<div><input type="text" data-fieldName="street"></input></div>
		</div>
		<div>
			<div><fmt:message key="STREET_2" /></div>
			<div><input type="text" data-fieldName="street2"></input></div>
		</div>
		<div>
			<div><fmt:message key="CITY" /></div>
			<div><input type="text" data-fieldName="city"></input></div>
		</div>
		<div>
			<div><fmt:message key="STATE" /></div>
			<div><input type="text" list="stateList" required autocomplete data-fieldName="state" />
                    <datalist id="stateList" class="hidden">
                        <tiles:insertAttribute name="statesOptions" />
                    </datalist></div>
		</div>
		<div>
			<div><fmt:message key="ZIP_CODE" /></div>
			<div><input type="text" pattern="[0-9]{5}" oninputchange="" data-fieldName="zipCode"></input></div>
		</div>
	</div>
</fieldset>