package se.dolkow.graphbender.scene.backgrounds;

import se.dolkow.graphbender.Globals;
import se.dolkow.graphbender.R;
import se.dolkow.graphbender.ui.Background;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class PictureBackground implements Background {

	private Bitmap mBackground;

	public PictureBackground() {
		mBackground = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.starrynight);
	}
	
	@Override
	public void draw(Canvas c, long frameTime, long deltaTime) {
		c.drawBitmap(mBackground, 0, 0, null);
	}

	@Override
    public void sizeChanged(int width, int height) {
    }
}
