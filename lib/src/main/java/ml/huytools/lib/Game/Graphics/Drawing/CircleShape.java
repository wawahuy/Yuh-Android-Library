package ml.huytools.lib.Game.Graphics.Drawing;

import android.graphics.Canvas;
import android.graphics.RectF;

public class CircleShape extends Drawable {
    private RectF rect;
    private int radius;
    private int startAngle;
    private int endAngle;
    private boolean center;
    private boolean drawToCenter;

    public CircleShape(){
        super();
        radius = 0;
        rect = new RectF();
        startAngle = 0;
        endAngle = 360;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.rect.right = this.rect.bottom = radius*2;
        this.radius = radius;
        this.drawToCenter = false;
        computeOrigin();
        hasUpdateDraw = true;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        hasUpdateDraw = true;
    }

    public int getAngleSwept() {
        return endAngle;
    }

    public void setAngleSwept(int endAngle) {
        this.endAngle = endAngle;
        hasUpdateDraw = true;
    }

    public boolean isDrawToCenter() {
        return drawToCenter;
    }

    public void setDrawToCenter(boolean drawToCenter) {
        this.drawToCenter = drawToCenter;
        hasUpdateDraw = true;
    }

    public void centerOrigin(){
        center = true;
        computeOrigin();
        hasUpdateDraw = true;
    }

    private void computeOrigin(){
        if(center){
            setOrigin(radius, radius);
            hasUpdateDraw = true;
        }
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawArc(rect, startAngle, endAngle, drawToCenter, paint);
    }
}
