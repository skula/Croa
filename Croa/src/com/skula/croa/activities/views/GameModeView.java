package com.skula.croa.activities.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.croa.R;
import com.skula.croa.activities.BoardActivity;
import com.skula.croa.constants.Cnst;
import com.skula.croa.constants.PictureLibrary;

public class GameModeView extends View {
	private static final int X0 = 1000;
	private static final int Y0 = 500;
	private static final int DY = 125;
	
	private PictureLibrary lib;
	private Paint paint;
	
	public GameModeView(Context context) {
		super(context);
		this.lib = new PictureLibrary(context.getResources());
		this.paint = new Paint();
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
			if(getPlayerCount(x, y)!=-1){
				Intent intent = new Intent(getContext(), BoardActivity.class);
				intent.putExtra(Cnst.BUNDLE_NAME_PLAYERS_COUNT, getPlayerCount(x, y)+"");
				getContext().startActivity(intent);
			}
			invalidate();
			break;
		}		
		return true;
	}
	
	private int getPlayerCount(int x, int y) {	
		int xCount = X0;
		int yCount = Y0;
		
		Rect r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		if(r.contains(x,y)){
			return 2;
		}
		yCount += DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		if(r.contains(x,y)){
			return 3;
		}
		yCount += DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		if(r.contains(x,y)){
			return 4;
		}
		
		return -1;
	}
	
	@Override
	public void draw(Canvas canvas) {		
		canvas.drawBitmap(lib.get(R.drawable.croa_logo), new Rect(0, 0, Cnst.LOGO_WIDTH,
						Cnst.LOGO_HEIGHT), new Rect(0,0, Cnst.LOGO_WIDTH, Cnst.LOGO_HEIGHT),
						paint);
						
		int xCount = X0;
		int yCount = Y0;
		
		/*Rect r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		canvas.drawBitmap(lib.get(R.drawable.gamemode_two_player), new Rect(0, 0, Cnst.GAME_MODE_WIDTH,
						Cnst.GAME_MODE_HEIGHT), r, paint);
		yCount += DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		canvas.drawBitmap(lib.get(R.drawable.gamemode_three_player), new Rect(0, 0, Cnst.GAME_MODE_WIDTH,
						Cnst.GAME_MODE_HEIGHT), r, paint);
		
		yCount += DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		canvas.drawBitmap(lib.get(R.drawable.gamemode_four_player), new Rect(0, 0, Cnst.GAME_MODE_WIDTH,
						Cnst.GAME_MODE_HEIGHT), r, paint);*/
	}
}