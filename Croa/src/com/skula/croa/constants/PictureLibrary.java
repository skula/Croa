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
		this.map.put(R.drawable.depth_one, 		BitmapFactory.decodeResource(res, R.drawable.depth_one));
		this.map.put(R.drawable.depth_two, 		BitmapFactory.decodeResource(res, R.drawable.depth_two));
		this.map.put(R.drawable.depth_three, 		BitmapFactory.decodeResource(res, R.drawable.depth_three));
		

		this.map.put(R.drawable.male_blue, 		BitmapFactory.decodeResource(res, R.drawable.male_blue));
		this.map.put(R.drawable.male_green, 		BitmapFactory.decodeResource(res, R.drawable.male_green));
		this.map.put(R.drawable.male_pink, 		BitmapFactory.decodeResource(res, R.drawable.male_pink));
		this.map.put(R.drawable.male_purple, 		BitmapFactory.decodeResource(res, R.drawable.male_purple));
		this.map.put(R.drawable.male_red, 		BitmapFactory.decodeResource(res, R.drawable.male_red));
		this.map.put(R.drawable.male_yellow, 		BitmapFactory.decodeResource(res, R.drawable.male_yellow));

		this.map.put(R.drawable.mosquito, 		BitmapFactory.decodeResource(res, R.drawable.mosquito));
		this.map.put(R.drawable.mud, 		BitmapFactory.decodeResource(res, R.drawable.mud));
		this.map.put(R.drawable.pike, 		BitmapFactory.decodeResource(res, R.drawable.pike));
		this.map.put(R.drawable.reed, 		BitmapFactory.decodeResource(res, R.drawable.reed));
		this.map.put(R.drawable.waterlilly, 		BitmapFactory.decodeResource(res, R.drawable.waterlilly));
		this.map.put(R.drawable.woodlog, 		BitmapFactory.decodeResource(res, R.drawable.woodlog));
		
		this.map.put(R.drawable.frog_blue, 		BitmapFactory.decodeResource(res, R.drawable.frog_blue));
		this.map.put(R.drawable.frog_green, 		BitmapFactory.decodeResource(res, R.drawable.frog_green));
		this.map.put(R.drawable.frog_yellow, 		BitmapFactory.decodeResource(res, R.drawable.frog_yellow));
		this.map.put(R.drawable.frog_red, 		BitmapFactory.decodeResource(res, R.drawable.frog_red));
	}
		
	public Bitmap get(int id){
		return map.get(id);
	}	
}