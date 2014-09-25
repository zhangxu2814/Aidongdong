package com.kdcm.aidongdong.UI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaQuery;
import com.baidu.frontia.api.FrontiaPush;
import com.baidu.frontia.api.FrontiaPushListener.CommonMessageListener;
import com.baidu.frontia.api.FrontiaPushListener.DescribeMessageListener;
import com.baidu.frontia.api.FrontiaPushListener.DescribeMessageResult;
import com.baidu.frontia.api.FrontiaPushListener.ListMessageListener;
import com.baidu.frontia.api.FrontiaPushListener.PushMessageListener;
import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.baidu.frontia.api.FrontiaPushUtil;
import com.baidu.frontia.api.FrontiaPushUtil.MessageContent;
import com.baidu.frontia.api.FrontiaPushUtil.NotificationContent;
import com.baidu.frontia.api.FrontiaPushUtil.Trigger;
import com.kdcm.aidongdong.R;

public class PushActivity extends Activity {

	private final static String TAG = "Push";
	
	private FrontiaPush mPush;

	private String mAccessToken;

	private TextView mResultTextView;

	private static String mUserId;

	private static String mChannelId;
	
	private String mId;

	private final static String mTag = "friends";

	private final static String mMessageId = "test";

	private final static String mMessage = "test liu";
	private final static String mUpdateMessage = "更新的信息";

