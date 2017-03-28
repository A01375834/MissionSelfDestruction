package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mauricio on 27/03/2017.
 */

public class MedKit {

    float vida = 1;
    private int ALTURA = 800;
    private int ANCHO = 1280;
    int x, y;
    Texture texturaMedKit;

    //Colisiones
    ColliderRect rect;


    public MedKit(SpriteBatch batch, int x, int y) {
        if (texturaMedKit == null) {
            texturaMedKit = new Texture(Gdx.files.internal("medkit grande.png"));
        }
        this.x = x;
        this.y = y;
        rect = new ColliderRect(x, y, 128, 128);

    }

    public void ActualizarMedKit(SpriteBatch batch) {
        batch.draw(texturaMedKit, x, y);
    }

    public ColliderRect getCollisionRect() {
        return rect;
    }

}
