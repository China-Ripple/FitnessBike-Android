package com.signalripple.fitnessbike.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.signalripple.fitnessbike.R;

public class Activity2Activity {

	public static void gotoNewActivity(Context context,Class classs)
	{
		Intent intent = new Intent(context, classs);
		((Activity)context).overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
		context.startActivity(intent);
	}
}
