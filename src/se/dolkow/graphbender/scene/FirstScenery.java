package se.dolkow.graphbender.scene;

import se.dolkow.graphbender.Globals;
import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.R;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;

public class FirstScenery extends AbstractScenery {
	private static final int[] JELLY_COLORS = {
		0xffaaaa, 0xaaffaa, 0xaaaaff,
		0xffffaa, 0xffaaff, 0xaaffff,
		0xaaaaaa, 0xddaa77, 0xdd55aa,
	};
	private static final Paint[] JELLY_PAINTS;
	private final Paint mRegularPaint;
	private final Paint mSelectedPaint;
	private final Paint mHoveredPaint;
	private final Paint mTextPaint;
	private final Paint mTextPinkPaint;
	private final Paint mConnectedPaint;
	private Paint mTargetLinePaint;
	private float mTextMargin;
	private Bitmap[][] sprites;
	private long mTime;
	private int spriteI;
	
	static {
		JELLY_PAINTS = new Paint[JELLY_COLORS.length];
		for (int i=0; i<JELLY_COLORS.length; ++i) {
			int argb = 0xff000000 | JELLY_COLORS[i];
			JELLY_PAINTS[i] = new Paint();
			PorterDuffColorFilter filter = new PorterDuffColorFilter(argb, Mode.MULTIPLY);
			JELLY_PAINTS[i].setColorFilter(filter);
		}
	}
	
	public FirstScenery() {
		Bitmap jelly = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.jellyfish);
		sprites = new Bitmap[2][6];
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 2; y++) {
				sprites[y][x] = Bitmap.createBitmap(jelly, x * 96, y * 96, 96, 96);
			}
		}
		sprites[0][4] = sprites[0][2];
		sprites[0][5] = sprites[0][1];
		sprites[1][4] = sprites[1][3];
        sprites[1][3] = sprites[1][1];
		
		mRegularPaint = new Paint();
		mRegularPaint.setColor(Color.RED);
		mRegularPaint.setAntiAlias(true);
		
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(Metric.VERTEX_TEXT_SIZE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTypeface(Typeface.MONOSPACE);
		mTextPaint.setShadowLayer(3, 3, 3, Color.BLACK);
		mTextPinkPaint = new Paint(mTextPaint);
		mTextPinkPaint.setColor(0xFFFFAADD); // that's pink!
		mTextMargin = mTextPaint.measureText("0") / 2;

		mSelectedPaint = new Paint();
		mSelectedPaint.setColor(Color.BLUE);
		mSelectedPaint.setAntiAlias(true);
		
		mHoveredPaint = new Paint();
		mHoveredPaint.setColor(Color.GREEN);
		mHoveredPaint.setAntiAlias(true);;
		
		mTargetLinePaint = new Paint();
		mTargetLinePaint.setColor(Color.YELLOW);
		mTargetLinePaint.setStrokeWidth(7);
		mTargetLinePaint.setStyle(Style.STROKE);
		mTargetLinePaint.setAntiAlias(true);
		mTargetLinePaint.setAlpha(200);
		mTargetLinePaint.setShadowLayer(3, 3, 3, Color.BLACK);
		Bitmap lightningBitmap = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.lightning_texture);
		Shader lightningTextureShader = new BitmapShader(lightningBitmap, TileMode.MIRROR, TileMode.MIRROR);
		mTargetLinePaint.setShader(lightningTextureShader);
		
		//mTargetLinePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
		mConnectedPaint = new Paint();
		mConnectedPaint.setColor(Color.BLUE);
		mConnectedPaint.setStyle(Style.STROKE);
		mConnectedPaint.setShader(lightningTextureShader);
		mConnectedPaint.setStrokeWidth(7);
		mConnectedPaint.setAntiAlias(true);
		mConnectedPaint.setShadowLayer(3, 3, 3, Color.BLACK);
		mTime = System.currentTimeMillis();
	}

	@Override
	protected void drawEdge(Canvas c, Vertex v1, Vertex v2) {
		SceneryUtils.drawlineWithRandomWalk(c, v1.x, v1.y, v2.x, v2.y, mConnectedPaint);
	}

	@Override
	protected void drawTargetLine(Canvas c, Vertex v, int touchX, int touchY) {
		SceneryUtils.drawlineWithRandomWalk(c, v.x, v.y, touchX, touchY, mTargetLinePaint);
	}

	
	@Override
    protected void drawVertex(Canvas c, Vertex v) {
		int x = v.x; // + mRand.nextInt(v.required*2+1);
		int y = v.y; // + mRand.nextInt(v.required*2+1);
		//c.drawCircle(x, y, Metric.VERTEX_RADIUS,
		//		v.selected ? mSelectedPaint : v.hovered ? mHoveredPaint : mRegularPaint);
		long time = System.currentTimeMillis();
		if (time > mTime + 200) {
			spriteI++;
			mTime = time;
		}
		int personality = v.hashCode() >> 5;
		double diff = 5 * Math.sin(personality + time * 0.005);
		
		Paint fishPaint = JELLY_PAINTS[personality % JELLY_PAINTS.length];
		
		c.drawBitmap(sprites[1][(personality + spriteI) % 4], x - 48, (int) (y - 48 + diff), fishPaint);
		c.drawBitmap(sprites[0][(personality + spriteI) % 6], x - 48, (int) (y - 48 + diff), fishPaint);
		c.drawBitmap(sprites[1][4], v.x - 48, v.y - 48 + (int) (diff * 0.7), null);
		int r = v.getRequired();
		c.drawText(r > 0 ? "" + r : "♥", x - mTextMargin, v.y - Metric.VERTEX_TEXT_SIZE, r > 0 ? mTextPaint : mTextPinkPaint);
    }



}
