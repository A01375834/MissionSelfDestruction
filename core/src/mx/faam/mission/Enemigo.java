package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

/**
 * Created by angel.
 */
public class Enemigo {
    //Velocidad Enemigo
    public static int vx;
    float x, y;
    private Sprite sprite;
    private int vidas;


    //animacion
    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame

    //Collider
    ColliderRect rect;
    public boolean remove = false;


    public Enemigo(Texture textura, float x, float y, int vidas, int vx) {
        this.x = x;
        this.y = y;
        this.vidas = vidas;
        this.vx = vx;
        TextureRegion texturaEnemigo = new TextureRegion(textura);
        //int random = MathUtils.random(1,3);

        sprite = new Sprite(texturaEnemigo);

        TextureRegion[][] texturaPersonaje = texturaEnemigo.split(288, 128);
        animacion = new Animation(0.15f, texturaPersonaje[0][3], texturaPersonaje[0][2], texturaPersonaje[0][1]);
        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite cuando para el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // quieto

        rect = new ColliderRect(x, y, 288, 128);

    }

    public void actualizar(float deltaTime, float xE) {
        x += vx * deltaTime;
        //xE Enemigo
        if (x > xE + 1280)
            remove = true;

        rect.mover(x, y);

    }

    public void render(SpriteBatch batch) {

        timerAnimacion += Gdx.graphics.getDeltaTime();
        // Obtiene el frame que se debe mostrar (de acuerdo al timer)
        TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
        batch.draw(region, x, y);
    }

    public void setPosicion(SpriteBatch batch, float x, float y) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public ColliderRect getColliderRect() {
        return rect;
    }

}