package com.signalripple.fitnessbike.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.signalripple.fitnessbike.R;

public class ChartView extends View {
	public int XPoint = 62; // 原点的X坐标
	public int YPoint = 260; // 原点的Y坐标
	public int XScale = 30; // X的刻度长度
	public int YScale = 10; // Y的刻度长度
	/**initValue()方法中有根据本控件获取到的宽高重新进行尺寸定位*/
	public int XLength = 320; // X轴的长度
	public int YLength = 180; // Y轴的长度   
	public String[] XLabel; // X的刻度
	public String[] YLabel; // Y的刻度
	public String[] Data; // 数据
	public String Title; // 显示的标题
	private int yLabelOffSet = 50; // Y轴刻度标号相对于Y轴的偏移量 即该值距离Y轴的 距离量
	private int xLabelOffSet = 30; // X轴刻度标号相对于X轴的偏移量 即该值距离X轴的 距离量
	private int textColor = Color.WHITE; // 文字颜色
	private int xyColor = Color.WHITE; // XY坐标颜色
	private int pointRadius = 5; // 折线图中圆点的半径大小 ,默认2
	private int textSize = 16; // 文本字体大小
	private int pointValueTextOffSet = 12;
	private int pointValueTextColor = Color.WHITE;
	private boolean isInited = false; // 是否初始化完毕
	private String pefValue = "";
	public static final String KEY_LAST_DATE = "last_date";
	public static final String KEY_SET_DATA = "set_data";

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ChartView(Context context) {
		super(context);
	}

	public void setInfo(String[] XLabels, String[] YLabels, String[] AllData,
			String strTitle) {
		XLabel = XLabels;
		YLabel = YLabels;
		Data = AllData;
		Title = strTitle;
	}

	public String currentDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return format.format(date);
	}

	public void setData(String data) {
		// String lastDate = ShareDB.getStringFromDB(getContext(),
		// KEY_LAST_DATE);
		// String currentDate = currentDate();
		//
		// int week = getWeekDay();
		// // 同一周
		// if (isSameWeek(lastDate, currentDate)) {
		// Toast.makeText(getContext(), "week=" + week, Toast.LENGTH_SHORT)
		// .show();
		// Data[week * 2 - 1] = data;
		// }
		// // 不同周
		// else {
		// for (int j = 0; j < Data.length; j++) {
		// Data[j] = "0";
		// }
		// Data[0] = data;
		// }

		// 以 0|0|0|123|0|0|0|0|0|0|0|0|0|0 形式存储到SharedPreferences
		// ShareDB.save2DB(getContext(), KEY_SET_DATA, stringArray2Set(Data));
		invalidate();
	}

	public String stringArray2Set(String[] array) {
		String set = "";
		for (int i = 0; i < array.length; i++) {
			set += array[i] + "|";
		}
		set = set.substring(0, set.length() - 1);
		return set;
	}

	/** 判断当前时间是星期几 return 1,2,3,4,5,6,7 **/
	public int getWeekDay() {
		// Calendar calendar = Calendar.getInstance();
		// Date date = new Date();
		// calendar.setTime(date);
		// int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		// if (dayOfWeek == 0)
		// dayOfWeek = 7;
		// return dayOfWeek;

		Locale.setDefault(Locale.CHINA);
		// 获取计算机默认语言环境
		Locale l = Locale.getDefault();

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
		if (isFirstSunday) {
			dayOfWeek = dayOfWeek - 1;
		}
		if (dayOfWeek == 0)
			dayOfWeek = 7;
		return dayOfWeek;

	}

	private static Calendar caculateTime(Calendar c, int day) {
		c.add(Calendar.DATE, day);
		return c;
	}

	public static long getTimeDiff(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			Date now = new Date();
			String nowStr = df.format(now);

			Date d1 = df.parse(nowStr);

			Date d2 = df.parse(time);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			System.out.println("days" + days);

			return days;

		} catch (Exception e) {
		}

		return -1;
	}

	/** 判断两个日期是否是同一周 **/
	public static boolean isSameWeek(String date1, String date2) {
		if (date1 == null)
			return true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(date1);
			d2 = format.parse(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		// subYear==0,说明是同一年
		if (subYear == 0) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		// 例子:cal1是"2005-1-1"，cal2是"2004-12-25"
		// java对"2004-12-25"处理成第52周
		// "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
		// 大家可以查一下自己的日历
		// 处理的比较好
		// 说明:java的一月用"0"标识，那么12月用"11"
		else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		// 例子:cal1是"2004-12-31"，cal2是"2005-1-1"
		else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	public void setPEF(String pef) {
		this.pefValue = pef;
		invalidate();
	}

	public void setXLabels(String[] xLabels) {
		this.XLabel = xLabels;
		invalidate();
	}

	public void setYLabels(String[] yLabels) {
		this.YLabel = yLabels;
		invalidate();
	}

	public void setPointRadius(int radius) {
		this.pointRadius = radius;
		invalidate();
	}

	public void setTitle(String title) {
		Title = title;
		invalidate();
	}

	public void setPointValueTextColor(int color) {
		this.pointValueTextColor = color;
		invalidate();
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
		invalidate();
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
		invalidate();
	}

	public void setxyColor(int xyColor) {
		this.xyColor = xyColor;
		invalidate();
	}

	public void setValueArray(String[] value) {
		Data = value;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// 重写onDraw方法

		// 此三项不允许为空
		if (XLabel == null || YLabel == null)
			return;

		initValue();

		// canvas.drawColor(Color.WHITE);//设置背景颜色
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);// 去锯齿
		paint.setColor(textColor);// 颜色
		paint.setTextSize(textSize);

		Paint paint1 = new Paint();
		paint1.setStrokeWidth(2.5f);
		// paint1.setStyle(Paint.Style.STROKE);
		paint1.setAntiAlias(true);// 去锯齿
		paint1.setColor(xyColor);
		// paint.setTextSize(14); // 设置轴文字大小

		// 设置Y轴
//		canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint1); // 轴线
		for (int i = 0; i * YScale < YLength; i++) {
//			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
//					* YScale, paint1); // 刻度
			
			if(i < 7)
			//测量横线
			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + XLength, YPoint - i * YScale, paint1); // 轴线
			
			try {
				//paint.setColor(Color.RED);
				canvas.drawText(YLabel[i], XPoint - yLabelOffSet, YPoint - i
						* YScale + 5, paint); // 文字
			} catch (Exception e) {
			}
		}
//		canvas.drawLine(XPoint, YPoint - YLength, XPoint - 6, YPoint - YLength
//				+ 9, paint1); // 箭头
//		canvas.drawLine(XPoint, YPoint - YLength, XPoint + 6, YPoint - YLength
//				+ 9, paint1);
		
		Log.i("XU", "YPoint - YLength="+(YPoint - YLength));
		
		// 设置X轴
		canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint1); // 轴线
		for (int i = 0; i * XScale < XLength; i++) {
//			canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale,
//					YPoint - 5, paint1); // 刻度
			try {
				canvas.drawText(XLabel[i], XPoint + i * XScale - 10, YPoint + xLabelOffSet, paint); // 文字
				if (Data != null) {
					// 数据值
					if (!"0".equals(Data[i])) {
						drawChart(canvas,XPoint + (i+1) * XScale, YCoord(Data[i]));
						}
					}
				} catch (Exception e) {
			}
		}
