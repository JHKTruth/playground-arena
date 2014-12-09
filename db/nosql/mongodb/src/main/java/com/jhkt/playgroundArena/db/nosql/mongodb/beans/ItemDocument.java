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
public final class ItemDocument extends AbstractDocument {
    
    private String _name;
    private String _description;
    private Double _price;
    private Double _shippingFee;
    private Double _taxRate;
    private Date _lastModified;
    private String _sellerUserName;
    private Set<ReviewDocument> _reviews;
    private Set<ImageDocument> _images;
    private Set<String> _categoryNames;
    
    ItemDocument() {
        super();
        
        _reviews = new HashSet<ReviewDocument>();
        _images = new HashSet<ImageDocument>();
        _categoryNames = new HashSet<String>();
    }
    
    @IDocumentKeyValue
    public String getSellerUserName() {
        return _sellerUserName;
    }
    public void setSellerUserName(String sellerUserName) {
        _sellerUserName = sellerUserName;
    }
    
    @IDocumentKeyValue
    public Set<ReviewDocument> getReviews() {
        return _reviews;
    }
    public void setReviews(Set<ReviewDocument> reviews) {
    	_reviews = reviews;
    }
    public void addReview(ReviewDocument review) {
        _reviews.add(review);
    }
    
    @IDocumentKeyValue
    public Set<ImageDocument> getImages() {
        return _images;
    }
    public void setImages(Set<ImageDocument> images) {
    	_images = images;
    }
    public void addImage(ImageDocument image) {
        _images.add(image);
    }
    
    @IDocumentKeyValue
    public Set<String> getCategoryNames() {
        return _categoryNames;
    }
    public void setCategoryNames(Set<String> categoryNames) { 
    	_categoryNames = categoryNames;
    }
    public void addCategoryName(String categoryName) {
        _categoryNames.add(categoryName);
    }
    
    @IDocumentKeyValue
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }
    
    @IDocumentKeyValue
    public String getDescription() {
        return _description;
    }
    public void setDescription(String description) {
        _description = description;
    }
    
    @IDocumentKeyValue
    public Double getPrice() {
        return _price;
    }
    public void setPrice(Double price) { 
        _price = price;
    }
    
    @IDocumentKeyValue
    public Double getShippingFee() {
        return _shippingFee;
    }
    public void setShippingFee(Double shippingFee) {
        _shippingFee = shippingFee;
    }
    
    @IDocumentKeyValue
    public Double getTaxRate() {
        return _taxRate;
    }
    public void setTaxRate(Double taxRate) {
        _taxRate = taxRate;
    }
    
    @IDocumentKeyValue
    public Date getLastModified() {
        return _lastModified;
    }
    public void setLastModified(Date lastModified) {
        _lastModified = lastModified;
    }
    
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _name.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _description.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _price.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _shippingFee.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _taxRate.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _lastModified.hashCode();
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof ItemDocument)) {
            return false;
        }
        
        ItemDocument idInstance = ItemDocument.class.cast( instance );
        return idInstance._name.equals(_name) && idInstance._description.equals(_description) && 
                idInstance._price.equals(_price) && idInstance._shippingFee.equals(_shippingFee) && 
                idInstance._taxRate.equals(_taxRate) && idInstance._lastModified.equals(_lastModified);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("ItemDocument{ name: ");
    	content.append(_name);
    	content.append(", description: ");
    	content.append(_description);
    	content.append(", price: ");
    	content.append(_price);
    	content.append(", shippingFee: ");
    	content.append(_shippingFee);
    	content.append(", taxRate: ");
    	content.append(_taxRate);
    	content.append(", lastModified: ");
    	content.append(_lastModified);
    	content.append(", sellerUserName: ");
    	content.append(_sellerUserName);
    	content.append(", reviews: ");
    	content.append(_reviews);
    	content.append(", images: ");
    	content.append(_images);
    	content.append(", categoryNames: ");
    	content.append(_categoryNames);
    	content.append(" }");
    	return content.toString();
    }
    
}
