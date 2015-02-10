package com.skula.croa.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.croa.constants.Cnst;

public class Tile {
	private int type;
	private int backType;
	private boolean hidden;

	private int maleId;

	public static void main(String[] args) {
		List<Tile> tiles = getAllTiles();
		Collections.shuffle(tiles);
		System.out.println(tiles.size());
	}

	public Tile(int type, int backType, int maleId, int spaceLeft) {
		this.type = type;
		this.backType = backType;
		this.maleId = maleId;
		this.hidden = true;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBackType() {
		return backType;
	}

	public void setBackType(int backType) {
		this.backType = backType;
	}

	public int getMaleId() {
		return maleId;
	}

	public void setMaleId(int maleId) {
		this.maleId = maleId;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public static List<Tile> getAllTiles() {
		List<Tile> list = new ArrayList<Tile>();
		// Nénuphars
		for (int i = 0; i < 6; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_WATERLILY, Cnst.TILE_BACK_DEPTH_1,
					0, 1));
		}
		for (int i = 0; i < 4; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_WATERLILY, Cnst.TILE_BACK_DEPTH_2,
					0, 1));
			list.add(new Tile(Cnst.TILE_TYPE_WATERLILY, Cnst.TILE_BACK_DEPTH_3,
					0, 1));
		}

		// Moustiques
		for (int i = 0; i < 4; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_MOSQUITO, Cnst.TILE_BACK_DEPTH_1,
					0, 1));
		}
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_MOSQUITO, Cnst.TILE_BACK_DEPTH_2,
					0, 1));
			list.add(new Tile(Cnst.TILE_TYPE_MOSQUITO, Cnst.TILE_BACK_DEPTH_3,
					0, 1));
		}

		// Roseaux
		for (int i = 0; i < 10; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_REED, Cnst.TILE_BACK_DEPTH_1, 0, 1));
		}
		for (int i = 0; i < 3; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_REED, Cnst.TILE_BACK_DEPTH_2, 0, 1));
			list.add(new Tile(Cnst.TILE_TYPE_REED, Cnst.TILE_BACK_DEPTH_3, 0, 1));
		}

		// Vase
		for (int i = 0; i < 4; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_MUD, Cnst.TILE_BACK_DEPTH_1, 0, 1));
		}

		// Brochet
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_PIKE, Cnst.TILE_BACK_DEPTH_2, 0, 1));
			list.add(new Tile(Cnst.TILE_TYPE_PIKE, Cnst.TILE_BACK_DEPTH_3, 0, 1));
		}

		// Rondin de bois
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_WOODLOG, Cnst.TILE_BACK_DEPTH_1,
					0, 2));
		}
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(Cnst.TILE_TYPE_WOODLOG, Cnst.TILE_BACK_DEPTH_2,
					0, 2));
			list.add(new Tile(Cnst.TILE_TYPE_WOODLOG, Cnst.TILE_BACK_DEPTH_3,
					0, 2));
		}

		// Males
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_1,
				Cnst.MALE_GREEN, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_1,
				Cnst.MALE_YELLOW, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_1,
				Cnst.MALE_RED, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_1,
				Cnst.MALE_BLUE, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_1,
				Cnst.MALE_PINK, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_1,
				Cnst.MALE_PURPLE, 1));

		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_2,
				Cnst.MALE_GREEN, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_2,
				Cnst.MALE_YELLOW, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_2,
				Cnst.MALE_RED, 1));

		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_3,
				Cnst.MALE_BLUE, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_3,
				Cnst.MALE_PINK, 1));
		list.add(new Tile(Cnst.TILE_TYPE_MALE, Cnst.TILE_BACK_DEPTH_3,
				Cnst.MALE_PURPLE, 1));
		return list;

	}
}
