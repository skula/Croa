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
		

		c.drawBitmap(lib.get(R.drawable.frog_red), new Rect(0, 0, 45, 45), new Rect(280,300,325,345), paint);
		c.drawBitmap(lib.get(R.drawable.frog_green), new Rect(0, 0, 45, 45), new Rect(300,300,345,345), paint);
	}
	
	public void drawScores(Canvas c) {
		
	}
	
	public void drawFrogs(Canvas c) {
		TileOccupants occ = null;
		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				occ = engine.getOccupants(j, i);
				int id = 0;
				if(occ.getCount()==1){
					switch(occ.getFrog1Id()){
					case 0:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 1:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 2:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 3:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					}
					
					// draw in the middle
				}else if(occ.getCount()==2){
					switch(occ.getFrog1Id()){
					case 0:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 1:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 2:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 3:
						// id = occ.isFrog1Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					}
					
					// draw on the left
					
					switch(occ.getFrog2Id()){
					case 0:
						// id = occ.isFrog2Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 1:
						// id = occ.isFrog2Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 2:
						// id = occ.isFrog2Queen()? R.drawable.queen_red:R.drawable.frog_red;
						break;
					case 3:
						// id = occ.isFrog2Queen()? R.drawable.queen_red:R.drawable.frog_red;
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
				if(tiles[j][i].isHidden()){
					switch (tiles[j][i].getBackType()) {
					case Cnst.TILE_BACK_DEPTH_1:
						id = R.drawable.depth_one;
						break;
					case Cnst.TILE_BACK_DEPTH_2:
						id = R.drawable.depth_two;
						break;
					case Cnst.TILE_BACK_DEPTH_3:
						id = R.drawable.depth_three;
						break;
					}
				}else{
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
						id = R.drawable.woodlog;
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
				}
				try{
				c.drawBitmap(lib.get(id), new Rect(0, 0, 100, 100), new Rect(x0+dx*j, y0+dy*i, x0+dx*j+Cnst.TILE_SIZE, y0+dy*i+Cnst.TILE_SIZE), paint);
				}catch(Exception e){
					e.getMessage();
				}
			}
		}
	}
}