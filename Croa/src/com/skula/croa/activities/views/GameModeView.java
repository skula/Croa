package com.skula.croa.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.croa.constants.Cnst;
import com.skula.croa.constants.PictureLibrary;

public class GameModeView extends View {
	private static final int X0 = 1000;
	private static final int Y0 = 500;
	private static final int DY = 125;
	
	private PictureLibrary lib;
	
	public GameModeView(Context context) {
		super(context);
		this.lib = new PictureLibrary(context.getResources());
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
		yCount +=DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		if(r.contains(x,y)){
			return 3;
		}
		yCount +=DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		if(r.contains(x,y)){
			return 4;
		}
		
		return -1;
	}
	
	@Override
	public void draw(Canvas canvas) {
		
		/*canvas.drawBitmap(lib.get(id), new Rect(0, 0, Cnst.TILE_SIZE,
						Cnst.TILE_SIZE), new Rect(Cnst.X0 + Cnst.TILE_SIZE * j,
						Cnst.Y0 + Cnst.TILE_SIZE * i, Cnst.X0 + Cnst.TILE_SIZE
								* (j + 1), Cnst.Y0 + Cnst.TILE_SIZE * (i + 1)),
						paint);*/
						
		int xCount = X0;
		int yCount = Y0;
		
		Rect r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		
		yCount +=DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
		
		yCount +=DY;
		r = new Rect(xCount, yCount, xCount + Cnst.GAME_MODE_WIDTH, yCount + Cnst.GAME_MODE_HEIGHT);
	}
}