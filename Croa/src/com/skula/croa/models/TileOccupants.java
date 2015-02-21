package com.skula.croa.models;

public class TileOccupants {
	private int count;
	private int frog1pId;
	private int frog2pId;
	private Frog frog1;
	private Frog frog2;
	
	public TileOccupants() {
		this.count = 0;
	}

	public void addOccupant(int id, Frog frog) {
		if (count == 2) {
			return;
		}

		if (count == 0) {
			frog1pId = id;
			frog1 = frog;
		} else if (count == 1) {
			frog2pId = id;
			frog2 = frog;
		}

		count++;
	}

	public boolean isQueenAndMaid(int playerId) {
		if (count != 2) {
			return false;
		}
		return frog1pId == frog2pId && frog1pId == playerId;
	}

	public Frog getFrogByPlayerId(int playerId) {
		if (frog1pId == playerId) {
			return frog1;
		} else {
			return frog2pId == playerId ? frog2 : null;
		}
	}
	
	public int getWeight(){
		if(count==0){
			return 0;
		}
		if(count==1){
			return frog1.isQueen()?2:1;
		}else{
			int res = 0;
			res +=frog1.isQueen()?2:1;
			res +=frog2.isQueen()?2:1;
			return res;
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getFrog1pId() {
		return frog1pId;
	}

	public void setFrog1pId(int frog1Id) {
		this.frog1pId = frog1Id;
	}

	public int getFrog2pId() {
		return frog2pId;
	}

	public void setFrog2pId(int frog2Id) {
		this.frog2pId = frog2Id;
	}

	public Frog getFrog1() {
		return frog1;
	}

	public void setFrog1(Frog frog1) {
		this.frog1 = frog1;
	}

	public Frog getFrog2() {
		return frog2;
	}

	public void setFrog2(Frog frog2) {
		this.frog2 = frog2;
	}
}
