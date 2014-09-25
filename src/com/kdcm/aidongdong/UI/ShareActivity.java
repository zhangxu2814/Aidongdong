/******************************************************************************
 * 
 * Copyright (c) 2012 ~ 2020 Baidu.com, Inc. All Rights Reserved
 * 
 *****************************************************************************/
package com.kdcm.aidongdong.UI;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShare.FrontiaTheme;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;
import com.kdcm.aidongdong.R;
import com.kdcm.aidongdong.Date.Conf;


/**
 * @author zhangchi09
 *
 */
public class ShareActivity extends Activity implements OnClickListener{
	
	private Button mShareButton;
	
	private Button mShowButton;
	
	private FrontiaSocialShare mSocialShare;
	
	private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share);
		mShareButton = (Button) findViewById(R.id.share_share);
		mShareButton.setOnClickListener(this);
		mShowButton = (Button) findViewById(R.id.share_show);
		mShowButton.setOnClickListener(this);
		mSocialShare = Frontia.getSocialShare();
		mSocialShare.setContext(this);
		mSocialShare.setClientId(MediaType.SINAWEIBO.toString(), Conf.SINA_APP_KEY);
		mSocialShare.setClientId(MediaType.QZONE.toString(), "100358052");
		mSocialShare.setClientId(MediaType.QQFRIEND.toString(), "100358052");
		mSocialShare.setClientName(MediaType.QQFRIEND.toString(), "百度");
		mSocialShare.setClientId(MediaType.WEIXIN.toString(), "wx329c742cb69b41b8");
		mImageContent.setTitle("百度开发中心");
		mImageContent.setContent("欢迎使用百度社会化分享组件，相关问题请邮件dev_support@baidu.com");
		mImageContent.setLinkUrl("http://developer.baidu.com/");
		mImageContent.setImageUri(Uri.parse("http://apps.bdimg.com/developer/static/04171450/developer/images/icon/terminal_adapter.png"));
	}

	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.share_share:
				mSocialShare.share(mImageContent,MediaType.BATCHSHARE.toString(),new ShareListener(),true);
				break;
			case R.id.share_show:
				mSocialShare.show(ShareActivity.this.getWindow().getDecorView(), mImageContent, FrontiaTheme.DARK,  new ShareListener());
				break;
		}
	}
	
	private class ShareListener implements FrontiaSocialShareListener {

		@Override
		public void onSuccess() {
			Log.d("Test","share success");
		}

		@Override
		public void onFailure(int errCode, String errMsg) {
			Log.d("Test","share errCode "+errCode);
		}

		@Override
		public void onCancel() {
			Log.d("Test","cancel ");
		}
		
	}
}
