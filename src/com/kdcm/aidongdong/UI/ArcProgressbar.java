package com.kdcm.aidongdong.UI;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 弧形进度条
 * 
 * @author Eric
 * 
 */
public class ArcProgressbar extends View {
	private float mTxtWidth;
	// 画字体的画笔
	private Paint mTextPaint;
	/** 分段颜色 */
	private static final int[] SECTION_COLORS = { Color.GREEN, Color.YELLOW,
			Color.RED, Color.WHITE,Color.BLUE };
	  /**
     * 进度条280满
     */
    private int progress=1;
    /**
     * 进度
     */
    private float section;
	public void setProgress(int progress){
		this.progress=progress<=280?progress:280;
		invalidate();
	}
    public ArcProgressbar(Context context) {
        super(context);
    }

    public ArcProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init(canvas);
    }

    private void init(Canvas canvas) {
        // 画弧形的矩阵区域。
    	
        rectBg = new RectF(100,25, diameter+75, diameter);

 

        // 弧形背景。
        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Style.STROKE);
        mPaintBg.setStrokeWidth(bgStrokeWidth);
        mPaintBg.setColor(bgColor);
        canvas.drawArc(rectBg, startAngle, endAngle, false, mPaintBg);

        // 弧形小背景。
        if (showSmallBg) {
            mPaintSmallBg = new Paint();
            mPaintSmallBg.setAntiAlias(true);
            mPaintSmallBg.setStyle(Style.STROKE);
            mPaintSmallBg.setStrokeWidth(barStrokeWidth);
            mPaintSmallBg.setColor(smallBgColor);
            canvas.drawArc(rectBg, startAngle, endAngle, false, mPaintSmallBg);
        }

        // 弧形ProgressBar。
        mPaintBar = new Paint();
        mPaintBar.setAntiAlias(true);
        mPaintBar.setStyle(Style.STROKE);
        mPaintBar.setStrokeWidth(barStrokeWidth);
    	float section = progress <= 280f?progress/280f:1;
    	if(section!=0.0){
    	Log.i("section",section+"" );}
        if (section <= 1.0f / 3.0f) {
			if (section != 0.0f) {
				mPaintBar.setColor(SECTION_COLORS[0]);
			} else {
				mPaintBar.setColor(Color.TRANSPARENT);
			}
        }else {
        	int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
//			int count=0;
//			if(section>1.0f / 3.0f&&section<=1.0f/2.0f/3f){
//				count=2;
//			}
////			}else if(section>1.0f/2.0f&&section<=3.0f/4.0f){
////				count=3;
////			}
//				else{
//				count=3;	
//			}
			int[] colors = new int[count];
			System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
			float[] positions = new float[count];
			if (count == 2) {
				
				positions[1] = 0.0f;
				positions[0] = 1.0f - positions[1];
			} else {
				positions[2]=0;
				positions[0]=1.0f - positions[2]*2;
				positions[1] = (280 / 3) / progress;
//				positions[0] = 0.0f;
//				positions[2] = 1.0f - positions[0] * 2;
			}
			
			positions[positions.length - 1] = 1.0f;
			LinearGradient shader = new LinearGradient(3, 3, (diameter - 3)
					* section, diameter - 3, colors, null,
					Shader.TileMode.MIRROR);
			mPaintBar.setShader(shader);

        }
	//mPaintBar.setColor(SECTION_COLORS[0]);
//        mPaintBar.setColor(barColor);
        
        canvas.drawArc(rectBg, startAngle, progress, false, mPaintBar);

        invalidate();
    }



    /**
     * 设置弧形背景的画笔宽度。
     */
    public void setBgStrokeWidth(int bgStrokeWidth) {
        this.bgStrokeWidth = bgStrokeWidth;
    }

    /**
     * 设置弧形ProgressBar的画笔宽度。
     */
    public void setBarStrokeWidth(int barStrokeWidth) {
        this.barStrokeWidth = barStrokeWidth;
    }

    /**
     * 设置弧形背景的颜色。
     */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 设置弧形ProgressBar的颜色。
     */
    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    /**
     * 设置弧形小背景的颜色。
     */
    public void setSmallBgColor(int smallBgColor) {
        this.smallBgColor = smallBgColor;
    }

    /**
     * 设置弧形的直径。
     */
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    /**
     * 是否显示小背景。
     */
    public void setShowSmallBg(boolean showSmallBg) {
        this.showSmallBg = showSmallBg;
    }

  
/**
 * 外径宽度
 */
    private int bgStrokeWidth = 34;
    /**
     * 内径宽度
     */
    private int barStrokeWidth =28;
    private int bgColor = Color.GRAY;
    private int barColor = Color.RED;
    private int smallBgColor = Color.WHITE;//小背景颜色
  

    /**
     * 开始的角度
     */
    private int startAngle = 130;
    /**
     * 结束的角度
     */
    private int endAngle = 280;
    private Paint mPaintBar = null;
    private Paint mPaintSmallBg = null;
    private Paint mPaintBg = null;
 
    /**
	 * 矩形、确定弧形的大小用
	 */
    private RectF rectBg = null;
    /**
     * 直徑。
     */
    private int diameter = 300;

    private boolean showSmallBg = false;// 是否显示小背景。
 

}
