package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


/**
 * Created by angel on 13/02/2017.
 */
public class PantallaJuego implements Screen {

    public static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private static final float ANCHO_MAPA = 3968;
    private boolean pausa;

    private final SelfDestruction selfDestruction;


    //Variable sonido
    private boolean sonidoTocando = false;

    private SpriteBatch batch;

    //Estado Jugando
    public EstadoJuego estado = EstadoJuego.JUGANDO;

    //vida
    Vida vida;

    //Sonido Caminar
    Sound sonidoCaminar = Gdx.audio.newSound(Gdx.files.internal("SonidoCaminar1.wav"));


    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Escena
    private Stage escena;

    //HUD
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Stage escenaHUD;
    //Textura Pausa
    private Texture TexturaPausa;
    //botones disparar
    private Texture TexturaBotonDisparar;
    private Texture TexturaBotonSwitch;

    //Mapa
    private TiledMap TexturaFondoJuego;
    private OrthogonalTiledMapRenderer renderer; //dibuja el mapa

    //Textura Oberon
    private Heroe oberon;
    private Texture TexturaOberon;
    private Texture TexturaOberonDisparando;
    private Texture oberonIzq;
    private Bala bala;

    //Textura enemigos
    private Texture TexturaChiquito;
    private Enemigo chiquito1;

    //Arreglo de balas
    ArrayList<Bala> balas = new ArrayList<Bala>();
    //Arreglo Enemigo
    ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();


    public PantallaJuego(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }


