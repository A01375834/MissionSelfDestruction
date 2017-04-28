package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.awt.Graphics;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Mauricio on 24/03/2017.
 */

public class Vida {
    private int vida = 100;
    private int ALTURA = 800;
    private  int ANCHO = 1280;

    private Texture textura;
    private Texture texturaVida;
    private Texture texturaMedKit;

    //Colisiones
    ColliderRect rect;

    public Vida(){
        if(textura==null && texturaVida ==null) {
            textura = new Texture("vidaCuadro.png");
            texturaVida = new Texture("vida.png");

        }
    }

    public void medKit(SpriteBatch batch,int x, int y){
        if(texturaMedKit==null){
            texturaMedKit = new Texture(Gdx.files.internal("medkit grande.png"));
        }
        rect = new ColliderRect(x,y,128,128);
        batch.draw(texturaMedKit,x,y);
    }


    public Texture textura(){
        return texturaMedKit;
    }

    public void actualizarVida(Batch batch,float vida) {
        batch.draw(textura,154,ALTURA-100,8*vida,10 );
        batch.draw(texturaVida,154,ALTURA-60);
        batch.setColor(Color.WHITE);


    }

    public int getVida(){
        return vida;
    }

    public void setVida(int vida){
        this.vida = vida;
    }
    public ColliderRect getCollisionRect(){
        return rect;
    }

    public void colorVida(Batch batch) {
        if (vida > 60)
            batch.setColor(Color.GREEN);
        else if (vida > 20)
            batch.setColor(Color.ORANGE);
        else
            batch.setColor(Color.RED);
    }

    public void herir(int herida){
       vida -= herida;

     }

}
