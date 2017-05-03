package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by Mauricio on 23/03/2017.
 */

public class PantallaCargando implements Screen {
    // Animación cargando
    private static final float TIEMPO_ENTRE_FRAMES = 0.05f;
    private Sprite spriteCargando;
    private float timerAnimacion = TIEMPO_ENTRE_FRAMES;


    private static final float ANCHO = 1280;
    private static final float ALTO = 800;

    private OrthographicCamera camara = new OrthographicCamera(ANCHO,ALTO);
//AA

    // AssetManager
    private AssetManager manager;

    private SelfDestruction selfDestruction;
    private int avance = 0; // % de carga
    private Texto texto;

    private Texture texturaCargando;
    private SpriteBatch batch = new SpriteBatch();

    public PantallaCargando(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }



    @Override
    public void show() {
        texturaCargando = new Texture(Gdx.files.internal("cargando.png"));
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth()/2,ALTO/2-spriteCargando.getHeight()/2);
        texto = new Texto("Fonts/fontLoading.fnt");
        camara.position.set(ANCHO/2, ALTO/2, 0);
        camara.update();

    }


    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCargando.draw(batch);
        texto.mostrarMensaje(batch,avance+" %",ANCHO/2+20,ALTO/2+110);
        batch.end();

        //Actualizar
        if(avance>100){
            selfDestruction.setScreen(new PantallaMenu(selfDestruction));
        }

        actualizar();

    }

    private void actualizar() {
            avance +=1;
            spriteCargando.rotate(-30);

    }

    private void borrarPantalla(int r, int g, int b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        texturaCargando.dispose();


    }
}
