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

/**
 * Note that one can not contain JPA annotations within package-info.java for Global 
 * scoping as the specification removed the package visibility of JPA annotations.
 * 
 * So one needs to provide this mapping within hibernate.cfg.xml.
 * 
 * A many-to-many association may always be represented as two many-to-one associations to an 
 * intervening class. This model is usually more easily extensible, so we tend not to use 
 * many-to-many associations in applications.
 * 
 * Remember that Hibernate ignores the state of an inverse collection! This time, however, the 
 * collection contains information that is needed to update the database correctly; the position of 
 * its elements. If only the state of each Bid instance is considered for synchronization, and the 
 * collection is inverse and ignored, Hibernate has not value for the BID_POSITION column. If you map 
 * a bidirectional one-to-many entity association with an indexed collection (this is also true for maps 
 * and arrays), you have to switch the inverse sides. You can't make an indexed collection inverse="true". 
 * The collection becomes responsible for state synchronization, and the one side, the Bid, has to be made 
 * inverse. However, there is no inverse="true" for many-to-one mapping so you need to simulate this attribute 
 * on a many-to-one. Setting insert and update to false has the desired effect. As we discussed earlier, 
 * these two attributes used together make a property effectively read-only. This side of the association is 
 * therefore ignored for any write operations, and the state of the collection (including the index of the 
 * elements) is the relevant state when the in-memory state is synchronized with the database. You've switched 
 * the inverse/noninverse sides of the association, a requirement if you switch from a set or bag to a list 
 * (or any other indexed collection).
 * 
 * Brief note regarding polymorphism:
 * If you have a reference to a BillingDetails which is abstract, if you invoke user.getBillingDetail [since 
 * Hibernate performs lazy loading], the reference will be null as well as instanceof checking with the sub type 
 * returning false. If one still wishes to use the abstract type within the variable, one must perform a load on the instance 
 * [i.e. CreditCard cc = (CreditCard) session.load( CreditCard.class, bg.getid() );]
 * 
 * It is strongly encouraged that one does not perform bean equality with database identifier, for object's identifier 
 * values are not assigned until the object becomes persistent.
 * 
 * The one difference between get() and load() is how they indicate that the instance could not be found. Get returns null, 
 * whereas load throws an ObjectNotFoundException. More important, the load() method may return a proxy, a placeholder, without 
 * hitting the database. A consequence of this is that you may get an ObjectNotFoundException later, as soon as you try to access 
 * the returned placeholder and force its initialization. The get() method on the other hand never returns a proxy, it always hits 
 * the database. EntityManager's equivalent form are find() and getReference().
 * 
 * Because Hibernate proxies are instances of runtime generated subclasses of your entity classes, you can't get the class of an object 
 * with the usual operators. This is where the helper method HibernateProxyHelper.getClassWithoutInitializingProxy(o) is useful.
 * 
 * The least amount of scoping that one can provide is of package.
 * 
 * Starting with release 3.2.3, there are 2 new generators which represent a re-thinking of 2 different aspects of identifier generation. 
 * The first aspect is database portability; the second is optimization Optimization means that you do not have to query the database for 
 * every request for a new identifier value. These two new generators are intended to take the place of some of the named generators described 
 * above, starting in 3.3.x.
 * 
 * Hibernate Annotations supports out of the box enum type mapping either into a ordinal column (saving the enum ordinal) or a string based column 
 * (saving the enum string representation): the persistence representation, defaulted to ordinal, can be overridden through the @Enumerated annotation.
 * In another words, order must be consistent in addition to the Enum.
 * 
 * By default the access type of a class hierarchy is defined by the position of the @Id or @EmbeddedId annotations. If these annotations are 
 * on a field, then only fields are considered for persistence and the state is accessed via the field. If there annotations are on a getter, then 
 * only the getters are considered for persistence and the state is accessed via the getter/setter. That works well in practice and is the recommended approach.
 * 
 * Two entities cannot share a reference to the same collection instance. Due to the underlying relational model, 
 * collection-valued properties do not support null value semantics. Hibernate does not distinguish between a null collection 
 * reference and an empty collection.
 * 
 * Regarding HQL syntax:
 * Occasionally, you might be able to achieve better performance by executing the query using the iterate() method. This will usually be the case if you 
 * expect that the actual entity instances returned by the query will already be in the session or second-level cache. If they are not already 
 * cached, iterate() will be slower than list() and might require many database hits for a simple query, usually 1 for the initial select which only returns 
 * identifiers, and n additional selects to initialize the actual instances.
 * 
 * @author Ji Hoon Kim
 */
/*
 * JPA prohibits annotations within package-info.java
 * 
@org.hibernate.annotations.NamedQueries({
    @org.hibernate.annotations.NamedQuery(
            name="findItemsWithCertainShippingFee",
            query="select i from Item i where i.shippingFee <= ?"
    )  
})

//Variable_name, value
@org.hibernate.annotations.NamedQueries({
    @org.hibernate.annotations.NamedQuery(
            name="getSystemVariables",
            query="show variables"
    )  
})
@org.hibernate.annotations.NamedQueries({
    @org.hibernate.annotations.NamedQuery(
            name="getStatusVariables",
            query="show status"
    )  
})
*/

@org.hibernate.annotations.TypeDefs({
    @org.hibernate.annotations.TypeDef(
            name="dateFormatterType",
            typeClass=com.jhkt.playgroundArena.db.sql.mysql.beans.extentionPoints.DateFormatterType.class
    ),
    @org.hibernate.annotations.TypeDef(
            name="regularExpressionType",
            typeClass=com.jhkt.playgroundArena.db.sql.mysql.beans.extentionPoints.RegularExpressionUserType.class,
            parameters={
                @org.hibernate.annotations.Parameter(name="regularExpression", 
                                                        value="^\\d{5}(-\\d{4})?$"
                )
            }
    )
})

package com.jhkt.playgroundArena.db.sql.mysql.beans;