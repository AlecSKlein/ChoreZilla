package com.chorezilla.android.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import com.chorezilla.android.R;
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
		final EditText username = (EditText) findViewById(R.id.username);
		final EditText password = (EditText) findViewById(R.id.password);
		final CheckBox rememberMe = (CheckBox) findViewById(R.id.remember_me);
		
		rememberMe.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				File file = getApplicationContext().getFileStreamPath(
						"RememberMe.txt");
				file.delete();
				FileOutputStream fOut = null;
				try {
					fOut = openFileOutput("RememberMe.txt", MODE_WORLD_READABLE);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				OutputStreamWriter osw = new OutputStreamWriter(fOut);
				try {
					osw.write(username.getText().toString());
					osw.flush();
					osw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				file = getApplicationContext().getFileStreamPath(
						"RememberMe.txt");
				if(!rememberMe.isChecked())
					file.delete();
				System.out.println(file.exists());

			}
		});
		File file = getApplicationContext().getFileStreamPath(
				"RememberMe.txt");
		username.requestFocus();
		if (file.exists()) {
			FileInputStream fis = null;
			try {
				fis = openFileInput("RememberMe.txt");
			} catch (FileNotFoundException e2) {
			}
			InputStream inStreamObject = ((InputStream) fis);
			Scanner sc = new Scanner(inStreamObject);
			username.setText(sc.nextLine()); // fetch previously used username
												// from file
			password.requestFocus();
		}

		Button signIn = (Button) findViewById(R.id.sign_in);

		signIn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				boolean exists = false;
				boolean passwordCorrect = false;
				;
				String[] files = fileList();
				for (int x = 0; x < files.length; x++) { // Checks through list
															// for file's
															// existence
					if ((username.getText().toString() + ".txt")
							.equals(files[x])) {
						exists = true;
						passwordCorrect = isCorrectPassword(username.getText()
								.toString(), password.getText().toString());
						break;
					}
				}
				if (exists) // If username file is found
				{
					if (passwordCorrect) {
						Intent i = new Intent(SignInScreen.this,
								EditListScreen.class);
						int requestCode = 0;
						startActivityForResult(i, requestCode);
					} else {
						Toast.makeText(getApplicationContext(),
								"Password does not match username, try again",
								Toast.LENGTH_LONG).show();
						password.requestFocus();
						password.setText("");
					}
				} else { // If username file is not found
					Toast.makeText(getApplicationContext(),
							"Username does not exist, have you registered?",
							Toast.LENGTH_LONG).show();
					username.requestFocus();
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

	public boolean isCorrectPassword(String username, String inputPassword) {
		FileInputStream fis = null;
		try {
			fis = openFileInput(username + ".txt");
		} catch (FileNotFoundException e2) {
		}
		InputStream inStreamObject = ((InputStream) fis);
		Scanner sc = new Scanner(inStreamObject);
		String line = sc.nextLine();
		int index = line.indexOf(" ");
		if (line.substring(0, index).equals(inputPassword))
			return true;
		System.out.println(line);
		return false;
	}
}
