package com.kdcm.aidongdong.UI;

import com.kdcm.aidongdong.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class GoodsMainActivity extends TabActivity {
	Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
		it = getIntent();
		String goods_id = it.getStringExtra("goods_id");
		String roll_pics = it.getStringExtra("roll_pics");
		String name = it.getStringExtra("name");
		String price = it.getStringExtra("price");
		String sold_num = it.getStringExtra("sold_num");
		String size = it.getStringExtra("size");
		String color = it.getStringExtra("color");
		String desc = it.getStringExtra("desc");
		it = new Intent(this, GoodsActivity.class);
		it.putExtra("roll_pics", roll_pics);
		it.putExtra("name", name);
		it.putExtra("price", price);
		it.putExtra("sold_num", sold_num);
		it.putExtra("size", size);
		it.putExtra("color", color);
		it.putExtra("goods_id", goods_id);
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("宝贝详情")
				.setContent(
						new Intent(this, GoodsActivity.class)
								.putExtra("roll_pics", roll_pics)
								.putExtra("name", name)
								.putExtra("price", price)
								.putExtra("sold_num", sold_num)
								.putExtra("size", size)
								.putExtra("color", color)
								.putExtra("goods_id", goods_id)
								.putExtra("desc", desc)));
		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator("图文详情")
				.setContent(
						new Intent(this, GoodsIMGActivity.class)
								.putExtra("roll_pics", roll_pics)
								.putExtra("name", name)
								.putExtra("price", price)
								.putExtra("sold_num", sold_num)
								.putExtra("size", size)
								.putExtra("color", color)
								.putExtra("goods_id", goods_id)
								.putExtra("desc", desc)));
		//Review
		tabHost.addTab(tabHost
				.newTabSpec("tab3")
				.setIndicator("商品评论")
				.setContent(
						new Intent(this, GoodsReviewActivity.class)
								.putExtra("roll_pics", roll_pics)
								.putExtra("name", name)
								.putExtra("price", price)
								.putExtra("sold_num", sold_num)
								.putExtra("size", size)
								.putExtra("color", color)
								.putExtra("goods_id", goods_id)
								.putExtra("desc", desc)));
	}

}
