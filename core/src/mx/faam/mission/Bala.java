package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mauricio on 23/03/2017.
 */

public class Bala {

    public static final int vx = 1000;
    private Texture texturaBala;
    float x, y;
    public boolean remove = false;

    public Bala(float x, float y) {
        this.x = x;
        this.y = y;

        if (texturaBala == null)
            texturaBala = new Texture("bala.png");
    }

    public void actualizarBala(float deltaTime) {
        x += vx * deltaTime;
        if (x > Gdx.graphics.getWidth())
            remove = true;

    }

    public void render(SpriteBatch batch) {
        batch.draw(texturaBala, x, y);

    }
}

