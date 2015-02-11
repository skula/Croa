package com.skula.croa.models;

import java.util.ArrayList;
import java.util.List;

import com.skula.croa.constants.Cnst;

public class Player {
	private int id;
	private List<Frog> frogs;
	private QueenFrog queen;
	private List<Integer> malesLeft;
	private int frogsLeft;

	public static void main(String[] args) {

	}

	public Player(int id) {
		this.id = id;
		this.malesLeft = new ArrayList<Integer>();
		this.malesLeft.add(Cnst.MALE_GREEN);
		this.malesLeft.add(Cnst.MALE_YELLOW);
		this.malesLeft.add(Cnst.MALE_RED);
		this.malesLeft.add(Cnst.MALE_BLUE);
		this.malesLeft.add(Cnst.MALE_PINK);
		this.malesLeft.add(Cnst.MALE_PURPLE);
		this.frogs = new ArrayList<Frog>();
		this.frogsLeft = Cnst.FROGS_COUNT;
	}

	public void addFrog(int x, int y) {
		frogs.add(new Frog(x, y));
		frogsLeft--;
	}

	public void setQueen(int x, int y) {
		queen = new QueenFrog(x, y);
	}

	public boolean hasFrog(int x, int y) {
		for (Frog f : frogs) {
			if (f.getxPos() == x && f.getyPos() == y) {
				return true;
			}
		}
		return false;
	}

	public int getWeigth(int x, int y) {
		if (hasQueen(x, y)) {
			return 2;
		}

		int res = 0;
		for (Frog f : frogs) {
			if (f.getxPos() == x && f.getyPos() == y) {
				res++;
			}
		}
		return res;
	}

	public boolean hasQueen(int x, int y) {
		return queen.getxPos() == x && queen.getyPos() == y;
	}

	public int getDeadFrogs() {
		return Cnst.FROGS_COUNT - frogsLeft - frogs.size();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Frog> getFrogs() {
		return frogs;
	}

	public void setFrogs(List<Frog> frogs) {
		this.frogs = frogs;
	}

	public QueenFrog getQueen() {
		return queen;
	}

	public void setQueen(QueenFrog queen) {
		this.queen = queen;
	}

	public List<Integer> getMalesLeft() {
		return malesLeft;
	}

	public void setMalesLeft(List<Integer> malesLeft) {
		this.malesLeft = malesLeft;
	}

	public int getFrogsLeft() {
		return frogsLeft;
	}

	public void setFrogsLeft(int frogsLeft) {
		this.frogsLeft = frogsLeft;
	}
}
