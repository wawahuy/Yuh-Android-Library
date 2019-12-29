package ml.huytools.lib.Math;

import android.graphics.PointF;

public class YMath {

    public static PointF Mul(float a, PointF pointF){
        PointF pointN = new PointF();
        pointN.x = a*pointF.x;
        pointN.y = a*pointF.y;
        return pointN;
    }

    public static PointF Mul(PointF pointF, float a){
        return Mul(a, pointF);
    }

    public static float Mul(PointF a, PointF b){
        return a.x*b.x + a.y*b.y;
    }

    public static PointF Add(PointF a, PointF b){
        PointF pointN = new PointF();
        pointN.x = a.x+b.x;
        pointN.y = a.y+b.y;
        return pointN;
    }

    public static PointF Add(PointF... p){
        PointF pointN = new PointF(0, 0);
        for(PointF pc:p){
            pointN.x += pc.x;
            pointN.y += pc.y;
        }
        return pointN;
    }

    public static Vector2D Add(Vector2D... p){
        Vector2D pointN = new Vector2D(0, 0);
        for(Vector2D pc:p){
            pointN.x += pc.x;
            pointN.y += pc.y;
        }
        return pointN;
    }

    public static PointF Subtract(PointF a, PointF b){
        PointF pointN = new PointF();
        pointN.x = a.x-b.x;
        pointN.y = a.y-b.y;
        return pointN;
    }

    public static PointF Div(PointF p, float a){
        PointF pointN = new PointF();
        pointN.x = p.x/a;
        pointN.y = p.y/a;
        return pointN;
    }
}
