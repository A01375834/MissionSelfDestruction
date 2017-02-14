package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by angel on 13/02/2017.
 */
public class PantallaJuego implements Screen {

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestructiom selfDestructiom;


    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    //Textura fondo
    private Texture TexturaFondoJuego;



    public PantallaJuego(SelfDestructiom selfDestructiom) {
        this.selfDestructiom = selfDestructiom;
    }



    @Override
    public void show() {
        //Al entrar haré...
        crearCamara();
        crearTexturas();
        crearObjetos();
    }

    private void crearObjetos() {
        //fondo
        batch = new SpriteBatch();
        escena = new Stage(vista, batch);
        Image fondoJuego = new Image(TexturaFondoJuego);
        escena.addActor(fondoJuego);
    }

    private void crearTexturas() {
        //textura fondp
        TexturaFondoJuego = new Texture("pruebafondojuego.jpg");
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }




    @Override
    public void render(float delta) {
        borrarPantalla();
        escena.draw();
    }
    private void borrarPantalla() {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
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
        escena.dispose();
        TexturaFondoJuego.dispose();
    }
}
