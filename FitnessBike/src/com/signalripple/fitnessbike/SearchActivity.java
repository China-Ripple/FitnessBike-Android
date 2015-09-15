package com.signalripple.fitnessbike;

import java.util.ArrayList;
import java.util.List;

import com.signalripple.fitnessbike.adapter.GridViewAdapter;
import com.signalripple.fitnessbike.bean.SuperBiker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;


public class SearchActivity extends BaseActivity implements OnClickListener {

	private GridView gridview;
	private ImageButton btnNearByPeople;
	private ImageButton btnSuperBiker;
	private ImageButton btnSearchBar;
	private List<SuperBiker> list = new ArrayList<SuperBiker>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initViews();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btnNearByPeople.setOnClickListener(this);
		btnSuperBiker.setOnClickListener(this);
		btnSearchBar.setOnClickListener(this);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		gridview = (GridView)this.findViewById(R.id.gridview);
		list.add(new SuperBiker());
		list.add(new SuperBiker());
		list.add(new SuperBiker());
		list.add(new SuperBiker());

		GridViewAdapter adapter = new GridViewAdapter(this, list);
		gridview.setAdapter(adapter);
		
		btnSearchBar    = (ImageButton)this.findViewById(R.id.ivSearchBar);
		btnNearByPeople = (ImageButton)this.findViewById(R.id.ibDetailsNearByPeople);
		btnSuperBiker = (ImageButton)this.findViewById(R.id.ibDetailsSuperBiker);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ibDetailsNearByPeople:
			 Intent intent = new Intent(this, GeneralListViewActivity.class);
			 startActivity(intent);
			break;
		case R.id.ibDetailsSuperBiker:
			 Intent intent2 = new Intent(this, GeneralListViewActivity.class);
			 startActivity(intent2);
			
			break;
		case R.id.ivSearchBar:
			 Intent intent3 = new Intent(this, GeneralListViewActivity.class);
			 startActivity(intent3);
			
			break;
		default:
			break;
		}
	}
}
