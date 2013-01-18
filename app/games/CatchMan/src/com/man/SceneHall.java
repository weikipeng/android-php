package com.man;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.man.net.GameClient;
import com.man.util.GameUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SceneHall extends Activity {
	
	private String TAG = this.getClass().getSimpleName();
	
	private GameClient client = null;
	
	private Button btnCreateRoom = null;
	private LinearLayout listAllRooms = null;
	
	private LayoutInflater inflater = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_hall);
		
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		listAllRooms = (LinearLayout) this.findViewById(R.id.list_all_rooms);
		
		// init game client
		client = new GameClient();
		client.setListener(new GameClient.Listener() {
			@Override
			public void onConnect() {
				Log.w(TAG, "onConnect:" + client.getClientId());
				client.login(client.getClientId());
				client.updateRooms();
			}
			@Override
			public void onMessage(String event, JSONArray arguments) {
				Log.w(TAG, "onMessage:" + arguments.toString());
			}
			@Override
			public void onDisconnect(int code, String reason) {
				Log.w(TAG, "onDisconnect:" + code);
			}
			@Override
			public void onError(Exception error) {
				Log.w(TAG, "onError:" + error.getMessage());
			}
			@Override
			public void onLogin(String event, JSONArray arguments) {
				Log.w(TAG, "onConnect:" + arguments.toString());
				Toast.makeText(SceneHall.this, arguments.toString(), Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onJoinRoom(String event, JSONArray arguments) {
				Log.w(TAG, "onJoinRoom:" + arguments.toString());
				client.updateRooms();
				String userId = null;
				String roomId = null;
				try {
					userId = arguments.getString(0);
					roomId = arguments.getString(1);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// create and enter the room
				if (userId != null && roomId != null) {
					if (roomId.equalsIgnoreCase(client.getClientId())) {
						enterRoom(roomId);
					}
				}
			}
			@Override
			public void onLeaveRoom(String event, JSONArray arguments) {
				Log.w(TAG, "onLeaveRoom:" + arguments.toString());
			}
			@Override
			public void onSendRoomMsg(String event, JSONArray arguments) {
				Log.w(TAG, "onGetRoomMsg:" + arguments.toString());
			}
			@Override
			public void onUpdateRooms(String event, JSONArray arguments) {
				Log.w(TAG, "onUpdateRooms:" + arguments.toString());
				// update room list
				updateRoomList(arguments);
			}
			@Override
			public void onGetRoomUsers(String event, JSONArray arguments) {
				Log.w(TAG, "onGetRoomUsers:" + arguments.toString());
			}
		});
		
		// update room list
		if (client.isConnected()) {
			client.updateRooms();
		}
		
		// create host
		btnCreateRoom = (Button) this.findViewById(R.id.btn_create_room);
		btnCreateRoom.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String roomId = client.getClientId();
				client.joinRoom(roomId);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:
				break;
			case KeyEvent.KEYCODE_BACK:
				quitHall();
				break;
			default:
				break;
		}
		return false;
	}
	
	public void enterRoom(String roomId) {
		Bundle params = new Bundle();
		params.putString("userId", client.getClientId());
		params.putString("roomId", roomId);
		GameUtil.forward(this, SceneRoom.class, params);
	}
	
	public void quitHall() {
//		try {
//			Log.w(TAG, "client disconnect");
//			client.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		GameUtil.forward(this, SceneMenu.class);
	}
	

	
	@SuppressWarnings("unchecked")
	private void updateRoomList(JSONArray arguments) {
		JSONObject rooms = null;
		try {
			// remove all rooms
			listAllRooms.removeAllViews();
			// add online rooms
			rooms = new JSONObject((String) arguments.get(0));
			Iterator<String> it = rooms.keys();
			while (it.hasNext()) {
				String roomId = it.next();
				JSONArray users = rooms.optJSONArray(roomId);
				String roomText = "Room " + roomId + " (" + users.length() + "/2)";
				// create room
				View roomView = inflater.inflate(R.layout.scene_hall_room, null);
				TextView tv = (TextView) roomView.findViewById(R.id.scene_hall_room_name);
				tv.setText(roomText);
				tv.setTag(roomId);
				// stop join when room full
				if (users.length() < 2) {
					tv.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
							String roomId = v.getTag().toString();
							client.joinRoom(roomId);
							enterRoom(roomId);
						}
					});
				}
				listAllRooms.addView(roomView);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}