package com.skula.croa.constants;

import com.skula.croa.R;
import com.skula.croa.enums.Male;




public class Cnst {
	// damier
	public static final int COLUMNS_COUNT = 8;
	public static final int ROWS_COUNT = 8;
	public static final int X0 = 280;
	public static final int Y0 = 0;
	
	// pictures
	public static final int TILE_SIZE = 90;
	public static final int FROG_WIDTH = 60;
	public static final int FROG_HEIGHT = 60;
	public static final int QUEEN_SIZE = 90;
	public static final int MALE_SPAWN_SIZE = 20;
	public static final int GRAVE_WIDTH = 70;
	public static final int GRAVE_HEIGHT = 47;

	public static final int FROGS_COUNT = 6;

	public static final int SPACE_MAX_SINGLE = 1;
	public static final int SPACE_MAX_DOUBLE = 2;
	
	public static int getMaidPictId(int playerId, boolean stuck){
		switch (playerId) {
		case 0:
			return stuck?R.drawable.maid_stuck_blue:R.drawable.maid_blue;
		case 1:
			return stuck?R.drawable.maid_stuck_pink:R.drawable.maid_pink;
		case 2:
			return stuck?R.drawable.maid_stuck_green:R.drawable.maid_green;
		case 3:
			return stuck?R.drawable.maid_stuck_yellow:R.drawable.maid_yellow;
		default:
			return 0;
		}
	}
	
	public static int getQueenPictId(int playerId, boolean stuck){
		switch (playerId) {
		case 0:
			return stuck?R.drawable.queen_stuck_blue:R.drawable.queen_blue;
		case 1:
			return stuck?R.drawable.queen_stuck_pink:R.drawable.queen_pink;
		case 2:
			return stuck?R.drawable.queen_stuck_green:R.drawable.queen_green;
		case 3:
			return stuck?R.drawable.queen_stuck_yellow:R.drawable.queen_yellow;
		default:
			return 0;
		}
	}
	
	public static int getMalePictId(Male m){
		switch(m){
		case GREEN:
			return R.drawable.male_spawn_green;
		case YELLOW:
			return R.drawable.male_spawn_yellow;
		case RED:
			return R.drawable.male_spawn_red;
		case BLUE:
			return R.drawable.male_spawn_blue;
		case PINK:
			return R.drawable.male_spawn_pink;
		case PURPLE:
			return R.drawable.male_spawn_purple;
		default:
			return 0;
		}
	}
}
