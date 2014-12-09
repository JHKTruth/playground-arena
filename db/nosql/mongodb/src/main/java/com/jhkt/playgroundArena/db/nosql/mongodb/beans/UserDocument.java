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
import java.util.SortedSet;
import java.util.TreeSet;

import com.jhkt.playgroundArena.db.nosql.mongodb.annotations.IDocumentKeyValue;
import com.jhkt.playgroundArena.shared.utils.JPAConstants;

/**
 * @author Ji Hoon Kim
 */
public final class UserDocument extends AbstractDocument {
    
    private String _userName;
    private String _password;
    private String _firstName;
    private String _lastName;
    private String _email;
    
    private SortedSet<String> _nickNames;
    private Set<CreditCardDocument> _creditCards;
    private Set<BankAccountDocument> _bankAccounts;
    private Set<AddressDocument> _shippingAddresses;
    private Set<ReceiptDocument> _receipts;
    
    UserDocument() {
        super();
        
        _nickNames = new TreeSet<String>();
        _creditCards = new HashSet<CreditCardDocument>();
        _bankAccounts = new HashSet<BankAccountDocument>();
        _shippingAddresses = new HashSet<AddressDocument>();
        _receipts = new HashSet<ReceiptDocument>();
    }
    
    @IDocumentKeyValue
    public SortedSet<String> getNickNames() {
        return _nickNames;
    }
    public void setNickNames(SortedSet<String> nickNames) { 
    	_nickNames = nickNames;
    }
    public void addNickName(String nickName) {
        _nickNames.add(nickName);
    }
    
    @IDocumentKeyValue
    public Set<CreditCardDocument> getCreditCards() {
        return _creditCards;
    }
    public void setCreditCards(Set<CreditCardDocument> creditCards) { 
    	_creditCards = creditCards;
    }
    public void addCreditCard(CreditCardDocument creditCard) {
        _creditCards.add(creditCard);
    }
    
    @IDocumentKeyValue
    public Set<BankAccountDocument> getBankAccounts() {
        return _bankAccounts;
    }
    public void setBankAccounts(Set<BankAccountDocument> bankAccounts) { 
    	_bankAccounts = bankAccounts;
    }
    public void addBankAccount(BankAccountDocument bankAccount) {
        _bankAccounts.add(bankAccount);
    }
    
    @IDocumentKeyValue
    public Set<AddressDocument> getShippingAddresses() {
        return _shippingAddresses;
    }
    public void setShippingAddresses(Set<AddressDocument> shippingAddresses) { 
    	_shippingAddresses = shippingAddresses;
    }
    public void addShippingAddress(AddressDocument shippingAddress) {
        _shippingAddresses.add(shippingAddress);
    }
    
    @IDocumentKeyValue
    public Set<ReceiptDocument> getReceipts() {
        return _receipts;
    }
    public void setReceipts(Set<ReceiptDocument> receipts) { 
    	_receipts = receipts;
    }
    public void addReceipt(ReceiptDocument receipt) {
        _receipts.add(receipt);
    }
    
    @IDocumentKeyValue
    public String getUserName() {
        return _userName;
    }
    public void setUserName(String userName) {
        _userName = userName;
    }
    
    @IDocumentKeyValue
    public String getPassword() {
        return _password;
    }
    public void setPassword(String password) {
        _password = password;
    }
    
    @IDocumentKeyValue
    public String getFirstName() {
        return _firstName;
    }
    public void setFirstName(String firstName) {
        _firstName = firstName;
    }
    
    @IDocumentKeyValue
    public String getLastName() {
        return _lastName;
    }
    public void setLastName(String lastName) {
        _lastName = lastName;
    }
    
    @IDocumentKeyValue
    public String getEmail() {
        return _email;
    }
    public void setEmail(String email) {
        _email = email;
    }
    
    @Override
    public int hashCode() {
        int hashCodeVal = JPAConstants.HASH_CODE_INIT_VALUE;
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _userName.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _password.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _firstName.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _lastName.hashCode();
        hashCodeVal = JPAConstants.HASH_CODE_MULTIPLY_VALUE * hashCodeVal + _email.hashCode();
        return hashCodeVal;
    }
    @Override
    public boolean equals(Object instance) {
        if(!(instance instanceof UserDocument)) {
            return false;
        }
        
        UserDocument udInstance = UserDocument.class.cast( instance );
        return udInstance._userName.equals(_userName) && udInstance._password.equals(_password) && udInstance._firstName.equals(_firstName) && 
                udInstance._lastName.equals(_lastName) && udInstance._email.equals(_email);
    }
    @Override
    public String toString() {
    	StringBuilder content = new StringBuilder();
    	content.append("UserDocument{ userName: ");
    	content.append(_userName);
    	content.append(", firstName: ");
    	content.append(_firstName);
    	content.append(", lastName: ");
    	content.append(_lastName);
    	content.append(", email: ");
    	content.append(_email);
    	content.append(", nickNames: ");
    	content.append(_nickNames);
    	content.append(", creditCards: ");
    	content.append(_creditCards);
    	content.append(", bankAccounts: ");
    	content.append(_bankAccounts);
    	content.append(", shippingAddresses: ");
    	content.append(_shippingAddresses);
    	content.append(", receipts: ");
    	content.append(_receipts);
    	content.append(" }");
    	return content.toString();
    }
    
}
