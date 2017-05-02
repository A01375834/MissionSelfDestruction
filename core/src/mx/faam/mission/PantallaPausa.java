package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Mauricio on 19/03/2017.
 */

public class PantallaPausa implements Screen {
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestruction selfDestruction;
    private final PantallaJuego pantallaJuego;
    //Estado Juego
    private EstadoJuego estado = EstadoJuego.PAUSADO;

    //Musica Fondo Menu
    //Sound musicaFondo = Gdx.audio.newSound(Gdx.files.internal("MusicaFondoMenu.mp3"));


    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    //Texturas boton
    private Texture TexturaBotonBackMenu;
    private Texture TexturaBotonResume;

    //fondo
    private Texture TexturaFondoBack;
    private boolean musicaTocando = false;

    public PantallaPausa(SelfDestruction selfDestruction, PantallaJuego pantallaJuego){
        this.selfDestruction = selfDestruction;
        this.pantallaJuego = pantallaJuego;
    }


    @Override
    public void show() {
        crearCamara();
        crearTexturas();
        crearObjetos();

    }

    private void crearObjetos() {
        //fondo
        batch = new SpriteBatch();
        escena = new Stage(vista, batch);
        Image fondoCreditos = new Image(TexturaFondoBack);
        escena.addActor(fondoCreditos);

        //botonResume
        TextureRegionDrawable trdBtnResume = new TextureRegionDrawable(new TextureRegion(TexturaBotonResume));
        ImageButton btnResume = new ImageButton(trdBtnResume);
        btnResume.setPosition(ANCHO/2-300,ALTO/2);
        escena.addActor(btnResume);

        //Evento del boton
        btnResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Pausa", "Me hicieron click");
                pantallaJuego.estado = EstadoJuego.JUGANDO;
                selfDestruction.setScreen(pantallaJuego);
                //musicaFondo.stop();

            }
        });
        //botonBM
        TextureRegionDrawable trdBtnBm = new TextureRegionDrawable(new TextureRegion(TexturaBotonBackMenu));
        ImageButton btnBm = new ImageButton(trdBtnBm);
        btnBm.setPosition(ANCHO / 2-300, ALTO / 2-250);
        escena.addActor(btnBm);

        //Evento del boton
        btnBm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Pausa", "Me hicieron click");
                selfDestruction.setScreen(new PantallaMenu(selfDestruction));
                //musicaFondo.stop();
            }

        });
        Gdx.input.setInputProcessor(escena);
    }


    private void crearTexturas() {
        //Textura fondo
        TexturaFondoBack= new Texture("paused menu!.png");


        //textura boton
        TexturaBotonBackMenu = new Texture("back to menu grande.png");
        TexturaBotonResume = new Texture("Resume grande.png");
    }

    private void crearCamara() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
    }


    @Override
    public void render(float delta) {
        //borrarPantalla();
        escena.draw();
        if(musicaTocando==false) {
            //musicaFondo.loop(0.4f);
            musicaTocando = true;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            selfDestruction.setScreen(new PantallaMenu(selfDestruction));
        }

    }

    private void borrarPantalla() {
        Gdx.gl.glClearColor(0,0.6f,1,0);
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
        TexturaFondoBack.dispose();
        TexturaBotonBackMenu.dispose();
        TexturaBotonResume.dispose();

    }
}
