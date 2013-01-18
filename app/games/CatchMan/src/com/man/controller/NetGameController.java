package com.man.controller;

import org.json.JSONArray;
import org.json.JSONException;

import com.man.SceneHall;
import com.man.net.GameClient;
import com.man.plug.AlertNetGame;
import com.man.util.GameUtil;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 键盘控制器
 */
public class NetGameController extends GameController {

	private String TAG = this.getClass().getSimpleName();
	
	private GameClient client = null;
	
	private String userId = null;
	private String roomId = null;
	private String roleId = null;
	private String winner = null;
	
	public NetGameController(Activity activity, Bundle args) {
		super(activity);

		userId = args.getString("userId");
		roomId = args.getString("roomId");
		roleId = args.getString("roleId");
		Log.e("AAAA", userId+":"+roomId+":"+roleId);
		
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
			}
			@Override
			public void onLeaveRoom(String event, JSONArray arguments) {
				Log.w(TAG, "onLeaveRoom:" + arguments.toString());
			}
			@Override
			public void onSendRoomMsg(String event, JSONArray arguments) {
				Log.w(TAG, "onSendRoomMsg:" + arguments.toString());
				try {
					JSONArray msg = new JSONArray(arguments.getString(2));
					int action = msg.getInt(0);
					// 玩家操作
					if (action == 1) {
						// 获取角色
						int roleId = msg.getInt(1);
						// 男人玩家
						if (roleId == 0) {
							float x = (float) msg.getDouble(2);
							float y = (float) msg.getDouble(3);
							PointF direction = new PointF(x, y);
							man.forwardTo(direction);
						}
						// 女人玩家
						if (roleId == 1) {
							float x = (float) msg.getDouble(2);
							float y = (float) msg.getDouble(3);
							addWoman(newWoman(x, y));
						}
					}
					// 获胜信息
					if (action == 2) {
						String winner = msg.getString(1);
						int wscore = msg.getInt(2);
						showAlert(winner, wscore);
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
			}
		});
	}
	
	/**
	 * 网络游戏主逻辑
	 */
	@Override
	public void run() {
		runNetGame();
	}
	
	/**
	 * 用户操控逻辑
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (this.isLive()) {
			float x = event.getX();
			float y = event.getY();
			String msg = "[1," + roleId + "," + x + "," + y + "]";
			client.sendRoomMsg(msg);
		}
		return false;
	}
	
	@Override
	public void endGame(boolean showDialog) {
		// 记录分数
		int thisScore = (int) gameTime;
		if (roleId.equalsIgnoreCase("1")) {
			winner = "User " + userId;
			String msg = "[2,\"" + winner + "\"," + thisScore + "]";
			client.sendRoomMsg(msg);
		}
		handler.removeCallbacks(this);
		setLive(false);
	}
	
	private void showAlert(String winner, int wscore) {
		AlertNetGame alertNetGame = new AlertNetGame(context);
		alertNetGame.setWinner(winner);
		alertNetGame.setWinScore(wscore);
		alertNetGame.setBtnCallback(new AlertNetGame.BtnCallback(){
			@Override
			public void onQuit() {
				client.leaveRoom();
				GameUtil.forward(activity, SceneHall.class);
			}
		});
		alertNetGame.show();
	}
	
}
