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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Parent;

import com.jhkt.playgroundArena.db.sql.mysql.beans.extentionPoints.beans.FormattedDate;
import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * Hibernate Annotations supports something that is not explicitly supported by the JPA specification. 
 * You can annotate a embedded object with the @MappedSuperclass annotation to make the 
 * superclass properties persistent (see @MappedSuperclass for more informations).
 * 
 * Any class in the hierarchy non annotated with @MappedSuperclass nor @Entity will be ignored.
 * 
 * Composite elements can contain components but not collections.
 * 
 * @author Ji Hoon Kim
 */
@Embeddable
@MappedSuperclass
public class Review implements Serializable {
    
    public enum RATING {
        ONE, TWO, THREE, FOUR, FIVE
    }
    
    private Item item;
    private RATING rating;
    private String comment;
    private FormattedDate createdDate;
    
    @Parent
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name="RATING", 
            nullable=false, 
            updatable=false
    )
    public RATING getRating() {
        return rating;
    }
    public void setRating(RATING rating) {
        this.rating = rating;
    }
    
    @Lob
    @Column(name="COMMENT", length=512)
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    @org.hibernate.annotations.Type(type="dateFormatterType")
    @Columns(columns={@Column(name="REVIEWED_DATE",
                                updatable=false,
                                insertable=false
                              )
                      }
    )
    @org.hibernate.annotations.Generated(
            org.hibernate.annotations.GenerationTime.INSERT
    )
    /** 
     * When using composite user type, you will have to express column definitions. The @Columns has been introduced for that purpose.
     */
    public FormattedDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(FormattedDate createdDate) {
        this.createdDate = createdDate;
    }
    
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + item.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + rating.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + comment.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + createdDate.hashCode();
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof Review)) {
            return false;
        }
        
        Review review = Review.class.cast( instance );
        return review.item.equals(item) && review.rating.equals(rating) 
                && review.comment.equals(comment) && review.createdDate.equals(createdDate);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("Review{ item: ");
        content.append(item);
        content.append(", rating: ");
        content.append(rating);
        content.append(", comment: ");
        content.append(comment);
        content.append(", createdDate: ");
        content.append(createdDate);
        content.append(" }");
        
        return content.toString();
    }
    
}
