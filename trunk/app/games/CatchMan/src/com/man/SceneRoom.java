package com.man;

import org.json.JSONArray;
import org.json.JSONException;
import com.man.net.GameClient;
import com.man.util.GameUtil;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SceneRoom extends Activity {
	
	private String TAG = this.getClass().getSimpleName();
	
	private String userId = null;
	private String roomId = null;
	private String roleId = null;
	private GameClient client = null;
	
	private Button btnStartGame = null;
	private LinearLayout listAllPlayers = null;
	
	private LayoutInflater inflater = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_room);
		
		Bundle params = this.getIntent().getExtras();
		userId = params.getString("userId");
		roomId = params.getString("roomId");
		if (userId.equalsIgnoreCase(roomId)) {
			roleId = "0"; // 男人角色
		} else {
			roleId = "1"; // 女人角色
		}
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		
		listAllPlayers = (LinearLayout) this.findViewById(R.id.list_all_players);
		
		// init game client
		client = new GameClient();
		client.setListener(new GameClient.Listener() {
			@Override
			public void onConnect() {
				Log.w(TAG, "onConnect:" + userId);
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
			}
			@Override
			public void onJoinRoom(String event, JSONArray arguments) {
				Log.w(TAG, "onJoinRoom:" + arguments.toString());
				client.getRoomUsers(roomId);
				
			}
			@Override
			public void onLeaveRoom(String event, JSONArray arguments) {
				Log.w(TAG, "onLeaveRoom:" + arguments.toString());
				client.getRoomUsers(roomId);
			}
			@Override
			public void onSendRoomMsg(String event, JSONArray arguments) {
				Log.w(TAG, "onSendRoomMsg:" + arguments.toString());
				try {
					JSONArray msg = new JSONArray(arguments.getString(2));
					int action = msg.getInt(0);
					// 游戏状态
					if (action == 0) {
						int command = msg.getInt(1);
						if (command == 1) {
							enterGame();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onUpdateRooms(String event, JSONArray arguments) {
				Log.w(TAG, "onUpdateRooms:" + arguments.toString());
			}
			@Override
			public void onGetRoomUsers(String event, JSONArray arguments) {
				Log.w(TAG, "onGetRoomUsers:" + arguments.toString());
				// update player list
				updatePlayers(arguments);
			}
		});
		
		// get room users
		if (client.isConnected()) {
			client.getRoomUsers(roomId);
		}
		
		// start net game
		btnStartGame = (Button) this.findViewById(R.id.btn_start_game);
		btnStartGame.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				enterGame();
				String msg = "[0,1]";
				client.sendRoomMsg(msg);
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
				quitRoom();
				break;
			default:
				break;
		}
		return false;
	}
	
	public void enterGame() {
		Bundle params = new Bundle();
		params.putString("userId", userId);
		params.putString("roomId", roomId);
		params.putString("roleId", roleId);
		GameUtil.forward(this, SceneNetGame.class, params);
	}
	
	public void quitRoom() {
		client.leaveRoom();
		GameUtil.forward(this, SceneHall.class);
	}
	
	private void updatePlayers(JSONArray arguments) {
		JSONArray users = null;
		try {
			// remove all rooms
			listAllPlayers.removeAllViews();
			// add online rooms
			users = new JSONArray((String) arguments.get(0));
			for (int i = 0; i < users.length(); i++) {
				String playerId = users.getString(i);
				String playerName = "User " + playerId;
				// create player
				View playerView = inflater.inflate(R.layout.scene_room_player, null);
				ImageView iv = (ImageView) playerView.findViewById(R.id.scene_room_player_face);
				iv.setImageDrawable(this.getPlayerFace(i));
				TextView tv = (TextView) playerView.findViewById(R.id.scene_room_player_name);
				tv.setText(playerName);
				listAllPlayers.addView(playerView);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private Drawable getPlayerFace(int id) {
		// role : man
		if (id == 0) {
			return this.getResources().getDrawable(R.drawable.w2);
		}
		// role : catcher
		return this.getResources().getDrawable(R.drawable.w1);
	}
	
}