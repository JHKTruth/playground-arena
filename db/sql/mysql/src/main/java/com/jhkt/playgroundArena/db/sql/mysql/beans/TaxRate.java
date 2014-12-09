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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

/**
 * @author Ji Hoon Kim
 */
//@Entity
//@Table(name="TAX_RATE")
public class TaxRate {
    
    public enum SALES_TAX {
        AL(4.0), AK(0.0), AZ(6.6), AR(6.0), CA(7.25), CO(2.9), CT(6.35),
        DE(0.0), DC(6.0), FL(6.0), GA(4.0), HI(4.0), ID(6.0), IL(6.25), 
        IN(7.0), IA(6.0), KS(6.3), KY(6.0), LA(4.0), ME(5.0), MD(6.0), 
        MA(6.25), MI(6.0), MN(6.875), MS(7.0), MO(4.225), MT(0.0), NE(5.5),
        NV(6.85), NH(0.0), NJ(7.0), NM(5.125), NY(4.0), NC(4.75), ND(5.0), 
        OH(5.5), OK(4.5), OR(0.0), PA(6.0), RI(7.0), SC(6.0), SD(4.0), 
        TN(7.0), TX(6.25), UT(5.95), VT(6.0), VA(5.0), WA(6.5), WV(6.0), 
        WI(5.0), WY(4.0);
        
        private Double generalTax;
        
        SALES_TAX(Double generalTax) {
            this.generalTax = generalTax;
        }
        
    }
    
    //@MapKeyEnumerated
    private Map<SALES_TAX, Double> stateSalesTax = new HashMap<SALES_TAX, Double>();
    
}
