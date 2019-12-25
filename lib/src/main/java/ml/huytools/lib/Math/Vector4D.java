package ml.huytools.lib.Math;

public class Vector4D {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4D(){
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }

    /**
     *
     * @param x in color 'r'
     * @param y in color 'y'
     * @param z in color 'z'
     * @param w in color 'a'
     */
    public Vector4D(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4D add(Vector4D p){
        Vector4D v = new Vector4D();
        v.x = x + p.x;
        v.y = y + p.y;
        v.z = z + p.z;
        v.w = w + p.w;
        return v;
    }


    public Vector4D sub(Vector4D p){
        Vector4D v = new Vector4D();
        v.x = x - p.x;
        v.y = y - p.y;
        v.z = z - p.z;
        v.w = w - p.w;
        return v;
    }

    public float mul(Vector4D p){
        return x*p.x + y*p.y + z*p.z + w*p.w;
    }

    public Vector4D mul(float a){
        Vector4D v = new Vector4D();
        v.x = x*a;
        v.y = y*a;
        v.z = z*a;
        v.w = w*a;
        return v;
    }

    public Vector4D div(float a){
        Vector4D v = new Vector4D();
        v.x = x/a;
        v.y = y/a;
        v.z = z/a;
        v.w = w/a;
        return v;
    }

    public float length(){
        return (float) Math.sqrt(x*x+y*y+z*z+w*w);
    }

    public Vector4D normalize(){
        return div(length());
    }

    public String toString(){
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}
