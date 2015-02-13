package com.skula.croa.models;

public class Frog {
	private int xPos;
	private int yPos;
	private int stuckTime;
	private boolean active;
	
	public static void main(String[] args) {

	}

	public Frog(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.stuckTime = 0;
		this.active = true;
	}
	
	public Frog() {
		this.stuckTime = 0;
		this.active = true;
	}
	
	public void moveTo(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public boolean isThere(int x, int y){
		return xPos == x && yPos == y;		
	}
	
	public void updateStuckTime(){
		if(stuckTime>0){
			stuckTime--;
		}
	}
	
	public void setStuck(){
		stuckTime = 2;
	}
	public boolean isStuck(){
		return stuckTime > 0;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