	private final static String mTitle = "Push";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPush = Frontia.getPush();
		setupViews();
//		FrontiaAuthorization auth = Frontia.getAuthorization();
//		auth.authorize(this, MediaType.BAIDU.toString(), new AuthorizationListener() {
//			
//			@Override
//			public void onSuccess(FrontiaUser user) {
//				mAccessToken = user.getAccessToken();
//				setupViews();
//			}
//			
//			@Override
//			public void onFailure(int errCode, String errMsg) {
//				finish();
//			}
//			
//			@Override
//			public void onCancel() {
//				finish();
//			}
//		});
	}
	
	private void setupViews() {
		setContentView(R.layout.push);

		mResultTextView = (TextView) findViewById(R.id.pushResult);

		Button startButton = (Button) findViewById(R.id.start);
		startButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				start();
			}

		});

		Button stopButton = (Button) findViewById(R.id.stop);
		stopButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stop();
			}

		});

		Button resumeButton = (Button) findViewById(R.id.resume);
		resumeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				resume();
			}

		});

		Button isPushButton = (Button) findViewById(R.id.isPush);
		isPushButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				isPush();
			}

		});

		Button setTagsButton = (Button) findViewById(R.id.setTags);
		setTagsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setTags();
			}

		});

		Button delTagsButton = (Button) findViewById(R.id.delTags);
		delTagsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				delTags();
			}

		});

		Button messageToPeerButton = (Button) findViewById(R.id.messageToPeer);
		messageToPeerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				messageToPeer();
			}

		});

		Button notificationToPeerButton = (Button) findViewById(R.id.notificationToPeer);
		notificationToPeerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				notificationToPeer();
			}

		});
		
		Button messageToPeerWithTriggerButton = (Button) findViewById(R.id.messageToPeerWithTrigger);
		messageToPeerWithTriggerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				messageToPeerWithTrigger();
			}

		});

		Button notificationToPeerWithTriggerButton = (Button) findViewById(R.id.notificationToPeerWithTrigger);
		notificationToPeerWithTriggerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				notificationToPeerWithTrigger();
			}

		});

		Button messageToGroupButton = (Button) findViewById(R.id.messageToGroup);
		messageToGroupButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				messageToGroup();
			}

		});

		Button notificationToGroupButton = (Button) findViewById(R.id.notificationToGroup);
		notificationToGroupButton
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
                        clearViews();
						notificationToGroup();
					}

				});

		Button messageToPublicButton = (Button) findViewById(R.id.messageToPublic);
		messageToPublicButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				messageToPublic();
			}

		});

		Button notificationToPublicButton = (Button) findViewById(R.id.notificationToPublic);
		notificationToPublicButton
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
                        clearViews();
						notificationToPublic();
					}

		});
		
		Button updateButton = (Button) findViewById(R.id.update);
		updateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				update();
			}

		});
		
		Button deleteButton = (Button) findViewById(R.id.delete);
		deleteButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				delete();
			}

		});
		
		Button listButton = (Button) findViewById(R.id.list);
		listButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				list();
			}

		});
		
		Button DescribeButton = (Button) findViewById(R.id.describe);
		DescribeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                clearViews();
				Describe();
			}

		});

		Button startDebugButton = (Button) findViewById(R.id.startDebug);
		startDebugButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startDebug();
			}

		});

		Button stopDebugButton = (Button) findViewById(R.id.stopDebug);
		stopDebugButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stopDebug();
			}

		});

	}

    void clearViews(){
        mResultTextView.setText("");
    }

	protected void start() {
		mPush.start(mAccessToken);
	}

	protected void stop() {
		mPush.stop();

	}

	protected void resume() {
		mPush.resume();
	}

	protected void isPush() {
		boolean result = mPush.isPushWorking();
		Log.d(TAG, "push is running?" + result);
		if (null != mResultTextView) {
			if (result) {
				mResultTextView.setText("Push is working.");
			} else {
				mResultTextView.setText("Push is not working.");
			}
		}
	}

	protected void setTags() {

        List<String> tags = createTags();
        mPush.setTags(tags);
	}

	protected void delTags() {

        List<String> tags = createTags();
        mPush.deleteTags(tags);
	}

	protected void messageToPeer() {

		final String content = "发送消息给个人";
		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setMessage(content);

		mPush.pushMessage(mUserId, mChannelId, msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {

                String msg = "push message to peer:\n" +
                        "    id: " + id + "\n" +
                        "    content: " + content + "\n";
                mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

                String fail = "Fail to push message to peer:\n" +
                        " content: " + mMessage + "\n" +
                        " errCode: " + errCode + " errMsg: " + errMsg + "\n";
                mResultTextView.setText(fail);
			}

		});

	}
	
	protected void messageToPeerWithTrigger() {

		final String content = "发送延时消息给个人";
		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setMessage(content);
		
		Trigger trigger = new Trigger();
		trigger.setCrontab("*/1 * * * *");

		mPush.pushMessage(mUserId, mChannelId, trigger, msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {

				mId = id;
                String msg = "push message:\n" +
                            "    id: " + id + "\n" +
                            "    content: " + content + "\n";
				mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

				String fail = "Fail to push message:\n" +
                            " content: " + mMessage + "\n" +
                            " errCode: " + errCode + " errMsg: " + errMsg + "\n";
				mResultTextView.setText(fail);
			}

		});

	}

	protected void notificationToPeer() {
		
		final String content = "发送通知给个人";
		NotificationContent notification = new NotificationContent(mTitle, content);

		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setNotification(notification);

		mPush.pushMessage(mUserId, mChannelId, msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {

                String msg = "push notification:\n" +
                        "    id: " + id + "\n" +
                        "    title: " + mTitle + "\n" +
                        "    content: " + content + "\n";
				mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

                String fail = "Fail to push notification:\n" +
                        " title: " + mTitle + "\n" +
                        " content: " + mMessage + "\n" +
                        " errCode: " + errCode + " errMsg: " + errMsg + "\n";
                mResultTextView.setText(fail);
			}

		});

	}

	protected void notificationToPeerWithTrigger() {
		final String content = "发送延时通知给个人";
		NotificationContent notification = new NotificationContent(mTitle, content);

		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setNotification(notification);

		Trigger trigger = new Trigger();
		trigger.setCrontab("*/2 * * * *");
		
		mPush.pushMessage(mUserId, null, trigger, msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {
                mId = id;
                String msg = "push notification:\n" +
                        "    id: " + id + "\n" +
                        "    title: " + mTitle + "\n" +
                        "    content: " + content + "\n";
                mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

                String fail = "Fail to push notification:\n" +
                        " title: " + mTitle + "\n" +
                        " content: " + mMessage + "\n" +
                        " errCode: " + errCode + " errMsg: " + errMsg + "\n";
                mResultTextView.setText(fail);
			}

		});

	}
	
	protected void messageToGroup() {
		final String content = "发送消息给群组";
		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setMessage(content);

		mPush.pushMessage(mTag, msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {

                String msg = "push message:\n" +
                        "    id: " + id + "\n" +
                        "    content: " + content + "\n";
                mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

                String fail = "Fail to push message:\n" +
                        " content: " + mMessage + "\n" +
                        " errCode: " + errCode + " errMsg: " + errMsg + "\n";
                mResultTextView.setText(fail);
			}

		});
	}

	protected void notificationToGroup() {
		final String content = "发送通知给群组";
        NotificationContent notification = new NotificationContent(mTitle, content);

		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setNotification(notification);

		mPush.pushMessage(mTag, msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {

                String msg = "push notification:\n" +
                        "    id: " + id + "\n" +
                        "    title: " + mTitle + "\n" +
                        "    content: " + content + "\n";
                mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

                String fail = "Fail to push notification:\n" +
                        " title: " + mTitle + "\n" +
                        " content: " + mMessage + "\n" +
                        " errCode: " + errCode + " errMsg: " + errMsg + "\n";
                mResultTextView.setText(fail);
			}

		});
	}

	protected void messageToPublic() {
		final String content = "发送消息给所有人";
		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setMessage(content);

        mPush.pushMessage(msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {

                String msg = "push message:\n" +
                        "    id: " + id + "\n" +
                        "    content: " + content + "\n";
                mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

                String fail = "Fail to push message:\n" +
                        " content: " + mMessage + "\n" +
                        " errCode: " + errCode + " errMsg: " + errMsg + "\n";
                mResultTextView.setText(fail);
			}

		});
	}

	protected void notificationToPublic() {
		final String content = "发送通知给所有人";
        NotificationContent notification = new NotificationContent(mTitle, content);

		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setNotification(notification);

		mPush.pushMessage(msg, new PushMessageListener() {

			@Override
			public void onSuccess(String id) {

                String msg = "push notification:\n" +
                        "    id: " + id + "\n" +
                        "    title: " + mTitle + "\n" +
                        "    content: " + content + "\n";
                mResultTextView.setText(msg);
			}

			@Override
			public void onFailure(int errCode, String errMsg) {

                String fail = "Fail to push notification:\n" +
                        " title: " + mTitle + "\n" +
                        " content: " + mMessage + "\n" +
                        " errCode: " + errCode + " errMsg: " + errMsg + "\n";
                mResultTextView.setText(fail);
			}

		});
	}	

	protected void update() {

		FrontiaPushUtil.MessageContent msg = new FrontiaPushUtil.MessageContent(
				mMessageId, FrontiaPushUtil.DeployStatus.PRODUCTION);

		msg.setMessage(mUpdateMessage);
		
		Trigger trigger = new Trigger();
		trigger.setCrontab("*/10 * * * *");

		//该例子要替换的mId依赖于推送延时消息给个人的message id.请先推送延时消息给个人，才能获取到该mId，当然你也可以把该id替换成别的message id
		mPush.replaceMessage(mId, mUserId, mChannelId, trigger, msg, new CommonMessageListener() {

			@Override
			public void onSuccess() {
				if (null != mResultTextView) {
					mResultTextView
							.setText("update message success.");
				}
				
			}

			@Override
			public void onFailure(int errCode, String errMsg) {
				if (null != mResultTextView) {
					mResultTextView.setText("Fail to update message:\nerrCode:" + errCode + ", errMsg:"
							+ errMsg);
				}
			}

		});

	}

	protected void delete() {
		mPush.removeMessage(mId, new CommonMessageListener() {

			@Override
			public void onSuccess() {
				if (null != mResultTextView) {
					mResultTextView
							.setText("remove message success.");
				}
				
			}

			@Override
			public void onFailure(int errCode, String errMsg) {
				if (null != mResultTextView) {
					mResultTextView.setText("errCode:" + errCode + ", errMsg:"
							+ errMsg);
				}
			}

		});

	}

	protected void list() {
		FrontiaQuery query = new FrontiaQuery();

		mPush.listMessage(query, new ListMessageListener() {

			@Override
			public void onSuccess(List<DescribeMessageResult> results) {
				String resultShow = "";
				for(DescribeMessageResult result : results) {
					String id = result.getId();
					String userId = result.getUserId();
					String channelId = result.getChannelId();
					MessageContent messageContent = result.getMessage();
					String tag = result.getTag();
					Trigger trigger = result.getTrigger();
					JSONObject extras = result.getExtras();
					resultShow = resultShow + "id:" + id + ", userId:" + userId + ", channelId:" + channelId + "\n";
				}
				
				if (null != mResultTextView) {
					mResultTextView.setText(resultShow);
				}
			}

			@Override
			public void onFailure(int errCode, String errMsg) {
				if (null != mResultTextView) {
					mResultTextView.setText("errCode:" + errCode + ", errMsg:"
							+ errMsg);
				}
			}
			
		});
	}

	protected void Describe() {
		mPush.describeMessage(mId, new DescribeMessageListener() {

			@Override
			public void onSuccess(DescribeMessageResult result) {
				String id = result.getId();
				String userId = result.getUserId();
				String channelId = result.getChannelId();
				MessageContent messageContent = result.getMessage();
				String tag = result.getTag();
				Trigger trigger = result.getTrigger();
				JSONObject extras = result.getExtras();
				
				if (null != mResultTextView) {
					mResultTextView.setText("id:" + id + ", userId:" + userId + ", channelId:" + channelId);
				}
			}

			@Override
			public void onFailure(int errCode, String errMsg) {
				if (null != mResultTextView) {
					mResultTextView.setText("errCode:" + errCode + ", errMsg:"
							+ errMsg);
				}
			}
			
		});
	}

	private void startDebug() {
		mPush.setDebugModeEnabled(true);
		mResultTextView.setText("start debug mode.");
	}

	private void stopDebug() {
		mPush.setDebugModeEnabled(false);
		mResultTextView.setText("stop debug mode.");
	}

	private List<String> createTags() {
		List<String> tags = new ArrayList<String>();
		tags.add("test");
		tags.add("friends");
		return tags;
	}
	
	public static class PushMessageReceiver extends FrontiaPushMessageReceiver {

		private final static String TAG = "PushMessageReceiver";

		@Override
		public void onBind(Context context, int errorCode, String appid,
				String userId, String channelId, String requestId) {
			mUserId = userId;
			mChannelId = channelId;
			StringBuffer sb = new StringBuffer();
			sb.append("绑定成功\n");
			sb.append("errCode:"+errorCode);
			sb.append("appid:"+appid+"\n");
			sb.append("userId:"+userId+"\n");
			sb.append("channelId:"+channelId+"\n");
			sb.append("requestId"+requestId+"\n");
			Log.d(TAG,sb.toString());
		}

		@Override
		public void onUnbind(Context context, int errorCode, String requestId) {
			StringBuffer sb = new StringBuffer();
			sb.append("解绑成功\n");
			sb.append("errCode:"+errorCode);
			sb.append("requestId"+requestId+"\n");
			Log.d(TAG,sb.toString());
		}

		@Override
		public void onSetTags(Context context, int errorCode,
				List<String> successTags, List<String> failTags,
				String requestId) {
			StringBuffer sb = new StringBuffer();
			sb.append("设置tag成功\n");
			sb.append("errCode:"+errorCode);
			sb.append("success tags:");
			for(String tag:successTags){
				sb.append(tag+"\n");
			}
			sb.append("fail tags:");
			for(String tag:failTags){
				sb.append(tag+"\n");
			}
			sb.append("requestId"+requestId+"\n");
			Log.d(TAG,sb.toString());
		}

		@Override
		public void onDelTags(Context context, int errorCode,
				List<String> successTags, List<String> failTags,
				String requestId) {
			StringBuffer sb = new StringBuffer();
			sb.append("删除tag成功\n");
			sb.append("errCode:"+errorCode);
			sb.append("success tags:");
			for(String tag:successTags){
				sb.append(tag+"\n");
			}
			sb.append("fail tags:");
			for(String tag:failTags){
				sb.append(tag+"\n");
			}
			sb.append("requestId"+requestId+"\n");
			Log.d(TAG,sb.toString());
		}

		@Override
		public void onListTags(Context context, int errorCode,
				List<String> tags, String requestId) {
			StringBuffer sb = new StringBuffer();
			sb.append("list tag成功\n");
			sb.append("errCode:"+errorCode);
			sb.append("tags:");
			for(String tag:tags){
				sb.append(tag+"\n");
			}
			sb.append("requestId"+requestId+"\n");
			Log.d(TAG,sb.toString());
		}

		@Override
		public void onMessage(Context context, String message,
				String customContentString) {
			StringBuffer sb = new StringBuffer();
			sb.append("收到消息\n");
			sb.append("内容是:"+customContentString+"\n");
			sb.append("tags:");
			sb.append("message:"+message+"\n");
			Log.d(TAG,sb.toString());
		}

		@Override
		public void onNotificationClicked(Context context, String title,
				String description, String customContentString) {
			StringBuffer sb = new StringBuffer();
			sb.append("通知被点击\n");
			sb.append("title:"+title+"\n");
			sb.append("description:"+description);
			sb.append("customContentString:"+customContentString+"\n");
			Log.d(TAG,sb.toString());
		}

		

	}

}
