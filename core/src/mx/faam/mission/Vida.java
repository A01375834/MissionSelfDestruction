package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Mauricio on 24/03/2017.
 */

public class Vida {
    float vida= 1;
    private int ALTURA = 800;
    private  int ANCHO = 1280;

    Texture textura;
    Texture texturaVida;

    public Vida(){
        if(textura==null && texturaVida ==null) {
            textura = new Texture("barraNueva.png");
            texturaVida = new Texture("vida.png");
        }


    }

    public void actualizarVida(Batch batch,float vida) {
        batch.draw(textura,154,ALTURA-100,ANCHO-600*vida,10 );
        batch.draw(texturaVida,154,ALTURA-60);

    }


    public void curar(float curacion){
        vida += curacion;
        if(vida>=100)
            vida = 100;
    }

    public float getVida(){
        return vida;
    }

    public void setVida(float vida){
        this.vida = vida;
    }
}
