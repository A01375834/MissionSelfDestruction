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
    private Texture texturaBala;
    float x, y;
    public boolean remove = false;
    //Colisiones
    ColliderRect rect;

    //Sonido de Bala
    Sound balazo;
    private boolean sonidoBala;
    protected Sprite bala;

    public Bala(float x, float y) {
        this.x = x;
        this.y = y;
        rect = new ColliderRect(x,y,23,14);

        if (texturaBala == null)
            texturaBala = new Texture("bala.png");
        if(balazo == null)
            balazo = Gdx.audio.newSound(Gdx.files.internal("balazo.wav"));
    }

    public void actualizarBala(float deltaTime,float xO) {
        x += vx * deltaTime;
        if (x > xO+Gdx.graphics.getWidth())
            remove = true;
        if(sonidoBala == false) {
            balazo.play(1.0f);
            sonidoBala = true;
        }

        rect.mover(x,y);

    }

    public void render(SpriteBatch batch) {
        batch.draw(texturaBala, x, y);


    }

    public ColliderRect getCollisionRect(){
        return rect;
    }

    public Rectangle getRectangle(){
        return bala.getBoundingRectangle();
    }
}

