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
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * @author Ji Hoon Kim
 */
@Entity
@Table(name="USER")
public class User {
    
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private CreditCard mainCreditCard;
    private SortedSet<String> nickNames = new TreeSet<String>();
    private Set<CreditCard> creditCards = new HashSet<CreditCard>();
    private Set<BankAccount> bankAccounts = new HashSet<BankAccount>();
    private Address primaryShippingAddress;
    private Set<Address> shippingAddresses = new HashSet<Address>();
    private Set<Receipt> previousPurchases = new HashSet<Receipt>();
    
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    public Long getId() {
        return id;
    }
    void setId(Long id) {
    	this.id = id;
    }
    
    @Column(name="USER_NAME",
            nullable=false,
            unique=true
    )
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    @Column(name="PASSWORD",
            nullable=false
    )
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="FIRST_NAME",
            nullable=false
    )
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    @Column(name="LAST_NAME",
            nullable=false
    )
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Column(name="EMAIL",
            nullable=false
    )
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    /**
     * @NotNull
     * 
     * You can mark an association as mandatory by using the optional=false attribute. We 
     * recommend to use Bean Validation's @NotNull annotation as a better alternative however. As a 
     * consequence, the foreign key column(s) will be marked as not nullable (if possible).
     */
    public CreditCard getMainCreditCard() {
        return mainCreditCard;
    }
    public void setMainCreditCard(CreditCard mainCreditCard) {
        this.mainCreditCard = mainCreditCard;
    }
    
    @ElementCollection
    @CollectionTable(name="USER_NICKNAMES", joinColumns=@JoinColumn(name="USER_ID"))
    @Column(name="NICKNAME")
    @Sort(type=SortType.NATURAL)
    public SortedSet<String> getNickNames() {
        return nickNames;
    }
    void setNickNames(SortedSet<String> nickNames) {
    	this.nickNames = nickNames;
    }
    public void addNickName(String nickName) {
        nickNames.add(nickName);
    }
    
    @OneToMany(mappedBy="user",
                cascade=CascadeType.ALL,
                orphanRemoval=true)
    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }
    void setCreditCards(Set<CreditCard> creditCards) {
    	this.creditCards = creditCards;
    }
    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(creditCard);
    }
    
    @OneToMany(mappedBy="user",
                cascade=CascadeType.ALL,
                orphanRemoval=true)
    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
    void setBankAccounts(Set<BankAccount> bankAccounts) {
    	this.bankAccounts = bankAccounts;
    }
    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }
    
    /**
     * Below is an example of instead of sharing a primary key, two rows can have a 
     * foreign key relationship. One table has a foreign key column that references 
     * the primary key of the associated table. (The source and target of this foreign 
     * key constraint can even be the same table: This is called a self-referencing relationship)
     * 
     * The @JoinColumn attribute is optional, the default value(s) is the concatenation of the name of 
     * the relationship in the owner side, _ (underscore), and the name of the primary key column in the 
     * owned side.
     */
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumns({
    	@JoinColumn(name="sa_name", referencedColumnName="fld_name"),
        @JoinColumn(name="sa_street", referencedColumnName="fld_street"),
        @JoinColumn(name="sa_street2", referencedColumnName="fld_street2"),
        @JoinColumn(name="sa_city", referencedColumnName="fld_city"),
        @JoinColumn(name="sa_state", referencedColumnName="fld_state"),
        @JoinColumn(name="sa_zipCode", referencedColumnName="fld_zipCode")
    })
    public Address getPrimaryShippingAddress() {
        return primaryShippingAddress;
    }
    public void setPrimaryShippingAddress(Address primaryShippingAddress) {
        this.primaryShippingAddress = primaryShippingAddress;
    }
    
    @OneToMany(mappedBy="user",
                cascade=CascadeType.ALL,
                orphanRemoval=true)
    public Set<Address> getShippingAddresses() {
        return shippingAddresses;
    }
    void setShippingAddresses(Set<Address> shippingAddresses) {
    	this.shippingAddresses = shippingAddresses;
    }
    public void addShippingAddress(Address shippingAddress) {
        shippingAddresses.add(shippingAddress);
    }
    
    @OneToMany(mappedBy="user",
                cascade=CascadeType.ALL,
                orphanRemoval=true)
    public Set<Receipt> getPreviousPurchases() {
        return previousPurchases;
    }
    void setPreviousPurchases(Set<Receipt> previousPurchases) {
    	this.previousPurchases = previousPurchases;
    }
    public void addPurchase(Receipt purchase) {
        previousPurchases.add(purchase);
    }
    
    @Override
    public int hashCode() {
        return userName.hashCode();
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof User)) {
            return false;
        }
        
        User user = User.class.cast( instance );
        return user.userName.equals(userName);
    }
    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append("User{ id: ");
        content.append(id);
        content.append(", userName: ");
        content.append(userName);
        content.append(", password: ");
        content.append(password);
        content.append(", firstName: ");
        content.append(firstName);
        content.append(", lastName: ");
        content.append(lastName);
        content.append(", email: ");
        content.append(email);
        content.append(", mainCreditCard: ");
        content.append(mainCreditCard);
        content.append(", nickNames: ");
        content.append(nickNames);
        content.append(", creditCards: ");
        content.append(creditCards);
        content.append(", bankAccounts: ");
        content.append(bankAccounts);
        content.append(", primaryShippingAddress: ");
        content.append(primaryShippingAddress);
        content.append(", shippingAddresses: ");
        content.append(shippingAddresses);
        content.append(", previousPurchases: ");
        content.append(previousPurchases);
        content.append(" }");
        
        return content.toString();
    }
    
}
