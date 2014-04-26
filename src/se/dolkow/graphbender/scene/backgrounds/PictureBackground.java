package se.dolkow.graphbender.scene.backgrounds;

import se.dolkow.graphbender.Globals;
import se.dolkow.graphbender.R;
import se.dolkow.graphbender.ui.Background;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

public class PictureBackground implements Background {

	private Bitmap mBackground;
	
	int[] pics = new int[] {R.drawable.pool1};
	private Paint mPaint;

	public PictureBackground(int n) {
		n -= 3;
		mBackground = BitmapFactory.decodeResource(Globals.sAppResources, pics[n % pics.length]);
		BitmapShader bitmapShader = new BitmapShader(mBackground, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
		mPaint = new Paint();
		mPaint.setShader(bitmapShader);
	}
	
	@Override
	public void draw(Canvas c, long frameTime, long deltaTime) {
		c.drawPaint(mPaint);
	}

	@Override
    public void sizeChanged(int width, int height) {
    }
}
