package com.skula.croa.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {

	public BoardView(Context context, int nPlayers) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:
		
			break;
		}
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
	}
}