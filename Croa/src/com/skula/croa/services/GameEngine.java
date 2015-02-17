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
	private Player cPlayer;
	private boolean isQueenSel;
	private boolean updateFrogsActivity;
	private TileType typeDest;
	
	private int frogId;
	private List<Integer> playableFrogs;

	public GameEngine(int nPlayers) {
		this.nPlayers = nPlayers;
		this.pToken = 0;
		this.players = new ArrayList<Player>();
		this.isQueenSel = false;
		this.updateFrogsActivity = true;
		this.typeDest = TileType.NONE;
		this.frogId = -1;
		clearSrcPosition();
		clearDestPosition();
		
		positionTiles();
		
		positionFrogs();
		updatePlayableFrogs();
		
		cPlayer = getPlayer(pToken);
	}
	
	public boolean canSrcSelect(int x, int y){
		Frog f = cPlayer.getMaid(x, y);
		boolean resM = f!=null && isPlayable(f.getId()); 
		boolean resQ = cPlayer.hasQueen(x, y) && isPlayable(cPlayer.getQueen().getId());
		return resM || resQ;
	}

	// POUR L'INSTANT: une et une seule grenouille par tuile maximum !!!!!
	/*public boolean canProcess() {
		if (!isDestSelected()) {
			// si case sans grenouille du joueur: RAZ
			if (!players.get(pToken).hasMaid(xSrc, ySrc) || !getPlayer(pToken).getMaid(xSrc, ySrc).isActive()){
				if (!players.get(pToken).hasQueen(xSrc, ySrc) || !getPlayer(pToken).getQueen().isActive()){
					clearSrcPosition();
					clearDestPosition();
					getPlayer(pToken).activeFrogs();
					return false;
				}
			}			

			getPlayer(pToken).desactiveFrogsBut(xSrc, ySrc, players.get(pToken).hasQueen(xSrc, ySrc));
			return false;			
		} else {
			// si WATERLILY
			if(typeDest.equals(TileType.WATERLILY)){
				// si pas une case adjacente: RAZ
				if (!Tile.areTilesAdjacent(xSrc, ySrc, xDest, yDest)) {
					clearDestPosition();
					return false;
				}
				
				// clique sur la meme case: on deselectionne
				if (xDest == xSrc && yDest == ySrc) {
					clearDestPosition();
					return false;
				}
				
				if(isQueenSel){
					if (canQueenMove(pToken, xDest, yDest)) {
						return true;
					} else {
						clearSrcPosition();
						return false;
					}
				}else{
					if (canMaidMove(pToken, xDest, yDest)) {
						return true;
					} else {
						clearSrcPosition();
						return false;
					}
				}
			// si MOSQUITO
			}else if(typeDest.equals(TileType.MOSQUITO)){
				
			}else {
			
				// clique sur la meme case: on deselectionne
				if (xDest == xSrc && yDest == ySrc) {
					clearSrcPosition();
					clearDestPosition();
					getPlayer(pToken).activeFrogs();
					return false;
				}
	
				// si pas une case adjacente: RAZ
				if (!Tile.areTilesAdjacent(xSrc, ySrc, xDest, yDest)) {
					clearSrcPosition();
					clearDestPosition();
					getPlayer(pToken).activeFrogs();
					return false;
				}
	
				/// si la tuile est face cachée
				if (tiles[xDest][yDest].isHidden()) {
					getPlayer(pToken).desactiveFrogsBut(xSrc, ySrc, players.get(pToken).hasQueen(xSrc, ySrc));
					return true;
				}
	
				// recherche de la grenouille du joueur (reine ou servante)
				TileOccupants occ = getTileOccupants(pToken, xSrc, ySrc);
				if (occ.getCount() == 1) {
					if (occ.isFrog1queen()) {
						if (canQueenMove(pToken, xDest, yDest)) {
							isQueenSel = true;
							getPlayer(pToken).desactiveFrogsBut(xSrc, ySrc, players.get(pToken).hasQueen(xSrc, ySrc));
							return true;
						} else {
							clearSrcPosition();
							clearDestPosition();
							getPlayer(pToken).activeFrogs();
							return false;
						}
					} else {
						if (canMaidMove(pToken, xDest, yDest)) {
							isQueenSel = false;
							getPlayer(pToken).desactiveFrogsBut(xSrc, ySrc, players.get(pToken).hasQueen(xSrc, ySrc));
							return true;
						} else {
							clearSrcPosition();
							clearDestPosition();
							getPlayer(pToken).activeFrogs();
							return false;
						}
					}
				} else if (occ.getCount() == 2) {
					if (occ.isFrog1queen()) {
						if (canQueenMove(pToken, xDest, yDest)) {
							isQueenSel = true;
							getPlayer(pToken).desactiveFrogsBut(xSrc, ySrc, players.get(pToken).hasQueen(xSrc, ySrc));
							return true;
						} else {
							clearSrcPosition();
							clearDestPosition();
							getPlayer(pToken).activeFrogs();
							return false;
						}
					} else {
						if (canMaidMove(pToken, xDest, yDest)) {
							isQueenSel = false;
							getPlayer(pToken).desactiveFrogsBut(xSrc, ySrc, players.get(pToken).hasQueen(xSrc, ySrc));
							return true;
						} else {
							clearSrcPosition();
							clearDestPosition();
							getPlayer(pToken).activeFrogs();
							return false;
						}
					}
				}
			}
		}
		getPlayer(pToken).activeFrogs();
		return false;
	}*/

	public void process() {
		if(updateFrogsActivity){
			typeDest = tiles[xDest][yDest].getType();
		}

		// deplacement
		if (isQueenSel) {
			getPlayer(pToken).getQueen().moveTo(xDest, yDest);
		} else {
			getPlayer(pToken).getMaid(xSrc, ySrc).moveTo(xDest, yDest);
		}

		// mange un enemie: a gérer avec la reine
		if (tiles[xDest][yDest].getType().equals(TileType.WOODLOG)) {

		} else {

		}

		// execute le pouvoir de la tuile
		switch (tiles[xDest][yDest].getType()) {
		case MOSQUITO:
			getPlayer(pToken).activeFrogsBut(xDest, yDest, isQueenSel);
			updateFrogsActivity = false;
			break;
		case WATERLILY:
			xSrc = xDest;
			ySrc = yDest;
			clearDestPosition();
			updateFrogsActivity = false;
			break;
		case MUD:
			if (isQueenSel) {
				getPlayer(pToken).getQueen().setStuck();
			} else {
				getPlayer(pToken).getMaid(xDest, yDest).setStuck();
			}
			nextPlayer();
			updateFrogsActivity = true;
			break;
		case PIKE:
			if (isQueenSel) {
				getPlayer(pToken).setDead(true);
			} else {
				getPlayer(pToken).removeMaid(xDest, yDest);
			}
			updateFrogsActivity = true;
			nextPlayer();
			break;
		case WOODLOG:
			nextPlayer();
			updateFrogsActivity = true;
			break;
		case REED:
			nextPlayer();
			updateFrogsActivity = true;
			break;
		case MALE:
			if (isQueenSel && canReproduce(tiles[xDest][yDest].getMaleId())) {
				reproduce(tiles[xDest][yDest].getMaleId());
			}
			updateFrogsActivity = true;
			nextPlayer();
			break;
		default:
			break;
		}
	}
	
	public void updatePlayableFrogs(){
		switch(typeDest){
		case MOSQUITO:
			break;
		case WATERLILY:
			break;
		default:
			break;
		}
	}
	
	public boolean isPlayable(int id){
		return playableFrogs.contains(id);
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

		clearSrcPosition();
		clearDestPosition();
		this.typeDest = TileType.NONE;

		getPlayer(pToken).getQueen().updateStuckTime();
		for(Frog f : getPlayer(pToken).getMaids()){
			f.updateStuckTime();
		}
		getPlayer(pToken).activeFrogs();
		

		cPlayer = getPlayer(pToken);
	}

	public void initPlayerRound(){
		getPlayer(pToken).getQueen().updateStuckTime();
		for(Frog f : getPlayer(pToken).getMaids()){
			f.updateStuckTime();
		}
		//getPlayer(pToken).activeFrogs();
	}
	
	public boolean canReproduce(Male maleId) {
		return getPlayer(pToken).getMalesLeft().contains(maleId)
				&& getPlayer(pToken).getMaidsLeft() > 0;
	}

	public void reproduce(Male maleId) {
		getPlayer(pToken).getMalesLeft().remove(maleId);
		getPlayer(pToken).addMaid(xDest, yDest);
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
			

			//p.addMaid(0, 0);
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

	public int getFrogId() {
		return frogId;
	}

	public void setFrogId(int frogId) {
		this.frogId = frogId;
	}
}
