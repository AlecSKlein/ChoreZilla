package com.chorezilla.android;

import java.util.ArrayList;

public class CheckList
{
	//attributes
	private String listName;
	private String listOwner;
	private ArrayList<String> listUsers;
	private ArrayList<ChoreItem> itemArray;
	
	//Constructor used for new lists
	public CheckList(String name, String owner)
	{
		listName = name;
		listOwner = owner;		
		listUsers = new ArrayList<String>();		
		itemArray = new ArrayList<ChoreItem>();
	}
	
	//Constructor used for existing lists
	public CheckList(String name, String owner, ArrayList<String> users)
	{
		listName = name;
		listOwner = owner;
		listUsers = users;
		itemArray = new ArrayList<ChoreItem>();
	}

/////////////////////////////////////
//////Item management functions//////
/////////////////////////////////////
	
	public void addItem(ChoreItem c)
	{
		itemArray.add(c);
	}
	
	public boolean removeItem(String itemName)
	{
		for(int i = 0; i < itemArray.size(); i++){
			if(itemArray.get(i).getName().equals(itemName)){
				itemArray.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeItem(int itemIndex)
	{
		if(itemIndex < 0 || itemIndex > getNumberItems())
			return false;
		
		itemArray.remove(itemIndex); 
		return true;
	}

	public ChoreItem findItem(String itemName)	//find an item by name
	{
		for(int i = 0; i < itemArray.size(); i++){
			if(itemArray.get(i).getName().equals(itemName))	//if chore name matches search
				return itemArray.get(i);
		}
		return null;
	}
	
	public ChoreItem findItem(int itemIndex)		//get item by array index
	{	
		if(itemIndex < 0 || itemIndex > getNumberItems())
			return null;
		
		return itemArray.get(itemIndex); 
	}
	
	public ArrayList<ChoreItem> getItemArrayList(){	//get whole item array, all items
		return itemArray;
	}
	
	
/////////////////////////////////////
//////User management functions//////
/////////////////////////////////////
	
	public void addUser(String userName)
	{
		listUsers.add(userName);
	}
	
	public void removeUsers(String userName)
	{
		for(int i = 0; i < listUsers.size(); i++){
			if(listUsers.get(i).equals(userName)){	//if chore name matches search
				listUsers.remove(i);
			}
		}
	}
	
//////Attribute Getters
	public String getName() {
		return listName;
	}
	
	public String getOwner(){
		return listOwner;
	}
	
	public int getNumberItems(){
		return itemArray.size();
	}
	
	public int getNumberUsers(){
		return listUsers.size();
	}
		
	
//////Attribute Setters
	public void setName(String name) {
		listName = name;
	}
	
	public void setOwner(String owner){
		listOwner = owner;
	}
	

/////////////////////////////////////
//////XML management functions//////
/////////////////////////////////////
	
	public String toXML() 
	{
		String str;
		
		//write list start tag
		str = "<list name =\""+ listName +"\" owner=\""+ listOwner +"\" users=\"";

		for(int i = 0; i < listUsers.size(); i++)
			str+= listUsers.get(i)+" ";	//get list users
		str+= "\">\n";
		
		//write items WITHIN list
		for(int j = 0; j < itemArray.size(); j++)
			str+= itemArray.get(j).toXML();
		
		//write end tag
		str+= "</list>\n";
		
		return str;
	}
}
