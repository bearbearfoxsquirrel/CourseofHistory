package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

/**
 * Created by Jordan on 06/03/2017.
 */

public class Vector
{
    private static final Vector Zero = new Vector(0, 0);

    private float x;
    private float y;

    public Vector()
    {
    }

    public Vector(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

   public Vector(Vector other)
   {
       this.x = other.x;
       this.y = other.y;
   }

   public boolean isZero()
   {
       return x == 0.0f && y == 0.0f;
   }

   public float length()
   {
       return (float) Math.sqrt((x * x) + (y * y));
   }

   public float lengthSquared()
   {
       return (x * x) + (y * y);
   }

   public void set(float x, float y)
   {
       this.x = x;
       this.y = y;
   }

   public void set(Vector other)
   {
       this.x = other.x;
       this.y = other.y;
   }

   public void add(float x, float y)
   {
       this.x += x;
       this.y += y;
   }

   public void add(Vector other)
   {
       this.x += other.x;
       this.y += other.y;
   }

   public void subtract(float x, float y)
   {
       this.x -= x;
       this.y -= y;
   }

   public void subtract(Vector other)
   {
       this.x -= other.x;
       this.y -= other.y;
   }

   public void multiply(float scalar)
   {
       x *= scalar;
       y *= scalar;
   }

   public void divide(float scalar)
   {
       x /= scalar;
       y /= scalar;
   }

   public void normalise()
   {
       float length = length();
       if(length != 0)
       {
           x /= length;
           y /= length;
       }
   }

    public static Vector getZero() {
        return Zero;
    }

    public float getVectorX() {
        return x;
    }

    public void setVectorX(float x) {
        this.x = x;
    }

    public float getVectorY() {
        return y;
    }

    public void setVectorY(float y) {
        this.y = y;
    }
}
