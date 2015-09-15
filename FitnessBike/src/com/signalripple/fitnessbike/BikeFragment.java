package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BikeFragment extends Fragment implements OnPageChangeListener {

	ViewPager viewPager;
	List<View> viewList = new ArrayList<View>();
	List<ImageView> selectPoint = new ArrayList<ImageView>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_1, null);
		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		
		selectPoint.add((ImageView) view.findViewById(R.id.point1));
		selectPoint.add((ImageView) view.findViewById(R.id.point2));
		
		View firstView  = inflater.inflate(R.layout.first_page, null);
		View secondView = inflater.inflate(R.layout.second_page, null);

		viewList.add(firstView);
		viewList.add(secondView);
		
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		return view;
	}

	PagerAdapter adapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public int getCount() {

			return viewList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(viewList.get(position));

		}

		@Override
		public int getItemPosition(Object object) {

			return super.getItemPosition(object);
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(viewList.get(position));
			return viewList.get(position);
		}
	};
	
	private void setSelect(int targer)
	{
		for (int i = 0; i < selectPoint.size(); i++) {
			if(i != targer)
			{
				selectPoint.get(i).setBackgroundResource(R.drawable.icon_unselect);
			}
			else
			{
				selectPoint.get(i).setBackgroundResource(R.drawable.icon_select);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setSelect(arg0);
	}
}
