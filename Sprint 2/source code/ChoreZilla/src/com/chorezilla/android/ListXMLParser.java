package com.chorezilla.android;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ListXMLParser
{
	public static ListCollection readCollection(InputStream in)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance(); 
		SAXParser parser = null;
		
		//list collection to be returned to be returned
		ListCollection returnable = null;
		
		try {
			//initialize a new SAX
			parser = factory.newSAXParser();
		} 
		catch (ParserConfigurationException e) {
			//log - Parser Conf error
			return null;
		} 
		catch (SAXException e) {
			//log - SAX error
			return null;
		}
		
		//object to handle parsing tags
		ListXMLHandler handler = new ListXMLHandler();

		try{
			parser.parse(in, handler); // parse the file
		} 
		catch (SAXException e){
			Log.e("SAX ERROR", e.getMessage());
			return null;
		} 
		catch (IOException e){
			Log.e("IOException ERROR", e.getMessage());
			return null;
		}

		returnable = handler.getCollection();	//retrieve compiled list 
		return returnable;
	}
}

//builds the list collection
class ListXMLHandler extends DefaultHandler 
{
	private String data;				//temp for data gathered from attributes
	private ListCollection collection;	//list to be returned after compiling 
	private CheckList currentList;
	private ChoreItem currentItem;

	//startDocument() is called before parsing starts
	@Override
	public void startDocument()
	{
		System.out.println("Parsing Started.");
		
		//initialize
		data = "";
		collection = new ListCollection();
		currentList = null;
		currentItem = null; 
	}

	//endDocument() is called after parsing is complete
	@Override
	public void endDocument()
	{
		System.out.println("Parsing Complete.");
	}

	//startElement() handles start tag, and its attributes
	@Override
	public void startElement(String ID, String localName, String tag, Attributes attributes)
	{
		if (tag.equals("list"))
		{
			String name = "";
			String owner = "";
			ArrayList<String> users = new ArrayList<String>();
			
			//get tag attributes
			for (int i=0;i<attributes.getLength();i++)
			{
				if (attributes.getQName(i).equals("name"))
				{
					name = attributes.getValue(i);					
				}
				else if (attributes.getQName(i).equals("owner"))
				{
					owner = attributes.getValue(i);					
				}
				else if (attributes.getQName(i).equals("users"))
				{
					String temp = attributes.getValue(i);	
					StringTokenizer tokenizer = new StringTokenizer(temp);	//tokenize by spaces
					while(tokenizer.hasMoreElements())
						users.add(tokenizer.nextToken()); 
				}
			}
			
			//handle provided tag attributes
			if (name.length()>0 && owner.length()>0 && users.size()>0)
			{
				currentList = new CheckList(name, owner, users);
			}	
			else
			{
				//handle error
			}
		}
		else if (tag.equals("item"))
		{
			String name="";
			String owner="";

			for (int i=0;i<attributes.getLength();i++)
			{
				if (attributes.getQName(i).equals("name"))
				{
					name = attributes.getValue(i);					
				}
				else if (attributes.getQName(i).equals("owner"))
				{
					owner = attributes.getValue(i);	
				}
			}
			
			//handle provided tag attributes
			if (name.length()>0 && owner.length()>0)
			{
				currentItem = new ChoreItem(name, owner);
			}	
			else
			{
				//handle error
			}
		}
		
		data = "";
	}

	@Override
	public void endElement(String uri,String localName,String tag)
	{
		if (tag.equals("collection"))
		{
			
		}
		else if (tag.equals("item"))
		{
			if(currentItem != null){
				currentList.addItem(currentItem);
				currentItem = null;	//reset 
			}
			else{
				//handle error
			}
		}
		else if (tag.equals("list"))
		{
			if(currentList != null){
				collection.addList(currentList);
				currentList = null;	//reset 
			}
			else{
				//handle error
			}
		}
		
		else if (tag.equals("resp"))
		{
			String temp = data;	
			StringTokenizer tokenizer = new StringTokenizer(temp);	//tokenize by spaces
			while(tokenizer.hasMoreElements())
				currentItem.addUser(tokenizer.nextToken());
		}
		
		else if (tag.equals("weight"))
		{
			if(!data.equals(""))
				currentItem.setWeight(Integer.parseInt(data));
		}
		
		else if (tag.equals("due"))
		{
			if(!data.equals(""))
				currentItem.setDueDate(Long.parseLong(data));	
		}
		
		else if (tag.equals("done"))
		{
			if(!data.equals(""))
				currentItem.setCompletedDate(Long.parseLong(data));	
		}
		
		else if (tag.equals("by"))
		{
			if(!data.equals(""))
				currentItem.setCompletedBy(data);	
		}
		
		data = "";
	}

	//read the data between tags
	@Override
	public void characters(char ch[], int start, int length) throws SAXException 
	{
		if (data.length()>0)
			data = data + "" + new String(ch,start,length);
		else
			data = new String(ch,start,length);
	}

	public ListCollection getCollection()
	{
		return collection;
	}


}

