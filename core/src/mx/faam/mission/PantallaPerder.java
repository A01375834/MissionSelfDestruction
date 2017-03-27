package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Mauricio on 26/03/2017.
 */

public class PantallaPerder implements Screen {
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestruction selfDestruction;

    //Estado Juego
   // private EstadoJuego estado = EstadoJuego.PAUSADO;

    //Musica Fondo Menu
   Sound musicaFondo = Gdx.audio.newSound(Gdx.files.internal("dead.mp3"));
    Sound musicaRisa = Gdx.audio.newSound(Gdx.files.internal("risaOk.wav"));


    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    //Texturas boton
    private Texture TexturaBotonBackMenu;

    //fondo
    private Texture texturaPerdiste;
    private boolean musicaTocando = false;
    private boolean musicaTocandoRisa = true;

    public PantallaPerder(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }


    @Override
    public void show() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
        //fondo
        batch = new SpriteBatch();
        escena = new Stage(vista, batch);
        texturaPerdiste = new Texture("DEAD.png");
        Image fondoPerdiste = new Image (texturaPerdiste);
        escena.addActor(fondoPerdiste);

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escena.draw();
        if(musicaTocando==false) {
            musicaFondo.play(1.0f);
            musicaTocando = true;
            //musicaTocandoRisa = false;
            musicaRisa.loop(1.0f);
        }



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

    }

    @Override
    public void dispose() {
        //escena.dispose();
        texturaPerdiste.dispose();
        musicaFondo.dispose();
        musicaRisa.dispose();

    }
}