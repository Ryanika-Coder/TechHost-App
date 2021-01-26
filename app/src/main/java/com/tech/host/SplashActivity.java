package com.tech.host;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.*;
import android.os.Build;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.content.Intent;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressbar1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
		initialize(savedInstanceState);
		Handler handler;
		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 3000);
    }
	
	private void initialize (Bundle savedInstanceState) {
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setNavigationBarColor(Color.parseColor("#008DCD"));
		}
		progressbar1.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN );
	}
}
