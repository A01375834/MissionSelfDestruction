package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Mauricio on 24/03/2017.
 */

public class Vida {
    float vida = 100;
    private int ALTURA = 800;
    private  int ANCHO = 1280;

    Texture textura;
    Sprite spriteVida;
    public Vida(){
        textura = new Texture("barraDeVidaVerde.png");
        spriteVida = new Sprite(textura);
    }

    public void actualizarVida() {
        spriteVida.setSize(500,50);
        spriteVida.setPosition(150,ALTURA-100);
    }

    public void render(SpriteBatch batch) {
        spriteVida.draw(batch);
    }

    public void Herir(float dano){
        vida -= dano;
    }

    public void curar(float curacion){
        vida += curacion;
        if(vida>=100)
            vida = 100;
    }

    public float GetVida(){
        return vida;
    }
}
