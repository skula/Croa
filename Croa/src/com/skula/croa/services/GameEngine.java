package com.skula.croa.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.croa.constants.Cnst;
import com.skula.croa.enums.Male;
import com.skula.croa.enums.TileBackType;
import com.skula.croa.enums.TileType;
import com.skula.croa.models.Frog;
import com.skula.croa.models.Player;
import com.skula.croa.models.Tile;
import com.skula.croa.models.TileOccupants;

public class GameEngine {
	private Tile[][] tiles;
	private List<Player> players;
	private int nPlayers;

	public int xSrc;
	public int ySrc;
	public int xDest;
	public int yDest;
	private int pToken;
	private boolean isQueenSel;
	private TileType destTileType;

	public static void main(String[] args) {
		GameEngine ge = new GameEngine(3);
		ge.positionTiles();
		ge.test();
	}

	public GameEngine(int nPlayers) {
		this.nPlayers = nPlayers;
		this.pToken = 0;
		this.players = new ArrayList<Player>();
		this.isQueenSel = false;
		this.destTileType = TileType.NONE;
		positionTiles();
		positionFrogs();

		clearSrcPosition();
		clearDestPosition();
	}

	public void test() {
		TileOccupants occ = getOccupants(0, 0);
		occ.getCount();
	}

	// POUR L'INSTANT: une et une seule grenouille par tuile maximum !!!!!
	public boolean canProcess() {
		if (isSrcSelected()) {
			if (!isDestSelected()) {
				// si case sans grenouille du joueur: RAZ
				if (!players.get(pToken).hasMaid(xSrc, ySrc)
						&& !players.get(pToken).hasQueen(xSrc, ySrc)) {
					clearSrcPosition();
					return false;
				}
				return true;
			} else {
				
				// clique sur la meme case: on deselectionne
				if(xDest == xSrc && yDest == ySrc){
					clearDestPosition();
					clearSrcPosition();
					return false;
				}
				
				// si pas une case adjacente
				if(!canMove(xSrc, ySrc, xDest, yDest)){
					clearSrcPosition();
					clearDestPosition();
					return false;
				}
				
				// recherche de la grenouille du joueur (reine ou grenouille)
				TileOccupants occ = getOccupants(xSrc, ySrc);
				if (occ.getCount() == 1) {
					if(occ.isFrog1queen()){
						return canQueenMove(pToken, xDest, yDest);
					}else{
						return canMaidMove(pToken, xDest, yDest);
					}
				} else if (occ.getCount() == 2) {
					if(occ.getFrog1Id() == pToken){
						if(occ.isFrog1queen()){
							if(canQueenMove(pToken, xDest, yDest)){
								isQueenSel = true;
							}else{
								clearSrcPosition();
								clearDestPosition();
								return false;
							}
						}else{
							if(canMaidMove(pToken, xDest, yDest)){
								isQueenSel = false;
							}else{
								clearSrcPosition();
								clearDestPosition();
								return false;
							}
						}
					}else{
						if(occ.isFrog2queen()){
							if(canQueenMove(pToken, xDest, yDest)){
								isQueenSel = true;
							}else{
								clearSrcPosition();
								clearDestPosition();
								return false;
							}
						}else{
							if(canMaidMove(pToken, xDest, yDest)){
								isQueenSel = false;
							}else{
								clearSrcPosition();
								clearDestPosition();
								return false;
							}
						}
					}
				}
			}
		} else {
			// choix multiples ?? reine + servante, rondin de bois ??
		}
		return false;
	}

	public void process() {
		players.get(pToken).getMaid(xSrc, ySrc).moveTo(xDest, yDest);
		switch (tiles[xDest][yDest].getType()) {
		case MOSQUITO:
			break;
		default:
			break;
		}
	}

	public void nextPlayer() {
		boolean tmp = false;
		while (!tmp) {
			if (pToken == nPlayers - 1) {
				pToken = 0;
			} else {
				pToken++;
			}
			if (!players.get(pToken).isDead()) {
				tmp = true;
			}
		}
	}

	public boolean canMove(int xSrc, int ySrc, int xDest, int yDest) {
		return xDest >= xSrc - 1 && xDest <= xSrc + 1 && yDest >= ySrc - 1
				&& yDest <= ySrc + 1;
	}

