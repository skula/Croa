package com.skula.croa.models;

public class TileOccupants {
	private int count;
	
	private int frog1Id;
	private int frog2Id;
	private Frog frog1;
	private Frog frog2;
	
	public static void main(String[] args) {

	}

	public TileOccupants(){
		this.count = 0;
	}
	
	public void addOccupant(int id, Frog frog){
		if(count==2){
			return;
		}
		
		if(count==0){
			frog1Id = id;
			frog1 = frog;
		}else if( count == 1){
			frog2Id = id;
			frog2 = frog;
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
