package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Mauricio on 23/03/2017.
 */

public class Bala {

    public static final int vx = 1000;
    public static final int vxIzq = -1000;
    private Texture texturaBala;
    float x, y;
    public boolean remove = false;
    //Colisiones
    private ColliderRect rect;

    //Sonido de Bala
    private Sound balazo;
    private boolean sonidoBala;
    private Sprite bala;


    public Bala(float x, float y) {
        this.x = x;
        this.y = y;
        rect = new ColliderRect(x, y, 23, 14);

        if (texturaBala == null)
            texturaBala = new Texture("bala.png");
        bala = new Sprite(texturaBala);
        if (balazo == null)
            balazo = Gdx.audio.newSound(Gdx.files.internal("balazo.wav"));
    }

    public void actualizarBala(float deltaTime, float xO) {
        x += vx * deltaTime;
        if (x > xO+1280)
            remove = true;
        if (sonidoBala == false) {
            balazo.play();
            sonidoBala = true;
        }

        rect.mover(x, y);

    }

    public void actualizarBalaIzq(float deltaTime, float xO) {
        x += vxIzq * deltaTime;
        if (x < 0)
            remove = true;
        if (sonidoBala == false) {
            balazo.play();
            sonidoBala = true;
        }

        rect.mover(x, y);

    }
    public void render(SpriteBatch batch) {
        batch.draw(texturaBala, x, y);


    }

    public ColliderRect getCollisionRect() {
        return rect;
    }

    public Rectangle getRectangle() {
        return bala.getBoundingRectangle();
    }

    public float getX() {
        return bala.getX();
    }

    public float getY() {
        return bala.getY();
    }

}

