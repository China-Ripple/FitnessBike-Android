package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import com.signalripple.fitnessbike.adapter.ActionListViewAdapter;
import com.signalripple.fitnessbike.bean.ActionBean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ActionFragment extends Fragment{

	ListView listView;
	List<ActionBean> list = new ArrayList<ActionBean>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		View view = inflater.inflate(R.layout.fragment_4, null);
		list.add(new ActionBean("", "", null, null,1,1));
		listView = (ListView)view.findViewById(R.id.listview);
		listView.setAdapter(new ActionListViewAdapter(getActivity(), list));
		
		return view;
	}	
}