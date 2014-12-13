/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chorezilla.android;

/**
 *
 * @author Milica
 */
public class UserProfile {
    
    private String username;
    private String email;
    private String password;
    
    
    public UserProfile()
    {
    	 username = "";
	     email = "";
	     password = "";
    	
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) 
    {
        this.username = username;    
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password; 
    }
    
    
    
    
}
