package com.skula.croa.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.croa.constants.Cnst;
import com.skula.croa.models.TileOccupants;
import com.skula.croa.services.Drawer;
import com.skula.croa.services.GameEngine;

public class BoardView extends View {
	private Drawer drawer;
	private GameEngine engine;
	
	private Position position;
	
	private boolean dualSelect;

	public BoardView(Context context, int nPlayers) {
		super(context);
		this.engine = new GameEngine(nPlayers);
		this.drawer = new Drawer(context.getResources(), engine);
		this.dualSelect = false;
		this.position = new Position(-1,-1);
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
			// si deux grenouilles sur la mm case
			if(dualSelect){
				int choice = getChoice(x, y);
			// si une grenouilles sur la case
			}else{
				Position p = getTile(x, y);
				if(!p.isUndefined()){
					if(!engine.isSrcSelected()){
						if(engine.canSrcSelect(position.getX(), position.getY())){
							TileOccupants occ = engine.getTileOccupants(p.getX(), p.getY());
							
							
						}
					}
				}			
			}
			invalidate();
			break;
		}		
		return true;
	}
	
	private Position getTile(int x, int y) {	
		int size = Cnst.COLUMNS_COUNT * Cnst.TILE_SIZE;
		if(x >= Cnst.X0 && x <= Cnst.X0 + size && y >= Cnst.Y0 && y <= Cnst.Y0 + size){
			int x0 = ((x - Cnst.X0) * Cnst.COLUMNS_COUNT) / (size);
			int y0 = ((y - Cnst.Y0) * Cnst.ROWS_COUNT) / (size);
			return new Position(x0, y0);
		}else{
			return new Position(-1, -1);
		}
	}
	
	private int getChoice(int x, int y) {	
		return -1;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.drawTiles(canvas);
		drawer.drawActiveFrogs(canvas);
		drawer.drawFrogs(canvas);
		drawer.drawScores(canvas);
		
		Paint p = new Paint();
		p.setColor(Color.RED);
		p.setTextSize(20f);
		
		if(position!=null){
			canvas.drawText("(i=" + position.getX() + ", j:" + position.getY() + ")", 20, 20, p);
		}
	}
	
	public class Position{
		private int x;
		private int y;
		
		public Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public boolean isUndefined(){
			return x==-1 || y==-1;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
}