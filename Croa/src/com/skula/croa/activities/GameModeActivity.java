package com.skula.croa.activities;

import android.app.Activity;
import android.os.Bundle;

import com.skula.croa.activities.views.GameModeView;

public class GameModeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GameModeView(this));
	}
}