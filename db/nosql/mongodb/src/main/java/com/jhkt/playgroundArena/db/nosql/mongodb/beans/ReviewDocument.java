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

import com.jhkt.playgroundArena.db.nosql.mongodb.annotations.IDocumentKeyValue;
import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
public final class ReviewDocument extends AbstractDocument {
	
    public enum RATING {
        ONE, TWO, THREE, FOUR, FIVE;
    }
    
    private RATING _rating;
    private String _comment;
    private Date _createdDate;
    
    ReviewDocument() {
        super();
    }
    
    @IDocumentKeyValue
    public RATING getRating() {
        return _rating;
    }
    public void setRating(RATING rating) {
        _rating = rating;
    }
    
    @IDocumentKeyValue
    public String getComment() {
        return _comment;
    }
    public void setComment(String comment) {
        _comment = comment;
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
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _rating.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _comment.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _createdDate.hashCode();
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof ReviewDocument)) {
            return false;
        }
        
        ReviewDocument rdInstance = ReviewDocument.class.cast( instance );
        return rdInstance._rating.equals(_rating) && rdInstance._comment.equals(_comment) && rdInstance._createdDate.equals(_createdDate);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("ReviewDocument{ rating: ");
    	content.append(_rating);
    	content.append(", comment: ");
    	content.append(_comment);
    	content.append(", createdDate: ");
    	content.append(_createdDate);
    	content.append(" }");
    	return content.toString();
    }
    
}
