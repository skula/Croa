package com.skula.croa.activities.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.skula.croa.constants.Cnst;
import com.skula.croa.enums.TileType;
import com.skula.croa.models.Frog;
import com.skula.croa.models.Position;
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
			// Gestion sélection d'une tuile à 2 grenouille
			if(dualSelect){
				int choice = getChoice(x, y);
				if(choice==-1){
					return true;
				}

				if(engine.isSrcSelected()){
					engine.setSrcPos(position.getX(), position.getY());
					engine.setSelFrog(choice);
				}else{
					engine.setDestPos(position.getX(), position.getY());
					engine.process(choice);
				}
				
				dualSelect = false;		
				invalidate();
				return true;
			}
			
			Position p = getTile(x, y);
			if(p.isUndefined()){
				return true;
			}
			position = p;
			
			if(!engine.isSrcSelected()){
				if(engine.canSelectFrog(p.getX(), p.getY())){
					engine.setSrcPos(p.getX(), p.getY());
					TileOccupants occ = engine.getTileOccupants(p.getX(), p.getY());
					Frog f = occ.getFrogByPlayerId(engine.getToken());
					engine.setSelFrog(f.getId());
					if(occ.isQueenAndMaid(engine.getToken())){
						dualSelect = true;
					}else{
					}
				}else{
					return true;
				}
			}else{
				if(engine.canProcess(p.getX(), p.getY())){
					if(engine.getTiles()[p.getX()][p.getY()].getType().equals(TileType.WOODLOG)){
						TileOccupants occ = engine.getTileOccupants(p.getX(), p.getY());
						if(occ.getCount() == 2){
                            int pId = engine.getPlayer(engine.getToken()).getId();
                            if(!engine.getSelFrog().isQueen()
                                           && occ.getFrog1pId() != pId
                                           && occ.getFrog2pId() != pId){
                                           dualSelect = true;
                            }
						}
					}

					if(!dualSelect){
						engine.process();
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
		int x0 = (int)(Cnst.X0 + Cnst.TILE_SIZE*8.5);
		int y0 = (int)(Cnst.Y0 + Cnst.TILE_SIZE * 3.5);
		TileOccupants occ = engine.getTileOccupants(position.getX(), position.getY());
		
		Rect r = new Rect(x0, y0, x0 + Cnst.FROG_WIDTH, y0 + Cnst.FROG_HEIGHT);
		if(r.contains(x,y)){
			return occ.getFrog1().getId();
		}
		x0 += Cnst.TILE_SIZE + 10;
		r = new Rect(x0, y0, x0 + Cnst.FROG_WIDTH, y0 + Cnst.FROG_HEIGHT);
		if(r.contains(x,y)){
			return occ.getFrog2().getId();
		}
		return -1;
	}

	@Override
	public void draw(Canvas canvas) {
		drawer.drawTiles(canvas);
		drawer.drawActiveFrogs(canvas);
		drawer.drawFrogs(canvas);
		drawer.drawScores(canvas);
		
		if(dualSelect){
			drawer.drawDualSelect(canvas, engine.getTileOccupants(position.getX(), position.getY()));
		}
		
		/*Paint p = new Paint();
		p.setColor(Color.RED);
		p.setTextSize(20f);
		
		if(position!=null){
			canvas.drawText("(i=" + position.getX() + ", j:" + position.getY() + ")", 20, 20, p);
		}*/
	}
	
	
}