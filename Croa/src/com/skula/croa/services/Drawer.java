package com.skula.croa.services;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.croa.R;
import com.skula.croa.constants.Cnst;
import com.skula.croa.constants.PictureLibrary;
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
		drawFrogs(c);
		drawScores(c);

		// deux grenouilles
		int x0 = 280 - 10;
		int y0 = 0;
		c.drawBitmap(lib.get(R.drawable.frog_blue), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
				new Rect(x0, y0, x0+Cnst.FROG_WIDTH+20, y0+ Cnst.FROG_HEIGHT+20), paint);
		x0 += 30;
		y0 += 20;
		c.drawBitmap(lib.get(R.drawable.frog_yellow), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
				new Rect(x0, y0, x0+Cnst.FROG_WIDTH+20, y0+ Cnst.FROG_HEIGHT+20), paint);
		
		// une grenouille
		x0 = 280 + 100 -5;
		y0 = 0 + 10;
		c.drawBitmap(lib.get(R.drawable.frog_pink), new Rect(0, 0, Cnst.FROG_WIDTH, Cnst.FROG_HEIGHT),
				new Rect(x0, y0, x0+Cnst.FROG_WIDTH+20, y0+ Cnst.FROG_HEIGHT+20), paint);
	}

	public void drawScores(Canvas c) {

	}

	public void drawFrogs(Canvas c) {
		TileOccupants occ = null;
		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				occ = engine.getOccupants(j, i);
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
		int x0 = 280;
		int y0 = 0;

		int dx = Cnst.TILE_SIZE;
		int dy = Cnst.TILE_SIZE;

		Tile[][] tiles = engine.getTiles();

		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
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