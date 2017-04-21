package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        escena.addActor(fondoHistoria);

        //Pad



        //botonBackMenu
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
        Gdx.input.setInputProcessor(escena);
    }

    private void crearTexturas() {
        //Textura botones
        TexturaBotonBackMenu= new Texture("back to menu grande.png");
        Skin skin = new Skin();
        skin.add("base", new Texture("base de palanca grande.png"));
        skin.add("palanca", new Texture("palanca grande.png"));

        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("base");
        estilo.knob = skin.getDrawable("palanca");

        Touchpad pad = new Touchpad(20, estilo);
        pad.setBounds(50, 50, 200, 200);
        pad.setColor(1, 1, 1, 0.4f);
        BotonDisparar
        BotonSwitch;
        Tarjeta;
        botiquin;
        balas;


        //textura fondp
        TexturaFondoOpciones = new Texture("fondo.png");
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
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

    }
}
