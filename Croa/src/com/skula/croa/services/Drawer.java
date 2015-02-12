package com.skula.croa.services;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.croa.R;
import com.skula.croa.constants.Cnst;
import com.skula.croa.constants.PictureLibrary;
import com.skula.croa.models.Frog;
import com.skula.croa.models.Tile;
import com.skula.croa.models.TileOccupants;

public class Drawer {
	private PictureLibrary lib;
	private Paint paint;
	private GameEngine engine;

	public Drawer(Resources res, GameEngine engine) {
		this.engine = engine;
		this.paint = new Paint();
		this.lib = new PictureLibrary(res);
	}

	public void draw(Canvas c) {
		drawTiles(c);
		drawCursors(c);
		drawFrogs(c);
		drawScores(c);
	}
	
	private void drawScores(Canvas c) {
		// nombre de grenouilles restante
		// males restant
		// tombe (si reine morte)
	}

	private void drawCursors(Canvas c) {
		int token = engine.getToken();
		Rect r  = null;
		for(Frog f : engine.getPlayer(token).getMaids()){
			r = new Rect(Cnst.X0 + f.getxPos()*Cnst.TILE_SIZE, Cnst.Y0 + f.getyPos()*Cnst.TILE_SIZE, 
							Cnst.X0 + (f.getxPos()+1)*Cnst.TILE_SIZE , Cnst.Y0 + (f.getyPos()+1) * Cnst.TILE_SIZE);
			c.drawBitmap(lib.get(R.drawable.sel), new Rect(0, 0, Cnst.TILE_SIZE, Cnst.TILE_SIZE), r,  paint);
		}
		
		Frog q = engine.getPlayer(token).getQueen();
		r = new Rect(Cnst.X0 + q.getxPos()*Cnst.TILE_SIZE, Cnst.Y0 + q.getyPos()*Cnst.TILE_SIZE, 
							Cnst.X0 + (q.getxPos()+1)*Cnst.TILE_SIZE , Cnst.Y0 + (q.getyPos()+1) * Cnst.TILE_SIZE);
		c.drawBitmap(lib.get(R.drawable.sel), new Rect(0, 0, Cnst.TILE_SIZE, Cnst.TILE_SIZE), r,  paint);
	}
	
	private void drawFrogs(Canvas c) {
		TileOccupants occ = null;
		for (int i = 0; i < Cnst.ROWS_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				occ = engine.getTileOccupants(j, i);
				int id = 0;
				if (occ.getCount() == 1) {
					switch (occ.getFrog1Id()) {
					case 0:
						id = occ.isFrog1queen()?R.drawable.queen_blue:R.drawable.maid_blue;
						break;
					case 1:
						id = occ.isFrog1queen()?R.drawable.queen_pink:R.drawable.maid_pink;
						break;
					case 2:
						id = occ.isFrog1queen()?R.drawable.queen_green:R.drawable.maid_green;
						break;
					case 3:
						id = occ.isFrog1queen()?R.drawable.queen_yellow:R.drawable.maid_yellow;
						break;
					}
					c.drawBitmap(lib.get(id), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
								 new Rect(Cnst.X0 + 5 + j * Cnst.TILE_SIZE, Cnst.Y0 + 5 + + i * Cnst.TILE_SIZE , 
										  Cnst.X0 + 5 + j * Cnst.TILE_SIZE +  Cnst.FROG_WIDTH , Cnst.Y0 + 5 + i * Cnst.TILE_SIZE +  Cnst.FROG_HEIGHT) , 
								 paint);
				} else if (occ.getCount() == 2) {
					int id1 = 0;
					int id2 = 0;
					if(occ.isFrog1queen()){
						id1 = Cnst.getMaidPictId(occ.getFrog2Id());
						id2 = Cnst.getQueenPictId(occ.getFrog1Id());
					} else if(occ.isFrog2queen()){
						id1 = Cnst.getMaidPictId(occ.getFrog1Id());
						id2 = Cnst.getQueenPictId(occ.getFrog2Id());
					} else {
						id1 = Cnst.getMaidPictId(occ.getFrog1Id());
						id2 = Cnst.getMaidPictId(occ.getFrog2Id());
					}
					
					c.drawBitmap(lib.get(id1), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
							new Rect(Cnst.X0 - 10, Cnst.Y0 - 10, 
									 Cnst.X0 - 10 + Cnst.FROG_WIDTH, Cnst.Y0 - 10+ Cnst.FROG_HEIGHT), paint);
					c.drawBitmap(lib.get(id2), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
							new Rect(Cnst.X0 + 20, Cnst.Y0 + 10, 
									 Cnst.X0 + 20 + Cnst.FROG_WIDTH, Cnst.Y0 + 10 + Cnst.FROG_HEIGHT), paint);
				}
			}
		}
	}

	private void drawTiles(Canvas c) {
		Tile[][] tiles = engine.getTiles();

		for (int i = 0; i < Cnst.ROWS_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				int id = 0;
				if (tiles[j][i].isHidden()) {
					switch (tiles[j][i].getBackType()) {
					case DEPTH_1:
						id = R.drawable.depth_one;
						break;
					case DEPTH_2:
						id = R.drawable.depth_two;
						break;
					case DEPTH_3:
						id = R.drawable.depth_three;
						break;
					}
				} else {
					switch (tiles[j][i].getType()) {
					case MOSQUITO:
						id = R.drawable.mosquito;
						break;
					case MUD:
						id = R.drawable.mud;
						break;
					case WATERLILY:
						id = R.drawable.waterlilly;
						break;
					case PIKE:
						id = R.drawable.pike;
						break;
					case WOODLOG:
						id = R.drawable.woodlog;
						break;
					case REED:
						id = R.drawable.reed;
						break;
					case MALE:
						switch (tiles[j][i].getMaleId()) {
						case GREEN:
							id = R.drawable.male_green;
							break;
						case YELLOW:
							id = R.drawable.male_yellow;
							break;
						case RED:
							id = R.drawable.male_red;
							break;
						case BLUE:
							id = R.drawable.male_blue;
							break;
						case PINK:
							id = R.drawable.male_pink;
							break;
						case PURPLE:
							id = R.drawable.male_purple;
							break;
						default:
							break;
						}
						break;
					default:
						break;
					}
				}
				c.drawBitmap(lib.get(id), new Rect(0, 0, Cnst.TILE_SIZE, Cnst.TILE_SIZE),
						new Rect(Cnst.X0 + Cnst.TILE_SIZE * j, Cnst.Y0 + Cnst.TILE_SIZE * i, 
								 Cnst.X0 + Cnst.TILE_SIZE * (j +1), Cnst.Y0 + Cnst.TILE_SIZE * (i +1)), paint);
				
			}
		}
	}
}