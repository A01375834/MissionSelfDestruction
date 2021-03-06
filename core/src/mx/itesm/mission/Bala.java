package mx.itesm.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Mauricio on 23/03/2017.
 */

public class Bala {

    public  final int vx = 1000;
    public  final int vxIzq = -1000;
    public static final float ANCHO = 1280;
    public static final float ALTO = 800;

    private Texture texturaBala;
    float x, y;
    public boolean remove = false;
    //Colisiones
    private ColliderRect rect;

    //Sonido de Bala
    private Sound balazo;
    private boolean sonidoBala;
    private Sprite bala;
    private boolean izq;


    public Bala(float x, float y,String textura, Boolean bool) {
        this.x = x;
        this.y = y;
        rect = new ColliderRect(x, y, 23, 14);
        izq = bool;

        if (texturaBala == null)
            texturaBala = new Texture(textura);
        bala = new Sprite(texturaBala);
        if (balazo == null)
            balazo = Gdx.audio.newSound(Gdx.files.internal("balazo.wav"));
    }

    public void actualizarBala(float deltaTime, OrthographicCamera camara) {
        if(izq){
            x += vxIzq * deltaTime;
        }else{
            x += vx * deltaTime;
        }
        if (x > camara.position.x + ANCHO/2 || x < camara.position.x - ANCHO/2)
            remove = true;
        if (sonidoBala == false) {
            if (PantallaPausa.musicaOn) {
                balazo.play();
                sonidoBala = true;
            }

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

    public void balaFinalBoss(){

    }

}

