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

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
@Entity
@Table(name="ADDRESS")
public class Address {
    
    private User user;
    private AddressId addressId = new AddressId();
    
    @EmbeddedId
    @AttributeOverrides({
    		@AttributeOverride(name="name", column=@Column(name="fld_name")),
            @AttributeOverride(name="street", column=@Column(name="fld_street")),
            @AttributeOverride(name="street2", column=@Column(name="fld_street2")),
            @AttributeOverride(name="city", column=@Column(name="fld_city")),
            @AttributeOverride(name="state", column=@Column(name="fld_state")),
            @AttributeOverride(name="zipCode", column=@Column(name="fld_zipCode"))
    })
    public AddressId getAddressId() {
        return addressId;
    }
    void setAddressId(AddressId addressId) {
    	this.addressId = addressId;
    }
    
    @ManyToOne
    @JoinColumn(name="user_fk")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getName() { 
        return addressId.getName();
    }
    public void setName(String name) { 
        addressId.setName(name);
    }
    
    public String getStreet() {
        return addressId.getStreet();
    }
    public void setStreet(String street) {
        addressId.setStreet(street);
    }
    
    public String getStreet2() {
        return addressId.getStreet2();
    }
    public void setStreet2(String street2) {
        addressId.setStreet2(street2);
    }
    
    public String getCity() {
        return addressId.getCity();
    }
    public void setCity(String city) {
        addressId.setCity(city);
    }
    
    public String getState() {
        return addressId.getState();
    }
    public void setState(String state) {
        addressId.setState(state);
    }
    
    public Long getZipCode() {
        return addressId.getZipCode();
    }
    public void setZipCode(Long zipCode) {
        addressId.setZipCode(zipCode);
    }
    
    @Override
    public int hashCode() {
        return addressId.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof Address)) {
            return false;
        }
        
        Address addr = Address.class.cast( instance );
        return addr.getAddressId().equals(getAddressId());
    }
    @Override
    public String toString() {
        return addressId.toString();
    }
    
}

@Embeddable
class AddressId implements Serializable {
    
    private static final long serialVersionUID = -6552565350659998806L;
    
    private String name;
    private String street;
    private String street2;
    private String city;
    private String state;
    private Long zipCode;
    
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + name.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + street.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + street2.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + city.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + state.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + zipCode.hashCode();
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof AddressId)) {
            return false;
        }
        
        AddressId addrId = AddressId.class.cast( instance );
        return addrId.name.equals(name) && addrId.city.equals(city) && addrId.state.equals(state) && addrId.street.equals(street) && 
                    addrId.street2.equals(street2) && addrId.zipCode.equals(zipCode);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("AddressId{ name: ");
        content.append(name);
        content.append(", street: ");
        content.append(street);
        content.append(", street2: ");
        content.append(street2);
        content.append(", city: ");
        content.append(city);
        content.append(", state: ");
        content.append(state);
        content.append(", zipCode: ");
        content.append(zipCode);
        content.append(" }");
        
        return content.toString();
    }
    
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getStreet2() {
        return street2;
    }
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Long getZipCode() {
        return zipCode;
    }
    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }
    
}
