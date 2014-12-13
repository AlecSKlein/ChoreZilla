package com.chorezilla.android;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChoreItem 
{
	private String name;				//task name
	private String owner; 				//task owner
	private ArrayList<String> users;	//responsible users
	private double weight;				//item weight
	private boolean completed;			//check mark
	private String completedBy;			//user who checked task
	private long dueDate; 				//due date of chore in UNIX time
	private long completedDate;			//date completed in UNIX time


	public ChoreItem(String name, String owner)
	{
		this.name = name;
		this.owner = owner; 

		this.users = new ArrayList<String>(10);
		this.weight = 0;
		this.completed = false;
		this.completedBy = null;
		this.dueDate = -1; 
		this.completedDate = -1;

	}

	//setters
	public void setName(String name)
	{
		this.name = name;
	}
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	public void check()
	{
		this.completed = true;
	}
	public void uncheck()
	{
		this.completed = false;
	}
	public void setDueDate(long UTC){
		this.dueDate = UTC;
	}
	public void setCompletedDate(long completedDate){
		this.completedDate = completedDate;
	}
	public void setComepletedBy(String completedBy){
		this.completedBy = completedBy;
	}

	//getters
	public String getName()
	{
		return name;
	}
	public String getOwner()
	{
		return owner;
	}
	public ArrayList<String> getUsers()
	{
		return users;
	}
	public String listUsers()
	{
		String str = "";
		
		if(users.isEmpty())
			return str;
		
		for(int i = 0; i < users.size(); i++)
		{
			str += users.get(i) + ", "; 
		}
		
		str = str.substring(0, str.length()-2);
		return str; 
	}
	public double getWeight()
	{
		return weight;
	}
	public boolean isEvaluated()
	{
		return completed;
	}
	public long getDueDate(){
		return dueDate;
	}
	public long getCompletedDate(){
		return completedDate;
	}
	public String getComepletedBy(){
		return completedBy;
	}

	/////////////////////////////////////
	//////User management functions//////
	/////////////////////////////////////

	public void addUser(String userName)
	{
		users.add(userName);
	}

	public void removeUsers(String userName)
	{
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).equals(userName)){	//if chore name matches search
				users.remove(i);
			}
		}
	}

	public String toXML() 
	{
		String str;

		str = "\t<item ";
		str+= "name=\"" + this.getName() + "\" ";
		str+= "owner=\"" + this.getOwner() + "\">\n";

		str+= "\t\t<resp>";
		ArrayList<String> temp = this.getUsers(); //temp array for user names
		for(int i = 0; i < temp.size(); i++)
			str+= temp.get(i)+" ";
		str+= "</resp>\n";

		str+= "\t\t<weight>"+ this.getWeight() +"</weight>\n";

		if(dueDate != -1)
			str+= "\t\t<due>" + dueDate + "</due>\n";
		else
			str+= "\t\t<due></due>\n";

		if(completedDate != -1)
			str+= "\t\t<done>"+ completedDate +"</done>\n";
		else
			str+= "\t\t<done></done>\n";

		if(completedBy != null)
			str+= "\t\t<by>"+completedBy+"</by>\n";
		else
			str+= "\t\t<by></by>\n";

		str+= "\t</item>\n";

		return str;
	}

}
