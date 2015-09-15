package cn.fireup.yuanyang.adapter;


import cn.fireup.loadmore.R;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @类名:NewsIndexAdapter
 * @描述:TODO(新闻适配器)
 * @作者:zhaohao
 * @时间 2013-3-4 下午4:06:18
 */
public class NewsIndexAdapter extends MyBaseAdapter {
	private LayoutInflater inflater = null;

	public NewsIndexAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
	}

	public void setAbsListView(AbsListView absListView) {
		this.absListView = absListView;
	}

	public void clear() {
		this.alObjects.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			
			convertView =inflater.inflate(R.layout.package_row, parent, false);
			 holder = new ViewHolder();
	            holder.ivImage = (ImageView) convertView.findViewById(R.id.example_row_iv_image);
	            holder.tvTitle = (TextView) convertView.findViewById(R.id.example_row_tv_title);
	            holder.tvDescription = (TextView) convertView.findViewById(R.id.example_row_tv_description);
	            holder.bAction1 = (Button) convertView.findViewById(R.id.example_row_b_action_1);
	            holder.bAction2 = (Button) convertView.findViewById(R.id.example_row_b_action_2);
	            holder.bAction3 = (Button) convertView.findViewById(R.id.example_row_b_action_3);
	            convertView.setTag(holder);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
	       holder.tvTitle.setText("aaaaa");
	        holder.tvDescription.setText("bbbbb");
		// if(!TextUtils.isEmpty(indexEntity.getSERVER_DOMAIN())){
		// viewHolder.ivindexloadimg.setVisibility(View.VISIBLE);
		// App.getIns().display(img, viewHolder.ivindexloadimg,
		// R.drawable.newlist_default_icon);
		// }else
		

		return convertView;
	}

	 static class ViewHolder {
	        ImageView ivImage;
	        TextView tvTitle;
	        TextView tvDescription;
	        Button bAction1;
	        Button bAction2;
	        Button bAction3;
	    }

	@Override
	public int getCount() {
		return alObjects.size();
	}

	@Override
	public Object getItem(int arg0) {
		return alObjects.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

}
