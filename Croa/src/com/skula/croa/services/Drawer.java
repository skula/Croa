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

		// deux grenouilles
		int x0 = Cnst.X0 - 10;
		int y0 = Cnst.Y0 - 10;
		c.drawBitmap(lib.get(R.drawable.maid_pink), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
				new Rect(x0, y0, x0+Cnst.FROG_WIDTH, y0+ Cnst.FROG_HEIGHT), paint);
		x0 += 30;
		y0 += 20;
		c.drawBitmap(lib.get(R.drawable.maid_blue), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
				new Rect(x0, y0, x0+Cnst.FROG_WIDTH, y0+ Cnst.FROG_HEIGHT), paint);
		
		// une grenouille
		x0 = Cnst.X0 + 100 -5;
		y0 = Cnst.Y0 + 5 ;
		c.drawBitmap(lib.get(R.drawable.queen_pink), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
				new Rect(x0, y0, x0+Cnst.FROG_WIDTH, y0+ Cnst.FROG_HEIGHT), paint);
	}

	public void drawCursors(Canvas c) {
		int token = engine.getToken();
		for(Frog f : engine.getPlayer(token).getMaids()){
			Rect r = new Rect(Cnst.X0 + f.getxPos()*Cnst.TILE_SIZE, Cnst.Y0 + f.getyPos()*Cnst.TILE_SIZE, 
							Cnst.X0 + (f.getxPos()+1)*Cnst.TILE_SIZE , Cnst.Y0 + (f.getyPos()+1) * Cnst.TILE_SIZE);
			// TODO
		}
		
		Frog q = engine.getPlayer(token).getQueen();
		Rect r = new Rect(Cnst.X0 + q.getxPos()*Cnst.TILE_SIZE, Cnst.Y0 + q.getyPos()*Cnst.TILE_SIZE, 
							Cnst.X0 + (q.getxPos()+1)*Cnst.TILE_SIZE , Cnst.Y0 + (q.getyPos()+1) * Cnst.TILE_SIZE);
	}
	
	public void drawScores(Canvas c) {

	}

	public void drawFrogs(Canvas c) {
		TileOccupants occ = null;
		for (int i = 0; i < Cnst.ROWS_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				occ = engine.getTileOccupants(j, i);
				int id = 0;
				if (occ.getCount() == 1) {
					switch (occ.getFrog1Id()) {
					case 0:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 1:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 2:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 3:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					}

					// draw in the middle
				} else if (occ.getCount() == 2) {
					switch (occ.getFrog1Id()) {
					case 0:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 1:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 2:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 3:
						// id = occ.isFrog1Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					}

					// draw on the left

					switch (occ.getFrog2Id()) {
					case 0:
						// id = occ.isFrog2Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 1:
						// id = occ.isFrog2Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 2:
						// id = occ.isFrog2Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 3:
						// id = occ.isFrog2Queen()?
						// R.drawable.queen_red:R.drawable.frog_red;
						break;
					}

					// draw on the right
				}
			}
		}
	}

	public void drawTiles(Canvas c) {
		int x0 = Cnst.X0;
		int y0 = Cnst.Y0;

		int dx = Cnst.TILE_SIZE;
		int dy = Cnst.TILE_SIZE;

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
						}
						break;
					}
				}
				try {
					c.drawBitmap(lib.get(id), new Rect(0, 0, 100, 100),
							new Rect(x0 + dx * j, y0 + dy * i, x0 + dx * j
									+ Cnst.TILE_SIZE, y0 + dy * i
									+ Cnst.TILE_SIZE), paint);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}
}