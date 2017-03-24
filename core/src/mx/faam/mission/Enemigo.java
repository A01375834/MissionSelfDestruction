package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by angel on 23/03/2017.
 */
//aa

public class Enemigo extends Objeto {
    private final float VELOCIDAD_X = 6;      // Velocidad horizontal

    private Animation<TextureRegion> spriteDerecha, spriteIzquierda;         // Animación caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación

    private Estado estadoMovimiento = Estado.QUIETO;


    public Enemigo(Texture TexturaDerecha, Texture TexturaIzquierda, float x, float y) {
        TextureRegion TexturaDerecho = new TextureRegion(TexturaDerecha);
        TextureRegion TexturaIzquierdo = new TextureRegion(TexturaIzquierda);
        TextureRegion[][] texturaEnemigoDerecha = TexturaDerecho.split(64 + 64, 64 + 64 + 64 + 64);
        TextureRegion[][] texturaEnemigoIzquierda = TexturaIzquierdo.split(64 + 64, 64 + 64 + 64 + 64);


        spriteDerecha = new Animation(0.15f, texturaEnemigoDerecha[0][4], texturaEnemigoDerecha[0][3],
                texturaEnemigoDerecha[0][2], texturaEnemigoDerecha[0][1]);
        spriteIzquierda = new Animation(0.15f, texturaEnemigoIzquierda[0][4], texturaEnemigoIzquierda[0][3],
                texturaEnemigoIzquierda[0][2], texturaEnemigoIzquierda[0][1]);

        spriteDerecha.setPlayMode(Animation.PlayMode.LOOP);
        spriteIzquierda.setPlayMode(Animation.PlayMode.LOOP);

        timerAnimacion = 0;
        sprite = new Sprite(texturaEnemigoDerecha[0][0]);    // QUIETO
        sprite.setPosition(x, y);    // Posición inicial

    }

    public void dibujar(SpriteBatch batch) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion region = spriteDerecha.getKeyFrame(timerAnimacion);
                if (estadoMovimiento == Estado.MOV_IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true, false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true, false);

                    }
                }
                batch.draw(region, sprite.getX(), sprite.getY());
                break;
            case QUIETO:
                sprite.draw(batch);// Dibuja el sprite estático
                break;
        }
    }

    // Accesor de estadoMovimiento
    public Enemigo.Estado getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(Enemigo.Estado estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }


   public enum Estado {
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA,
        ATACANDO,
        MUERTO
    }


}
