package com.example.musicb1;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class Playing extends Activity implements SensorEventListener{
	ImageButton b1,b3,b4, b2,b5;
	ImageView iv;
	MediaPlayer mp;
	double starttime=0,finaltime=0;
	Handler myhandler=new Handler();
	int forwwardtime=5000,backwardtime=5000;
	SeekBar seekbar;
	TextView tv1,tv2,tv3;
	public static int onetimeonly=0;
	int f1=0;
	Sensor sensor;
	SensorManager sm;
	boolean getImg=false;
	PowerManager pm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playing_main);
		b1=(ImageButton) findViewById(R.id.button);
		b2=(ImageButton) findViewById(R.id.button2);
		b3=(ImageButton) findViewById(R.id.button3);
		b4=(ImageButton) findViewById(R.id.button4);
		b5=(ImageButton) findViewById(R.id.button5);
		tv1=(TextView) findViewById(R.id.textView2);
		tv2=(TextView) findViewById(R.id.textView3);
		tv3=(TextView) findViewById(R.id.textView4);
		sm=(SensorManager)getSystemService(SENSOR_SERVICE);
		sensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		Intent intent=getIntent();
		String path=intent.getStringExtra("path");
		String name=intent.getStringExtra("name");
		onetimeonly=0;
		tv3.setText(name);
		Uri uri=Uri.parse(path);
		mp=MediaPlayer.create(this,uri);
		seekbar=(SeekBar) findViewById(R.id.seekBar1);
		seekbar.setClickable(false);
		b2.setEnabled(false);
		Toast.makeText(getApplicationContext(), "Playing Sound", Toast.LENGTH_SHORT).show();
		mp.start();
		
		finaltime=mp.getDuration();
		starttime=mp.getCurrentPosition();
		
		if(onetimeonly==0){
			seekbar.setMax((int) finaltime);
			onetimeonly=1;
		}
		tv2.setText(String.format("%d min, %d sec",
	            TimeUnit.MILLISECONDS.toMinutes((long) finaltime),
	            TimeUnit.MILLISECONDS.toSeconds((long) finaltime) -
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finaltime)))
	            );
		
		tv1.setText(String.format("%d min, %d sec",
	            TimeUnit.MILLISECONDS.toMinutes((long) starttime),
	            TimeUnit.MILLISECONDS.toSeconds((long) starttime) -
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) starttime)))
	            );
		seekbar.setProgress((int) starttime);
		myhandler.postDelayed(UpdateSongTime, 100);
		b2.setEnabled(true);
		b3.setEnabled(false);
		
	
		
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Playing Sound", Toast.LENGTH_SHORT).show();
				mp.start();
				
				finaltime=mp.getDuration();
				starttime=mp.getCurrentPosition();
				
				if(onetimeonly==0){
					seekbar.setMax((int) finaltime);
					onetimeonly=1;
				}
				tv2.setText(String.format("%d min, %d sec",
			            TimeUnit.MILLISECONDS.toMinutes((long) finaltime),
			            TimeUnit.MILLISECONDS.toSeconds((long) finaltime) -
			            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finaltime)))
			            );
				
				tv1.setText(String.format("%d min, %d sec",
			            TimeUnit.MILLISECONDS.toMinutes((long) starttime),
			            TimeUnit.MILLISECONDS.toSeconds((long) starttime) -
			            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) starttime)))
			            );
				seekbar.setProgress((int) starttime);
				myhandler.postDelayed(UpdateSongTime, 100);
				b2.setEnabled(true);
				b3.setEnabled(false);
				
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Pausing Sound", Toast.LENGTH_SHORT).show();
				mp.pause();
				b2.setEnabled(false);
				b3.setEnabled(true);
			}
		});
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int temp=(int) starttime;
				
				if((temp+forwwardtime) <= finaltime){
					starttime=starttime+forwwardtime;
					mp.seekTo((int) starttime);
					Toast.makeText(getApplicationContext(), "You have jumped 5 secs", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "You cannot jump 5 secs", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int temp=(int) starttime;
				
				if((temp-backwardtime) > 0){
					starttime=starttime-backwardtime;
					mp.seekTo((int) starttime);
					Toast.makeText(getApplicationContext(), "You have jumped back 5 secs", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "You cannot jump back 5 secs", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		b5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(f1==0){
					b5.setBackgroundDrawable(getResources().getDrawable(R.drawable.stary));
					f1=1;
				}
				else{
					b5.setBackgroundDrawable(getResources().getDrawable(R.drawable.starb));
					f1=0;
				}
				
			}
		});
	}
	
	

	private Runnable UpdateSongTime=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			starttime=mp.getCurrentPosition();
			tv1.setText(String.format("%d min, %d sec",
			         
			         TimeUnit.MILLISECONDS.toMinutes((long) starttime),
			         TimeUnit.MILLISECONDS.toSeconds((long) starttime) -
			         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
			         toMinutes((long) starttime)))
			         );
			seekbar.setProgress((int) starttime);
			myhandler.postDelayed(this, 100);
		}
	};

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.values[0]==3){
			mp.pause();
		}
		else{
			mp.start();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sm.unregisterListener(this,sensor);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sm.registerListener(this, sensor,sm.SENSOR_DELAY_NORMAL);
	}
	
}
