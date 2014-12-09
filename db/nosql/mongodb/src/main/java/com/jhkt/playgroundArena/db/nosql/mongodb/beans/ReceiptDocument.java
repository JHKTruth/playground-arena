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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.jhkt.playgroundArena.db.nosql.mongodb.annotations.IDocumentKeyValue;
import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
public final class ReceiptDocument extends AbstractDocument {
    
    private Date _createdDate;
    /*
     * to minimize the amount of data stored for the receipt document, store the id of the items
     * [double check later whether this is the correct approach]
     */
    private Set<String> _itemIds;
    private Double _total;
    
    ReceiptDocument() {
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
    public Double getTotal() {
        return _total;
    }
    public void setTotal(Double total) {
        _total = total;
    }
    
    @IDocumentKeyValue
    public Date getCreatedDate() {
        return _createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        _createdDate = createdDate;
    }
    
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _createdDate.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _itemIds.hashCode();
        
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof ReceiptDocument)) {
            return false;
        }
        
        ReceiptDocument rdInstance = ReceiptDocument.class.cast( instance );
        return rdInstance._createdDate.equals( _createdDate ) && rdInstance._itemIds.equals(_itemIds);
    }
    
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("ReceiptDocument{ createdDate: ");
    	content.append(_createdDate);
    	content.append(", itemIds: ");
    	content.append(_itemIds);
    	content.append(", total: ");
    	content.append(_total);
    	content.append(" }");
    	return content.toString();
    }
    
}
