package com.signalripple.fitnessbike.view;

import com.signalripple.fitnessbike.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class FriendRankingView extends View {

	public FriendRankingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.friend_list, null);
	}

}
