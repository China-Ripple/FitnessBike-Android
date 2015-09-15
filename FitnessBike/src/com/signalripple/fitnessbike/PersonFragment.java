package com.signalripple.fitnessbike;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.signalripple.fitnessbike.view.ChartView;

public class PersonFragment extends Fragment implements OnCheckedChangeListener{

	ChartView chartView;
	RadioGroup radioGroup;
	RadioButton rbDistance;
	RadioButton rbCalory;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.fragment_2, null);		
		radioGroup = (RadioGroup)view.findViewById(R.id.radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
		rbCalory = (RadioButton)view.findViewById(R.id.rbCalori);
		rbDistance = (RadioButton)view.findViewById(R.id.rbDistance);
		chartOpeartion(view);
		
		return view;
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
				values, // 数据
				"图标的标题");

		// 设置详细数据 更多方法请参考 com.kewensheng.view.ChartView.java
		chartView.setTextColor(Color.GRAY); // 设置文字颜色
		chartView.setxyColor(Color.GRAY); // 设置xy轴的绘制颜色
		chartView.setTitle("卡路里统计图表"); // 设置标题
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
			rbCalory.setBackgroundResource(R.drawable.bg_buttons);
			rbCalory.setTextColor(getResources().getColor(R.color.sea_blue));
			
			rbDistance.setBackgroundResource(android.R.color.transparent);
			rbDistance.setTextColor(Color.WHITE);
		}
		else
		{
			rbDistance.setBackgroundResource(R.drawable.bg_buttons);
			rbDistance.setTextColor(getResources().getColor(R.color.sea_blue));
			
			rbCalory.setBackgroundResource(android.R.color.transparent);
			rbCalory.setTextColor(Color.WHITE);
		}
	}	
}