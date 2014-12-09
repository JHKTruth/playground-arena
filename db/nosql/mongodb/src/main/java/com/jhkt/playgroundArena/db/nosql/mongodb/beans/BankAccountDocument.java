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
public final class BankAccountDocument extends AbstractBillingDetailsDocument {
    
    private String _bankName;
    private String _routingNumber;
    
    BankAccountDocument() {
        super();
    }
    
    @IDocumentKeyValue
    public String getBankName() {
        return _bankName;
    }
    public void setBankName(String bankName) {
        _bankName = bankName;
    }
    
    @IDocumentKeyValue
    public String getRoutingNumber() {
        return _routingNumber;
    }
    public void setRoutingNumber(String routingNumber) {
        _routingNumber = routingNumber;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof BankAccountDocument)) {
            return false;
        }
        
        return super.equals(instance);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("BankAccountDocument{ bankName: ");
    	content.append(_bankName);
    	content.append(", routingNumber: ");
    	content.append(_routingNumber);
    	content.append(", ");
    	content.append(super.toString());
    	content.append(" }");
    	return content.toString();
    }
    
}
