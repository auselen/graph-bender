package se.dolkow.graphbender.scene.backgrounds;

import se.dolkow.graphbender.Globals;
import se.dolkow.graphbender.R;
import se.dolkow.graphbender.ui.Background;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PictureBackground implements Background {

	private Bitmap mBackground;
	private Rect srcRect;

	public PictureBackground() {
		mBackground = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.starrynight);
		srcRect = new Rect(0, 0, mBackground.getWidth(), mBackground.getHeight());
		
	}
	
	@Override
	public void draw(Canvas c, long frameTime, long deltaTime) {
		c.drawBitmap(mBackground, srcRect,
				new Rect(0, 0, c.getWidth(), c.getHeight()), null);
	}

	@Override
    public void sizeChanged(int width, int height) {
    }
}
