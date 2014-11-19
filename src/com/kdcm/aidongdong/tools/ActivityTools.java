package com.kdcm.aidongdong.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityTools extends Activity {
	private static Intent it;
	/**
	 * 跳转太多了，写了一个方法
	 * @param con
	 * @param cla
	 */
	public static void mIntent(Context con,Class cla){
		it=new Intent(con, cla);
		con.startActivity(it);
	}
}
