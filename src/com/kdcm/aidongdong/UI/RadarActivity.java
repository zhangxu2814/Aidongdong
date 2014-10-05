package com.kdcm.aidongdong.UI;


import com.kdcm.aidongdong.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class RadarActivity extends Activity{
	protected static final int SCAN_LODING = 1;
	protected static final int FINSH_SCAN = 2;
	private ImageView im_scan;
	private ImageView im_dian;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radar);
		im_scan = (ImageView) findViewById(R.id.im_scan);
		im_dian = (ImageView) findViewById(R.id.im_dian);
		
		RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(2000);
		animation.setRepeatCount(Animation.INFINITE);
		im_scan.startAnimation(animation);
		
		AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
		animation2.setDuration(3000);
		animation2.setRepeatCount(Animation.INFINITE);
		im_dian.startAnimation(animation2);
	}

}
