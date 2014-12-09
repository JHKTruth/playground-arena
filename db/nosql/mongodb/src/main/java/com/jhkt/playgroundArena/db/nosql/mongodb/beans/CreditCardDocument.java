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
public final class CreditCardDocument extends AbstractBillingDetailsDocument {
    
    public enum CREDIT_CARD_TYPE {
        AMEX, DISCOVER, MASTERCARD, VISA;
    }
    
    private CREDIT_CARD_TYPE _type;
    private String _expMonth;
    private String _expYear;
    
    CreditCardDocument() {
        super();
    }
    
    @IDocumentKeyValue
    public CREDIT_CARD_TYPE getType() {
        return _type;
    }
    public void setType(CREDIT_CARD_TYPE type) {
        _type = type;
    }
    
    @IDocumentKeyValue
    public String getExpMonth() {
        return _expMonth;
    }
    public void setExpMonth(String expMonth) {
        _expMonth = expMonth;
    }
    
    @IDocumentKeyValue
    public String getExpYear() {
        return _expYear;
    }
    public void setExpYear(String expYear) {
        _expYear = expYear;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof CreditCardDocument)) {
            return false;
        }
        
        return super.equals(instance);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("CreditCardDocument{ type: ");
    	content.append(_type);
    	content.append(", expMonth: ");
    	content.append(_expMonth);
    	content.append(", expYear: ");
    	content.append(_expYear);
    	content.append(", ");
    	content.append(super.toString());
    	content.append(" }");
    	return content.toString();
    }
    
}
