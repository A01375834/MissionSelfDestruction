package mx.faam.mission;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by angel on 24/03/2017.
 */

public abstract class Enemigo {
    protected Sprite sprite;
    protected float xInicial, yInicial;
    private static AssetManager manager = new AssetManager();
    boolean atacando = false;
    protected boolean muerte;
    long startTime;



    public Enemigo(){

    }
    public Enemigo(Texture textura, float x, float y){
        xInicial = x;
        yInicial = y;
        sprite = new Sprite(sprite);
        sprite.setPosition(x,y);

    }
    public Enemigo(TextureRegion texture, float x, float y){
        xInicial = x;
        yInicial = y;
        sprite = new Sprite(sprite);
        sprite.setPosition(x,y);
    }
    public abstract void attack();
    public abstract void setEstado(Estado estado);
    public abstract void dibujar(SpriteBatch batch);
    public abstract void stop();
    public boolean colision(Bala b){
        return sprite.getBoundingRectangle().overlaps(b.getRectangle());
    }

    public abstract boolean muerte();
    public void setPos(float x, float y){
        sprite.setPosition(x,y);

    }
    public void setMuete(boolean muerte){
        this.muerte = muerte;
    }
    public float getX(){
        return sprite.getX();
    }
    public float getY(){
        return sprite.getY();
    }
    public void dispose(){
        manager.dispose();
    }


    public enum Estado{
        MOVIMIENTO,
        QUIETO,
        ATACAR,
        MUERTO
    }



}
