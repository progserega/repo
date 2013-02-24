package com.RemoteOsMoDroidClient;

import java.util.Timer;
import java.util.TimerTask;

import com.OsMoDroid.IRemoteOsMoDroidService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RemoteOsMoDroidClientActivity extends Activity {
	TextView t;
	TextView infotextView;
	Button b1;
	Button b2;
	 TimerTask task = new TimerTask(){
	        public void run() {
	        	RemoteOsMoDroidClientActivity.this.runOnUiThread(getinfo);
	        }
	    };
	    Timer timer = new Timer();
	    
	
	final Runnable getinfo = new Runnable() {
        public void run() {
        	if (connected){ getinfo();}	
        }
    };
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		timer.schedule(task, 0, 1000);
	}
	Button b3;
	Button getinfobutton;
	IRemoteOsMoDroidService mIRemoteService;
	boolean connected=false;

    ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			 mIRemoteService = IRemoteOsMoDroidService.Stub.asInterface(service);
			 connected=true;
			 try {
				
				 t.setText(Integer.toString(mIRemoteService.getVersion()));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}



          

    };
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent serviceIntent = (new Intent("OsMoDroid.remote"));
t = (TextView) findViewById(R.id.t1);
infotextView = (TextView) findViewById(R.id.infotextView);
b1 = (Button) findViewById(R.id.button1);
b2 = (Button) findViewById(R.id.Button2);
b3 = (Button) findViewById(R.id.Button3);
getinfobutton = (Button) findViewById(R.id.getInfobutton);


b1.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
		 Intent serviceIntent = (new Intent("OsMoDroid.remote"));
		startService(serviceIntent);

	}
});	

b2.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
		if (!(mConnection==null)){
    		try {
				unbindService(mConnection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

	}
});	

b3.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
		if (!(mConnection==null)){
			 try {
					
				mIRemoteService.Deactivate();
				 Intent serviceIntent = (new Intent("OsMoDroid.remote"));
					stopService(serviceIntent);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
});	

getinfobutton.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
	

	}
});	

             
		bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
       
		
        
    }
    
    void getinfo(){
    	String text ="";
    	try {
    		
    		 for (int i = 0; i < mIRemoteService.getNumberOfLayers(); i++) {
    			 
    			 text=mIRemoteService.getLayerName(mIRemoteService.getLayerId(i));
    			 for (int k = 0; k < mIRemoteService.getNumberOfObjects(mIRemoteService.getLayerId(i)); k++) {
    				 text=text+"\n"+mIRemoteService.getObjectName(mIRemoteService.getLayerId(i),(mIRemoteService.getObjectId(mIRemoteService.getLayerId(i), k)))+" "+mIRemoteService.getObjectLat(mIRemoteService.getLayerId(i),(mIRemoteService.getObjectId(mIRemoteService.getLayerId(i), k)))+" "+mIRemoteService.getObjectLon(mIRemoteService.getLayerId(i),(mIRemoteService.getObjectId(mIRemoteService.getLayerId(i), k)));
    				 
    			 }
    			 text=text+"\n";
    			 }
    		
    		
    	} catch (RemoteException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	infotextView.setText(text);
    	
    }
    
    @Override
    protected void onDestroy() {
    	if (!(mConnection==null)){
    		try {
				unbindService(mConnection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	super.onDestroy();
	}

    
    
}