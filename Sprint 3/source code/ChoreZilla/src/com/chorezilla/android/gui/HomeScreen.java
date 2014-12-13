package com.chorezilla.android.gui;

import com.chorezilla.android.R;

import android.view.View.OnClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends Activity {

	@Override
	public void onCreate(Bundle icicle) 
	{
		super.onCreate(icicle);
		setContentView(R.layout.activity_chore_list);
		
		Button enter = (Button) findViewById(R.id.enter);
		Button register = (Button) findViewById(R.id.register);
		
		enter.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(HomeScreen.this, SignInScreen.class);
				int requestCode = 0;
				startActivityForResult(i, requestCode);
			}
		});
		
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(HomeScreen.this, RegisterScreen.class);
				int requestCode = 0;
				startActivityForResult(i, requestCode);
			}
		});
	}

}