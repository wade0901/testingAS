package com.example.wade_lee.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by Wade_Lee on 2/2/2017.
 */

public class FlingContainer extends LinearLayout {

	public interface OnSwipeListener {
		int SWIPE_RIGHT = 100;
		int SWIPE_LEFT = 200;
		void onSwipeView(int eventType);
	}

	private static final int IGNORABLE_SWIPE_VELOCITY = 500;
	private GestureDetector mGestureDetector;
	private OnSwipeListener mOnSwipeListener;
	private float deviceDensity;

	public FlingContainer(Context context) {
		super(context);
		init(context);
	}

	public FlingContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public FlingContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
		mOnSwipeListener = onSwipeListener;
	}

	private void init(Context context) {
		mGestureDetector = new GestureDetector(getContext(), new SwipeListener());
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		deviceDensity = displayMetrics.density;
	}

	private class SwipeListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float dpVX = velocityX / deviceDensity;
			float dpVY = velocityY / deviceDensity;
			// velocity is too small. ignore it!
			if (Math.abs(dpVX) < IGNORABLE_SWIPE_VELOCITY && Math.abs(dpVY) < IGNORABLE_SWIPE_VELOCITY) {
				return false;
			}

			boolean flingToLeft = velocityX < 0;
			boolean flingToRight = velocityX > 0;
			if (flingToLeft) { // Fling to left
//				mOnSwipeListener.onSwipeView(OnSwipeListener.SWIPE_LEFT);
				performHandler(OnSwipeListener.SWIPE_LEFT);
			}
			if (flingToRight) { // Fling to right
//				mOnSwipeListener.onSwipeView(OnSwipeListener.SWIPE_RIGHT);
				performHandler(OnSwipeListener.SWIPE_RIGHT);
			}

			return true;
		}


		void performHandler(int swipeEvent){

			int selectedIndex = 0;
			for (int index = 0; index < getChildCount(); index++) {
				if (getChildAt(index).isSelected()){
					selectedIndex = index;
					break;
				}
			}

			boolean isRightSwipe = swipeEvent == OnSwipeListener.SWIPE_RIGHT;
			boolean isLeftSwipe = swipeEvent == OnSwipeListener.SWIPE_LEFT;

			System.out.println("~~:"+isRightSwipe+","+isLeftSwipe+","+selectedIndex+","+swipeEvent);

			if (isRightSwipe && selectedIndex < getChildCount()-1){
				System.out.println("~~~isRight:"+selectedIndex);
				getChildAt(++selectedIndex).performClick();
			}

			if (isLeftSwipe && selectedIndex > 0){
				System.out.println("~~~isLeft:"+selectedIndex);
				getChildAt(--selectedIndex).performClick();
			}
		}
	}

	private int lastMotionX;
	private int lastMotionY;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			lastMotionX = (int) event.getX();
			lastMotionY = (int) event.getY();
		}

		final int scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int diffX = Math.abs(x - lastMotionX);
		final int diffY = Math.abs(y - lastMotionY);
		boolean isSwipingSideways = diffX > scaledTouchSlop && diffX > diffY;

		if (action == MotionEvent.ACTION_MOVE && isSwipingSideways) {
			// it will intercept while return true
			return true;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return mGestureDetector.onTouchEvent(ev);
	}
}
