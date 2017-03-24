package mx.faam.mission;

/**
 * Created by Mauricio on 24/03/2017.
 */

public class ColliderRect {
    float x, y;
    int ANCHO,ALTO;

    public ColliderRect(float x,float y, int ANCHO,int ALTO){
        this.x = x;
        this.y = y;
        this.ALTO = ALTO;
        this.ANCHO = ANCHO;
    }

    public void mover(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean Choca(ColliderRect rect){
        return x < rect.x + rect.ANCHO && y < rect.y + rect.ALTO && x + ANCHO > rect.x && y + ALTO > rect.y;
    }
}
