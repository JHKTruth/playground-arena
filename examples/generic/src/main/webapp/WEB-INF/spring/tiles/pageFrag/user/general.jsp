<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<fmt:setBundle basename="com.googlecode.jihoonPlaygroundArena.examples.generic.nls.General" />

<fieldset class="padDirectChildren5b">
    <legend><fmt:message key="USER_FIELDS" /></legend>
	<div class="propertyContainer">
		<div>
			<div><fmt:message key="USER_NAME" /></div>
			<div><input type="text" data-fieldName="userName"></input></div>
		</div>
		<div>
			<div><fmt:message key="PASSWORD" /></div>
			<div><input type="password" data-fieldName="password"></input></div>
		</div>
		<div>
			<div><fmt:message key="FIRST_NAME" /></div>
			<div><input type="text" data-fieldName="firstName"></input></div>
		</div>
		<div>
			<div><fmt:message key="LAST_NAME" /></div>
			<div><input type="text" data-fieldName="lastName"></input></div>
		</div>
		<div>
			<div><fmt:message key="EMAIL" /></div>
			<div><input type="email" data-fieldName="email"></input></div>
		</div>
		<div>
			<div><fmt:message key="NICKNAMES" /></div>
			<div><input type="text" data-fieldName="nickNames" data-fieldType="Array"></input></div>
		</div>
	</div>
</fieldset>

<div class="columns" style="padding: 10px;">
    <div id="userAddressContainer" data-fieldContainer="shippingAddresses" data-fieldType="Array" 
        data-templateId='#userAddressTemplate' data-dialogTitle='<fmt:message key="ADD_ADDRESS" />'>
    </div>
    
    <div class="columnsBreakBefore" id="userCCContainer" data-fieldContainer="creditCards" data-fieldType="Array" 
        data-templateId='#userCCTemplate' data-dialogTitle='<fmt:message key="ADD_CC" />'>
    </div>
    
	<div class="columnsBreakBefore" id="userBAContainer" data-fieldContainer="bankAccounts" data-fieldType="Array" 
	        data-templateId='#userBATemplate' data-dialogTitle='<fmt:message key="ADD_BA" />'>
	</div>
</div>

<div class="hidden" id="userAddressTemplate">
	<div>
		<tiles:insertDefinition name="address.general" />
	</div>
</div>

<div class="hidden" id="userCCTemplate">
    <div>
        <tiles:insertDefinition name="creditCard.general" />
    </div>
</div>

<div class="hidden" id="userBATemplate">
    <div>
        <tiles:insertDefinition name="bankAccount.general" />
    </div>
</div>
