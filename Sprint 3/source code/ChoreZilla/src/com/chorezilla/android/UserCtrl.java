package com.chorezilla.android;

import com.chorezilla.android.dao.UserDAO;

public class UserCtrl 
{
	
	public static String newUser(String username, String email, String password, String confirm) 
	{
		//validate information
		if(!UserDAO.validateUsername(username)){
			return "Username is taken!";
		}
		
		//build user object
		UserProfile user = new UserProfile();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		
		//Permeate user in database  
		UserDAO.addUser(user);
		
		return ""; //success

	}
	
	public static String validateProfile(String username, String email, String password, String confirm) 
	{
		//Validate all fields filled
		if (username.equals("") || email.equals("") || password.equals("") || confirm.equals(""))
		{
			return "All fields must be filled!";
		}
		
		//validate password
		if (! (password.length()>6 && password.length()<15))
		{
			return "Password must be between 6 and 15 characters!";
		}
		
		if (!password.equals(confirm))
		{
			return "Passwords have to match!";
		}
		
		//validate email
		if (!(email.contains("@") || email.contains(".")))
		{
            return "Email Address must be a valid one!";
		}		
		
		return ""; //success
	}
	
	public static String deleteUserAccount(UserProfile user) 
	{
		//STUB METHOD
		UserDAO.removeUser(user);
		// will remove user from db and log user off. 
		
		return ""; //success
	}

}
