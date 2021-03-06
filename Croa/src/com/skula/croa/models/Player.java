package com.skula.croa.models;

import java.util.ArrayList;
import java.util.List;

import com.skula.croa.constants.Cnst;
import com.skula.croa.enums.FrogRank;
import com.skula.croa.enums.Male;

public class Player {
	private int id;
	private List<Frog> maids;
	private Frog queen;
	private List<Male> malesLeft;
	private int maidsLeft;
	private boolean dead;
	
	public Player(int id) {
		this.id = id;
		this.malesLeft = new ArrayList<Male>();
		this.malesLeft.add(Male.GREEN);
		this.malesLeft.add(Male.YELLOW);
		this.malesLeft.add(Male.RED);
		this.malesLeft.add(Male.BLUE);
		this.malesLeft.add(Male.PINK);
		this.malesLeft.add(Male.PURPLE);
		this.maids = new ArrayList<Frog>();
		this.maidsLeft = Cnst.FROGS_COUNT;
		this.dead = false;
	}
	
	public Position isQueenAndMaidOnOneTile(){
		for(Frog f : maids){
			if(f.getxPos()==queen.getxPos() && f.getyPos()==queen.getyPos()){
				return new Position(f.getxPos(), f.getyPos());
			}
		}
		return null;
	}
	
	public void updateStuckTime(){
		queen.updateStuckTime();
		for (Frog f :maids) {
			f.updateStuckTime();
		}
	}

	public void addMaid(int x, int y) {
		maids.add(new Frog(x, y, FrogRank.MAID));
		maidsLeft--;
	}
	
	public void removeMaid(int x, int y) {
		maids.remove(getMaid(x,y));
		maidsLeft++;
	}

	public void setQueen(int x, int y) {
		queen = new Frog(x, y, FrogRank.QUEEN);
	}
	
	public boolean hasFrog(int x, int y){
		return hasQueen(x,y) || hasMaid(x, y);
	}
	
	public boolean hasMaid(int x, int y) {
		for (Frog f : maids) {
			if (f.getxPos() == x && f.getyPos() == y) {
				return true;
			}
		}
		return false;
	}

	public int getFrogsWeigth(int x, int y) {
		if (hasQueen(x, y)) {
			return 2;
		}

		int res = 0;
		for (Frog f : maids) {
			if (f.getxPos() == x && f.getyPos() == y) {
				res++;
			}
		}
		return res;
	}
	
	public int getCountPlayableFrogs(){
		int res = queen.isStuck()?0:1;
		for (Frog f : maids) {
			if (!f.isStuck()) {
				res++;
			}
		}
		return res;
	}

	public boolean hasQueen(int x, int y) {
		return queen.getxPos() == x && queen.getyPos() == y;
	}

	public int getDeadFrogs() {
		return Cnst.FROGS_COUNT - maidsLeft - maids.size();
	}
	
	public void activeFrogsBut(int x, int y, boolean isQueen){
		if(isQueen){
			queen.setActive(false);
			for(Frog f : maids){
				f.setActive(!f.isStuck());
			}
		}else{
			queen.setActive(!queen.isStuck());
			for(Frog f : maids){
				if(f.isThere(x, y)){
					f.setActive(false);
				}else{
					f.setActive(!f.isStuck());
				}
			}
		}
	}
	
	public void desactiveFrogsBut(int x, int y, boolean isQueen){
		if(isQueen){
			queen.setActive(!queen.isStuck());
			for(Frog f : maids){
				f.setActive(false);
			}
		}else{
			queen.setActive(false);
			for(Frog f : maids){
				if(f.isThere(x, y)){
					f.setActive(!f.isStuck());
				}else{
					f.setActive(false);
				}
			}
		}
	}
	
	public void desactiveFrogsBut(int id){
		if(queen.getId() == id){
			queen.setActive(!queen.isStuck());
			for(Frog f : maids){
				f.setActive(false);
			}
		}else{
			queen.setActive(false);
			for(Frog f : maids){
				if(f.getId() == id){
					f.setActive(!f.isStuck());
				}else{
					f.setActive(false);
				}
			}
		}
	}
	
	public void activeFrogs(){
		queen.setActive(!queen.isStuck());
		for(Frog f: maids){
			f.setActive(!f.isStuck());
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Frog> getMaids() {
		return maids;
	}

	public Frog getMaid(int x, int y) {
		for (Frog f : maids) {
			if (f.getxPos() == x && f.getyPos() == y) {
				return f;
			}
		}
		return null;
	}
	
	public Frog getFrog(int id){
		if(queen.getId()==id){
			return queen;
		}else{
			for(Frog f: maids){
				if(f.getId()==id){
					return f;
				}
			}
		}
		return null;
	}
	
	public void eaten() {
		maids.clear();
		this.dead = true;
	}

	public void setMaids(List<Frog> maids) {
		this.maids = maids;
	}

	public Frog getQueen() {
		return queen;
	}

	public void setQueen(Frog queen) {
		this.queen = queen;
	}

	public List<Male> getMalesLeft() {
		return malesLeft;
	}

	public void setMalesLeft(List<Male> malesLeft) {
		this.malesLeft = malesLeft;
	}

	public int getMaidsLeft() {
		return maidsLeft;
	}

	public boolean isDead() {
		return dead;
	}

	public void setMaidsLeft(int maidsLeft) {
		this.maidsLeft = maidsLeft;
	}

}