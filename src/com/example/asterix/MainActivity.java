package com.example.asterix;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tv;
	ImageView im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		touchDisplay();
	}

	public void touchDisplay(){
		tv = (TextView) findViewById(R.id.intro);
		im = (ImageView) findViewById(R.id.imageView1);
		
		final AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
		fadeIn.setDuration(1200);
		fadeIn.setFillAfter(true);
		
		final AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
		fadeIn.setDuration(1200);
		fadeOut.setFillAfter(true);
		ImageButton ib = (ImageButton) findViewById(R.id.imageButton1);
		
		ib.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v){
				tv.startAnimation(fadeIn);
				tv.setVisibility(View.VISIBLE);
				im.startAnimation(fadeIn);
				im.setVisibility(View.VISIBLE);
			}
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		 if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
			// Toast.makeText(this, "Landscape", Toast.LENGTH_LONG).show();
			 setContentView(R.layout.detailed_info);
			 
			 ImageView img_holder = (ImageView)findViewById(R.id.imageView1);
			 ImageDownloader id_object = new ImageDownloader();
			
			 id_object.download("http://www.asterix.com/imgs/ast-home.png",img_holder);
		} 
		 else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			// Toast.makeText(this, "Portrait", Toast.LENGTH_SHORT).show();
			 setContentView(R.layout.activity_main);
			 touchDisplay();
		 }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
