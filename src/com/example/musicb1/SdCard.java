package com.example.musicb1;



import java.util.HashMap;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.DownloadManager.Query;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class SdCard extends Activity {
	ListView lv;
	ContentResolver mContentResolver;
	TextView tv,tv2;
	ArrayAdapter<HashMap<String, String>> aa;
	MediaPlayer mp;
	NotificationManager nm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sdcard);
		lv=(ListView) findViewById(R.id.listView1);
		aa=new ArrayAdapter<HashMap<String,String>>(this,android.R.layout.simple_list_item_1);
		ArrayAdapter<String> aa1=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
//		tv=(TextView) findViewById(R.id.textView1);
		//tv2=(TextView) findViewById(R.id.textView2);
		nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mContentResolver=getApplicationContext().getContentResolver();
		Uri uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] col={
					MediaStore.Audio.Media._ID,
					MediaStore.Audio.Media.DISPLAY_NAME,
					MediaStore.Audio.Media.DATA
				
		};
		Cursor c=mContentResolver.query(uri,col, null, null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		
		if(c!=null){
			c.moveToFirst();
			
			while(c.moveToNext()){
				HashMap<String,String> song=new HashMap<String, String>();
				song.put("Title",c.getString(1));
				song.put("path",c.getString(2));
				aa1.add(c.getString(1));
				aa.add(song);
			}
		}
		lv.setAdapter(aa1);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String,String> song=aa.getItem(position);
				String path=song.get("path");
				String name=song.get("Title");
				Intent intent=new Intent(SdCard.this,Playing.class);
				intent.putExtra("path",path);
				intent.putExtra("name",name);
				notification(name);
//				tv.setText(path);
				startActivity(intent);
				/*Uri uri=Uri.parse(path);
				
				if(mp!=null){
					try{
						mp.release();
						mp=null;
					}catch(Exception e){}
					mp=MediaPlayer.create(getApplicationContext(),uri);
					mp.start();
				}else{
					mp=MediaPlayer.create(getApplicationContext(),uri);
					mp.start();
				}*/
			}
			
		});
		
	}
	
	public void notification(String name) {
		RemoteViews remoteviews=new RemoteViews(getPackageName(),R.layout.notiflayout);
		Intent i=new Intent(SdCard.this,SdCard.class);
		PendingIntent pi=PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.pic1)
		.setTicker(getString(R.string.customnotificationticker))
		.setAutoCancel(true)
		.setContentIntent(pi)
		.setContent(remoteviews);		
		remoteviews.setTextViewText(R.id.textView1,name);
		
		NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(0,builder.build());
		
		
	}
	
}
