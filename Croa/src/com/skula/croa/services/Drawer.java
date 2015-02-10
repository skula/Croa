package com.skula.croa.services;

import java.util.Collections;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.skula.croa.R;
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
					id = R.drawable.mosquito;
					break;
				case Cnst.TILE_TYPE_MUD:
					id = R.drawable.mud;
					break;
				case Cnst.TILE_TYPE_WATERLILY:
					id = R.drawable.waterlilly;
					break;
				case Cnst.TILE_TYPE_PIKE:
					id = R.drawable.pike;
					break;
				case Cnst.TILE_TYPE_WOODLOG:
					id = R.drawable.waterlilly;
					break;
				case Cnst.TILE_TYPE_REED:
					id = R.drawable.reed;
					break;
				case Cnst.TILE_TYPE_MALE:
					switch (tiles[j][i].getMaleId()) {
					case Cnst.MALE_GREEN:
						id = R.drawable.male_green;
						break;
					case Cnst.MALE_YELLOW:
						id = R.drawable.male_yellow;
						break;
					case Cnst.MALE_RED:
						id = R.drawable.male_red;
						break;
					case Cnst.MALE_BLUE:
						id = R.drawable.male_blue;
						break;
					case Cnst.MALE_PINK:
						id = R.drawable.male_pink;
						break;
					case Cnst.MALE_PURPLE:
						id = R.drawable.male_purple;
						break;
					}
					break;
				}				

				c.drawBitmap(lib.get(id), new Rect(0, 0, 100, 100), new Rect(dx*j, dy*i, dx*j+100, dy*i+100), paint);
			}
		}
	}
}
