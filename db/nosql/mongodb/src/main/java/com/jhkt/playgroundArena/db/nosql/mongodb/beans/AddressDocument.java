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
package com.jhkt.playgroundArena.db.nosql.mongodb.beans;

import com.jhkt.playgroundArena.db.nosql.mongodb.annotations.IDocumentKeyValue;
import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
public final class AddressDocument extends AbstractDocument {
   
    private String _street;
    private String _street2 = "";       //this way to alleviate the equals comparison [i.e. null case]
    
    private String _city;
    private String _state;
    private Integer _zipCode;
    
    public AddressDocument() {
        super();
    }
    
    @IDocumentKeyValue
    public String getStreet() {
        return _street;
    }
    public void setStreet(String street) {
        _street = street;
    }
    @IDocumentKeyValue
    public String getStreet2() {
        return _street2;
    }
    public void setStreet2(String street2) {
        _street2 = street2;
    }
    @IDocumentKeyValue
    public String getCity() {
        return _city;
    }
    public void setCity(String city) {
        _city = city;
    }
    @IDocumentKeyValue
    public String getState() {
        return _state;
    }
    public void setState(String state) {
        _state = state;
    }
    @IDocumentKeyValue
    public Integer getZipCode() {
        return _zipCode;
    }
    public void setZipCode(Integer zipCode) {
        _zipCode = zipCode;
    }
    
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof AddressDocument)) {
            return false;
        }
        
        AddressDocument ad = AddressDocument.class.cast( instance );
        return ad._street.equals(_street) && ad._street2.equals(_street2) && ad._city.equals(_city) && ad._state.equals(_state) && 
                ad._zipCode.equals(_zipCode);
    }
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _street.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _street2.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _city.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _state.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _zipCode.hashCode();
        
        return hashCodeVal;
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("AddressDocument{ street: ");
    	content.append(_street);
    	content.append(", street2: ");
    	content.append(_street2);
    	content.append(", city: ");
    	content.append(_city);
    	content.append(", state: ");
    	content.append(_state);
    	content.append(", zipCode: ");
    	content.append(_zipCode);
    	content.append(" }");
    	return content.toString();
    }
    
}
