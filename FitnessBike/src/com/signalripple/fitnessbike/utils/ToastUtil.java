package com.signalripple.fitnessbike.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.signalripple.fitnessbike.R;

public class ToastUtil
{
//	enum TYPE
//	{
//		ERROR,WARN,INFO
//	}
	/**错误样式**/
	public static final int ERROR = 1;
	/**警告样式**/
	public static final int WARN  = 2;
	/**普通信息样式**/
	public static final int INFO  = 3;
	/**加载样式**/
	public static final int LOAD  = 4;
	/**成功提示样式**/
	public static final int SUCC  = 5;
	
	/**
	 * Toast显示
	 * @param context
	 * @param str	显示的字符串
	 * @param typeOfToastShow	Toast类型 ---> ERROR、WARN、INFO、LOAD
	 */
	public static void show(Context context,String str,int typeOfToastShow)
	{
		Toast toast = new Toast(context);
		View view = getView(typeOfToastShow, context , str);
		toast.setView(view);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**获取视图*/
	private static View getView(int type,Context context,String str)
	{
		View view = null;
		ImageView img = null;
		TextView txt = null;
		switch(type)
		{
			case ERROR:
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_error);
				break;
			case WARN :
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_warn);
				break;
			case LOAD :
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_load);
				break;
			case SUCC :
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_success);
				break;
			default :
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_info);
		}
		return view;
	}

	/**
	 * 可以控制显示时间的Toast
	 * @param context
	 * @param str 显示的字符串
	 * @param typeOfToastShow	Toast类型 ---> ERROR、WARN、INFO、LOAD
	 * @param showTime		显示时间
	 */
	public static void show(Context context,String str,int typeOfToastShow,int showTime)
	{
		Toast toast = new Toast(context);
		View view = getView(typeOfToastShow, context , str);
		toast.setView(view);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setDuration(showTime);
		toast.show();
	}
}
