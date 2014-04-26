package se.dolkow.graphbender;

import android.app.Activity;
import android.os.Bundle;

public class GraphActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Metric.SCALE = getResources().getDisplayMetrics().density;
		GameSurface surface = new GameSurface(this);
		setContentView(surface);
		GameEngine ge = new GameEngine(surface);
	}
}
