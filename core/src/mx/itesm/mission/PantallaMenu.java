package mx.itesm.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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

//Pantalla menu
/**
 * Created by angel on 11/02/2017.
 */
public class PantallaMenu implements Screen {
    //tamaño de pantallas
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestruction selfDestruction;

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //texturas boton
    private Texture TexturaBotonNuevoJuego;
    private Texture TexturaBotonCargarJuego;
    private Texture TexturaBotonOpciones;
    private Texture TexturaBotonHistoria;
    private Texture TexturaBotonCreditos;
    private Texture TexturaBotonInstrucciones;

    //LOgo
    private Texture TexturaLogoJuego;

    //textura fondo
    private Texture TexturaFondoMenu;


    //escena 
    private Stage escena;
    private SpriteBatch batch;

    //Musica
    private static Music musicaFondoMenu;



    public PantallaMenu(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }

    @Override
    public void show() {
        //Al entrar al juego haré...
        crearCamara();
        crearTexturas();
        crearObjetos();
        Gdx.input.setCatchBackKey(false);



    }

    private void crearTexturas() {
        //Textura botones
        TexturaBotonNuevoJuego = new Texture("start grande.png");
        TexturaBotonCargarJuego= new Texture("load button grande.png");
        TexturaBotonOpciones = new Texture("options grande.png");
        TexturaBotonHistoria = new Texture("story grande.png");
        TexturaBotonCreditos = new Texture("credits grande.png");
        TexturaBotonInstrucciones = new Texture("instruction.png");

        //Logo
        TexturaLogoJuego = new Texture("logo pequeño.png");

        //textura fondp
        TexturaFondoMenu = new Texture("fondo 2.png");

        //Audio
        AssetManager manager = new AssetManager();
        manager.load("MusicaFondoMenu.mp3", Music.class);
        manager.finishLoading();
        musicaFondoMenu = manager.get("MusicaFondoMenu.mp3");

    }

    private void crearObjetos() {
        //fondo
        batch = new SpriteBatch();
        escena = new Stage(vista, batch);
        Image fondoMenu = new Image (TexturaFondoMenu);
        escena.addActor(fondoMenu);


        musicaFondoMenu.setLooping(true);
        musicaFondoMenu.play();



        //botonNuevoJuego
        TextureRegionDrawable trdBtnNj = new TextureRegionDrawable(new TextureRegion(TexturaBotonNuevoJuego));
        ImageButton btnNj = new ImageButton(trdBtnNj);
        btnNj.setPosition(ANCHO/2+155, 2*ALTO/5+250);
        escena.addActor(btnNj);

        //Evento del boton
        btnNj.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestruction.setScreen(new PantallaCargandoMapa(selfDestruction));
                //Musica de fondo se para
                musicaFondoMenu.stop();
            }
        });


        //botonCargarJuego




        //botonOpciones
        TextureRegionDrawable trdBtnCargar = new TextureRegionDrawable(new TextureRegion(TexturaBotonInstrucciones));
        ImageButton btnCargar = new ImageButton(trdBtnCargar);
        btnCargar.setPosition(ANCHO/2+155, 2*ALTO/5+100);
        escena.addActor(btnCargar);

        //Evento del boton
        btnCargar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click Opciones");
                selfDestruction.setScreen(new PantallaOpciones(selfDestruction));
                musicaFondoMenu.stop();
                //musicaFondoMenu.play();
            }
        });
        //botonHistoria
        TextureRegionDrawable trdBtnHistoria = new TextureRegionDrawable(new TextureRegion(TexturaBotonHistoria));
        ImageButton btnHistoria = new ImageButton(trdBtnHistoria);
        btnHistoria.setPosition(ANCHO/2+155, 2*ALTO/5-50);
        escena.addActor(btnHistoria);

        //Evento del boton
        btnHistoria.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestruction.setScreen(new mx.itesm.mission.PantallaHistoria(selfDestruction));
                musicaFondoMenu.stop();
                //musicaFondoMenu.play();
            }
        });

        //BotonCreditos
        TextureRegionDrawable trdBtnCreditos = new TextureRegionDrawable(new TextureRegion(TexturaBotonCreditos));
        ImageButton btnCreditos = new ImageButton(trdBtnCreditos);
        btnCreditos.setPosition(ANCHO/2+155,2*ALTO/5-200);
        escena.addActor(btnCreditos);

        //Evento del boton
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestruction.setScreen(new PantallaCreditos(selfDestruction));
                musicaFondoMenu.stop();
                //musicaFondoMenu.play();
            }
        });

        //Logo
        TextureRegionDrawable trdLogoJuego = new TextureRegionDrawable((new TextureRegion(TexturaLogoJuego)));
        Image LogoJuego = new Image(trdLogoJuego);
        LogoJuego.setPosition(ANCHO/2-500, ALTO/2);
        escena.addActor(LogoJuego);

        Gdx.input.setCatchBackKey(false);
        Gdx.input.setInputProcessor(escena);
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
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
           selfDestruction.setScreen(new PantallaMenu(selfDestruction));
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
    public static Music getMusica(){
        return musicaFondoMenu;
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
        TexturaFondoMenu.dispose();
        TexturaBotonNuevoJuego.dispose();
        TexturaBotonCargarJuego.dispose();
        TexturaBotonOpciones.dispose();
        TexturaBotonHistoria.dispose();
        TexturaBotonCreditos.dispose();
    }
}
