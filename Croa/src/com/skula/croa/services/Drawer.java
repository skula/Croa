package com.skula.croa.services;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.croa.R;
import com.skula.croa.constants.Cnst;
import com.skula.croa.constants.PictureLibrary;
import com.skula.croa.enums.Male;
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

	public void drawWinner(Canvas c, int id){
	
	}
	
	public void drawScores(Canvas c) {
		int np = engine.getnPlayers();
		paint.setTextSize(30f);

		// joueur 1
		int x = 50;
		int y = 50;
		paint.setColor(Color.CYAN);
		c.drawText("Joueur 1", x, y, paint);
		if (engine.getPlayer(0).isDead()) {
			x = 80;
			y += 20;
			c.drawBitmap(lib.get(R.drawable.grave), new Rect(0, 0,
					Cnst.GRAVE_WIDTH, Cnst.GRAVE_HEIGHT), new Rect(x, y, x
					+ Cnst.GRAVE_WIDTH, y + Cnst.GRAVE_HEIGHT), paint);
		} else {
			y += 20;
			for (Male m : engine.getPlayer(0).getMalesLeft()) {
				int id = Cnst.getMalePictId(m);
				c.drawBitmap(lib.get(id), new Rect(0, 0, Cnst.MALE_SPAWN_SIZE,
						Cnst.MALE_SPAWN_SIZE), new Rect(x, y, x
						+ Cnst.MALE_SPAWN_SIZE, y + Cnst.MALE_SPAWN_SIZE),
						paint);
				x += 25;
			}
		}

		x = 1070;
		y = 50;
		// joueur 2
		paint.setColor(Color.MAGENTA);
		c.drawText("Joueur 2", x, y, paint);

		if (engine.getPlayer(1).isDead()) {
			x = 1100;
			y += 20;
			c.drawBitmap(lib.get(R.drawable.grave), new Rect(0, 0,
					Cnst.GRAVE_WIDTH, Cnst.GRAVE_HEIGHT), new Rect(x, y, x
					+ Cnst.GRAVE_WIDTH, y + Cnst.GRAVE_HEIGHT), paint);
		} else {
			y += 20;
			for (Male m : engine.getPlayer(1).getMalesLeft()) {
				int id = Cnst.getMalePictId(m);
				c.drawBitmap(lib.get(id), new Rect(0, 0, Cnst.MALE_SPAWN_SIZE,
						Cnst.MALE_SPAWN_SIZE), new Rect(x, y, x
						+ Cnst.MALE_SPAWN_SIZE, y + Cnst.MALE_SPAWN_SIZE),
						paint);
				x += 25;
			}
		}

		if (np >= 3) {
			paint.setColor(Color.GREEN);
			x = 1070;
			y = 630;
			c.drawText("Joueur 3", x, y, paint);

			if (engine.getPlayer(2).isDead()) {
				x = 1100;
				y += 20;
				c.drawBitmap(lib.get(R.drawable.grave), new Rect(0, 0,
						Cnst.GRAVE_WIDTH, Cnst.GRAVE_HEIGHT), new Rect(x, y, x
						+ Cnst.GRAVE_WIDTH, y + Cnst.GRAVE_HEIGHT), paint);
			} else {
				y += 20;
				for (Male m : engine.getPlayer(3).getMalesLeft()) {
					int id = Cnst.getMalePictId(m);
					c.drawBitmap(lib.get(id), new Rect(0, 0,
							Cnst.MALE_SPAWN_SIZE, Cnst.MALE_SPAWN_SIZE),
							new Rect(x, y, x + Cnst.MALE_SPAWN_SIZE, y
									+ Cnst.MALE_SPAWN_SIZE), paint);
					x += 25;
				}
			}
		}

		if (np == 4) {
			// joueur 3
			paint.setColor(Color.YELLOW);
			x = 50;
			y = 630;
			c.drawText("Joueur 4", x, y, paint);

			if (engine.getPlayer(3).isDead()) {
				x = 80;
				y += 20;
				c.drawBitmap(lib.get(R.drawable.grave), new Rect(0, 0,
						Cnst.GRAVE_WIDTH, Cnst.GRAVE_HEIGHT), new Rect(x, y, x
						+ Cnst.GRAVE_WIDTH, y + Cnst.GRAVE_HEIGHT), paint);
			} else {
				y += 20;
				for (Male m : engine.getPlayer(2).getMalesLeft()) {
					int id = Cnst.getMalePictId(m);
					c.drawBitmap(lib.get(id), new Rect(0, 0,
							Cnst.MALE_SPAWN_SIZE, Cnst.MALE_SPAWN_SIZE),
							new Rect(x, y, x + Cnst.MALE_SPAWN_SIZE, y
									+ Cnst.MALE_SPAWN_SIZE), paint);
					x += 25;
				}
			}
		}
	}

	public void drawDualSelect(Canvas c, TileOccupants occ) {
		paint.setColor(Color.DKGRAY);
		int x0 = (int) (Cnst.X0 + Cnst.TILE_SIZE * 8.5);
		int y0 = (int) (Cnst.Y0 + Cnst.TILE_SIZE * 3.5);
		c.drawRect(new Rect(x0, y0, x0 + Cnst.TILE_SIZE * 2, y0
				+ Cnst.TILE_SIZE), paint);

		int id1 = 0;
		int id2 = 0;
		if (occ.getFrog1().isQueen()) {
			id1 = Cnst.getQueenPictId(occ.getFrog1pId(), occ.getFrog1()
					.isStuck());
			id2 = Cnst.getMaidPictId(occ.getFrog2pId(), occ.getFrog2()
					.isStuck());
		} else if (occ.getFrog2().isQueen()) {
			id1 = Cnst.getMaidPictId(occ.getFrog1pId(), occ.getFrog1()
					.isStuck());
			id2 = Cnst.getQueenPictId(occ.getFrog2pId(), occ.getFrog2()
					.isStuck());
		} else {
			id1 = Cnst.getMaidPictId(occ.getFrog1pId(), occ.getFrog1()
					.isStuck());
			id2 = Cnst.getMaidPictId(occ.getFrog2pId(), occ.getFrog2()
					.isStuck());
		}

		c.drawBitmap(lib.get(id1), new Rect(0, 0, Cnst.FROG_WIDTH,
				Cnst.FROG_HEIGHT), new Rect(x0, y0, x0 + Cnst.FROG_WIDTH, y0
				+ Cnst.FROG_HEIGHT), paint);
		x0 += Cnst.TILE_SIZE + 10;
		c.drawBitmap(lib.get(id2), new Rect(0, 0, Cnst.FROG_WIDTH,
				Cnst.FROG_HEIGHT), new Rect(x0, y0, x0 + Cnst.FROG_WIDTH, y0
				+ Cnst.FROG_HEIGHT), paint);
	}

	public void drawActiveFrogs(Canvas c) {
		int token = engine.getToken();
		Rect r = null;
		for (Frog f : engine.getPlayer(token).getMaids()) {
			if (f.isActive()) {
				r = new Rect(Cnst.X0 + f.getxPos() * Cnst.TILE_SIZE, Cnst.Y0
						+ f.getyPos() * Cnst.TILE_SIZE, Cnst.X0
						+ (f.getxPos() + 1) * Cnst.TILE_SIZE, Cnst.Y0
						+ (f.getyPos() + 1) * Cnst.TILE_SIZE);
				c.drawBitmap(lib.get(R.drawable.sel), new Rect(0, 0,
						Cnst.TILE_SIZE, Cnst.TILE_SIZE), r, paint);
			}
		}

		if (engine.getPlayer(token).getQueen().isActive()) {
			Frog q = engine.getPlayer(token).getQueen();
			r = new Rect(Cnst.X0 + q.getxPos() * Cnst.TILE_SIZE, Cnst.Y0
					+ q.getyPos() * Cnst.TILE_SIZE, Cnst.X0 + (q.getxPos() + 1)
					* Cnst.TILE_SIZE, Cnst.Y0 + (q.getyPos() + 1)
					* Cnst.TILE_SIZE);
			c.drawBitmap(lib.get(R.drawable.sel), new Rect(0, 0,
					Cnst.TILE_SIZE, Cnst.TILE_SIZE), r, paint);
		}
	}

	public void drawFrogs(Canvas c) {
		TileOccupants occ = null;
		for (int i = 0; i < Cnst.ROWS_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				occ = engine.getTileOccupants(j, i);
				int id = 0;
				if (occ.getCount() == 1) {
					if (occ.getFrog1().isQueen()) {
						id = Cnst.getQueenPictId(occ.getFrog1pId(), occ
								.getFrog1().isStuck());
					} else {
						id = Cnst.getMaidPictId(occ.getFrog1pId(), occ
								.getFrog1().isStuck());
					}

					c.drawBitmap(lib.get(id), new Rect(0, 0, Cnst.FROG_WIDTH,
							Cnst.FROG_HEIGHT), new Rect(Cnst.X0 + 10 + j
							* Cnst.TILE_SIZE,
							Cnst.Y0 + 10 + i * Cnst.TILE_SIZE, Cnst.X0 + 10 + j
									* Cnst.TILE_SIZE + Cnst.FROG_WIDTH, Cnst.Y0
									+ 10 + i * Cnst.TILE_SIZE
									+ Cnst.FROG_HEIGHT), paint);
				} else if (occ.getCount() == 2) {
					int id1 = 0;
					int id2 = 0;
					if (occ.getFrog1().isQueen()) {
						id1 = Cnst.getMaidPictId(occ.getFrog2pId(), occ
								.getFrog2().isStuck());
						id2 = Cnst.getQueenPictId(occ.getFrog1pId(), occ
								.getFrog1().isStuck());
					} else if (occ.getFrog2().isQueen()) {
						id1 = Cnst.getMaidPictId(occ.getFrog1pId(), occ
								.getFrog1().isStuck());
						id2 = Cnst.getQueenPictId(occ.getFrog2pId(), occ
								.getFrog2().isStuck());
					} else {
						id1 = Cnst.getMaidPictId(occ.getFrog1pId(), occ
								.getFrog1().isStuck());
						id2 = Cnst.getMaidPictId(occ.getFrog2pId(), occ
								.getFrog2().isStuck());
					}

					c.drawBitmap(lib.get(id1), new Rect(0, 0, Cnst.FROG_WIDTH,
							Cnst.FROG_HEIGHT), new Rect(Cnst.X0 - 10 + j
							* Cnst.TILE_SIZE,
							Cnst.Y0 - 10 + i * Cnst.TILE_SIZE, Cnst.X0 - 10
									+ Cnst.FROG_WIDTH + j * Cnst.TILE_SIZE,
							Cnst.Y0 - 10 + Cnst.FROG_HEIGHT + i
									* Cnst.TILE_SIZE), paint);
					c.drawBitmap(lib.get(id2), new Rect(0, 0, Cnst.FROG_WIDTH,
							Cnst.FROG_HEIGHT), new Rect(Cnst.X0 + 20 + j
							* Cnst.TILE_SIZE,
							Cnst.Y0 + 10 + i * Cnst.TILE_SIZE, Cnst.X0 + 20
									+ Cnst.FROG_WIDTH + j * Cnst.TILE_SIZE,
							Cnst.Y0 + 10 + Cnst.FROG_HEIGHT + i
									* Cnst.TILE_SIZE), paint);
				}
			}
		}
	}

	public void drawTiles(Canvas c) {
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
				c.drawBitmap(lib.get(id), new Rect(0, 0, Cnst.TILE_SIZE,
						Cnst.TILE_SIZE), new Rect(Cnst.X0 + Cnst.TILE_SIZE * j,
						Cnst.Y0 + Cnst.TILE_SIZE * i, Cnst.X0 + Cnst.TILE_SIZE
								* (j + 1), Cnst.Y0 + Cnst.TILE_SIZE * (i + 1)),
						paint);
			}
		}
	}
}