package com.skula.croa.constants;

import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.skula.croa.R;

public class PictureLibrary{
	private Map<Integer, Bitmap> map;
	
	public PictureLibrary(Resources res){
		this.map = new HashMap<Integer, Bitmap>();		
		this.map.put(R.drawable.male_blue, 		BitmapFactory.decodeResource(res, R.drawable.male_blue));
		this.map.put(R.drawable.male_green, 		BitmapFactory.decodeResource(res, R.drawable.male_green));
		this.map.put(R.drawable.male_pink, 		BitmapFactory.decodeResource(res, R.drawable.male_pink));
		this.map.put(R.drawable.male_purple, 		BitmapFactory.decodeResource(res, R.drawable.male_purple));
		this.map.put(R.drawable.male_red, 		BitmapFactory.decodeResource(res, R.drawable.male_red));
		this.map.put(R.drawable.male_yellow, 		BitmapFactory.decodeResource(res, R.drawable.male_yellow));

		this.map.put(R.drawable.depth_one, 		BitmapFactory.decodeResource(res, R.drawable.depth_one));
		this.map.put(R.drawable.depth_two, 		BitmapFactory.decodeResource(res, R.drawable.depth_two));
		this.map.put(R.drawable.depth_three, 		BitmapFactory.decodeResource(res, R.drawable.depth_three));
		
		this.map.put(R.drawable.mosquito, 		BitmapFactory.decodeResource(res, R.drawable.mosquito));
		this.map.put(R.drawable.mud, 		BitmapFactory.decodeResource(res, R.drawable.mud));
		this.map.put(R.drawable.pike, 		BitmapFactory.decodeResource(res, R.drawable.pike));
		this.map.put(R.drawable.reed, 		BitmapFactory.decodeResource(res, R.drawable.reed));
		this.map.put(R.drawable.waterlilly, 		BitmapFactory.decodeResource(res, R.drawable.waterlilly));
		this.map.put(R.drawable.woodlog, 		BitmapFactory.decodeResource(res, R.drawable.woodlog));
		
		this.map.put(R.drawable.maid_blue, 		BitmapFactory.decodeResource(res, R.drawable.maid_blue));
		this.map.put(R.drawable.maid_pink, 		BitmapFactory.decodeResource(res, R.drawable.maid_pink));
		this.map.put(R.drawable.maid_yellow, 		BitmapFactory.decodeResource(res, R.drawable.maid_yellow));
		this.map.put(R.drawable.maid_green, 		BitmapFactory.decodeResource(res, R.drawable.maid_green));
		this.map.put(R.drawable.maid_stuck_blue, 		BitmapFactory.decodeResource(res, R.drawable.maid_stuck_blue));
		this.map.put(R.drawable.maid_stuck_pink, 		BitmapFactory.decodeResource(res, R.drawable.maid_stuck_pink));
		this.map.put(R.drawable.maid_stuck_yellow, 		BitmapFactory.decodeResource(res, R.drawable.maid_stuck_yellow));
		this.map.put(R.drawable.maid_stuck_green, 		BitmapFactory.decodeResource(res, R.drawable.maid_stuck_green));
			
		this.map.put(R.drawable.queen_blue, 		BitmapFactory.decodeResource(res, R.drawable.queen_blue));
		this.map.put(R.drawable.queen_pink, 		BitmapFactory.decodeResource(res, R.drawable.queen_pink));
		this.map.put(R.drawable.queen_yellow, 		BitmapFactory.decodeResource(res, R.drawable.queen_yellow));
		this.map.put(R.drawable.queen_green, 		BitmapFactory.decodeResource(res, R.drawable.queen_green));
		this.map.put(R.drawable.queen_stuck_blue, 		BitmapFactory.decodeResource(res, R.drawable.queen_stuck_blue));
		this.map.put(R.drawable.queen_stuck_pink, 		BitmapFactory.decodeResource(res, R.drawable.queen_stuck_pink));
		this.map.put(R.drawable.queen_stuck_yellow, 		BitmapFactory.decodeResource(res, R.drawable.queen_stuck_yellow));
		this.map.put(R.drawable.queen_stuck_green, 		BitmapFactory.decodeResource(res, R.drawable.queen_stuck_green));
		
		this.map.put(R.drawable.male_spawn_blue, 		BitmapFactory.decodeResource(res, R.drawable.male_spawn_blue));
		this.map.put(R.drawable.male_spawn_green, 		BitmapFactory.decodeResource(res, R.drawable.male_spawn_green));
		this.map.put(R.drawable.male_spawn_pink, 		BitmapFactory.decodeResource(res, R.drawable.male_spawn_pink));
		this.map.put(R.drawable.male_spawn_purple, 		BitmapFactory.decodeResource(res, R.drawable.male_spawn_purple));
		this.map.put(R.drawable.male_spawn_red, 		BitmapFactory.decodeResource(res, R.drawable.male_spawn_red));
		this.map.put(R.drawable.male_spawn_yellow, 		BitmapFactory.decodeResource(res, R.drawable.male_spawn_yellow));
		this.map.put(R.drawable.grave, 		BitmapFactory.decodeResource(res, R.drawable.grave));
		this.map.put(R.drawable.egg, 		BitmapFactory.decodeResource(res, R.drawable.egg));

		this.map.put(R.drawable.sel, 		BitmapFactory.decodeResource(res, R.drawable.sel));
		
		this.map.put(R.drawable.victory, 		BitmapFactory.decodeResource(res, R.drawable.victory));
		this.map.put(R.drawable.victory_player1, 		BitmapFactory.decodeResource(res, R.drawable.victory_player1));
		this.map.put(R.drawable.victory_player2, 		BitmapFactory.decodeResource(res, R.drawable.victory_player2));
		this.map.put(R.drawable.victory_player3, 		BitmapFactory.decodeResource(res, R.drawable.victory_player3));
		
		this.map.put(R.drawable.croa_logo, 		BitmapFactory.decodeResource(res, R.drawable.croa_logo));
		this.map.put(R.drawable.gamemode_four_players, 		BitmapFactory.decodeResource(res, R.drawable.gamemode_four_players));
		this.map.put(R.drawable.gamemode_three_players, 		BitmapFactory.decodeResource(res, R.drawable.gamemode_three_players));
		this.map.put(R.drawable.gamemode_two_players, 		BitmapFactory.decodeResource(res, R.drawable.gamemode_two_players));
	
	}
		
	public Bitmap get(int id){
		return map.get(id);
	}	
}