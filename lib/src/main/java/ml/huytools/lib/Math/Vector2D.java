package ml.huytools.lib.Math;

public class Vector2D implements Cloneable {
    public float x;
    public float y;

    public Vector2D(){
        x = 0.0f;
        y = 0.0f;
    }

    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D p){
        Vector2D v = new Vector2D();
        v.x = x + p.x;
        v.y = y + p.y;
        return v;
    }


    public Vector2D sub(Vector2D p){
        Vector2D v = new Vector2D();
        v.x = x - p.x;
        v.y = y - p.y;
        return v;
    }

    public float mul(Vector2D p){
        return x*p.x + y*p.y;
    }

    public Vector2D mul(float a){
        Vector2D v = new Vector2D();
        v.x = x*a;
        v.y = y*a;
        return v;
    }

    public Vector2D div(float a){
        Vector2D v = new Vector2D();
        v.x = x/a;
        v.y = y/a;
        return v;
    }

    public float length(){
        return (float) Math.sqrt(x*x+y*y);
    }

    public Vector2D normalize(){
        return div(length());
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}