//		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 9,
//				YPoint - 6, paint1); // 箭头
//		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 9,
//				YPoint + 6, paint1);
		paint.setTextSize(16);

		paint.setTextSize(24);
		canvas.drawText(Title, 150, 50, paint);
		paint.setTextSize(30);
		canvas.drawText(pefValue, XLength - 100, 50, paint);
	}
	
	private void drawChart(Canvas canvas,int x,int y)
	{
	       // 获取资源文件的引用res  
        Resources res = getResources();  
        // 获取图形资源文件  
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.chart);  
//        // 设置canvas画布背景为白色  
//        canvas.drawColor(Color.BLACK);  
        // 在画布上绘制缩放之前的位图，以做对比  
        //屏幕上的位置坐标是0,0  
        //canvas.drawBitmap(bmp, 0, 0, null);  
        // 定义矩阵对象  
        Matrix matrix = new Matrix();  
        // 缩放原图  
        matrix.postScale(1f, (float)(y-YPoint)/bmp.getHeight());//0.3f);  
        //bmp.getWidth(), bmp.getHeight()分别表示缩放后的位图宽高  
        Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),  
                matrix, true);  
        // 在画布上绘制旋转后的位图  
        //放在坐标为60,460的位置  
        canvas.drawBitmap(dstbmp, x, y, null);  
	}

	private void initValue() {
		// TODO Auto-generated method stub
		if (!isInited) {
			// delete by xushiyong
			//YLength = getHeight() - 150;
			XLength = getWidth() - 100;
			YLength = XLength / 2;
			YPoint = YLength;
			XScale = XLength / XLabel.length;
			YScale = YLength / YLabel.length;
			isInited = true;
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		isInited = false;
	}

	// 计算绘制时的Y坐标，无数据时返回-999
	private int YCoord(String y0) {
		int y;
		try {
			y = Integer.parseInt(y0);
		} catch (Exception e) {
			return -999; // 出错则返回-999
		}
		try {
			// 260 - 40/15
			return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
		} catch (Exception e) {
		}
		return y;
	}
}
