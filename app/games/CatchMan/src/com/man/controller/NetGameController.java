package com.man.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.man.SceneHall;
import com.man.cfg.CFG;
import com.man.module.Woman;
import com.man.net.GameClient;
import com.man.net.GameClientListener;
import com.man.plug.AlertNetGame;
import com.man.plug.MsgNetScore;
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
//	private String roomId = null;
	private String roleId = null;
	private String winner = null;
	
	private int winTime = 30; // win after these seconds
	
	float xRange1;
	float xRange2;
	float yRange1;
	float yRange2;
	
	public NetGameController(Activity activity, Bundle args) {
		super(activity);

		userId = args.getString("userId");
//		roomId = args.getString("roomId");
		roleId = args.getString("roleId");
		
		client = new GameClient();
		client.setListener(new NetGameControllerListener());
		
		xRange1 = CFG.CLICK_MARGIN;
		yRange1 = CFG.CLICK_MARGIN;
		xRange2 = CFG.SCREEN_WIDTH - CFG.CLICK_MARGIN;
		yRange2 = CFG.SCREEN_HEIGHT - CFG.CLICK_MARGIN;
	}
	
	/**
	 * 网络游戏主逻辑
	 */
	protected void runGame() {
		// 定时执行
		handler.postDelayed(this, CFG.DELAY_TIME);

		// 游戏计时
		gameTime += CFG.DELAY_TIME / 1000.0;
		msgNetScore.setTime((int) gameTime);
		if (gameTime >= winTime) {
			// 设置标志
			setLive(false);
			// 此模式下男人胜利
			if (roleId.equalsIgnoreCase("0")) {
				winner = "User " + userId;
				String msg = "[2,\"" + winner + "\"," + winTime + "]";
				client.sendRoomMsg(msg);
			}
			// 停止监听
			handler.removeCallbacks(this);
		}
		
		// 游戏执行
		if (isLive()) {
			// 移动男人
			man.move();
			// 移除出界女人
			removeWomen();
			// 重绘画面
			// GameView.redraw -> GameView.onDraw -> Controller.drawAll
			gameView.redraw();
			// 检测碰撞
			checkCollide();
		}
	}
	
	/**
	 * 用户操控逻辑
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (this.isLive()) {
			float x = event.getX();
			float y = event.getY();
			// 男人玩家
			if (roleId.equalsIgnoreCase("0")) {
				client.sendRoomMsg("[1,0," + x + "," + y + "]");
			}
			// 女人玩家
			if (roleId.equalsIgnoreCase("1")) {
				if (checkAddWoman(x, y)) {
					client.sendRoomMsg("[1,1," + x + "," + y + "]");
				}
			}
		}
		return false;
	}
	
	/**
	 * 新游戏
	 */
	@Override
	public void newGame() {
		// 产生一个男人
		man = newMan();

		// 产生一个女人
		women = new ArrayList<Woman>();

		// 计时
		gameTime = 0;

		// 创建一个时间提示器
		msgNetScore = new MsgNetScore();
		msgNetScore.setUser(userId);
		msgNetScore.setRole(roleId);
		msgNetScore.setWinTime(winTime);
		msgNetScore.setTime((int) gameTime);

		// 开始
		startGame();
	}
	
	@Override
	public void endGame(boolean showDialog) {
		// 设置标志
		setLive(false);
		// 此模式下女人胜利
		if (roleId.equalsIgnoreCase("1")) {
			winner = "User " + userId;
			String msg = "[2,\"" + winner + "\"," + winTime + "]";
			client.sendRoomMsg(msg);
		}
		// 停止监听
		handler.removeCallbacks(this);
	}
	
	private void showAlert(String winner, int wscore) {
		AlertNetGame alertNetGame = new AlertNetGame(context);
		alertNetGame.setWinner(winner);
		alertNetGame.setWinScore(wscore);
		alertNetGame.setBtnCallback(new AlertNetGame.BtnCallback(){
			@Override
			public void onQuit() {
				client.leaveRoom(); // 离开房间
				GameUtil.forward(activity, SceneHall.class);
			}
		});
		alertNetGame.show();
	}
	
	private class NetGameControllerListener extends GameClientListener {
		
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
	}
	
	private boolean checkAddWoman(float x, float y) {
		// 屏幕中间的方形部分不允许添加女人
		if (x > xRange1 && x < xRange2 && y > yRange1 && y < yRange2) {
			return false;
		}
		// 四周才可以添加
		return true;
	}
}
