package se.dolkow.graphbender;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Metric.setScale(getResources().getDisplayMetrics().density);
		GameSurface surface = new GameSurface(this);
		setContentView(surface);
		GameEngine ge = new GameEngine(surface);
	}
}
