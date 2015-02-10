package com.skula.croa.services;

import java.util.Collections;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.croa.constants.Cnst;
import com.skula.croa.constants.PictureLibrary;
import com.skula.croa.models.Tile;

public class Drawer {
	private Tile[][] tiles;
	private PictureLibrary lib;
	private Paint paint;

	public Drawer(Resources res) {
		this.paint = new Paint();
		this.lib = new PictureLibrary(res);
		List<Tile> tmp = Tile.getAllTiles();
		Collections.shuffle(tmp);

		this.tiles = new Tile[Cnst.COLUMNS_COUNT][Cnst.ROW_COUNT];

		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				tiles[j][i] = tmp.remove(0);
			}
		}
	}

	public void draw(Canvas c) {
		int x0 = 0;
		int y0 = 0;

		int dx = 100;
		int dy = 100;

		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				int id = 0;
				switch (tiles[j][i].getType()) {
				case Cnst.TILE_TYPE_MOSQUITO:

					break;
				case Cnst.TILE_TYPE_MUD:

					break;
				case Cnst.TILE_TYPE_WATERLILY:

					break;
				case Cnst.TILE_TYPE_PIKE:

					break;
				case Cnst.TILE_TYPE_WOODLOG:

					break;
				case Cnst.TILE_TYPE_REED:

					break;
				case Cnst.TILE_TYPE_MALE:
					switch (tiles[j][i].getMaleId()) {
					case Cnst.MALE_GREEN:

						break;
					case Cnst.MALE_YELLOW:

						break;
					case Cnst.MALE_RED:

						break;
					case Cnst.MALE_BLUE:

						break;
					case Cnst.MALE_PINK:

						break;
					case Cnst.MALE_PURPLE:

						break;
					}
					break;
				}				

				c.drawBitmap(lib.get(id), new Rect(0, 0, 100, 100), new Rect(x0, y0, x0 + j, y0 + 98), paint);
			}
		}
	}
}
