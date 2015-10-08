package com.signalripple.fitnessbike;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.api.BitmapCache;
import com.signalripple.fitnessbike.api.MRequset;
import com.signalripple.fitnessbike.api.URLFactory;
import com.signalripple.fitnessbike.utils.ShareDB;
import com.signalripple.fitnessbike.view.ChartView;
import com.signalripple.fitnessbike.view.CircleImageView;
import com.signalripple.fitnessbike.view.RoundProgressBar;

public class PersonFragment extends Fragment implements OnCheckedChangeListener{

	ChartView chartView;
	RadioGroup radioGroup;
	RadioButton rbDistance;
	RadioButton rbCalory;
	private RoundProgressBar roundProgressBar;
	private MRequset mRequset;
	private TextView txtTodayNumber;
	private TextView txtAllNumber;
	private TextView txtMaxRecorderNumber;
	private CircleImageView circleImageViewForHeader;
	private String[] mileValue ;
	private String[] caloryValue ;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.fragment_2, null);	
		
		mRequset = MRequset.getInstance(getActivity());

		// 初始化数据参数
		initValues();
		
		// 配置统计图
		chartOpeartion(view);
		
		// 初始化视图控件
		initViews(view);
		
		// 获取个人数据  
		getPersonData();
		
		// 获取每周里程数据
		getPersonWeekMileData();
		
		// 获取每周卡路里数据
		getPersonWeekCaloryData();
		
		return view;
	}
	
	/**获取卡路里*/
	private void getPersonWeekCaloryData() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", ShareDB.getStringFromDB(getActivity(), "account"));
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiWeeklymile, map), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				String result;
				try {
					result = response.getString("response");
					Log.i("XU", "卡路里历史结果="+response.toString());
					if(result != null && "success".equals(result))
					{
//						 "one":  112
//						    "two":  112
//						    "three":  112
//						    "four":  112
//						    "five":  112
//						    "six":  112
//						   "seven":  112
						String temps = "one,two,three,four,five,six,seven";
						String[] temp = temps.split(",");
						for (int i = 0; i < mileValue.length; i++) {
							caloryValue[i] = response.getString(temp[i]);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.i("XU", "卡路里获取出错结果="+error.toString());
			}
		});
	}

	private void initValues() {
		// TODO Auto-generated method stub
		/// 初始化模拟数据
		mileValue = new String[]{"300","200","123","324","225","126","97"};	
		caloryValue = new String[]{"100","300","223","524","125","126","107"};	
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		radioGroup = (RadioGroup)view.findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
		rbCalory = (RadioButton)view.findViewById(R.id.rbCalori);
		rbDistance = (RadioButton)view.findViewById(R.id.rbDistance);
		rbDistance.setChecked(true);	
		
		circleImageViewForHeader = (CircleImageView)view.findViewById(R.id.iv_fragment_person_header);
//		ImageLoader loader = new ImageLoader(mRequset.getRequestQueue(), new BitmapCache());
//
//	    ImageListener listener = ImageLoader.getImageListener(circleImageViewForHeader,  
//	            R.drawable.test1, R.drawable.test2);  
//
//		loader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", listener);
		
		
		txtAllNumber = (TextView)view.findViewById(R.id.txtAllNumber);
		txtMaxRecorderNumber = (TextView)view.findViewById(R.id.txtMaxRecorderNumber);
		txtTodayNumber = (TextView)view.findViewById(R.id.txtToadyNumber);
		
		roundProgressBar = (RoundProgressBar)view.findViewById(R.id.roundProgressBar);
		roundProgressBar.setProgress(40);
	}
