package se.dolkow.graphbender.scene.backgrounds;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import se.dolkow.graphbender.R;

public class PictureBackground implements Background {

	private Bitmap mBackground;

	public PictureBackground(Resources res) {
		mBackground = BitmapFactory.decodeResource(res, R.drawable.starrynight);
	}
	
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(mBackground, 0, 0, null);
	}
	
}
