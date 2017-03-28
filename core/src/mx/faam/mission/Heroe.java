package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


import static mx.faam.mission.Heroe.EstadoSalto.BAJANDO;
import static mx.faam.mission.Heroe.EstadoSalto.SUBIENDO;

/**
 * Created by angel on 14/03/2017.
 */

public class Heroe extends Objeto {
    private final float VELOCIDAD_X =12;      // Velocidad horizontal
    private Sprite spriteBala;

    private float vida = 100;

    private Animation<TextureRegion> spriteAnimado,spriteDisparando,spriteIzquierda;         // Animacion caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animacion

    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;
    // Salto
    private EstadoSalto estadoSalto = EstadoSalto.EN_PISO;
    private float alturaSalto;  // altura actual, inicia en cero
    private float yOriginal;
    int collisionX,collisionY;
    ColliderRect rect;

    // Recibe una imagen con varios frames

    public Heroe(Texture textura, Texture texturaDisparo, Texture texturaIzq, float x, float y) {
        // Lee la textura como region
        TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion texturaDisparando = new TextureRegion(texturaDisparo);
        TextureRegion textureComIzq = new TextureRegion(texturaIzq);
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(64+64,64+64+64+64);
        TextureRegion[][] texturaPersonajeDisparando = texturaDisparando.split(205,256);
        TextureRegion[][] texturaIzquierda = textureComIzq.split(64+64,64+64+64+64);

        // Crea la animacion con tiempo de 0.15 segundos entre frames.


        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][4], texturaPersonaje[0][3], texturaPersonaje[0][2],texturaPersonaje[0][1] );
        spriteDisparando = new Animation(0.15f,texturaPersonajeDisparando[0][0]);
        spriteIzquierda = new Animation(0.15f, texturaIzquierda[0][4], texturaIzquierda[0][3], texturaIzquierda[0][2],texturaIzquierda[0][1] );
        // AnimaciÃ³n infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        spriteDisparando.setPlayMode(Animation.PlayMode.LOOP);
        spriteIzquierda.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contara tiempo para saber que frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posicion inicial

        rect = new ColliderRect(x,y,64,128);
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

        /*if(estadoMovimiento == EstadoMovimiento.MOV_DERECHA && estadoSalto == EstadoSalto.EN_PISO){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regio = spriteIzquierda.getKeyFrame(timerAnimacion);
            sprite.setRegion(regio);
        }
        if(estadoMovimiento == EstadoMovimiento.MOV_IZQUIERDA && estadoSalto == EstadoSalto.EN_PISO){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
            sprite.setRegion(region);
        }
        if(estadoMovimiento == EstadoMovimiento.DISPARANDO && estadoSalto == EstadoSalto.EN_PISO){
            TextureRegion regi = spriteDisparando.getKeyFrame(timerAnimacion);
            sprite.setRegion(regi);
        }
        sprite.draw(batch);*/
        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujarÃ¡
                TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
                if (estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);

                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case QUIETO:
            case INICIANDO:
                sprite.draw(batch);// Dibuja el sprite estÃ¡tico
                break;
            case DISPARANDO:
                TextureRegion regi = spriteDisparando.getKeyFrame(timerAnimacion);
                batch.draw(regi,sprite.getX(),sprite.getY());
                rect.setAnchoYAlto(160,256);
                break;
        }
    }



    // Actualiza el sprite, de acuerdo al estadoMovimiento y estadoSalto
    public void actualizar(TiledMap mapa) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
                moverHorizontal(mapa);
                break;
            case MOV_IZQUIERDA:
                moverHorizontal(mapa);
                break;
        }
        switch (estadoSalto) {
            case SUBIENDO:
            case BAJANDO:
            default:
                moverVertical(mapa);
                break;
        }

    }
    // Realiza el salto
    private void moverVertical(TiledMap mapa) {
        float delta = Gdx.graphics.getDeltaTime()*450;
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get("pisos");
        int celdaX = (int) ((sprite.getX()+sprite.getWidth()/2) / capa.getTileWidth());
        int celdaY = (int) ((sprite.getY()-delta)/ capa.getTileHeight());
        TiledMapTileLayer.Cell cell = capa.getCell(celdaX, celdaY);
        switch (estadoSalto) {
            case SUBIENDO:
                sprite.setY(sprite.getY()+delta);
                alturaSalto += delta;
                if (alturaSalto>=sprite.getHeight()/2+10) {
                    estadoSalto = BAJANDO;
                }
                break;
            case BAJANDO:
                sprite.setY(sprite.getY()-delta);
                if (cell!=null) {
                    estadoSalto = EstadoSalto.EN_PISO;
                    alturaSalto = 0;
                    sprite.setY((celdaY+1)*capa.getTileHeight());
                }
                break;
            case EN_PISO:
                Gdx.app.log("Salto: ", "En piso");
                if (cell==null) {
                    estadoSalto = EstadoSalto.BAJANDO;
                }
                break;
        }

    }


    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverHorizontal(TiledMap mapa) {
        // Obtiene la primer capa del mapa (en este caso es la Ãºnica)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(0);
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // Â¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 32) / 32);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) (sprite.getY() / 32);

            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
            // Ejecutar movimiento horizontal
            nuevaX += VELOCIDAD_X;
            // Prueba que no salga del mundo por la derecha
            if (nuevaX <= capa.getWidth()*32 - sprite.getWidth()) {
                sprite.setX(nuevaX);
            }
            /*if ( celdaDerecha==null) {

            }*/
        }
        // Â¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
            int xIzq = (int) ((sprite.getX()) / 32);
            int y = (int) (sprite.getY() / 32);

            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            nuevaX -= VELOCIDAD_X;
            if (nuevaX >= 0) {
                sprite.setX(nuevaX);
            }
            /*if ( celdaIzquierda==null) {
                // Prueba que no salga del mundo por la izquierda

            }*/
        }

    }

    // Accesor de estadoMovimiento
    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }
    // Inicia el salto
    public void saltar() {

        if (estadoSalto!= SUBIENDO && estadoSalto!= BAJANDO) {
            // inicia
            estadoSalto = SUBIENDO;
            yOriginal = sprite.getY();
            alturaSalto = 0;
        }
    }

    public float getX(){
        return sprite.getX();
    }

    public float getY(){
        return sprite.getY();
    }

    public enum EstadoMovimiento {
        INICIANDO,
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA,
        DISPARANDO
    }
    public enum EstadoSalto {
        SUBIENDO,
        BAJANDO,
        EN_PISO
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
    public Sprite getSprite(){
        return sprite;
    }
    public ColliderRect getColliderRect(){
        return rect;
    }
    public void actualizarRect(float x, float y){
        rect.mover(x,y);
    }
}



