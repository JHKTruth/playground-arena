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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Ji Hoon Kim
 */
@Entity
@Table(name="CATEGORY")
public class Category {
    
    private Long id;
    private String name;
    private Set<Item> items = new HashSet<Item>();
    
    @Id
    @GeneratedValue
    @Column(name="CATEGORY_ID")
    public Long getId() {
        return id;
    }
    void setId(Long id) {
    	this.id = id;
    }
    
    @Column(name="NAME")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @ManyToMany
    @JoinTable(name="CATEGORY_ITEM",
                joinColumns={@JoinColumn(name="CATEGORY_ID")},
                inverseJoinColumns={@JoinColumn(name="ITEM_ID")}
    )
    public Set<Item> getItems() {
        return items;
    }
    void setItems(Set<Item> items) {
    	this.items = items;
    }
    public void addItem(Item item) {
        items.add(item);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof Category)) {
            return false;
        }
        
        Category category = Category.class.cast( instance );
        return category.name.equals(name);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("Category{ id: ");
        content.append(id);
        content.append(", name: ");
        content.append(name);
        content.append(", items: ");
        content.append(items);
        content.append(" }");
        
        return content.toString();
    }
    
}
