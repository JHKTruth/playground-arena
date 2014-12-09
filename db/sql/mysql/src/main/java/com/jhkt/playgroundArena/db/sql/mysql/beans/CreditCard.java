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
package com.jhkt.playgroundArena.db.sql.mysql.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Ji Hoon Kim
 */
@Entity
@Table(name="CREDIT_CARD")
public class CreditCard extends AbstractBillingDetails {
    
    public enum CREDIT_CARD_TYPE {
        AMEX, DISCOVER, MASTERCARD, VISA
    }
    
    private CREDIT_CARD_TYPE type;
    private String expMonth;
    private String expYear;
    
    @ManyToOne
    @JoinTable(name="USER_CC_DETAILS",
            joinColumns={@JoinColumn(name="BILLING_ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID")}
    )
    /**
     * You don't have to (must not) define any physical mapping in the mappedBy side.
     * 
     * Changes made only to the inverse end of the association are not persisted. This means that 
     * Hibernate has two representations in memory for every bidirectional association: one link from A 
     * to B and another link from B to A. 
     */
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    @Column(name="TYPE",
            nullable=false
    )
    public CREDIT_CARD_TYPE getType() {
        return type;
    }
    public void setType(CREDIT_CARD_TYPE type) {
        this.type = type;
    }
    
    @Column(name="EXP_MONTH",
            nullable=false
    )
    public String getExpMonth() {
        return expMonth;
    }
    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }
    
    @Column(name="EXP_YEAR",
            nullable=false
    )
    public String getExpYear() {
        return expYear;
    }
    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }
    
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof CreditCard)) {
            return false;
        }
        return super.equals(instance);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("CreditCard{ type: ");
        content.append(type);
        content.append(", expMonth: ");
        content.append(expMonth);
        content.append(", expYear: ");
        content.append(expYear);
        content.append(", ");
        content.append(super.toString());
        content.append(" }");
        
        return content.toString();
    }
    
}
