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
 * Created by angel on 11/02/2017.
 */
public class PantallaMenu implements Screen {
    //tamaño de pantallas
    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestructiom selfDestructiom;

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //texturas boton
    private Texture TexturaBotonNuevoJuego;
    private Texture TexturaBotonCargarJuego;
    private Texture TexturaBotonInstrucciones;
    private Texture TexturaBotonHistoria;
    private Texture TexturaBotonCreditos;

    //textura fondo
    private Texture TexturaFondoMenu;
    
    //escena 
    private Stage escena;
    private SpriteBatch batch; 


    public PantallaMenu(SelfDestructiom selfDestructiom) {

        this.selfDestructiom = selfDestructiom;
    }

    @Override
    public void show() {
        //Al entrar al juego haré...
        crearCamara();
        crearTexturas();
        crearObjetos();
        
        
        
    }

    private void crearTexturas() {
        //Textura botones
        TexturaBotonNuevoJuego = new Texture("botonGoTo.png");
        TexturaBotonCargarJuego= new Texture("botonGoToCargarPartida.png");
        TexturaBotonInstrucciones = new Texture("botonGoToInstruccionesP.png");
        TexturaBotonHistoria = new Texture("botonGoToHistoria.png");
        TexturaBotonCreditos = new Texture("botonGoToCreditos.png");

        //textura fondp
        TexturaFondoMenu = new Texture("pruebafondomenu.jpg");

    }

    private void crearObjetos() {
        //fondo
        batch = new SpriteBatch();
        escena = new Stage(vista, batch);
        Image fondoMenu = new Image (TexturaFondoMenu);
        escena.addActor(fondoMenu);


        //botonNuevoJuego
        TextureRegionDrawable trdBtnNj = new TextureRegionDrawable(new TextureRegion(TexturaBotonNuevoJuego));
        ImageButton btnNj = new ImageButton(trdBtnNj);
        btnNj.setPosition(ANCHO-btnNj.getWidth()/2, 3*ALTO/4-btnNj.getHeight()/2);
        escena.addActor(btnNj);

        //Evento del boton
        btnNj.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestructiom.setScreen(new PantallaJuego(selfDestructiom));
            }
        });
        //botonCargarJuego
        TextureRegionDrawable trdBtnCj = new TextureRegionDrawable(new TextureRegion(TexturaBotonCargarJuego));
        ImageButton btnCj = new ImageButton(trdBtnCj);
        btnCj.setPosition(ANCHO-btnCj.getWidth()/2, 3*ALTO/4-btnCj.getHeight()/2);
        escena.addActor(btnCj);

        //Evento del boton
        btnCj.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestructiom.setScreen(new PantallaJuego(selfDestructiom));
            }
        });

        //botonInstrucciones
        TextureRegionDrawable trdBtnInstrucciones = new TextureRegionDrawable(new TextureRegion(TexturaBotonInstrucciones));
        ImageButton btnInstrucciones = new ImageButton(trdBtnInstrucciones);
        btnInstrucciones.setPosition(ANCHO/3+btnInstrucciones.getWidth(), 3*ALTO/4-btnInstrucciones.getHeight()/2);
        escena.addActor(btnInstrucciones);

        //Evento del boton
        btnInstrucciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestructiom.setScreen(new PantallaInstrucciones(selfDestructiom));
            }
        });
        //botonHistoria
        TextureRegionDrawable trdBtnHistoria = new TextureRegionDrawable(new TextureRegion(TexturaBotonHistoria));
        ImageButton btnHistoria = new ImageButton(trdBtnHistoria);
        btnHistoria.setPosition(ANCHO/4-btnHistoria.getWidth()/2, 3*ALTO/4-btnHistoria.getHeight()/2);
        escena.addActor(btnHistoria);

        //Evento del boton
        btnHistoria.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestructiom.setScreen(new PantallaHistoria(selfDestructiom));
            }
        });

        //BotonCreditos
        TextureRegionDrawable trdBtnCreditos = new TextureRegionDrawable(new TextureRegion(TexturaBotonCreditos));
        ImageButton btnCreditos = new ImageButton(trdBtnCreditos);
        btnCreditos.setPosition(ANCHO/5-btnCreditos.getWidth()/2, 3*ALTO/4-btnCreditos.getHeight()/2);
        escena.addActor(btnCreditos);

        //Evento del boton
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","Me hicieron click");
                selfDestructiom.setScreen(new PantallaCreditos(selfDestructiom));
            }
        });
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
        TexturaFondoMenu.dispose();
        TexturaBotonNuevoJuego.dispose();
        TexturaBotonCargarJuego.dispose();
        TexturaBotonInstrucciones.dispose();
        TexturaBotonHistoria.dispose();
        TexturaBotonCreditos.dispose();
    }
}
