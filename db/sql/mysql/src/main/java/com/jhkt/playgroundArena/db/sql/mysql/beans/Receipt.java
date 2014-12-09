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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
@Entity
@Table(name="RECEIPT")
@Immutable
public class Receipt {
    
    private int HASH_CODE = -1;
    private Long id;
    private User user;
    private Set<Item> boughtItems = new HashSet<Item>();
    private String deliminatedItemList;
    private Double total;
    private Date createdDate;
    
    public Receipt(User user, Set<Item> boughtItems) {
        super();
        
        this.user = user;
        this.boughtItems = boughtItems;
        
        /*
         * Just to support MapKey
         */
        StringBuilder builder = new StringBuilder();
        for(Item item : this.boughtItems) {
            builder.append(item.getName());
        }
        deliminatedItemList = builder.toString();
    }
    
    @Id
    @GeneratedValue
    @Column(name="RECEIPT_ID")
    public Long getId() {
        return id;
    }
    void setId(Long id) {
    	this.id = id;
    }
    
    @ManyToOne
    @JoinColumn(name="user_fk")
    public User getUser() {
        return user;
    }
    void setUser(User user) {
    	this.user = user;
    }
    
    @ManyToMany(cascade={CascadeType.PERSIST, 
                            CascadeType.MERGE
                        }
    )
    @JoinTable(name="ITEM_RECEIPT",
            joinColumns={@JoinColumn(name="RECEIPT_ID")},
            inverseJoinColumns={@JoinColumn(name="ITEM_ID")}
    )
    /**
     * You also have to describe the association table and the join conditions using the @JoinTable annotation. If the
     * association is bidirectional, one side has to be the owner and one side has to be the inverse end 
     * (ie. it will be ignored when updating the relationship values in the association table)
     */
    public Set<Item> getBoughtItems() {
        return boughtItems;
    }
    void setBoughtItems(Set<Item> boughtItems) {
    	this.boughtItems = boughtItems;
    }
    
    @Column(name="DELIMINATED_ITEM_LIST",
            nullable=false
    )
    public String getDeliminatedItemList() {
        return deliminatedItemList;
    }
    void setDeliminatedItemList(String deliminatedItemList) {
    	this.deliminatedItemList = deliminatedItemList;
    }
    
    @Column(name="CREATED_DATE",
            updatable=false,
            insertable=false
    )
    @org.hibernate.annotations.Generated(
            org.hibernate.annotations.GenerationTime.INSERT
    )
    public Date getCreatedDate() {
        return createdDate;
    }
    void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }
    
    @Column(name="TOTAL",
            nullable=false
    )
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    
    @Override
    public int hashCode() {
        if(HASH_CODE == -1) {
            HASH_CODE = JPAConstants.HASH_CODE_INIT_VALUE;
            HASH_CODE = JPAConstants.HASH_CODE_MULTIPLY_VALUE * HASH_CODE + boughtItems.hashCode();
            HASH_CODE = JPAConstants.HASH_CODE_MULTIPLY_VALUE * HASH_CODE + user.getUserName().hashCode();
        }
        return HASH_CODE;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof Receipt)) {
            return false;
        }
        
        Receipt receipt = Receipt.class.cast( instance );
        return receipt.boughtItems.equals(boughtItems) && receipt.user.equals(user) && 
                receipt.createdDate.equals(createdDate);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("Receipt{ id: ");
        content.append(id);
        content.append(", user: ");
        content.append(user);
        content.append(", boughtItems: ");
        content.append(boughtItems);
        content.append(", deliminatedItemList: ");
        content.append(deliminatedItemList);
        content.append(", total: ");
        content.append(total);
        content.append(", createdDate: ");
        content.append(createdDate);
        content.append(" }");
        
        return content.toString();
    }
    
}
