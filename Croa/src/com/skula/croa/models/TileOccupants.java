package com.skula.croa.models;

public class TileOccupants {
	private int count;
	
	private int frog1Id;
	private int frog2Id;
	private boolean frog1Queen;
	private boolean frog2queen;
	private boolean frog1Stuck;
	private boolean frog2Stuck;
	
	public static void main(String[] args) {

	}

	public TileOccupants(){
		this.count = 0;
		this.frog1Id = -1;
		this.frog2Id = -1;
		this.frog1Queen = false;
		this.frog2queen = false;
	}
	
	public void addOccupant(int id, boolean isQueen, boolean stuck){
		if(count==2){
			return;
		}
		
		if(count==0){
			frog1Id = id;
			frog1Queen = isQueen;
			frog1Stuck = stuck;
		}else if( count == 1){
			frog2Id = id;
			frog2queen = isQueen;
			frog2Stuck = stuck;
		}

		count ++;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getFrog1Id() {
		return frog1Id;
	}

	public void setFrog1Id(int frog1Id) {
		this.frog1Id = frog1Id;
	}

	public int getFrog2Id() {
		return frog2Id;
	}

	public void setFrog2Id(int frog2Id) {
		this.frog2Id = frog2Id;
	}

	public boolean isFrog1queen() {
		return frog1Queen;
	}

	public void setFrog1queen(boolean frog1queen) {
		this.frog1Queen = frog1queen;
	}

	public boolean isFrog2queen() {
		return frog2queen;
	}

	public void setFrog2queen(boolean frog2queen) {
		this.frog2queen = frog2queen;
	}

	public boolean isFrog1Stuck() {
		return frog1Stuck;
	}

	public void setFrog1Stuck(boolean frog1Stuck) {
		this.frog1Stuck = frog1Stuck;
	}

	public boolean isFrog2Stuck() {
		return frog2Stuck;
	}

	public void setFrog2Stuck(boolean frog2Stuck) {
		this.frog2Stuck = frog2Stuck;
	}
}
