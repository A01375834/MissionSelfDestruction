package mx.faam.mission;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by angel on 13/02/2017.
 */

public class Boton {
    protected Sprite sprite;

    //crea el boton con su textura y en la posicion x, y
    public Boton(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x,y);
    }
}
