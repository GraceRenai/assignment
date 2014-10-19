package assignment.easyaccount.tabbar;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import assignment.easyaccount.R;

public class ButtonStateDrawable extends Drawable
{
	private String label;
	private Bitmap bitmap;
	private Shader labelShader;
	private Shader imageShader;
	private boolean onState;
	public static  int WIDTH;
	private Context context;
	public ButtonStateDrawable(Context context,int imageId,String label,boolean onState)
	{
		super();
		this.context = context;
		this.bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
		this.label = label;
		this.onState = onState;
		if (onState) 
		{
			labelShader = new LinearGradient(0, 0, 0, 10, new int[]{Color.WHITE, Color.LTGRAY}, null, Shader.TileMode.MIRROR);
		}
		else 
		{
			labelShader = new LinearGradient(0, 0, 0, 10, new int[]{Color.LTGRAY, Color.DKGRAY}, null, Shader.TileMode.MIRROR);
			this.bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
		}
	}
	@Override
	public void draw(Canvas canvas)
	{
		int bwidth = bitmap.getWidth();
		int bheight = bitmap.getHeight();
		int x = (WIDTH-bwidth)/2;
		int y = 4;
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(20);
		p.setFakeBoldText(true);
		p.setTextAlign(Align.CENTER);
		p.setShader(labelShader);
		p.setAntiAlias(true);
		canvas.drawText(label, WIDTH / 2, y + bheight + 11, p);
		if(!this.onState)
		{
			p.setShader(imageShader);
		}
		else
		{
			p.setShader(null);
			Shader bgShader=new LinearGradient(15,y-1,WIDTH-15,32+y+1,new int[]{context.getResources().getColor(R.color.tabbar_1),
			context.getResources().getColor(R.color.tabbar_2)},null,Shader.TileMode.CLAMP);
			p.setShader(bgShader);
			p.setStyle(Paint.Style.FILL);
			RectF rect = new RectF(15,y-1,WIDTH-15,32+y+1);
			canvas.drawRoundRect(rect, 5.0f, 5.0f, p);	
		}
		canvas.drawBitmap(bitmap, x, y, p);
	}

	@Override
	public int getOpacity()
	{
		return 0;
	}


	@Override
	public void setAlpha(int alpha)
	{
		
	}

	@Override
	public void setColorFilter(ColorFilter cf)
	{
		
	}
}
