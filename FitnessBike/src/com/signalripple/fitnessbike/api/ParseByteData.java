package com.signalripple.fitnessbike.api;

import android.util.Log;

import com.signalripple.fitnessbike.bean.BlueToohDataNormal;
import com.signalripple.fitnessbike.bean.BlueToohDataSynch;

/**
 * 蓝牙通讯协议数据解析封存工具
 * @author xushiyong
 */
public class ParseByteData {

	/**
	 * 正常工作模式  ： 解析数据并填充尸体
	 * @param data 通讯中从获取的字节数组   
	 * @return 已经被填充的实体
	 */
	public static Object parseAndFillData(byte[] data)
	{
		if(data == null)
			return null;
		
		Log.i("XU", "数据长度："+data.length);
		

		// 骑行机状态
		int bikeState = (data[1] >> 4) & 0x07;
		
		// 正常模式
		if(bikeState == 1)
		{
			return normalMode(data,bikeState);
		}
		// 数据同步模式
		else if(bikeState == 2)
		{
			return synchronousMode();
		}
		
		return null;
	}
	
	/**
	 * 数据同步模式
	 * @return
	 */
	private static BlueToohDataSynch synchronousMode()
	{
		return new BlueToohDataSynch();
	}
	
	/**
	 * 正常工作模式
	 * @param data
	 * @param bikeState
	 * @return
	 */
	private static BlueToohDataNormal normalMode(byte[] data,int bikeState)
	{
		// 阻力
		float gear = data[1] & 0x0f;
		// 轮子的周长  高位（亦即整数部分）
		float girthH = (data[2] & 0x07);
		// 轮子的周长  低位（亦即浮点数部分）
		float girthL = (data[2] >> 3) & 0x0f;
		// 拼接的全周长
		float girth = Float.valueOf(String.valueOf(girthH)+"."+String.valueOf(girthH));
		// 特定RPM值
		double RPM = ((data[4] & 0x7f) << 7) | (data[3] & 0x7f);
		// 时间 （单位 s）
		int timeInterval =  ((data[6] & 0x7f) << 7) | (data[5] & 0x7f);
		// 总的里程
		double distance = RPM / timeInterval * girth;
		//消耗的卡路里计算  公式待定
		float calorie = 0; // = //
		// 高位 版本
		int versionMain = (data[7] >> 4) & 0x07; 
		// 低位 版本
		int versionSlave = (data[7] & 0x0f);
		// 全版本
		float version = Float.valueOf(String.valueOf(versionMain)+"."+String.valueOf(versionSlave));
		// 高位 产品ID
		int proIDH = (data[8] >> 3) & 0x07;
		// 低位 产品ID
		int proIDL = (data[8] & 0x07);
		// 产品全ID
		float proID = Float.valueOf(String.valueOf(proIDH)+"."+String.valueOf(proIDL));
		// 是否需要回应返回当前时间
		int currentTimeRequest = ( data[8] >> 6 ) & 0x01;
		
		// 生成Boolean并判断设置值
		boolean isReturnCurrentTime;
		if(currentTimeRequest == 1)
			isReturnCurrentTime = true;
		else 
			isReturnCurrentTime = false;
		
		// 生成实体Bean
		BlueToohDataNormal blueToohData = new BlueToohDataNormal(version, gear, bikeState, proID, isReturnCurrentTime, girth, RPM, timeInterval, distance, calorie);
		Log.i("XU", "获取的数据结果:"+blueToohData.toString());
		return blueToohData;
	}
}
