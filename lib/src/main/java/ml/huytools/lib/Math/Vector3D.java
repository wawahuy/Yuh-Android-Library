package ml.huytools.lib.Math;

public class Vector3D<T extends Number> {
    public float x;
    public float y;
    public float z;

    public Vector3D(){
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vector3D(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D add(Vector3D p){
        Vector3D v = new Vector3D();
        v.x = x + p.x;
        v.y = y + p.y;
        v.z = z + p.z;
        return v;
    }


    public Vector3D sub(Vector3D p){
        Vector3D v = new Vector3D();
        v.x = x - p.x;
        v.y = y - p.y;
        v.z = z - p.z;
        return v;
    }

    public float mul(Vector3D p){
        return x*p.x + y*p.y + z*p.z;
    }

    public Vector3D mul(float a){
        Vector3D v = new Vector3D();
        v.x = x*a;
        v.y = y*a;
        v.z = z*a;
        return v;
    }

    public Vector3D div(float a){
        Vector3D v = new Vector3D();
        v.x = x/a;
        v.y = y/a;
        v.z = z/a;
        return v;
    }

    public float length(){
        return (float) Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3D normalize(){
        return div(length());
    }

    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
