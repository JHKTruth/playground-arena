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
@Table(name="BANK_ACCOUNT")
public class BankAccount extends AbstractBillingDetails {
    
    private String bankName;
    private String routingNumber;
    
    @ManyToOne
    @JoinTable(name="USER_BA_DETAILS",
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
    
    @Column(name="BANK_NAME",
            nullable=false
    )
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    @Column(name="ROUTING_NUMBER",
            nullable=false
    )
    public String getRoutingNumber() {
        return routingNumber;
    }
    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }
    
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof BankAccount)) {
            return false;
        }
        return super.equals(instance);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("BankAccount{ bankName: ");
        content.append(bankName);
        content.append(", routingNumber: ");
        content.append(routingNumber);
        content.append(", ");
        content.append(super.toString());
        content.append(" }");
        
        return content.toString();
    }
    
}
