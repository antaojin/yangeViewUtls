package com.example.myVIewUtils;

import java.util.concurrent.Semaphore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@ViewInject(R.id.tv1)
	private TextView tView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		Log.d("tag", tView.toString());
		
		
	}
	@OnClick(R.id.btn1)
	private void myClick(View view){
		Toast.makeText(this, "我被点击了", Toast.LENGTH_SHORT).show();
	}

}
