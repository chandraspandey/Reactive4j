
/**
 * 
 * 
 * @author Chandra Shekhar Pandey
 * Copyright � 2018 by Chandra Shekhar Pandey. All rights reserved.
 */

package example.flowr.node;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.flowr.framework.core.constants.Constant.FrameworkConstants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class User implements Serializable {
    
    private static final long serialVersionUID = FrameworkConstants.FRAMEWORK_VERSION_ID;

    @Id
    @Column(name="USER_ID")
    private int userId;

    @Column(name="EMAIL_ID")
    private String emailId;

    private String name;

    private String pass;



    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public String toString(){
        return "User{ userId : "+userId+" | emailId : "+emailId+" | name : "+name+"}";
    }

}
