package ml.huytools.lib.Math;

import android.graphics.PointF;


/***
 * CubicBezier.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 02/12/2019
 * Update:
 *
 */
public class CubicBezier {

    public enum TIMING {
        Ease, EaseInOut, EaseIn, EaseOut, Linear
    }

    Vector2D p0, p1, p2, p3;

    public CubicBezier(Vector2D p1, Vector2D p2){
        set(p1, p2);
    }

    public CubicBezier(float x1, float y1, float x2, float y2){
        set(x1, y1, x2, y2);
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

    public static TIMING StringToTiming(String timing){
        switch (timing){
            case "Ease":
                return TIMING.Ease;

            case "EaseInOut":
                return TIMING.EaseInOut;

            case "EaseIn":
                return TIMING.EaseIn;

            case "EaseOut":
                return TIMING.EaseOut;

            default:
                return TIMING.Linear;
        }
    }

    public void set(float x1, float y1, float x2, float y2){
        set(new Vector2D(x1, y1), new Vector2D(x2, y2));
    }

    public void set(Vector2D p1, Vector2D p2){
        this.p0 = new Vector2D(0, 0);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = new Vector2D(1,1);
    }

    public Vector2D B(float t){
        Vector2D pc1 = p0.mul((float) Math.pow(1-t, 3));
        Vector2D pc2 = p1.mul(3*t*(float) Math.pow(1-t, 2));
        Vector2D pc3 = p2.mul(3*t*t*(1-t));
        Vector2D pc4 = p3.mul(t*t*t);
        return YMath.Add(pc1, pc2, pc3, pc4);
    }

}
