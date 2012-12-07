package com.man.entity;

import com.man.cfg.CFG;

import android.graphics.PointF;
import android.util.FloatMath;

/**
 * Ů��
 */
public abstract class Woman extends People{

	/**
	 * ����
	 */
	protected PointF direction;

	/**
	 * ����һ���ˣ�һ�������ڸոճ�����ʱ�򣬽��Լ��ķ���������
	 * @param people
	 */
	public void lookPeople(People people){
		if (this.direction == null){
			this.direction = new PointF();
		}
		float dtx = people.location.x - this.location.x;
		float dty = people.location.y - this.location.y;
		
		this.direction.x = (dtx / FloatMath.sqrt(dtx * dtx + dty * dty) * CFG.WOMAN_SPEED);// TODO ��������Ż�
		this.direction.y = (dty / FloatMath.sqrt(dtx * dtx + dty * dty) * CFG.WOMAN_SPEED);
		
		float angle = this.getRotateAngle(this.location, people.location);
		this.setAngle(angle);
	}

	/**
	 * �ƶ�<br/>
	 * ������������
	 */
	public void move() throws PeopleMoveException{
		//������˱߽�����쳣
		float x = location.x + direction.x;
		float y = location.y + direction.y;
		if ((x < 0 || x > CFG.SCREEN_WIDTH) || (y < 0 || y > CFG.SCREEN_HEIGHT)){
			throw new PeopleMoveException();
		}
		location.x = x;
		location.y = y;
	}

	/**
	 * �����ײ
	 */
	@Override
	public boolean collide(People people) {
		//�������Բ�Ƿ��ཻ
		float x = people.location.x - this.location.x;
		float y = people.location.y - this.location.y;
		float distance = FloatMath.sqrt(x * x + y * y);
		return distance < people.radius + this.radius;
	}
	
}