package mx.itesm.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mauricio on 24/03/2017.
 */

public class Vida {
    //private int vida = 100;
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

        return mx.itesm.mission.Preferencias.cargarVida();
    }

    public void setVida(int vida){
        //this.vida = vida;
        mx.itesm.mission.Preferencias.guardarVidas(vida);
    }

    public ColliderRect getCollisionRect(){
        return rect;
    }

    public void colorVida(Batch batch) {
        if ( mx.itesm.mission.Preferencias.cargarVida() > 60)
            batch.setColor(Color.GREEN);
        else if (mx.itesm.mission.Preferencias.cargarVida() > 20)
            batch.setColor(Color.ORANGE);
        else
            batch.setColor(Color.RED);
    }

    public void herir(int herida){
        int vida = mx.itesm.mission.Preferencias.cargarVida();
        vida -= herida;
        mx.itesm.mission.Preferencias.guardarVidas(vida);
     }

}