//	http://wx.rongtai-china.com/fitnessbike/exercise?uid=835588741

	private void getPersonData() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", ShareDB.getStringFromDB(getActivity(), "account"));
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiExercise, map), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				String result;
				try {
					result = response.getString("response");
					Log.i("XU", "第二页面结果="+response.toString());
					if(result != null && "success".equals(result))
					{
						String data = response.getString("burning");
						JSONObject obj = new JSONObject(data);  
						
						Log.i("XU", "today="+obj.getString("today"));
						Log.i("XU", "score="+obj.getString("score"));
						Log.i("XU", "record="+obj.getString("record"));
						
						String toady = obj.getString("today");
						String score = obj.getString("score");
						String record = obj.getString("record");
						
						txtTodayNumber.setText(toady == null ? "0 cal":toady+"cal");
						txtMaxRecorderNumber.setText(score == null ? "0 cal":toady+"cal");
						txtAllNumber.setText(record == null ? "0 cal":toady+"cal");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.i("XU", "第二页面获取出错结果="+error.toString());
			}
		});
	}
	
	/**获取用户一周的里程历史*/
	private void getPersonWeekMileData() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", ShareDB.getStringFromDB(getActivity(), "account"));
		mRequset.requestForJsonObject(URLFactory.getURL(API.apiWeeklymile, map), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				String result;
				try {
					result = response.getString("response");
					Log.i("XU", "里程历史结果="+response.toString());
					if(result != null && "success".equals(result))
					{
//						 "one":  112
//						    "two":  112
//						    "three":  112
//						    "four":  112
//						    "five":  112
//						    "six":  112
//						   "seven":  112
						String temps = "one,two,three,four,five,six,seven";
						String[] temp = temps.split(",");
						for (int i = 0; i < mileValue.length; i++) {
							mileValue[i] = response.getString(temp[i]);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.i("XU", "里程历史获取出错结果="+error.toString());
			}
		});
	}

	private void chartOpeartion(View view) {
		// TODO Auto-generated method stub
		/** 折线统计图使用方法 start **/
		String[] values = new String[]{"300","200","123","324","225","126","97"};	
		// 获取该折线统计图控件
		chartView = (ChartView)view.findViewById(R.id.chartview);
//		ComputePefBySexTool computePefBySexTool = new ComputePefBySexTool(getActivity());
//		float biaozhun = computePefBySexTool.computePefBySex();
//		biaozhun = (float) (biaozhun*1.2);
//		biaozhun = biaozhun/4;
		// 初始化设置数据
		chartView.setInfo(new String[]
				{"","1","2","3","4","5","6","7"}, // X轴刻度
				// new
				// String[]{"周一上午","周一下午","周二上午","周二下午","周三上午","周三下午","周四上午","周四下午","周五上午","周五下午","周六上午","周六下午","周天上午","周天下午"},
				// //X轴刻度
				new String[]
						{"0", "100", "200", "300", "400","500","600"}, // Y轴刻度
						mileValue, // 数据
				"图标的标题");

		// 设置详细数据 更多方法请参考 com.kewensheng.view.ChartView.java
		chartView.setTextColor(Color.GRAY); // 设置文字颜色
		chartView.setxyColor(Color.GRAY); // 设置xy轴的绘制颜色
		chartView.setTitle("七日燃烧卡路里"); // 设置标题
		// chartView.setPEF("80L/min");
		chartView.setPointValueTextColor(Color.WHITE);// 设置圆点上的显示的数值的文本颜色
		chartView.setTextSize(28); // 设置刻度标号文本字体大小

		/** 折线统计图使用方法 end **/

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if(checkedId == R.id.rbCalori)
		{
			//rbCalory.setBackgroundResource(R.drawable.bg_buttons);
			rbCalory.setBackgroundColor(getResources().getColor(R.color.sea_blue));
			rbCalory.setTextColor(getResources().getColor(R.color.white));
			
			//rbDistance.setBackgroundResource(android.R.color.transparent);
			rbDistance.setBackgroundColor(getResources().getColor(R.color.white));
			rbDistance.setTextColor(getResources().getColor(R.color.sea_blue));
			
			chartView.setValueArray(caloryValue);
			chartView.setTitle("七日燃烧卡路里");
		}
		else
		{
//			rbDistance.setBackgroundResource(R.drawable.bg_buttons);
			rbDistance.setBackgroundColor(getResources().getColor(R.color.sea_blue));
			rbDistance.setTextColor(getResources().getColor(R.color.white));
			
//			rbCalory.setBackgroundResource(android.R.color.transparent);
			rbCalory.setBackgroundColor(getResources().getColor(R.color.white));
			rbCalory.setTextColor(getResources().getColor(R.color.sea_blue));
			
			chartView.setValueArray(mileValue);
			chartView.setTitle("七日运动里程");
		}
	}	
}