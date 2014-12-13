package com.chorezilla.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_user);
		final EditText username = (EditText) findViewById(R.id.username_field);
		final EditText email = (EditText) findViewById(R.id.email_field);
		final EditText password = (EditText) findViewById(R.id.password_field);
		final EditText confirmPassword = (EditText) findViewById(R.id.confirm_password_field);
		
		Button createAccount = (Button) findViewById(R.id.create_acc);
		createAccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				String userName = username.getText().toString();
				if (userExists(userName)) { //if username is the same as the one entered
					Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
					username.requestFocus(); 							//Sets focus to username
					username.setText(""); email.setText(""); 			//Clears
					password.setText(""); confirmPassword.setText("");  //all fields
				} 
				else { //otherwise
					if (username.getText().toString().equals("") //if any fields are blank
						|| email.getText().toString().equals("") 
						|| password.getText().toString().equals("")
						|| confirmPassword.getText().toString().equals("")) {
						username.requestFocus(); 							//Sets focus to username
						email.setText(""); password.setText(""); //Clears 
						confirmPassword.setText("");  			 //all fields
						Toast.makeText(getApplicationContext(), "You must fill all fields", Toast.LENGTH_LONG).show();
					}
					else { //otherwise
						if (!password.getText().toString() //if passwords are the same
							.equals(confirmPassword.getText().toString())) {
							password.requestFocus();
							password.setText(""); 
							confirmPassword.setText(""); 
							Toast.makeText(getApplicationContext(), "Both passwords must match", Toast.LENGTH_LONG).show();
						}
						else{
							//Add the file
							writeToFile(username.getText().toString(), email.getText().toString(), password.getText().toString());
							Intent i = new Intent(RegisterScreen.this, //make the new window
									EditListScreen.class);
							int requestCode = 0;
							startActivityForResult(i, requestCode);
						}
					}
				}
			}
		});
	}

	//Prints all username files to log
	public void listUsers(){
		String[] files = fileList();
		for(int x = 0; x< files.length; x++)
			System.out.println(files[x]);
	}
	
	//Returns true if username file exists
	public boolean userExists(String username){
		String[] files = fileList();
		for (int x = 0; x < files.length; x++) {
			if((username+".txt").equals(files[x]))
				return true;
		}
		return false;
	}
	
	//Writes info to username file
	public void writeToFile(String username, String email, String password){
		FileOutputStream fOut = null;
		String contents = email + " " + password;
		try {
			fOut = openFileOutput(username + ".txt",
					MODE_WORLD_READABLE);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		OutputStreamWriter osw = new OutputStreamWriter(fOut);
		try {
			osw.write(contents);
			osw.flush();
			osw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = getApplicationContext().getFileStreamPath(username + ".txt");
		System.out.println(file.exists());
	}
}