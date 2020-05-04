
/**
 * Defines Antecedent & Dependent applicable for Decision. Orchestration engine 
 * can make use of associated  Antecedent & Dependent to determine next course
 * of action.
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */
package org.flowr.framework.core.security.policy;

import org.flowr.framework.core.model.Model;
import org.flowr.framework.core.security.Antecedent;
import org.flowr.framework.core.security.Dependent;

public class DecisionModel implements Model{

    private Antecedent antecedent;
    private Dependent dependent;
    
    public void setAntecedent(Antecedent antecedent) {
        this.antecedent = antecedent;
    }

    public Antecedent getAntecedent() {
        return this.antecedent;
    }

    public void setDependent(Dependent dependent) {
        this.dependent = dependent;
    }

    public Dependent getDependent() {

        return this.dependent;
    }

    public String toString(){
        
        return "DecisionModel{"+
                "\n antecedent : "+antecedent+
                "\n dependent : "+dependent+
                "\n}\n";
    }   
}
