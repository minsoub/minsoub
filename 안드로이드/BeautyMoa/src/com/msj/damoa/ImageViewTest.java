package com.msj.damoa;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class ImageViewTest extends SurfaceView implements Callback {

	private Bitmap mImage01;
	private Bitmap mImage02;
	private Bitmap mImage03;
	private Bitmap mImage04;
	private Bitmap mImage05;
	private Bitmap mImage06;
	private Drawable mFrontImage[] = new Drawable[4];
	
	public ImageViewTest(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Resources res = context.getResources();
		
		mFrontImage[0] = context.getResources().getDrawable(R.drawable.k001);
		mFrontImage[1] = context.getResources().getDrawable(R.drawable.k002);
		mFrontImage[2] = context.getResources().getDrawable(R.drawable.k003);
		mFrontImage[3] = context.getResources().getDrawable(R.drawable.k004);
		
		// load background image as a Bitmap instead of a Drawable b/c
		// we don't need to transform it and it's faster to draw this way
		mImage01 = BitmapFactory.decodeResource(res, R.drawable.k001);
		mImage02 = BitmapFactory.decodeResource(res, R.drawable.k002);
		mImage03 = BitmapFactory.decodeResource(res, R.drawable.k003);
		mImage04 = BitmapFactory.decodeResource(res, R.drawable.k004);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		canvas.drawBitmap(mImage01, 0, 0, null);
		canvas.drawBitmap(mImage02, 0, 90, null);
		canvas.drawBitmap(mImage03, 0, 190, null);
		canvas.drawBitmap(mImage04, 0, 310, null);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
