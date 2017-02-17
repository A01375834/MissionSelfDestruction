package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
 * Created by angel on 13/02/2017.
 */
public class PantallaJuego implements Screen {

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestruction selfDestruction;


    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    //Textura fondo
    private Texture TexturaFondoJuego;

    //Textura Boton back
    private Texture TexturaBotonBackMenu;

    //Textura Oberon
    private Texture TexturaOberon;

    //Textura puerta
    private Texture TexturaPuerta;

    //Textura Pausa
    private Texture TexturaPausa;




    public PantallaJuego(SelfDestruction selfDestruction) {
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
        Image fondoJuego = new Image(TexturaFondoJuego);
        escena.addActor(fondoJuego);

        //botonBack
        TextureRegionDrawable trdBtnBm = new TextureRegionDrawable(new TextureRegion(TexturaBotonBackMenu));
        ImageButton btnBm = new ImageButton(trdBtnBm);
        btnBm.setPosition(0,0);
        escena.addActor(btnBm);

        //Evento del boton
        btnBm.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestruction.setScreen(new PantallaMenu(selfDestruction));
            }
        });

        //Oberon

        TextureRegionDrawable oberon = new TextureRegionDrawable(new TextureRegion(TexturaOberon));
        ImageButton obe = new ImageButton(oberon);
        obe.setPosition(ANCHO/2,0);
        escena.addActor(obe);

        //Textura puerta

        TextureRegionDrawable puerta = new TextureRegionDrawable(new TextureRegion(TexturaPuerta));
        ImageButton puer = new ImageButton(puerta);
        puer.setPosition(ANCHO/2+335,0);
        escena.addActor(puer);

        //Textura pausa

        TextureRegionDrawable pausa = new TextureRegionDrawable(new TextureRegion(TexturaPausa));
        ImageButton pau = new ImageButton(pausa);
        pau.setPosition(0,ALTO-pau.getHeight());
        escena.addActor(pau);


        //Evento del boton
        pau.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Pausa");
            }
        });





        Gdx.input.setInputProcessor(escena);


    }

    private void crearTexturas() {
        //textura fondp
        TexturaFondoJuego = new Texture("fondoJuego.png");
        TexturaBotonBackMenu = new Texture("botonBack.png");
        TexturaOberon = new Texture("oberon.png");
        TexturaPuerta = new Texture("puerta.png");
        TexturaPausa = new Texture("pausa.png");
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
        TexturaOberon.dispose();
        TexturaPuerta.dispose();
        TexturaBotonBackMenu.dispose();
    }
}
