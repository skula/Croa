package com.skula.croa.models;

public class Frog {
	private int xPos;
	private int yPos;
	private boolean stuck;
	private int weight;
	
	public static void main(String[] args) {

	}

	public Frog(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.stuck = false;
		this.weight = 1;
	}
	
	public Frog() {
		this.stuck = false;
		this.weight = 1;
	}
	
	public void moveTo(int x, int y){
		this.xPos = x;
		this.yPos = y;
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

	public boolean isStuck() {
		return stuck;
	}

	public void setStuck(boolean stuck) {
		this.stuck = stuck;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
