package mx.faam.mission;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by angel on 14/03/2017.
 */

public class Objeto {
    protected Sprite sprite;    // Imagen

    public Objeto(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }

    public Objeto(){

    }
    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean contains(Vector3 v) {
        float x = v.x;
        float y = v.y;

        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }
}


