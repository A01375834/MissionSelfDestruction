package mx.itesm.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by angel.
 */
public class Enemigo {
    //Velocidad Enemigo
    public static int vx;
    float x, y;
    private Sprite sprite;
    private int vidas;
    int ANCHO_T, ALTO_T;

    //animacion
    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame
    public TipoEnemigo tipoEnemigo;

    //Collider
    ColliderRect rect;
    public boolean remove = false;

    public Enemigo() {

    }

    public Enemigo(Texture textura, float x, float y, int vidas, int vx,Batch batch, int ANCHO_T , int ALTO_T) {
        this.x = x;
        this.y = y;
        this.vidas = vidas;
        this.vx = vx;
        this.ANCHO_T = ANCHO_T;
        this.ALTO_T = ALTO_T;

        TextureRegion texturaEnemigo = new TextureRegion(textura);

        tipoEnemigo = TipoEnemigo.PRIMERENEMIGO;
        //int random = MathUtils.random(1,3);

        sprite = new Sprite(texturaEnemigo);


        TextureRegion[][] texturaPersonaje = texturaEnemigo.split(ANCHO_T, ALTO_T);
        animacion = new Animation(0.15f, texturaPersonaje[0][3], texturaPersonaje[0][2], texturaPersonaje[0][1]);
        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite cuando para el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // quieto

        rect = new ColliderRect(x, y, 288, 128);
    }

    public Enemigo(Texture textura, float x, float y, int vidas,Batch batch, int ANCHO_T , int ALTO_T) {
        this.x = x;
        this.y = y;
        this.vidas = vidas;
        this.vx = -200;
        this.ANCHO_T = ANCHO_T;
        this.ALTO_T = ALTO_T;

        TextureRegion texturaEnemigo = new TextureRegion(textura);

        //int random = MathUtils.random(1,3);

        sprite = new Sprite(texturaEnemigo);


        TextureRegion[][] texturaPersonaje = texturaEnemigo.split(ANCHO_T, ALTO_T);
        animacion = new Animation(0.15f, texturaPersonaje[0][1], texturaPersonaje[0][2], texturaPersonaje[0][3],
                texturaPersonaje[0][4],texturaPersonaje[0][5],texturaPersonaje[0][6],texturaPersonaje[0][7]);
        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite cuando para el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // quieto

        rect = new ColliderRect(x, y, 128, 320);

        tipoEnemigo =TipoEnemigo.SEGUNDOENEMIGO;
    }

    public void FinalBoss(Texture textura, float x, float y, int vidas,Batch batch, int ANCHO_T , int ALTO_T){
        this.x = x;
        this.y = y;
        this.vidas = vidas;
        this.ANCHO_T = ANCHO_T;
        this.ALTO_T = ALTO_T;

        TextureRegion texturaEnemigo = new TextureRegion(textura);

        sprite = new Sprite(texturaEnemigo);

        TextureRegion[][] texturaPersonaje = texturaEnemigo.split(ANCHO_T, ALTO_T);
        animacion = new Animation(0.15f, texturaPersonaje[0][1], texturaPersonaje[0][2], texturaPersonaje[0][3],
                texturaPersonaje[0][4],texturaPersonaje[0][5],texturaPersonaje[0][6],texturaPersonaje[0][7]);
        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite cuando para el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // quieto

        rect = new ColliderRect(x, y, 128, 320);

        tipoEnemigo =TipoEnemigo.FINALBOSS;

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

    public void setANCHO(int ANCHO_T){ this.ANCHO_T = ANCHO_T;}

    public void setALTO(int ALTO_T){this.ALTO_T = ALTO_T;}

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

    public void herir(float delta,Batch batch){
        //delta -= 1;
        //while(delta>0){
        batch.setColor(Color.RED);
        //}
    }

    public enum TipoEnemigo{
        PRIMERENEMIGO,
        SEGUNDOENEMIGO,
        TERCERENEMIGO,
        FINALBOSS
    }

}