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

/**
 * @author Ji Hoon Kim
 */
public abstract class AbstractBillingDetailsDocument extends AbstractDocument {
    
    protected String _number;
    protected AddressDocument _billingAddress;
    
    AbstractBillingDetailsDocument() {
        super();
    }
    
    @IDocumentKeyValue
    public String getNumber() {
        return _number;
    }
    public void setNumber(String number) {
        _number = number;
    }
    
    @IDocumentKeyValue
    public AddressDocument getBillingAddress() {
        return _billingAddress;
    }
    public void setBillingAddress(AddressDocument billingAddress) {
        _billingAddress = billingAddress;
    }
    
    @Override
    public int hashCode() {
        return _number.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof AbstractBillingDetailsDocument)) {
            return false;
        }
        
        AbstractBillingDetailsDocument abddInstance = AbstractBillingDetailsDocument.class.cast( instance );
        return abddInstance._number.equals(_number);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("AbstractBillingDetailsDocument{ number: ");
    	content.append(_number);
    	content.append(", ");
    	content.append(_billingAddress.toString());
    	content.append(" }");
    	return content.toString();
    }
    
}
