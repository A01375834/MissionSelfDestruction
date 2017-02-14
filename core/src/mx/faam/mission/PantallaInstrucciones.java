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
public class PantallaInstrucciones implements Screen {

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestructiom selfDestructiom;

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    //Texturas boton
    private Texture TexturaBotonBackMenu;
    private Texture TexturaBotonNuevoJuego;

    //fondo
    private Texture TexturaFondoInstrucciones;

    public PantallaInstrucciones(SelfDestructiom selfDestructiom) {
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
        Image fondoInstrucciones = new Image(TexturaFondoInstrucciones);
        escena.addActor(fondoInstrucciones);

        //botonNuevoJuego
        TextureRegionDrawable trdBtnNj = new TextureRegionDrawable(new TextureRegion(TexturaBotonNuevoJuego));
        ImageButton btnNj = new ImageButton(trdBtnNj);
        btnNj.setPosition(ANCHO/2-btnNj.getWidth()/2, 3*ALTO/4-btnNj.getHeight()/2);
        escena.addActor(btnNj);

        //Evento del boton
        btnNj.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestructiom.setScreen(new PantallaJuego(selfDestructiom));
            }
        });
        //botonBackMenu
        TextureRegionDrawable trdBtnBm = new TextureRegionDrawable(new TextureRegion(TexturaBotonBackMenu));
        ImageButton btnBm = new ImageButton(trdBtnBm);
        btnBm.setPosition(ANCHO-btnBm.getWidth()/2, 3*ALTO/4-btnBm.getHeight()/2);
        escena.addActor(btnBm);

        //Evento del boton
        btnNj.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestructiom.setScreen(new PantallaMenu(selfDestructiom));
            }
        });


        Gdx.input.setInputProcessor(escena);
    }

    private void crearTexturas() {
        //Textura botones
        TexturaBotonNuevoJuego = new Texture("botonGoTo.png");
        TexturaBotonBackMenu= new Texture("botonBackMenuPrueba.png");


        //textura fondp
        TexturaFondoInstrucciones = new Texture("pruebafondoinstrucciones.jpg");
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
        TexturaBotonNuevoJuego.dispose();
        TexturaBotonBackMenu.dispose();
    }
}
