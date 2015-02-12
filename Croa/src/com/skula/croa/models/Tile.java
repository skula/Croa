package com.skula.croa.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.croa.enums.Male;
import com.skula.croa.enums.TileBackType;
import com.skula.croa.enums.TileType;

public class Tile {
	private TileType type;
	private TileBackType backType;
	private boolean hidden;

	private Male maleId;

	public static void main(String[] args) {
		List<Tile> tiles = getAllTiles();
		Collections.shuffle(tiles);
		System.out.println(tiles.size());
	}

	public Tile(TileType type, TileBackType backType, Male maleId, int spaceLeft) {
		this.type = type;
		this.backType = backType;
		this.maleId = maleId;
		this.hidden = !true;
	}
	
	public static boolean areTilesAdjacent(int xSrc, int ySrc, int xDest, int yDest) {
		return xDest >= xSrc - 1 && xDest <= xSrc + 1 && yDest >= ySrc - 1
				&& yDest <= ySrc + 1;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public TileBackType getBackType() {
		return backType;
	}

	public void setBackType(TileBackType backType) {
		this.backType = backType;
	}

	public Male getMaleId() {
		return maleId;
	}

	public void setMaleId(Male maleId) {
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
			list.add(new Tile(TileType.WATERLILY, TileBackType.DEPTH_1,
					Male.NONE, 1));
		}
		for (int i = 0; i < 4; i++) {
			list.add(new Tile(TileType.WATERLILY, TileBackType.DEPTH_2,
					Male.NONE, 1));
			list.add(new Tile(TileType.WATERLILY, TileBackType.DEPTH_3,
					Male.NONE, 1));
		}

		// Moustiques
		for (int i = 0; i < 4; i++) {
			list.add(new Tile(TileType.MOSQUITO, TileBackType.DEPTH_1,
					Male.NONE, 1));
		}
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(TileType.MOSQUITO, TileBackType.DEPTH_2,
					Male.NONE, 1));
			list.add(new Tile(TileType.MOSQUITO, TileBackType.DEPTH_3,
					Male.NONE, 1));
		}

		// Roseaux
		for (int i = 0; i < 10; i++) {
			list.add(new Tile(TileType.REED, TileBackType.DEPTH_1, Male.NONE, 1));
		}
		for (int i = 0; i < 3; i++) {
			list.add(new Tile(TileType.REED, TileBackType.DEPTH_2, Male.NONE, 1));
			list.add(new Tile(TileType.REED, TileBackType.DEPTH_3, Male.NONE, 1));
		}

		// Vase
		for (int i = 0; i < 4; i++) {
			list.add(new Tile(TileType.MUD, TileBackType.DEPTH_1, Male.NONE, 1));
		}

		// Brochet
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(TileType.PIKE, TileBackType.DEPTH_2, Male.NONE, 1));
			list.add(new Tile(TileType.PIKE, TileBackType.DEPTH_3, Male.NONE, 1));
		}

		// Rondin de bois
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(TileType.WOODLOG, TileBackType.DEPTH_1,
					Male.NONE, 2));
		}
		for (int i = 0; i < 2; i++) {
			list.add(new Tile(TileType.WOODLOG, TileBackType.DEPTH_2,
					Male.NONE, 2));
			list.add(new Tile(TileType.WOODLOG, TileBackType.DEPTH_3,
					Male.NONE, 2));
		}

		// Males
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.GREEN, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.YELLOW, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.RED, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.BLUE, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.PINK, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.PURPLE, 1));

		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_2, Male.GREEN, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_2, Male.YELLOW, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_2, Male.RED, 1));

		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_3, Male.BLUE, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_3, Male.PINK, 1));
		list.add(new Tile(TileType.MALE, TileBackType.DEPTH_3, Male.PURPLE, 1));
		return list;

	}
}
