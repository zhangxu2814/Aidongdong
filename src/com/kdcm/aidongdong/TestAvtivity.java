package com.kdcm.aidongdong;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kdcm.aidongdong.ImageTool.ImageLoader;
import com.kdcm.aidongdong.web.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TestAvtivity extends Activity {
	private ViewPager adViewPager;
	private LinearLayout pagerLayout;
	private List<View> pageViews;
	private ImageView[] imageViews;
	private ImageView imageView;
	// private AdPageAdapter adapter;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private boolean isContinue = true;
	public ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_baobeixiangqing);
		init();
		// imageLoader = new ImageLoader(getApplication());
		// initViewPager();
		// }
		//
		// private void initViewPager() {
		//
		// // 从布局文件中获取ViewPager父容器
		// pagerLayout = (LinearLayout) findViewById(R.id.view_pager_content);
		// // 创建ViewPager
		// adViewPager = new ViewPager(this);
		//
		// // 获取屏幕像素相关信息
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		//
		// // 根据屏幕信息设置ViewPager广告容器的宽高
		// adViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
		// dm.heightPixels * 2 / 5));
		//
		// // 将ViewPager容器设置到布局文件父容器中
		// pagerLayout.addView(adViewPager);
		//
		// initPageAdapter();
		//
		// initCirclePoint();
		//
		// adViewPager.setAdapter(adapter);
		// adViewPager.setOnPageChangeListener(new AdPageChangeListener());
		//
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// while (true) {
		// if (isContinue) {
		// viewHandler.sendEmptyMessage(atomicInteger.get());
		// atomicOption();
		// }
		// }
		// }
		// }).start();
		// }
		//
		// private void atomicOption() {
		// atomicInteger.incrementAndGet();
		// if (atomicInteger.get() > imageViews.length - 1) {
		// atomicInteger.getAndAdd(-5);
		// }
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		//
		// }
		// }
		//
		// /*
		// * 每隔固定时间切换广告栏图片
		// */
		// private final Handler viewHandler = new Handler() {
		//
		// @Override
		// public void handleMessage(Message msg) {
		// adViewPager.setCurrentItem(msg.what);
		// super.handleMessage(msg);
		// }
		//
		// };
		//
		// private void initPageAdapter() {
		// pageViews = new ArrayList<View>();
		//
		// ImageView img1 = new ImageView(this);
		// // img1.setBackgroundResource(R.drawable.view_add_1);
		// imageLoader
		// .DisplayImage(
		// "http://e.hiphotos.baidu.com/image/pic/item/29381f30e924b8993d7fca4f6c061d950a7bf602.jpg",
		// img1);
		// pageViews.add(img1);
		//
		// ImageView img6 = new ImageView(this);
		// imageLoader
		// .DisplayImage(
		// "http://e.hiphotos.baidu.com/image/pic/item/29381f30e924b8993d7fca4f6c061d950a7bf602.jpg",
		// img6);
		// pageViews.add(img6);
		//
		// adapter = new AdPageAdapter(pageViews);
		// }
		//
		// private void initCirclePoint() {
		// ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		// imageViews = new ImageView[pageViews.size()];
		// // 广告栏的小圆点图标
		// for (int i = 0; i < pageViews.size(); i++) {
		// // 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
		// imageView = new ImageView(this);
		// imageView.setLayoutParams(new LayoutParams(10, 10));
		// imageViews[i] = imageView;
		//
		// // 初始值, 默认第0个选中
		// if (i == 0) {
		// imageViews[i].setBackgroundResource(R.drawable.point_focused);
		// } else {
		// imageViews[i].setBackgroundResource(R.drawable.point_unfocused);
		// }
		// // 将小圆点放入到布局中
		// group.addView(imageViews[i]);
		// }
		// }
		//
		// /**
		// * ViewPager 页面改变监听器
		// */
		// private final class AdPageChangeListener implements
		// OnPageChangeListener {
		//
		// /**
		// * 页面滚动状态发生改变的时候触发
		// */
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		// }
		//
		// /**
		// * 页面滚动的时候触发
		// */
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		// }
		//
		// /**
		// * 页面选中的时候触发
		// */
		// @Override
		// public void onPageSelected(int arg0) {
		// // 获取当前显示的页面是哪个页面
		// atomicInteger.getAndSet(arg0);
		// // 重新设置原点布局集合
		// for (int i = 0; i < imageViews.length; i++) {
		// imageViews[arg0]
		// .setBackgroundResource(R.drawable.point_focused);
		// if (arg0 != i) {
		// imageViews[i]
		// .setBackgroundResource(R.drawable.point_unfocused);
		// }
		// }
		// }
		// }
		//
		// private final class AdPageAdapter extends PagerAdapter {
		// private List<View> views = null;
		//
		// /**
		// * 初始化数据源, 即View数组
		// */
		// public AdPageAdapter(List<View> views) {
		// this.views = views;
		// }
		//
		// /**
		// * 从ViewPager中删除集合中对应索引的View对象
		// */
		// @Override
		// public void destroyItem(View container, int position, Object object)
		// {
		// ((ViewPager) container).removeView(views.get(position));
		// }
		//
		// /**
		// * 获取ViewPager的个数
		// */
		// @Override
		// public int getCount() {
		// return views.size();
		// }
		//
		// /**
		// * 从View集合中获取对应索引的元素, 并添加到ViewPager中
		// */
		// @Override
		// public Object instantiateItem(View container, int position) {
		// ((ViewPager) container).addView(views.get(position), 0);
		// return views.get(position);
		// }
		//
		// /**
		// * 是否将显示的ViewPager页面与instantiateItem返回的对象进行关联 这个方法是必须实现的
		// */
		// @Override
		// public boolean isViewFromObject(View view, Object object) {
		// return view == object;
		// }
		// }
		// }
	}

	private void init() {
		HttpUtils.test(res);
	}

	JsonHttpResponseHandler res = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			super.onSuccess(statusCode, headers, response);
			int result = 0;
			try {
				result = Integer.valueOf(response.getString("result"));

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (statusCode == 200 & result == 6262) {
					Toast.makeText(getApplicationContext(), "获取数据成功",
							Toast.LENGTH_SHORT).show();
					Log.i("response", response+"");
					String data=response.getString("data");
					String id=new JSONObject(data).getString("id");
					Log.i("array", data+"idididi"+id);
				} else {
					Toast.makeText(getApplicationContext(), "请到检查库存是否充足",
							Toast.LENGTH_SHORT).show();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
