package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by angel on 03/03/2017.
 */

public class PantallaOpciones implements Screen {

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestruction selfDestruction;

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    private Music musicaFondo;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    //Texturas boton
    private Texture TexturaBotonBackMenu;
    private Texture BotonDisparar;
    private Texture BotonSwitch;
    private Texture Tarjeta;
    private Texture botiquin;
    private Texture balas;
    private Texture BackPad;
    private Texture FrontPad;

    //textos
    private Texto textoControles;
    private Texto textoItems;
    private Texture texturaItems;
    private Texture texturaControles;
    private Sprite spriteItems;
    private Sprite spriteControl;



    //fondo
    private Texture TexturaFondoOpciones;

    public PantallaOpciones(SelfDestruction selfDestruction){
        this.selfDestruction = selfDestruction;
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
        Image fondoHistoria = new Image(TexturaFondoOpciones);
        Image bala = new Image(balas);
        Image tarjeta = new Image(Tarjeta);
        Image medikit = new Image(botiquin);
        Image BotonD = new Image(BotonDisparar);
        Image BotonS = new Image(BotonSwitch);
        escena.addActor(fondoHistoria);
        //escena.addActor(bala);
        //escena.addActor(tarjeta);
        //escena.addActor(medikit);
        //escena.addActor(BotonD);
        //escena.addActor(BotonS);
        batch.begin();

        musicaFondo.setLooping(true);
        musicaFondo.play();

        //Texto


        //botonBackMenu
        TextureRegionDrawable trdBtnBm = new TextureRegionDrawable(new TextureRegion(TexturaBotonBackMenu));
        ImageButton btnBm = new ImageButton(trdBtnBm);
        btnBm.setPosition(0,0);
        escena.addActor(btnBm);

        TextureRegionDrawable trdBala = new TextureRegionDrawable(new TextureRegion(balas));
        Image balas = new Image(trdBala);
        balas.setPosition(32,ALTO/2+128);
        escena.addActor(balas);

        TextureRegionDrawable trdMedk = new TextureRegionDrawable(new TextureRegion(botiquin));
        Image boti = new Image(trdMedk);
        boti.setPosition(ANCHO/2-350,ALTO/2+128);
        escena.addActor(boti);

        TextureRegionDrawable trdTarj = new TextureRegionDrawable(new TextureRegion(Tarjeta));
        Image tajeta = new Image(trdTarj);
        tajeta.setPosition(ANCHO/2-440,ALTO/2+64);
        escena.addActor(tajeta);

        TextureRegionDrawable trdBotonD = new TextureRegionDrawable(new TextureRegion(BotonDisparar));
        Image disparar = new Image(trdBotonD);
        disparar.setPosition(ANCHO/2+30,ALTO/2-210);
        escena.addActor(disparar);




        TextureRegionDrawable trdBackP = new TextureRegionDrawable(new TextureRegion(BackPad));
        Image backP = new Image(trdBackP);
        backP.setPosition(ANCHO/2+200,ALTO/2-210);
        escena.addActor(backP);

        TextureRegionDrawable trdFrontP = new TextureRegionDrawable(new TextureRegion(FrontPad));
        Image frontP = new Image(trdFrontP);
        frontP.setPosition(ANCHO/2+230,ALTO/2-174);
        escena.addActor(frontP);




        batch.end();

        //AA







        //Evento del boton
        btnBm.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestruction.setScreen(new PantallaMenu(selfDestruction));
                musicaFondo.stop();
            }
        });
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(escena);
    }

    private void crearTexturas() {
        //Textura botones
        TexturaBotonBackMenu= new Texture("back to menu grande.png");
        BackPad = new Texture("base de palanca grande.png");
        FrontPad = new Texture("palanca grande.png");
        BotonDisparar = new Texture("boton disparo grande.png");
        BotonSwitch = new Texture("boton switch grande.png");
        Tarjeta = new Texture("key.png");
        botiquin = new Texture("medkit.png");
        balas= new Texture("municion.png");

        AssetManager manager = new AssetManager();
        manager.load("MusicaFondoMenu.mp3", Music.class);
        manager.finishLoading();
        musicaFondo = manager.get("MusicaFondoMenu.mp3");




        //textura fondp
        TexturaFondoOpciones = new Texture("fondo.png");

        //Texto
        textoControles = new Texto("Fonts/fontLoading.fnt");
        textoItems = new Texto("Fonts/fontLoading.fnt");
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
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        textoItems.mostrarMensaje(batch,"Collect them",ANCHO/2-330,ALTO-100);
        textoControles.mostrarMensaje(batch,"Shoot & Move",ANCHO/2+200,ALTO/2);
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            selfDestruction.setScreen(new PantallaMenu(selfDestruction));
            musicaFondo.stop();
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
        dispose();

    }

    @Override
    public void dispose() {
        escena.dispose();
        TexturaFondoOpciones.dispose();
        TexturaBotonBackMenu.dispose();
        BotonDisparar.dispose();
        BotonSwitch.dispose();
        Tarjeta.dispose();
        botiquin.dispose();
        balas.dispose();
        BackPad.dispose();
        FrontPad.dispose();

    }
}
