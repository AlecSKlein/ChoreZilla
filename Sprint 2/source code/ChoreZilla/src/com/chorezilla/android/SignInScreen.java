package com.chorezilla.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignInScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_back);
		CheckBox rememberMe = (CheckBox) findViewById(R.id.remember_me);
		final EditText username = (EditText) findViewById(R.id.username);
		final EditText password = (EditText) findViewById(R.id.password);
		username.requestFocus();
		if(rememberMe.isChecked())
		{
			username.setText(""); //fetch previously used username from file
			password.requestFocus();
		}
		
		Button signIn = (Button) findViewById(R.id.sign_in);
		
		signIn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean exists = false;
				String[] files = fileList();
				for(int x = 0; x< files.length; x++){ //Checks through list for file's existence
					if((username.getText().toString()+".txt").equals(files[x])){
						exists = true;
						break;
					}
				}
				if(exists) //If username file is found
				{
				Intent i = new Intent(SignInScreen.this, EditListScreen.class);
				int requestCode = 0;
				startActivityForResult(i, requestCode);
				}
				else{ //If username file is not found
					Toast.makeText(getApplicationContext(), "Username does not exist, go back and create an account", Toast.LENGTH_LONG).show();
					username.requestFocus();
					username.setText("");
					password.setText("");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_in_screen, menu);
		return true;
	}

}
