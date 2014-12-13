package com.chorezilla.android;

import java.io.*;
import java.util.ArrayList;

/**
 * Ideally, an xml stream available via a web service would deliver the information to build 
 * the ListCollection, Lists, and Items. 
 * 
 * @author bmsalm
 */


public class ListCollection
{
	private ArrayList<CheckList> listArray;
	private int numberOfLists;
	
	public ListCollection()
	{
		listArray = new ArrayList<CheckList>();
		numberOfLists = 0;
	}
	
	public CheckList getList(int i)
	{
		return listArray.get(i);
	}
	
	public CheckList getList(String name)
	{
		for(int i = 0; i < listArray.size(); i++){
			if(listArray.get(i).getName().equals(name))
				return listArray.get(i);
		}
		return null;
	}
	
	public void addList(CheckList c)
	{
		listArray.add(c);
		numberOfLists++;
	}
	public void removeList(String name)
	{
		for(int i = 0; i < listArray.size(); i++){
			if(listArray.get(i).getName().equals(name))
				listArray.remove(i);
		}
	}
	public int getNumberLists()
	{
		return numberOfLists; 
	}
	
	public String toXML()
	{
		String str;
		
		str = "<collection>\n";
		
		//write list XML to file, which will write item XML to file	
		for(int i = 0; i < listArray.size(); i++)
			str+= listArray.get(i).toXML();	
				
		str+= "</collection>";
		return str;
	}
	
	public void writeCollectionToXML(FileOutputStream fout)
	{
		String str = toXML();
		
		PrintWriter pw = new PrintWriter(fout);
		
		pw.print(str);
		pw.close();
	}
}
