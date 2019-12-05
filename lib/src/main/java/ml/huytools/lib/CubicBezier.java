package ml.huytools.lib;

import android.graphics.PointF;

/***
 *
 *
 *
 *
 *
 */
public class CubicBezier {

    enum TIMING {
        Ease, EaseInOut, EaseIn, EaseOut, Linear
    }

    PointF p0, p1;

    public CubicBezier(PointF p0, PointF p1){
        set(p0, p1);
    }

    public CubicBezier(float x0, float y0, float x1, float y1){
        set(x0, y0, x1, y1);
    }

    public CubicBezier(TIMING timing){
        switch (timing){
            case Ease:
                set(0.25f, 1, 0.25f, 1);
                break;
            case EaseInOut:
                set(0.42f, 0, 0.58f, 1);
                break;
            case EaseIn:
                set(0.42f, 0, 1, 1);
                break;
            case EaseOut:
                set(0, 0, 0.58f, 1);
                break;
            case Linear:
                set(0, 0, 1, 1);
                break;
        }
    }

    public void set(float x0, float y0, float x1, float y1){
        set(new PointF(x0, y0), new PointF(x1, y1));
    }

    public void set(PointF p0, PointF p1){
        this.p0 = p0;
        this.p1 = p1;
    }

    public PointF B(float t){
        PointF pointF = new PointF();

        return pointF;
    }
}
