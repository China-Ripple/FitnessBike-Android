package com.signalripple.fitnessbike.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.signalripple.fitnessbike.BaseActivity;
import com.signalripple.fitnessbike.R;

public class CustomTitleBar extends RelativeLayout{

    private Button btnRight;
    private Button btnLeft;
    private TextView txtTitle;
    private Context context;
    
    public CustomTitleBar(Context context) {
        // TODO Auto-generated constructor stub
        super(context);
        this.context = context;
    }
    
    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        initView();
        initEvent();
    }

    private void initEvent() {
        // TODO Auto-generated method stub
        btnRight.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
            }
        });
        
        btnLeft.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((BaseActivity)context).finish();
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        View temp = inflater.inflate(R.layout.titlebar, null);
        btnRight = (Button)temp.findViewById(R.id.include_view_btnRight);
        btnLeft  = (Button)temp.findViewById(R.id.include_view_btnLeft);
        txtTitle = (TextView)temp.findViewById(R.id.include_view_titlebar_text);
        this.addView(temp);
    }
    
    /**
     * set background color for this view
     * @author xushiyong
     * @param resourceId
     */
    public void setBackGroundColorForView(int color)
    {
        this.setBackgroundColor(color);
    }
    
    /**
     * set background for this view
     * @author xushiyong
     * @param resourceId
     */
    public void setBackGroundForView(int resid)
    {
        this.setBackgroundResource(resid);
    }

    
    /**
     * set click listener for left button
     * @author xushiyong
     * @param listener
     */
    public void setOnClickForLeftButton(OnClickListener listener)
    {
        btnLeft.setOnClickListener(listener);
    }

    /**
     * set background listener for left button by resourceId
     * @author xushiyong
     * @param resourceId
     */
    public void setBackGroundForLeftButton(int resourceId)
    {
        btnLeft.setBackgroundResource(resourceId);
    }
    
//    /**
//     * set background listener for left button by resourceId
//     * @author xushiyong
//     * @param resourceId
//     */
//    public void setBackGroundForLeftButton(Drawable drawable)
//    {
//        btnLeft.setBackground(drawable);
//    }
    
    /**
     * set click listener for right button
     * @author xushiyong
     * @param listener
     */
    public void setOnClickForRightButton(OnClickListener listener)
    {
        btnRight.setOnClickListener(listener);
    }

    /**
     * set click listener for right button
     * @author xushiyong
     * @param text
     */
    public void setTextForRightButtonByString(String text)
    {
        if(text != null)
        btnRight.setText(text);
    }

    /**
     * set click listener for right button
     * @author xushiyong
     * @param textId
     */
    public void setTextForRightButtonByString(int textId)
    {
        String temp = "";
        if((temp = context.getString(textId)) != null)
        {
            btnRight.setText(temp);
        }
    }

    
    /**
     * set title for titlebar use string format
     * @author xushiyong
     * @param title
     */
    public void setTitle(String title)
    {
        if(title != null)
            txtTitle.setText(title);
    }

    /**
     * set title for titlebar use resource id format
     * @author xushiyong
     * @param titleStringId
     */
    public void setTitle(int titleStringId)
    {
        String temp = "";
        if((temp = context.getString(titleStringId)) != null)
        {
            txtTitle.setText(temp);
//            setTitle(temp);
        }
    }


}
