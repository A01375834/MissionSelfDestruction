package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by angel on 13/02/2017.
 */
public class PantallaJuego implements Screen {

    public static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private static final float ANCHO_MAPA = 2528;

    private final SelfDestruction selfDestruction;

    private SpriteBatch batch;





    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //HUD
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Stage escenaHUD;

    //Mapa
    private TiledMap TexturaFondoJuego;
    private OrthogonalTiledMapRenderer renderer; //dibuja el mapa




    //Textura Oberon
    private Heroe oberon;
    private Texture TexturaOberon;



    //Textura Pausa
    private Texture TexturaPausa;




    public PantallaJuego(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }



    @Override
    public void show() {


        //Heroe
        TexturaOberon = new Texture("marioSprite.png");
        oberon = new Heroe(TexturaOberon, 0, 64);

        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapaInicialPrimerNivel.tmx",TiledMap.class);

        manager.finishLoading();    //cargar Recursos
        TexturaFondoJuego = manager.get("mapaInicialPrimerNivel.tmx");

        camara = new OrthographicCamera(ALTO/2,ANCHO/2);
        vista = new StretchViewport(ANCHO, ALTO, camara);
        batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego,batch);
        renderer.setView(camara);


        crearHUD();











        Gdx.input.setInputProcessor(escenaHUD);


    }

    private void crearHUD() {
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO / 2, ALTO / 2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, camaraHUD);

        //HUD Aqui va el boton de pausa, las vidas, analogo, boton disparar y creo ya.
        // HUD
        Skin skin = new Skin();
        skin.add("base", new Texture("base de palanca grande.png"));
        skin.add("palanca", new Texture("palanca grande.png"));

        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("base");
        estilo.knob = skin.getDrawable("palanca");

        Touchpad pad = new Touchpad(20, estilo);
        pad.setBounds(50, 50, 200, 200);
        pad.setColor(1,1,1,0.4f);

        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad) actor;
                if (pad.getKnobPercentX()>0.20) {
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.MOV_DERECHA);
                } else if (pad.getKnobPercentX()<-0.20){
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.MOV_IZQUIERDA);
                } else if (pad.getKnobPercentY()>0.20){
                    oberon.saltar();
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.QUIETO);
                }
                else{
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.QUIETO);
                }
            }
        });

        TexturaPausa = new Texture("pausa.png");
        TextureRegionDrawable trdBtnPausa = new TextureRegionDrawable(new TextureRegion(TexturaPausa));
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(0,ALTO-100);
        btnPausa.setColor(1,1,1,0.4f);




        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnPausa);

    }


    @Override
    public void render(float delta) {
        //Actualizar
        oberon.actualizar(TexturaFondoJuego);

        //efectos de sonidoO


        actualizarMapa();

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        renderer.setView(camara);
        renderer.render();

        batch.begin();
        oberon.dibujar(batch);
        batch.end();

        //Camara HUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

    }

    private void actualizarMapa() {
        float posX = oberon.sprite.getX();
        // Si está en la parte 'media'
        if (posX>=ANCHO/2 && posX<=ANCHO_MAPA-ANCHO/2) {
            // El personaje define el centro de la cámara
            camara.position.set((int)posX, camara.position.y, 0);
        } else if (posX>ANCHO_MAPA-ANCHO/2) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(ANCHO_MAPA-ANCHO/2, camara.position.y, 0);
        } else if ( posX<ANCHO/2 ) { // La primera mitad
            camara.position.set(ANCHO/2, PantallaJuego.ALTO/2,0);
        }
        camara.update();
    }


    private void borrarPantalla() {
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

        vista.update(width,height);
        vistaHUD.update(width,height);
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
        escenaHUD.dispose();
        TexturaFondoJuego.dispose();
        TexturaOberon.dispose();

    }
}
