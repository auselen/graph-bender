package se.dolkow.graphbender.scene.backgrounds;

import se.dolkow.graphbender.Globals;
import se.dolkow.graphbender.R;
import se.dolkow.graphbender.ui.Background;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class PictureBackground implements Background {

	private static Bitmap sOrigBitmap;
	private static Bitmap sBackground;

	@Override
	public void draw(Canvas c, long frameTime, long deltaTime) {
		c.drawBitmap(sBackground, 0, 0, null);
	}

	@Override
    public void sizeChanged(int width, int height) {
		Log.i("PicBG", width + ", " + height);
		if (width != 0 && height != 0) {
			if (sBackground == null || width != sBackground.getWidth() && height != sBackground.getHeight()) {
				if (sOrigBitmap == null) {
					sOrigBitmap = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.starrynight);
				}
				sBackground = Bitmap.createScaledBitmap(sOrigBitmap, width, height, true);
			}
		}
    }
}
