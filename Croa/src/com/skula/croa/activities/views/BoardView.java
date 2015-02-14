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
			if(res){
				if(!engine.isSrcSelected()){
					engine.setSrcPos(xTile, yTile);
				}else{
					engine.setDestPos(xTile, yTile);
				}
				
				if(engine.canProcess()){
					engine.process();
				}
			}
			invalidate();
			break;
		}		
		return true;
	}
	
	private boolean getTile(int x, int y) {	
		int size = Cnst.COLUMNS_COUNT * Cnst.TILE_SIZE;
		if(x >= Cnst.X0 && x <= Cnst.X0 + size && y >= Cnst.Y0 && y <= Cnst.Y0 + size){
			xTile = ((x - Cnst.X0) * Cnst.COLUMNS_COUNT) / (size);
			yTile = ((y - Cnst.Y0) * Cnst.ROWS_COUNT) / (size);
			return true;
		}else{
			xTile = -1;
			yTile = -1;
			return false;
		}
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