package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Mauricio on 23/03/2017.
 */

public class PantallaCargando implements Screen {

    private float tiempoVisible = 1.2f;

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestruction selfDestruction;


    //logo Tec
    private Texture texturaLogo;
    private Sprite spriteLogo;

    private OrthographicCamera camara = new OrthographicCamera(ANCHO,ALTO);
    private Viewport vista = new StretchViewport(ANCHO,ALTO,camara);

    //Escena
    private SpriteBatch batch = new SpriteBatch();


    public PantallaCargando(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }

    @Override
    public void show() {
        texturaLogo = new Texture(Gdx.files.internal("logo.png"));
        spriteLogo = new Sprite(texturaLogo);
        spriteLogo.setPosition(ANCHO/2-(spriteLogo.getWidth()/2), ALTO/2-(spriteLogo.getHeight()/2));
        escalarLogo();
    }

    private void escalarLogo() {
        float factorCamara = ANCHO/ALTO;
        float factorPantalla = 1.0f*Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float escala = factorCamara / factorPantalla;
        spriteLogo.setScale(escala,1);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);

        //batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteLogo.draw(batch);
        batch.end();

        tiempoVisible -= delta;
        if(tiempoVisible<=0){
            selfDestruction.setScreen(new PantallaMenu(selfDestruction));
        }

    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
        actualizarVista();

    }

    private void actualizarVista() {
        escalarLogo();
    }

    private void borrarPantalla(int r, float g, int b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaLogo.dispose();

    }
}
