package com.chorezilla.android.gui;

import com.chorezilla.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditChore extends Activity {
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_chore);
		
		//data fields
		final EditText choreName = (EditText) findViewById(R.id.choreName);
		final EditText choreUsers = (EditText) findViewById(R.id.choreUsers);	
		final EditText choreWeight = (EditText) findViewById(R.id.choreWeight);
		final EditText choreDate = (EditText) findViewById(R.id.choreDate);	
		
		//get passed item information
		Intent i = getIntent();
		Bundle information = i.getExtras();
		
		choreName.setText(information.getString("Name"));
		choreUsers.setText(information.getString("Users"));
		choreWeight.setText(information.getString("Weight"));
		choreDate.setText(information.getString("Date"));
		
		Button save = (Button) findViewById(R.id.sav);
		Button cancel = (Button) findViewById(R.id.can);
		
		
		save.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent returnIntent = new Intent();
			    returnIntent.putExtra("Name", choreName.getText().toString());
			    returnIntent.putExtra("Users", choreUsers.getText().toString());
			    returnIntent.putExtra("Weight", choreWeight.getText().toString());
			    returnIntent.putExtra("Date", choreDate.getText().toString());
			    
			    setResult(RESULT_OK, returnIntent);
				finish();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED, returnIntent);
				finish();
			}
		});
		
	}
	
}
