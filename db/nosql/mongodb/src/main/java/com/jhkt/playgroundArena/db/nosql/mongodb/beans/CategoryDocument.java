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

import java.util.HashSet;
import java.util.Set;

import com.jhkt.playgroundArena.db.nosql.mongodb.annotations.IDocumentKeyValue;

/**
 * @author Ji Hoon Kim
 */
public final class CategoryDocument extends AbstractDocument {
    
    private String _name;
    private Set<String> _itemIds;
    
    CategoryDocument() {
        super();
        
        _itemIds = new HashSet<String>();
    }
    
    @IDocumentKeyValue
    public Set<String> getItemIds() {
        return _itemIds;
    }
    public void setItemIds(Set<String> itemIds) {
    	_itemIds = itemIds;
    }
    public void addItemId(String itemId) {
        _itemIds.add(itemId);
    }
    
    @IDocumentKeyValue
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }
    
    @Override
    public int hashCode() {
        return _name.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof CategoryDocument)) {
            return false;
        }
        
        CategoryDocument cdInstance = CategoryDocument.class.cast( instance );
        return cdInstance._name.equals(_name);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("CategoryDocument{ name: ");
    	content.append(_name);
    	content.append(", itemIds:  ");
    	content.append(_itemIds);
    	content.append(" }");
    	return content.toString();
    }
    
}
