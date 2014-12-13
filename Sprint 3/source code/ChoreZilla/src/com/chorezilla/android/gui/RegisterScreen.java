package com.chorezilla.android.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.chorezilla.android.R;
import com.chorezilla.android.UserCtrl;

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
				String eMail = email.getText().toString();
				String passWord = password.getText().toString();
				String confirm = confirmPassword.getText().toString();

				if (!userExists(userName)) 
				{
					//validate profile info
					String message = UserCtrl.validateProfile(userName, eMail, passWord, confirm);
					
					//create user, push to database
					if (message.equals("")) 
					{
						UserCtrl.newUser(userName, eMail, passWord, confirm);
						writeToFile(userName, eMail, passWord); //temporary system for storing user information
						Intent i = new Intent(RegisterScreen.this, HomeScreen.class); // make the new window
						startActivityForResult(i, 0);
					} 
					else 
					{
						Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
						password.requestFocus();
						password.setText("");
						confirmPassword.setText("");
					}
				} 
				else 
				{
					Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_LONG).show();
					username.requestFocus();
					password.setText("");
					confirmPassword.setText("");
				}
			}
		});
	}

	// temporary method for checking if user information is stored in android file system, will be changed to DB later
	public boolean userExists(String username) 
	{
		String[] files = fileList();
		for (int x = 0; x < files.length; x++) {
			if ((username + ".txt").equals(files[x]))
				return true;
		}
		return false;
	}

	// temporary method for writing user information to android file system, will be changed to DB later
	public void writeToFile(String username, String email, String password) 
	{
		FileOutputStream fOut = null;
		String contents = password + " " + email;
		try {
			fOut = openFileOutput(username + ".txt", MODE_WORLD_READABLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
		File file = getApplicationContext()
				.getFileStreamPath(username + ".txt");
		System.out.println(file.exists());
	}
}