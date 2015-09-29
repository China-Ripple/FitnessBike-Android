package com.signalripple.fitnessbike.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.signalripple.fitnessbike.R;
import com.signalripple.fitnessbike.bean.SuperBiker;
import com.signalripple.fitnessbike.view.CircleImageView;

public class GridViewAdapter extends BaseAdapter{

	private List<SuperBiker> list;
	private Context context;
	private LayoutInflater inflater;
	int width;
	int height;
	
	public GridViewAdapter(Context context,List<SuperBiker> list)
	{
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);//.getHeight() is  deprecated
        width = point.x;
        height = point.y;
        
        Log.i("XU", "w="+width+"  h="+height);
        
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.superbike_gridview_item, null);
			holder.ivHead = (CircleImageView)convertView.findViewById(R.id.ivHead);
			
			LinearLayout.LayoutParams lp = (LayoutParams) holder.ivHead.getLayoutParams();
			lp.width  = width / 3;
			lp.height = lp.width ;
			holder.ivHead.setLayoutParams(lp);
			
			holder.txtName = (TextView)convertView.findViewById(R.id.txtName);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		SuperBiker superBiker = list.get(position);
		String name = superBiker.getName();
		String url = superBiker.getImageUrl();
		
		holder.txtName.setText(name);
		if("test1".equals(url))
			holder.ivHead.setImageResource(R.drawable.test1);
		else if("test2".equals(url))
			holder.ivHead.setImageResource(R.drawable.test2);
		else if("test3".equals(url))
			holder.ivHead.setImageResource(R.drawable.test3);
		else if("test4".equals(url))
			holder.ivHead.setImageResource(R.drawable.test4);
		else
		{
			//TODO 根据网络路劲加载图片
		}
		return convertView;
	}
	
	private static final class  ViewHolder 
	{
		CircleImageView ivHead;
		TextView txtName;
	}
}
