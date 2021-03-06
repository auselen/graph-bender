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
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;

public class FirstScenery extends AbstractScenery {
	private static final int SPRITE_SIZE = 96;
	private static final int[] JELLY_COLORS = {
		0x88ccff, 0xaaffaa, 0xaaaaff,
		0xffffaa, 0xffaaff, 0xffaaaa, 
		0xaaaaaa, 0xddaa77, 0xdd55aa,
	};
	private static final Paint[] JELLY_PAINTS;
	private final Paint mRegularPaint;
	private final Paint mPendingOkPaint;
	private final Paint mPendingMaybePaint;
	private final Paint mPendingBadPaint;
	private final Paint mTextPaint;
	private final Paint mTextPinkPaint;
	private final Paint mConnectedPaint;
	private Paint mTargetLinePaint;
	private float mTextMargin;
	private float mHeartMargin;
	private Bitmap[][] sprites;
	private float mHaloLength;
	
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
		sprites = new Bitmap[3][6];
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 3; y++) {
				sprites[y][x] = Bitmap.createBitmap(jelly, x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
			}
		}
		sprites[0][4] = sprites[0][2];
		sprites[0][5] = sprites[0][1];
		
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
		mHeartMargin = mTextPaint.measureText("♥") / 2;

		mPendingOkPaint = new Paint();
		mPendingOkPaint.setColor(Color.GREEN);
		mPendingOkPaint.setStyle(Style.STROKE);
		mPendingOkPaint.setAntiAlias(true);
		mPendingOkPaint.setStrokeWidth(25);
		
		mPendingMaybePaint = new Paint(mPendingOkPaint);
		mPendingMaybePaint.setColor(Color.YELLOW);

		mPendingBadPaint = new Paint(mPendingOkPaint);
		mPendingBadPaint.setColor(Color.RED);
		
		mTargetLinePaint = new Paint();
		mTargetLinePaint.setColor(Color.YELLOW);
		mTargetLinePaint.setStrokeWidth(3 * Metric.SCALE);
		mTargetLinePaint.setStyle(Style.STROKE);
		Bitmap lightningBitmap = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.lightning_texture);
		Shader lightningTextureShader = new BitmapShader(lightningBitmap, TileMode.MIRROR, TileMode.MIRROR);
		mTargetLinePaint.setShader(lightningTextureShader);
		
		mConnectedPaint = new Paint();
		mConnectedPaint.setColor(Color.BLUE);
		mConnectedPaint.setStrokeWidth(3 * Metric.SCALE);
		mConnectedPaint.setStyle(Style.STROKE);
		mConnectedPaint.setShader(lightningTextureShader);

		mHaloLength = (float) (Math.pow(Metric.VERTEX_RADIUS * 1.5f, 2) * Math.PI) / 500;
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
    protected void drawVertex(Canvas c, Vertex v, VertexData vd) {
		int x = v.x; // + mRand.nextInt(v.required*2+1);
		int y = v.y; // + mRand.nextInt(v.required*2+1);
		//c.drawCircle(x, y, Metric.VERTEX_RADIUS,
		//		v.selected ? mSelectedPaint : v.hovered ? mHoveredPaint : mRegularPaint);
		long time = System.currentTimeMillis();
		int spriteI = (int)(time / 300) & 0x0ffffff;
		final double phase = (v.personality + time * 0.002) % (2*Math.PI); 
		double diff = 10 * Math.cos(phase);
		
		if (vd.selected || vd.hovered) {
			Paint haloPaint = vd.connectable ? mPendingOkPaint :
				(vd.unconnectable ? mPendingBadPaint : mPendingMaybePaint);
			
			DashPathEffect effect = new DashPathEffect(new float[] { mHaloLength, mHaloLength }, (float) diff * 2);
			haloPaint.setPathEffect(effect);
			c.drawCircle(x, y, Metric.VERTEX_RADIUS * 1.5f, haloPaint);
		}
	
		Paint fishPaint = JELLY_PAINTS[v.personality % JELLY_PAINTS.length];
		int legs = 0;
		if (phase < 0.4) {
			legs = 1;
		} else if (phase < Math.PI+0.2) {
			legs = 3;
		} else if (phase < 5) {
			legs = 2;
		}
		c.drawBitmap(sprites[1][legs], x - SPRITE_SIZE/2, (int) (y - SPRITE_SIZE/2 + diff), fishPaint);

		c.drawBitmap(sprites[0][(v.personality + spriteI) % 6], x - SPRITE_SIZE/2, (int) (y - SPRITE_SIZE/2 + diff), fishPaint);
		
		int r = v.getRequired();
		int eyes = Math.min((r+1)/2, 3);
		c.drawBitmap(sprites[2][eyes], v.x - SPRITE_SIZE/2, v.y - SPRITE_SIZE/2 + (int) (diff * 0.7), null);
		float margin = r > 0 ? mTextMargin : mHeartMargin; 
		c.drawText(r > 0 ? "" + r : "♥", x - margin, v.y - (int)(Metric.VERTEX_TEXT_SIZE*1.2),
				r > 0 ? mTextPaint : mTextPinkPaint);
    }



}
