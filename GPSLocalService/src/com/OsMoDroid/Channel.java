package com.OsMoDroid;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Channel {
	IM chanIM;
	public String name;
	public String u;
	public String created;
	public String ch;
	public String url;
	public List<Device> deviceList= new ArrayList<Device>();
	public List<String> messagesstringList= new ArrayList<String>();
	
	public Channel(){
		
	}
	
	public Channel( 
	 String name,
	 String u,
	 String created){
		 this.u=u;
		 this.name=name;
		 this.created=created;
		}
	
	public Channel(JSONObject jo)
	{
		
		this.name=jo.optString("name");
		this.u=jo.optString("u");
		this.created=jo.optString("created");
		this.ch=jo.optString("ch");
		this.url="http://esya.ru/om/"+jo.optString("url");
	
	
		 JSONArray a =jo.optJSONArray("user");
		for (int i = 0; i < a.length(); i++) {
	 			JSONObject jsonObject;
				try {
					jsonObject = a.getJSONObject(i);
				
//	 			 u = 1163
//	 				    uid = 192
//	 				    lat = 54.907503
//	 				    lon = 41.271125
//	 				    state = 0
//	 				    online = 0
//	 				    name = toxIC jiayu
//	 				    icon = 1
		 			
	
		this.deviceList.add(new Device(jsonObject.getString("u")
				, jsonObject.getString("name"),""
				,"",
				"",
				"",
				jsonObject.getString("lat"),
				jsonObject.getString("lon"),
				jsonObject.getString("online"),
				jsonObject.getString("state")
				) );
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
		

	//channelList.add(chanitem);
//	netutil.newapicommand((ResultsListener)serContext, "om_channel_user:"+chanitem.u);


	 		 }
		
		connect();
				}
	
	public void connect(){
		Log.d(getClass().getSimpleName(),"Channel connecting");	
		chanIM= new IM("om_"+this.ch+",om_"+this.ch+"_chat", LocalService.serContext, 2);
		
	}

	public void close(){
		if(chanIM!=null){  chanIM.close();}
	}
	
	@Override
	public String toString() {
		
		
		return "Channel:u="+u+",name="+name+",added="+created;
	}

	

}
