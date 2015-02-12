package com.skula.croa.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.croa.constants.Cnst;
import com.skula.croa.services.Drawer;
import com.skula.croa.services.GameEngine;

public class BoardView extends View {
	private Drawer drawer;
	private GameEngine engine;
	
	private int xTile;
	private int yTile;

	public BoardView(Context context, int nPlayers) {
		super(context);
		this.engine = new GameEngine(nPlayers);
		this.drawer = new Drawer(context.getResources(), engine);
		this.xTile = -1;
		this.yTile = -1;
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
			boolean res = getTile(x, y);
			/*if(res){
				if(!engine.isSrcSelected()){
					engine.setSrcPos(xTile, yTile);
				}else{
					engine.setDestPos(xTile, yTile);
				}
				
				if(engine.canProcess()){
					engine.process();
				}
			}*/
			invalidate();
			break;
		}		
		return true;
	}
	
	private boolean getTile(int x, int y) {
		int x0 = 280;
		int y0 = 0;
		int x1 = x0 + Cnst.COLUMNS_COUNT * Cnst.TILE_SIZE;
		int y1 = y0 + Cnst.ROWS_COUNT * Cnst.TILE_SIZE;
		Rect rect = null;		
		
		rect = new Rect(x0, y0, x1, y1);
		if(!rect.contains(x,y)){
			xTile = -1;
			yTile = -1;
			return false;
		}
		
		xTile = ((x - x0) * Cnst.COLUMNS_COUNT) / (Cnst.COLUMNS_COUNT*Cnst.TILE_SIZE);
		yTile = ((y - y0) * Cnst.ROWS_COUNT) / (Cnst.ROWS_COUNT*Cnst.TILE_SIZE);
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.draw(canvas);
		Paint p = new Paint();
		p.setColor(Color.RED);
		p.setTextSize(20f);
		if(xTile!=-1){
			canvas.drawText("(i=" + xTile + ", j:" + yTile + ")", 20, 20, p);
		}
	}
}