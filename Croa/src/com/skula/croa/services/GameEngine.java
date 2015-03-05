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
import com.skula.croa.models.Position;
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
	private Player cPlayer;

	private Frog selFrog;
	private List<Integer> playableFrogs;

	public GameEngine(int nPlayers) {
		this.nPlayers = nPlayers;
		this.players = new ArrayList<Player>();
		this.pToken = 0;
		this.selFrog = null;
		clearSrcPosition();
		clearDestPosition();

		//positionTiles();
		testMock();
		positionFrogs();

		cPlayer = getPlayer(pToken);
		playableFrogs = new ArrayList<Integer>();
		updatePlayableFrogs(TileType.NONE);
	}

	public boolean canSelectFrog(int x, int y) {
		Frog f = cPlayer.getMaid(x, y);
		boolean resM = f != null && isPlayable(f.getId());
		boolean resQ = cPlayer.hasQueen(x, y)
				&& isPlayable(cPlayer.getQueen().getId());
		if (resM || resQ) {
			return true;
		} else {
			activePlayableFrogs();
			return false;
		}
	}

	public boolean canProcess(int x, int y) {
		// si pas une case adjacente: RAS
		if (!Tile.areTilesAdjacent(xSrc, ySrc, x, y)) {
			return false;
		}

		// si clique sur la meme case: deselection
		if (x == xSrc && y == ySrc) {
			clearSrcPosition();
			activePlayableFrogs();
			return false;
		}

		// si tuile encore face cachee: ok
		if (tiles[x][y].isHidden()) {
			xDest = x;
			yDest = y;
			return true;
		}

		// si la grenouille peut se deplacer
		if (selFrog.isQueen()) {
			if (canQueenMove(x, y)) {
				xDest = x;
				yDest = y;
				return true;
			} else {
				return false;
			}
		} else {
			if (canMaidMove(x, y)) {
				xDest = x;
				yDest = y;
				return true;
			} else {
				return false;
			}
		}
	}

	public void process() {
		process(0);
	}

	public void process(int destFrogId) {
		tiles[xDest][yDest].setHidden(false);

		// mange un enemi ?
		TileOccupants occ = getTileOccupants(xDest, yDest);
		if (occ.getCount() > 0) {
			if (tiles[xDest][yDest].getType().equals(TileType.WOODLOG)) {
				if (selFrog.isQueen()) {
					if (occ.getCount() == 1) {
						if (occ.getFrog1().isQueen()) {
							players.get(occ.getFrog1pId()).eaten();
						} else {
							players.get(occ.getFrog1pId()).removeMaid(xDest,
									yDest);
						}
					} else {
						players.get(occ.getFrog1pId()).removeMaid(xDest, yDest);
						players.get(occ.getFrog2pId()).removeMaid(xDest, yDest);
					}
				} else {
					if (occ.getCount() == 1) {
						// si une servante se deplace sur une case occupée par une reine, la reine est mangee
						if (occ.getFrog1().isQueen()) {
							players.get(occ.getFrog1pId()).eaten();
						}
					} else {
						// si une des 2 servante est une amie, on mnage l'autre
						if(occ.getFrog1pId() == pToken || occ.getFrog1pId() == pToken){
							if(occ.getFrog1pId() == pToken){
								players.get(occ.getFrog2pId()).removeMaid(xDest, yDest);
							}else{
								players.get(occ.getFrog1pId()).removeMaid(xDest, yDest);
							}
						}else{
							// si 2 servante enemies, choisir entre les 2
							if (occ.getFrog1().getId() == destFrogId) {
								players.get(occ.getFrog1pId()).removeMaid(xDest,
										yDest);
							} else {
								players.get(occ.getFrog2pId()).removeMaid(xDest,
										yDest);
							}
						}
					}
				}
			} else {
				int idEaten = occ.getFrog1pId();
				if (occ.getFrog1().isQueen()) {
					players.get(idEaten).eaten();
				} else {
					players.get(idEaten).removeMaid(xDest, yDest);
				}

				if (occ.getCount() == 2) {
					idEaten = occ.getFrog1pId();
					if (occ.getFrog1().isQueen()) {
						players.get(idEaten).eaten();
					} else {
						players.get(idEaten).removeMaid(xDest, yDest);
					}
				}
			}
		}

		// deplacement
		cPlayer.getFrog(selFrog.getId()).moveTo(xDest, yDest);

		// execution du pouvoir de la tuile
		switch (tiles[xDest][yDest].getType()) {
		case MOSQUITO:
			if (cPlayer.getCountPlayableFrogs() > 1) {
				updatePlayableFrogs(TileType.MOSQUITO);
				activePlayableFrogs();
				clearSrcPosition();
				clearDestPosition();
			} else {
				nextPlayer();
			}
			break;
		case WATERLILY:
			xSrc = xDest;
			ySrc = yDest;
			clearDestPosition();
			updatePlayableFrogs(TileType.WATERLILY);
			break;
		case MUD:
			if (selFrog.isQueen()) {
				cPlayer.getQueen().setStuck();
			} else {
				cPlayer.getMaid(xDest, yDest).setStuck();
			}
			nextPlayer();
			break;
		case PIKE:
			if (selFrog.isQueen()) {
				getPlayer(pToken).eaten();
			} else {
				getPlayer(pToken).removeMaid(xDest, yDest);
			}
			nextPlayer();
			break;
		case WOODLOG:
			nextPlayer();
			break;
		case REED:
			nextPlayer();
			break;
		case MALE:
			if (selFrog.isQueen()
					&& canReproduce(tiles[xDest][yDest].getMaleId())) {
				reproduce(tiles[xDest][yDest].getMaleId());
			}
			nextPlayer();
			break;
		default:
			break;
		}
	}
	
	public int getWinner(){
		int id = -1;
		int countAlive = 0;
		for(Player p : players){
			if(!p.isDead()){
				if(countAlive==0){
					id = p.getId();
					countAlive++;
				}else{
					return -1;
				}
			}			
		}		
		return id;
	}

	private void updatePlayableFrogs(TileType type) {
		playableFrogs.clear();
		switch (type) {
		case MOSQUITO:
			for (Frog f : cPlayer.getMaids()) {
				if (!f.isStuck() && f.getId() != selFrog.getId()) {
					playableFrogs.add(f.getId());
				}
			}
			if (!cPlayer.getQueen().isStuck()
					&& cPlayer.getQueen().getId() != selFrog.getId()) {
				playableFrogs.add(cPlayer.getQueen().getId());
			}
			activePlayableFrogs();
			break;
		case WATERLILY:
			playableFrogs.add(selFrog.getId());
			break;
		default:
			for (Frog f : cPlayer.getMaids()) {
				if (!f.isStuck()) {
					playableFrogs.add(f.getId());
				}
			}
			if (!cPlayer.getQueen().isStuck()) {
				playableFrogs.add(cPlayer.getQueen().getId());
			}
			break;
		}
	}

	public void activePlayableFrogs() {
		for (Frog f : cPlayer.getMaids()) {
			f.setActive(playableFrogs.contains(f.getId()));
		}
		cPlayer.getQueen().setActive(
				playableFrogs.contains(cPlayer.getQueen().getId()));
	}

	private boolean isPlayable(int id) {
		return playableFrogs.contains(id);
	}

	private void nextPlayer() {
		boolean tmp = false;
		while (!tmp) {
			if (pToken == nPlayers - 1) {
				pToken = 0;
			} else {
				pToken++;
			}
			if (!players.get(pToken).isDead() && players.get(pToken).getCountPlayableFrogs()>0) {
				tmp = true;
			}
		}

		clearSrcPosition();
		clearDestPosition();
		cPlayer = getPlayer(pToken);

		cPlayer.updateStuckTime();

		Position p = cPlayer.isQueenAndMaidOnOneTile();
		if (p != null) {
			playableFrogs.clear();
			playableFrogs.add(cPlayer.getQueen().getId());
			playableFrogs.add(cPlayer.getMaid(p.getX(), p.getY()).getId());
		} else {
			updatePlayableFrogs(TileType.NONE);
		}

		activePlayableFrogs();
	}

	public boolean canReproduce(Male maleId) {
		return getPlayer(pToken).getMalesLeft().contains(maleId)
				&& getPlayer(pToken).getMaidsLeft() > 0;
	}

	public void reproduce(Male maleId) {
		cPlayer.getMalesLeft().remove(maleId);
		cPlayer.addMaid(xDest, yDest);
	}

	public boolean canMaidMove(int x, int y) {
		if (!tiles[x][y].getType().equals(TileType.WOODLOG)) {
			return !cPlayer.hasFrog(x, y);
		} else {
			TileOccupants occ = getTileOccupants(x, y);
			if(occ.getCount() == 0){
				return true;
			}else if (occ.getCount() == 1){
				return !cPlayer.hasQueen(x, y);
			}else{
				return occ.getFrog1pId() != pToken || occ.getFrog2pId() != pToken;
			}
		}
	}

	public boolean canQueenMove(int x, int y) {
		if (tiles[x][y].getType().equals(TileType.WOODLOG)) {
			TileOccupants occ = getTileOccupants(x, y);
			if (occ.getCount() == 0) {
				return true;
			} else if (occ.getCount() == 1) {
				return occ.getFrog1pId() != pToken;
			} else {
				return occ.getFrog1pId() != pToken
						&& occ.getFrog2pId() != pToken;
			}
		} else {
			return !cPlayer.hasMaid(x, y);
		}
	}

	public int isEaten(int id, int x, int y) {
		return 0;
	}

	public TileOccupants getTileOccupants(int x, int y) {
		TileOccupants occ = new TileOccupants();

		for (Player p : players) {
			if (!p.isDead()) {
				if (p.hasQueen(x, y)) {
					occ.addOccupant(p.getId(), p.getQueen());
				}

				for (Frog f : p.getMaids()) {
					if (f.isThere(x, y)) {
						occ.addOccupant(p.getId(), f);
					}
				}
			}
		}

		return occ;
	}

	public TileOccupants getTileOccupants(int id, int x, int y) {
		TileOccupants occ = new TileOccupants();

		Player p = players.get(id);
		if (p.hasQueen(x, y)) {
			occ.addOccupant(p.getId(), p.getQueen());
		}

		for (Frog f : p.getMaids()) {
			if (f.getxPos() == x && f.getyPos() == y) {
				occ.addOccupant(p.getId(), f);
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
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROWS_COUNT - 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 1, Cnst.ROWS_COUNT - 2);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROWS_COUNT - 1);
			players.add(p);
		} else if (nPlayers == 3) {
			p = new Player(0);
			p.setQueen(0, 0);
			p.addMaid(1, 0);
			p.addMaid(0, 1);
			players.add(p);

			p = new Player(1);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROWS_COUNT / 2);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROWS_COUNT / 2 - 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROWS_COUNT / 2 + 1);
			players.add(p);

			p = new Player(2);
			p.setQueen(0, Cnst.ROWS_COUNT - 1);
			p.addMaid(0, Cnst.ROWS_COUNT - 2);
			p.addMaid(1, Cnst.ROWS_COUNT - 1);
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
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROWS_COUNT - 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 1, Cnst.ROWS_COUNT - 2);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROWS_COUNT - 1);
			players.add(p);

			p = new Player(3);
			p.setQueen(0, Cnst.ROWS_COUNT - 1);
			p.addMaid(0, Cnst.ROWS_COUNT - 2);
			p.addMaid(1, Cnst.ROWS_COUNT - 1);
			players.add(p);
		}
	}

	private void positionTiles() {
		List<Tile> tmp = Tile.getAllTiles();
		Collections.shuffle(tmp);

		this.tiles = new Tile[Cnst.COLUMNS_COUNT][Cnst.ROWS_COUNT];

		for (int i = 0; i < Cnst.ROWS_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				tiles[j][i] = tmp.remove(0);
			}
		}
	}

	private void testMock() {
		this.tiles = new Tile[Cnst.COLUMNS_COUNT][Cnst.ROWS_COUNT];

		for (int i = 0; i < Cnst.ROWS_COUNT; i++) {
			for (int j = 0; j < Cnst.COLUMNS_COUNT; j++) {
				tiles[j][i] = new Tile(TileType.REED, TileBackType.DEPTH_1, Male.NONE, 1);
			}
		}

		tiles[1][1] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[1][2] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[1][3] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[1][4] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[1][5] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[1][6] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);	
		tiles[2][2] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[2][3] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[2][4] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[2][5] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[4][3] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[4][4] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[5][2] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[5][5] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[6][1] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[6][6] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[6][6] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[3][2] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		tiles[3][5] = new Tile(TileType.WATERLILY, TileBackType.DEPTH_1, Male.NONE, 1);
		
		tiles[3][3] = new Tile(TileType.WOODLOG, TileBackType.DEPTH_1, Male.NONE, 2);
		tiles[3][4] = new Tile(TileType.WOODLOG, TileBackType.DEPTH_1, Male.NONE, 2);

		tiles[4][2] = new Tile(TileType.MUD, TileBackType.DEPTH_1, Male.NONE, 2);
		tiles[4][5] = new Tile(TileType.MUD, TileBackType.DEPTH_1, Male.NONE, 2);
		
		tiles[0][1] = new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.RED, 1);
		tiles[0][2] = new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.GREEN, 1);
		tiles[0][3] = new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.YELLOW, 1);
		tiles[0][4] = new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.PURPLE, 1);
		tiles[0][5] = new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.BLUE, 1);
		tiles[0][6] = new Tile(TileType.MALE, TileBackType.DEPTH_1, Male.PINK, 1);
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

	public Player getPlayer(int id) {
		return players.get(id);
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

	public int getToken() {
		return pToken;
	}

	public Frog getSelFrog() {
		return selFrog;
	}

	public void setSelFrog(int id) {
		this.selFrog = cPlayer.getFrog(id);
		cPlayer.desactiveFrogsBut(id);
	}

	public void setSelFrog(Frog frog) {
		this.selFrog = selFrog;
		cPlayer.desactiveFrogsBut(frog.getId());
	}
}