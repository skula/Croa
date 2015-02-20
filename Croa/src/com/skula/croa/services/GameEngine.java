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
		
		positionTiles();
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
		if(resM || resQ){
			return true;
		} else{
			activePlayableFrogs();
			return false;
		}
	}

	public boolean canProcess(int x, int y) {
		// si pas une case adjacente
		if (!Tile.areTilesAdjacent(xSrc, ySrc, x, y)) {
			return false;
		}

		// clique sur la meme case: deselection
		if (x == xSrc && y == ySrc) {
			clearSrcPosition();
			activePlayableFrogs();
			return false;
		}		

		// si tuile encore cachee
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
				clearSrcPosition();
				activePlayableFrogs();
				return false;
			}
		} else {
			if (canMaidMove(x, y)) {
				xDest = x;
				yDest = y;
				return true;
			} else {
				clearSrcPosition();
				activePlayableFrogs();
				return false;
			}
		}
	}

	public void process() {
		tiles[xDest][yDest].setHidden(false);
	
		// mange un enemi
		TileOccupants occ = getTileOccupants(xDest, yDest);
		
		if(occ.getCount()>0){
			if (tiles[xDest][yDest].getType().equals(TileType.WOODLOG)) {
				if (selFrog.isQueen()) {
					// TODO
				}else{
					// TODO
				}
			} else {
				int idEaten = occ.getFrog1pId();
				if(occ.getFrog1().isQueen()){
					players.get(idEaten).eaten();
				}else{
					players.get(idEaten).removeMaid(xDest, yDest);
				}
				
				if(occ.getCount()==2){
					idEaten = occ.getFrog1pId();
					if(occ.getFrog1().isQueen()){
						players.get(idEaten).eaten();
					}else{
						players.get(idEaten).removeMaid(xDest, yDest);
					}
				}
			}
		}
		
		// deplacement
		cPlayer.getFrog(selFrog.getId()).moveTo(xDest, yDest);

		// execute le pouvoir de la tuile
		switch (tiles[xDest][yDest].getType()) {
		case MOSQUITO:
			//getPlayer(pToken).activeFrogsBut(xDest, yDest, isQueenSel);
			updatePlayableFrogs(TileType.MOSQUITO);
			activePlayableFrogs();
			clearSrcPosition();
			clearDestPosition();
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
			if (selFrog.isQueen() && canReproduce(tiles[xDest][yDest].getMaleId())) {
				reproduce(tiles[xDest][yDest].getMaleId());
			}
			nextPlayer();
			break;
		default:
			break;
		}
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
			if (!cPlayer.getQueen().isStuck() && cPlayer.getQueen().getId() != selFrog.getId()) {
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

	public void activePlayableFrogs(){		
		for(Frog f : cPlayer.getMaids()){
			f.setActive(playableFrogs.contains(f.getId()));
		}
		cPlayer.getQueen().setActive(playableFrogs.contains(cPlayer.getQueen().getId()));
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
			if (!players.get(pToken).isDead()) {
				tmp = true;
			}
		}

		clearSrcPosition();
		clearDestPosition();
		cPlayer = getPlayer(pToken);

		cPlayer.updateStuckTime();
		
		Position p = cPlayer.isQueenAndMaidOnOneTile();
		if(p!=null){
			// TODO: !!! cas a traiter: une reine et une maid sur la mm case jouent obligatoirement !!!
			playableFrogs.clear();
			playableFrogs.add(cPlayer.getQueen().getId());
			playableFrogs.add(cPlayer.getMaid(p.getX(), p.getY()).getId());
		}else{
			updatePlayableFrogs(TileType.NONE);
		}
		
		activePlayableFrogs();
	}

	public boolean canReproduce(Male maleId) {
		return getPlayer(pToken).getMalesLeft().contains(maleId)
				&& getPlayer(pToken).getMaidsLeft() > 0;
	}

	public void reproduce(Male maleId) {
		getPlayer(pToken).getMalesLeft().remove(maleId);
		getPlayer(pToken).addMaid(xDest, yDest);
	}

	public boolean canMaidMove(int x, int y) {
		if (!tiles[x][y].getType().equals(TileType.WOODLOG)) {
			return !cPlayer.hasFrog(x, y);
		} else {
			return cPlayer.getFrogsWeigth(x, y) < 2;
		}
	}

	public boolean canQueenMove(int x, int y) {
		if (tiles[x][y].getType().equals(TileType.WOODLOG)) {
			TileOccupants occ = new TileOccupants();
			if (occ.getCount() == 0) {
				return true;
			} else if (occ.getCount() == 1) {
				return occ.getFrog1pId() != pToken;
			} else {
				return occ.getFrog1pId() != pToken && occ.getFrog2pId() != pToken;
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
			if (p.hasQueen(x, y)) {
				occ.addOccupant(p.getId(), p.getQueen());
			}

			for (Frog f : p.getMaids()) {
				if (f.isThere(x, y)) {
					occ.addOccupant(p.getId(), f);
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

			// p.addMaid(0, 0);
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
			int a;
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
			p.setQueen(0, Cnst.ROWS_COUNT - 1);
			p.addMaid(0, Cnst.ROWS_COUNT - 2);
			p.addMaid(1, Cnst.ROWS_COUNT - 1);
			players.add(p);

			p = new Player(3);
			p.setQueen(Cnst.COLUMNS_COUNT - 1, Cnst.ROWS_COUNT - 1);
			p.addMaid(Cnst.COLUMNS_COUNT - 1, Cnst.ROWS_COUNT - 2);
			p.addMaid(Cnst.COLUMNS_COUNT - 2, Cnst.ROWS_COUNT - 1);
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

	private void dummy() {
		List<Tile> tmp = new ArrayList<Tile>();
		for (int i = 0; i < 64; i++) {
			tmp.add(new Tile(TileType.REED, TileBackType.DEPTH_1, Male.NONE, 1));
		}

		Collections.shuffle(tmp);

		this.tiles = new Tile[Cnst.COLUMNS_COUNT][Cnst.ROWS_COUNT];

		for (int i = 0; i < Cnst.ROWS_COUNT; i++) {
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