    @Override
    public void show() {
        if (!pausa) {
            //Heroe
            TexturaOberon = new Texture("prueba tamaño derecha.png");
            TexturaOberonDisparando = new Texture("posicion disparo derecha.png");
            oberonIzq = new Texture("prueba tamaño izquierda.png");

            //Enemigos
            TexturaChiquito = new Texture("enemigo 2 animacion izquierda.png");

            //Vida
            vida = new Vida();

            //oberonDisparando = new Heroe(TexturaOberonDisparando,0,64 );
            oberon = new Heroe(TexturaOberon, TexturaOberonDisparando, oberonIzq, 0, 64);
            chiquito1 = new Enemigo(TexturaChiquito,1280,188,5,-100);
            Enemigo chiquito2 = new Enemigo(TexturaChiquito,3000,188,5,-100);
            enemigos.add(chiquito1);
            enemigos.add(chiquito2);



            AssetManager manager = new AssetManager();
            manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            manager.load("mapaInicialPrimerNivel.tmx", TiledMap.class);

            manager.finishLoading();    //cargar Recursos
            TexturaFondoJuego = manager.get("mapaInicialPrimerNivel.tmx");

            camara = new OrthographicCamera(ALTO / 2, ANCHO / 2);
            vista = new StretchViewport(ANCHO, ALTO, camara);
            batch = new SpriteBatch();
            renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego, batch);
            renderer.setView(camara);

            crearHUD();


        } else {
            pausa = false;
        }
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
        pad.setColor(1, 1, 1, 0.4f);

        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad) actor;
                if (pad.getKnobPercentX() > 0.20) {
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.MOV_DERECHA);
                    //sonidoCaminar.setVolume(sonidoCaminarId,1.0f);
                    if (sonidoTocando == false) {
                        sonidoCaminar.loop();
                        sonidoTocando = true;

                    }

                } else if (pad.getKnobPercentX() < -0.20) {
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.MOV_IZQUIERDA);
                    if (sonidoTocando == false) {
                        sonidoCaminar.loop();
                        sonidoTocando = true;
                    }
                } else if (pad.getKnobPercentY() > 0.20) {
                    oberon.saltar();
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.QUIETO);

                } else {
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.QUIETO);
                    sonidoCaminar.stop();
                    sonidoTocando = false;
                }
            }
        });

        TexturaPausa = new Texture("pausa.png");
        TextureRegionDrawable trdBtnPausa = new TextureRegionDrawable(new TextureRegion(TexturaPausa));
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(0, ALTO - 100);
        btnPausa.setColor(1, 1, 1, 0.4f);

        TexturaBotonDisparar = new Texture("boton disparo grande.png");
        TextureRegionDrawable trdBtnDisparar = new TextureRegionDrawable(new TextureRegion(TexturaBotonDisparar));
        ImageButton btnDisparar = new ImageButton(trdBtnDisparar);
        btnDisparar.setPosition(ANCHO - 325, 30);
        btnDisparar.setColor(1, 1, 1, 0.6f);


        TexturaBotonSwitch = new Texture("boton switch grande.png");
        TextureRegionDrawable trdBtnSwitch = new TextureRegionDrawable(new TextureRegion(TexturaBotonSwitch));
        ImageButton btnBotonSwitch = new ImageButton(trdBtnSwitch);
        btnBotonSwitch.setPosition(ANCHO - 200, 50);
        btnBotonSwitch.setColor(1, 1, 1, 0.6f);

        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnPausa);
        escenaHUD.addActor(btnDisparar);
        escenaHUD.addActor(btnBotonSwitch);


        //Evento del boton
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Pausa");
                estado = EstadoJuego.PAUSADO;
                pausa = true;
                selfDestruction.setScreen(new PantallaPausa(selfDestruction, PantallaJuego.this));


            }
        });

        btnDisparar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Disparar");
                oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.DISPARANDO);
                balas.add(new Bala(oberon.getX() + TexturaOberonDisparando.getWidth(), oberon.getY() + 188));


            }
        });
    }

    @Override
    public void render(float delta) {
        //Arreglo Balas para quitar
        ArrayList<Bala> balasQuitar = new ArrayList<Bala>();
        //Arreglo Enemigos por quitar
        ArrayList<Enemigo> enemigoQuitar = new ArrayList<Enemigo>();

        //Actualizar
        oberon.actualizar(TexturaFondoJuego);

        actualizarMapa();
        for (Bala bala : balas) {
            bala.actualizarBala(delta, oberon.getX());
            if (bala.remove)
                balasQuitar.add(bala);

        }

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        renderer.setView(camara);

        renderer.render();
        batch.begin();
        if (estado == EstadoJuego.JUGANDO) {
            oberon.dibujar(batch);
            oberon.actualizarRect(oberon.getX(), oberon.getY());
            for(Enemigo enemigo : enemigos) {
                enemigo.actualizar(delta, enemigo.getX());
            }

            for (Enemigo enemigo : enemigos) {
                enemigo.render(batch);
            }
        }

        if (oberon.getEstadoMovimiento() == Heroe.EstadoMovimiento.DISPARANDO)
            for (Bala bala : balas) {
                bala.render(batch);
            }

        batch.end();
        for (Bala bala : balas) {
            for (Enemigo enemigo : enemigos) {
                if (bala.getCollisionRect().choca(enemigo.getColliderRect())) {
                    balasQuitar.add(bala);
                    enemigo.setVidas(enemigo.getVidas() - 1);
                    if (enemigo.getVidas() <= 0) {
                        enemigoQuitar.add(enemigo);
                        Gdx.app.log("Enemigo", "Mori");
                    }
                    Gdx.app.log("Colision", "Mata");
                }
            }
        }
        balas.removeAll(balasQuitar);
        enemigos.removeAll(enemigoQuitar);

        for (Enemigo enemigo : enemigos) {
            if (enemigo.getColliderRect().choca(oberon.getColliderRect())) {
                vida.setVida((float) (vida.getVida() + 0.1));
                if (vida.getVida() >= 2) {
                    selfDestruction.setScreen(new PantallaPerder(selfDestruction));
                }
            }
        }


        //Camara HUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

        //Vida
        batch.begin();
        vida.actualizarVida(batch, vida.getVida());
        batch.end();
    }


    private void actualizarMapa() {
        float posX = oberon.sprite.getX();
        // Si está en la parte 'media'
        if (posX >= ANCHO / 2 && posX <= ANCHO_MAPA - ANCHO / 2) {
            // El personaje define el centro de la cámara
            camara.position.set((int) posX, camara.position.y, 0);
        } else if (posX > ANCHO_MAPA - ANCHO / 2) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(ANCHO_MAPA - ANCHO / 2, camara.position.y, 0);
        } else if (posX < ANCHO / 2) { // La primera mitad
            camara.position.set(ANCHO / 2, PantallaJuego.ALTO / 2, 0);
        }
        camara.update();
    }


    private void borrarPantalla() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

        vista.update(width, height);
        vistaHUD.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //dispose();

    }

    @Override
    public void dispose() {
        escenaHUD.dispose();
        TexturaFondoJuego.dispose();
        TexturaOberon.dispose();

    }
}
