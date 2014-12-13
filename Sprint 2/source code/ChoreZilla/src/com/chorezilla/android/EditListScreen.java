package com.chorezilla.android;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class EditListScreen extends Activity implements OnItemSelectedListener
{
	private Spinner ls;
	private ListCollection theCollection;
	private CheckList currentList;
	private ChoreItem currentChore;
	private LinearLayout linear_layout;
	private SimpleDateFormat dateFormat; 
	private boolean resumeHasRun;

	public void populateChoreList()
	{
		//clear list
		linear_layout.removeAllViews();

		//populate list
		for(int i = 0; i < currentList.getNumberItems(); i++) 
		{
			ChoreItem tempChore = currentList.findItem(i);
			Date tempDate = null;
			String dateString = "";

			if(tempChore.getDueDate() == -1){
				dateString = "No Due Date";
			}
			else{
				tempDate = new Date((long)tempChore.getDueDate() * 1000);
				dateString = dateFormat.format(tempDate);
			}

			CheckBox cb = new CheckBox(this);
			cb.setText(""+ (i+1)+". " +tempChore.getName()+"\n"
					+ dateString +" | "+ tempChore.listUsers());
			cb.setId(i);

			cb.setOnClickListener(new OnClickListener() 
			{
				public void onClick(View v) {				
					Intent i = new Intent(EditListScreen.this, EditChore.class);
					
					int checkbox_id = v.getId();
					System.out.println(checkbox_id);
					
					currentChore = currentList.findItem(checkbox_id);
					
					i.putExtra("Name", currentChore.getName());
					String users = new String();
					Date tempDate = null;
					String dateString = "";

					String temp;
					for (int u = 0; u < currentChore.getUsers().size(); u++)
					{
						temp = "";
						temp = temp.concat(currentChore.getUsers().get(u));
						users = users.concat(temp + ", ");
					}
					
					i.putExtra("Users", users);
					i.putExtra("Weight", Double.toString(currentChore.getWeight()));
					
					if (currentChore.getDueDate() == -1)
					{
						dateString = "No Due Date";
					}
					else
					{
						tempDate = new Date((long)currentChore.getDueDate() * 1000);
						dateString = dateFormat.format(tempDate);
					}

					i.putExtra("Date", dateString);
					
					int requestCode = 0;
					startActivityForResult(i, requestCode);
					CheckBox cb = (CheckBox) v.findViewById(checkbox_id);
					cb.setChecked(false);
				}
			});
			
			linear_layout.addView(cb);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode) {
	      case 0:
	            if (resultCode == RESULT_OK) 
	            {
	            	//chance object name
	                currentChore.setName(data.getStringExtra("Name"));
	                
	                //change object users
	                ArrayList<String> users = new ArrayList<String>(10);
	                StringTokenizer tokenziner= new StringTokenizer(data.getStringExtra("Users"), ", "); 
	                
	                while(tokenziner.hasMoreTokens())
	                {
	                	users.add(tokenziner.nextToken());
	                }
	                currentChore.setUsers(users);
	                
	                //set weight
	                currentChore.setWeight(Double.parseDouble(data.getStringExtra("Weight")));
	                
	                //set Date
	                
	                break;
	            }
	            if (resultCode == RESULT_CANCELED) 
	            {
	                break;
	            }
	      }
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	    if (!resumeHasRun) {
	        resumeHasRun = true;
	        return;
	    }
	    
	    populateChoreList();
	}
	
	public void populateListSpinner()
	{
		ArrayList<String> list = new ArrayList<String>();

		list.add("Please Select a List");
		for(int i = 0; i < theCollection.getNumberLists(); i++)
		{
			list.add(theCollection.getList(i).getName());
		}

		//Spinner code
		ls = (Spinner) findViewById(R.id.listSpinner);
		ls.setSelection(0);
		/* add chores to list */
		ls.setOnItemSelectedListener(this);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				EditListScreen.this, android.R.layout.simple_spinner_item, list); //import lists from saved data

		ls.setAdapter(arrayAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) 
	{
		//find selected index
		int spinnerPos = ls.getSelectedItemPosition(); 

		if(spinnerPos == 0)
			Toast.makeText(getApplicationContext(),
					"Please Select a List", Toast.LENGTH_SHORT).show();
		else
		{
			currentList = theCollection.getList(ls.getSelectedItem().toString());
			Toast.makeText(getApplicationContext(),
					"List " + currentList.getName() + " selected", Toast.LENGTH_SHORT).show();
			populateChoreList();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onCreate(Bundle b) 
	{
		super.onCreate(b);
		setContentView(R.layout.main_activity); 
		linear_layout = (LinearLayout) findViewById(R.id.llay);

		//set date format
		dateFormat = new SimpleDateFormat();
		SimpleDateFormat.getDateTimeInstance();

		//get collection by parsing XML
		AssetManager am = getApplicationContext().getAssets(); 
		InputStream XMLInputStream = null;
		theCollection = null;

		try {
			XMLInputStream = am.open("Test1.xml");
			theCollection = ListXMLParser.readCollection(XMLInputStream);
			XMLInputStream.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
			//handle error
		} catch (IOException e) {
			e.printStackTrace();
			//handle error
		} 	

		//fill lists
		populateListSpinner();

		//Buttons for bottom
		Button addItem = (Button) findViewById(R.id.addItem);
		Button renameList = (Button) findViewById(R.id.renameList);
		Button addUser = (Button) findViewById(R.id.addUser);
		
		addItem.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) 
					{
						if(currentList == null)
						{
							Toast.makeText(getApplicationContext(), "Please select a list!", Toast.LENGTH_SHORT).show();
							return;
						}
						
						//get user input
						final EditText input = new EditText(EditListScreen.this);	
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditListScreen.this); 
						alertDialog.setTitle("Add a chore").setMessage("Enter a Chore Name:").setView(input);

						alertDialog.setPositiveButton("Add Chore", 
								new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which) 
							{
								currentList.addItem(new ChoreItem(input.getText().toString(), "bmsalm")); //!!!HARDCODED OWNER NAME
								populateChoreList();
								Toast.makeText(getApplicationContext(), "Chore was added to list", Toast.LENGTH_SHORT).show();
							}
						});

						alertDialog.setNegativeButton("Cancel", 
								new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which)
							{
								Toast.makeText(getApplicationContext(), "Changes were discarded", Toast.LENGTH_SHORT).show();
								dialog.cancel();
							}
						});
						alertDialog.show();
					}
				});

		renameList.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						
						if(currentList == null)
						{
							Toast.makeText(getApplicationContext(), "Please select a list!", Toast.LENGTH_SHORT).show();
							return;
						}

						//get user input
						final EditText input = new EditText(EditListScreen.this); 
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditListScreen.this);
						alertDialog.setTitle("Rename List").setMessage("Enter a New Name for This List:").setView(input);

						alertDialog.setPositiveButton("Save", 
								new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which) {
								currentList.setName(input.getText().toString());	//change name
								int tempPos = ls.getSelectedItemPosition();
								populateListSpinner();
								ls.setSelection(tempPos);
								Toast.makeText(getApplicationContext(),"List was renamed", Toast.LENGTH_SHORT).show();
							}
						});

						alertDialog.setNegativeButton("Cancel", //no
								new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which) {
								// Nothing is done
								Toast.makeText(getApplicationContext(), "Changes were discarded", Toast.LENGTH_SHORT).show();
								dialog.cancel();
							}
						});
						alertDialog.show();
					}
				});	

		addUser.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						
						if(currentList == null)
						{
							Toast.makeText(getApplicationContext(), "Please select a list!", Toast.LENGTH_SHORT).show();
							return;
						}

						//get user input
						final EditText input = new EditText(EditListScreen.this); 
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditListScreen.this);
						alertDialog.setTitle("Add User").setMessage("Enter the Name of User:").setView(input);

						alertDialog.setPositiveButton("Add", 
								new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which) {
								currentList.addUser(input.getText().toString());
								Toast.makeText(getApplicationContext(),"User "+input.getText().toString()+ " Added", Toast.LENGTH_SHORT).show();
							}
						});

						alertDialog.setNegativeButton("Cancel", //no
								new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(getApplicationContext(), "Changes were discarded", Toast.LENGTH_SHORT).show();
								dialog.cancel();
							}
						});
						alertDialog.show();
					}
				});
	}
}