	public boolean canMaidMove(int id, int x, int y) {
		// alliee deja presente
		if (!tiles[x][y].getType().equals(TileType.WOODLOG)
				&& players.get(id).getWeigth(x, y) > 0) {
			return false;
		}

		// rondin de bois
		if (tiles[x][y].getType().equals(TileType.WOODLOG)
				&& players.get(id).getWeigth(x, y) == 2) {
			return false;
		}

		return true;
	}

	public boolean canQueenMove(int id, int x, int y) {
		return !players.get(id).hasMaid(x, y);
	}

	public int isEaten(int id, int x, int y) {
		return 0;
	}

	public TileOccupants getOccupants(int x, int y) {
		TileOccupants occ = new TileOccupants();

		for (Player p : players) {
			if (p.getQueen().getxPos() == x && p.getQueen().getxPos() == y) {
				occ.addOccupant(p.getId(), true);
			}

			for (Frog f : p.getMaids()) {
				if (f.getxPos() == x && f.getyPos() == y) {
					occ.addOccupant(p.getId(), false);
				}
			}
		}

		return occ;
	}

	private void positionFrogs() {
		Player p = null;
		if (nPlayers == 2) {
			p = new Player(0);
			p.setQueen(0, 0);
			p.addMaid(1, 0);
			p.addMaid(0, 1);
			players.add(p);

			p = new Player(1);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 2);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT - 1);
			players.add(p);
		} else if (nPlayers == 3) {
			p = new Player(0);
			p.setQueen(0, 0);
			p.addMaid(1, 0);
			p.addMaid(0, 1);
			players.add(p);

			p = new Player(1);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT / 2);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT / 2 - 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT / 2 + 1);
			players.add(p);

			p = new Player(2);
			p.setQueen(0, Cnst.ROW_COUNT - 1);
			p.addMaid(0, Cnst.ROW_COUNT - 2);
			p.addMaid(1, Cnst.ROW_COUNT - 1);
			players.add(p);

		} else {
			p = new Player(0);
			p.setQueen(0, 0);
			p.addMaid(1, 0);
			p.addMaid(0, 1);
			players.add(p);

			p = new Player(1);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, 0);
			p.addMaid(Cnst.COLUMNS_COUNT - 1, 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, 0);
			players.add(p);

			p = new Player(2);
			p.setQueen(0, Cnst.ROW_COUNT - 1);
			p.addMaid(0, Cnst.ROW_COUNT - 2);
			p.addMaid(1, Cnst.ROW_COUNT - 1);
			players.add(p);

			p = new Player(3);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 2);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT - 1);
			players.add(p);
		}
	}

	private void positionTiles() {
		List<Tile> tmp = Tile.getAllTiles();
		Collections.shuffle(tmp);

		this.tiles = new Tile[Cnst.COLUMNS_COUNT][Cnst.ROW_COUNT];

		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				tiles[j][i] = tmp.remove(0);
			}
		}
	}

	private void dummy() {
		List<Tile> tmp = new ArrayList<Tile>();
		for (int i = 0; i < 64; i++) {
			tmp.add(new Tile(TileType.REED, TileBackType.DEPTH_1, Male.NONE, 1));
		}

		Collections.shuffle(tmp);

		this.tiles = new Tile[Cnst.COLUMNS_COUNT][Cnst.ROW_COUNT];

		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				tiles[j][i] = tmp.remove(0);
			}
		}

		Player p = new Player(0);
		p.setQueen(2, 0);
		p.addMaid(0, 0);
		p.addMaid(0, 1);
		players.add(p);

		p = new Player(1);
		p.setQueen(1, 1);
		p.addMaid(0, 0);
		players.add(p);
	}

	public void setSrcPos(int x, int y) {
		xSrc = x;
		ySrc = y;
	}

	public void setDestPos(int x, int y) {
		xDest = x;
		yDest = y;
	}

	public void clearSrcPosition() {
		xSrc = -1;
		ySrc = -1;
	}

	public void clearDestPosition() {
		xDest = -1;
		yDest = -1;
		destTileType = TileType.NONE;
	}

	public boolean isSrcSelected() {
		return xSrc != -1 && ySrc != -1;
	}

	public boolean isDestSelected() {
		return xDest != -1 && yDest != -1;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getnPlayers() {
		return nPlayers;
	}

	public void setnPlayers(int nPlayers) {
		this.nPlayers = nPlayers;
	}
}
