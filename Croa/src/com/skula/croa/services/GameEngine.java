package com.skula.croa.services;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.skula.croa.constants.Cnst;
import com.skula.croa.models.Frog;
import com.skula.croa.models.Player;
import com.skula.croa.models.Tile;
import com.skula.croa.models.TileOccupants;

public class GameEngine {
	private Tile[][] tiles;
	private List<Player> players;
	private int nPlayers;
	private int token;

	public static void main(String[] args) {

		GameEngine ge = new GameEngine(3);
		// ge.draw();
		ge.positionTiles();
		// ge.drawBoard();
		// ge.drawBackBoard();
		ge.test();
	}

	public GameEngine(int nPlayers) {
		this.nPlayers = nPlayers;
		this.token = 0;
		this.players = new ArrayList<Player>();
		//positionTiles();
		//positionFrogs();
		dummy();
	}
	
	public void test(){
		TileOccupants occ = getOccupants(0,0);
		occ.getCount();
	}

	public boolean canMove(int xSrc, int ySrc, int xDest, int yDest) {
		return xDest >= xSrc - 1 && xDest <= xSrc + 1 && yDest >= ySrc - 1
				&& yDest <= ySrc + 1;
	}

	public boolean canFrogMove(int id, int x, int y) {
		// alliee deja presente
		if (tiles[x][y].getType() != Cnst.TILE_TYPE_WOODLOG
				&& players.get(id).getWeigth(x, y) > 0) {
			return false;
		}

		// rondin de bois
		if (tiles[x][y].getType() == Cnst.TILE_TYPE_WOODLOG
				&& players.get(id).getWeigth(x, y) == 2) {
			return false;
		}

		return true;
	}

	public boolean canQueenMove(int id, int x, int y) {
		return !players.get(id).hasFrog(x, y);
	}
	
	public int isEaten(int id, int x, int y){
		return 0;
	}
	
	public TileOccupants getOccupants(int x, int y){
		TileOccupants occ = new TileOccupants();
		
		for(Player p : players){
			if(p.getQueen().getxPos() == x && p.getQueen().getxPos() == y){
				occ.addOccupant(p.getId(), true);
			}

			for(Frog f : p.getFrogs()){
				if(f.getxPos() == x && f.getyPos() == y){
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
			p.addFrog(1, 0);
			p.addFrog(0, 1);
			players.add(p);

			p = new Player(1);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 1);
			p.addFrog(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 2);
			p.addFrog(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT - 1);
			players.add(p);
		} else if (nPlayers == 3) {
			p = new Player(0);
			p.setQueen(0, 0);
			p.addFrog(1, 0);
			p.addFrog(0, 1);
			players.add(p);

			p = new Player(1);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT / 2);
			p.addFrog(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT / 2 - 1);
			p.addFrog(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT / 2 + 1);
			players.add(p);

			p = new Player(2);
			p.setQueen(0, Cnst.ROW_COUNT - 1);
			p.addFrog(0, Cnst.ROW_COUNT - 2);
			p.addFrog(1, Cnst.ROW_COUNT - 1);
			players.add(p);

		} else {
			p = new Player(0);
			p.setQueen(0, 0);
			p.addFrog(1, 0);
			p.addFrog(0, 1);
			players.add(p);

			p = new Player(1);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, 0);
			p.addFrog(Cnst.COLUMNS_COUNT - 1, 1);
			p.addFrog(Cnst.COLUMNS_COUNT - 2, 0);
			players.add(p);

			p = new Player(2);
			p.setQueen(0, Cnst.ROW_COUNT - 1);
			p.addFrog(0, Cnst.ROW_COUNT - 2);
			p.addFrog(1, Cnst.ROW_COUNT - 1);
			players.add(p);

			p = new Player(3);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 1);
			p.addFrog(Cnst.COLUMNS_COUNT - 1, Cnst.ROW_COUNT - 2);
			p.addFrog(Cnst.COLUMNS_COUNT - 2, Cnst.ROW_COUNT - 1);
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

	private void dummy(){
		List<Tile> tmp = new ArrayList<Tile>();
		for(int i =0; i<64; i++){
			tmp.add(new Tile(Cnst.TILE_TYPE_REED, Cnst.TILE_BACK_DEPTH_1, 0, 1));
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
		p.addFrog(0, 0);
		p.addFrog(0, 1);
		players.add(p);
		
		p = new Player(1);
		p.setQueen(1, 1);
		p.addFrog(0, 0);
		players.add(p);
	}
	
	public void drawBoard() {
		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				switch (tiles[j][i].getType()) {
				case Cnst.TILE_TYPE_MALE:
					System.out.print("c");
					break;
				case Cnst.TILE_TYPE_MOSQUITO:
					System.out.print("m");
					break;
				case Cnst.TILE_TYPE_MUD:
					System.out.print("v");
					break;
				case Cnst.TILE_TYPE_PIKE:
					System.out.print("b");
					break;
				case Cnst.TILE_TYPE_REED:
					System.out.print("r");
					break;
				case Cnst.TILE_TYPE_WATERLILY:
					System.out.print("n");
					break;
				case Cnst.TILE_TYPE_WOODLOG:
					System.out.print("w");
					break;
				}
			}
			System.out.println("");
		}
	}

	public void drawBackBoard() {
		for (int i = 0; i < Cnst.ROW_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				switch (tiles[j][i].getBackType()) {
				case Cnst.TILE_BACK_DEPTH_1:
					System.out.print("1");
					break;
				case Cnst.TILE_BACK_DEPTH_2:
					System.out.print("2");
					break;
				case Cnst.TILE_BACK_DEPTH_3:
					System.out.print("3");
					break;
				}
			}
			System.out.println("");
		}
	}

	public void draw() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boolean tmp = false;
				for (Player p : players) {
					if (!tmp) {
						if (p.hasFrog(j, i)) {
							System.out.print("f");
							tmp = true;
						} else {
							if (p.hasQueen(j, i)) {
								System.out.print("q");
								tmp = true;
							}
						}
					}
				}
				if (!tmp) {
					System.out.print("0");
				}
			}
			System.out.println("");
		}
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
