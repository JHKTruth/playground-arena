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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

/**
 * Following note regarding InheritanceType.SINGLE_TABLE which requires more annotations in 
 * comparison to InheritanceType.TABLE_PER_CLASS:
 * 1) Columns for properties declared by subclasses must be declared to be nullable.
 * 2) This strategy creates functional dependencies between nonkey columns, violating the third normal form
 * 3) One must declare @DiscriminatorColumn annotations to differentiate between one subclass to an another
 * 4) Though this strategy is the winner in terms of both performance and simplicity compared to 
 *    other three strategies, it sacrifices long-term stability, maintainability, and the integrity of data.
 * 
 * If you don't require polymorphic associations or queries, lean toward table per concrete class, if you never or 
 * rarely query for BillingDetails and you have no class that has an association to BillingDetails. An explicit 
 * UNION-based mapping should be preferred, because (optimized) polymorphic queries and associations will then be 
 * possible later. Implicit polymorphism is mostly useful for queries utilizing non-persistence-related interfaces.
 * 
 * @author Ji Hoon Kim
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractBillingDetails {
    
    protected Long billingId;
    protected User user;
    protected String number;
    protected Address billingAddress;
    
    @Id
    @GeneratedValue
    @Column(name="BILLING_ID")
    public Long getBillingId() {
        return billingId;
    }
    public void setBillingId(Long billingId) {
        this.billingId = billingId;
    }
    
    @NaturalId
    @Column(name="NUMBER")
    /** 
     * A natural key is a property or combination of properties that is unique and non-null. It is also immutable...
     * Hibernate will generate the necessary unique key and nullability constraints and, as a result, your mapping 
     * will be more self-documenting.
     * 
     * @ColumnTransformer(read="decrypt(_number)",
     *                      write="encrypt(?)")
     */
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    
    @OneToOne
    @JoinColumns({
    	@JoinColumn(name="ba_name", referencedColumnName="fld_name"),
        @JoinColumn(name="ba_street", referencedColumnName="fld_street"),
        @JoinColumn(name="ba_street2", referencedColumnName="fld_street2"),
        @JoinColumn(name="ba_city", referencedColumnName="fld_city"),
        @JoinColumn(name="ba_state", referencedColumnName="fld_state"),
        @JoinColumn(name="ba_zipCode", referencedColumnName="fld_zipCode")
    })
    public Address getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(Address billingAddress) { 
        this.billingAddress = billingAddress;
    }
    
    @Override
    public int hashCode() {
        return number.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof AbstractBillingDetails)) {
            return false;
        }
        AbstractBillingDetails bd = AbstractBillingDetails.class.cast( instance );
        return bd.number.equals(number);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("AbstractBillingDetails{ billingId: ");
        content.append(billingId);
        content.append(", user: ");
        content.append(user);
        content.append(", number: ");
        content.append(number);
        content.append(", billingAddress: ");
        content.append(billingAddress);
        content.append(" }");
        
        return content.toString();
    }
    
}
