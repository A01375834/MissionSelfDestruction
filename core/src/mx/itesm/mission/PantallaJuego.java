package mx.itesm.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
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
    public static final float ALTO = 800;

    private boolean pausa;

    private SelfDestruction selfDestruction;


    //Variable sonido
    private boolean sonidoTocando = false;

    private SpriteBatch batch;

    //Estado Jugando
    public mx.itesm.mission.EstadoJuego estado = mx.itesm.mission.EstadoJuego.JUGANDO;

    public static EstadoNivel estadoNivel = EstadoNivel.PRIMERNIVEL;

    //vida
    public static Vida vida;
    private Texture medKit;
    private Texture medkitDos;
    private Texture medKitTres;
    private ColliderRect rectMed, rectMedDos, rectMedTres;
    private boolean medKitBool, medKitDosBool,medKitTresBool;

    //Sonido Caminar
    private Sound sonidoCaminar = Gdx.audio.newSound(Gdx.files.internal("SonidoCaminar1.wav"));
    //Sonido oberon Quejido
    private Sound sonidoQuejido = Gdx.audio.newSound(Gdx.files.internal("quejidoOberon.wav"));
    //Sonido enemigo Muriendo
    private Sound sonidoEnemigo = Gdx.audio.newSound(Gdx.files.internal("enemigoMuriendo.wav"));
    //Musica primer nivel
    private Music musicaPrimerNivel = Gdx.audio.newMusic(Gdx.files.internal("msicaPrimerNivel.wav"));
    //Sonido MedKit
    private Sound sonidoMedKit = Gdx.audio.newSound(Gdx.files.internal("medKit.wav"));


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

    //Mapa
    private TiledMap TexturaFondoJuego;
    private OrthogonalTiledMapRenderer renderer; //dibuja el mapa
    //Textura Oberon
    private Heroe oberon;
    private Texture TexturaOberon;
    private Texture TexturaOberonDisparando;
    private Texture oberonIzq;

    //Textura llave
    private Texture llave;
    private ColliderRect rectLlave;
    private Boolean llaveBoolean = false;

    //Texttura llave Parte Dos
    private Texture llaveDos;
    private ColliderRect rectLlaveDos;
    private Boolean llaveBooleanDos = false;
    private Texture llaveIcono;
    private AssetManager managerUno;

    //Textura enemigos
    private Texture texturaChiquito;
    private Texture texturaEnemigoDos;
    //Comentario

    //Textura y colisione pueta siguiente parte del nivel
    private Texture puerta;
    private ColliderRect rect;

    private Texture puertaDos;

    private Texture puertaWin;
    private ColliderRect rectWin;
    private Texture llaveWin;
    private Boolean winBoolean = false;
    private ColliderRect rectLlaveWin;

    //AssetManager
    private AssetManager manager = new AssetManager();


    //Arreglo de balas
    private ArrayList<mx.itesm.mission.Bala> balas = new ArrayList<mx.itesm.mission.Bala>();
    private String texturaBala;
    private Boolean balaBoolean;
    //Municion
    private Texture texturaMunicion;
    private ColliderRect rectMunicion = new ColliderRect(1200, 64, 176, 96);

    //Arreglo Enemigo
    private ArrayList<mx.itesm.mission.Enemigo> enemigos = new ArrayList<mx.itesm.mission.Enemigo>();
    private float tiempoEnemigo;
    private float tiempoMaximo = 10f;

    //Arreglo Balas para quitar
    private ArrayList<mx.itesm.mission.Bala> balasQuitar = new ArrayList<mx.itesm.mission.Bala>();
    //Arreglo Enemigos por quitar
    private ArrayList<mx.itesm.mission.Enemigo> enemigoQuitar = new ArrayList<mx.itesm.mission.Enemigo>();



    public PantallaJuego(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }


    @Override
    public void show() {
        managerUno = selfDestruction.getManager();
        if (!pausa) {

            //long inicio=System.nanoTime();
            //Heroe
            TexturaOberon = new Texture("prueba tamaño derecha.png");
            TexturaOberonDisparando = new Texture("posicion disparo derecha.png");
            oberonIzq = new Texture("prueba tamaño izquierda.png");
            puerta = new Texture("puerta.png");
            rect = new ColliderRect(3765, 64, 192, 288);

            puertaDos = new Texture("puerta.png");
            puertaWin = new Texture("puerta.png");

            //Llave
            llave = new Texture(Gdx.files.internal("key.png"));
            rectLlave = new ColliderRect(2180, 940, 64, 32);
            llaveIcono = new Texture("key.png");
            llaveWin = new Texture("key.png");

            //Enemigos
            texturaChiquito = new Texture("enemigo 2 animacion izquierda.png");
            texturaEnemigoDos = new Texture("enemigoDos.png");

            tiempoEnemigo = MathUtils.random(1.5f, 5.0f);

            texturaMunicion = new Texture("municion.png");


            //Vida
            vida = new Vida();
            medKitBool = false;
            medKitDosBool = false;
            medKitTresBool = false;

            //oberonDisparando = new Heroe(TexturaOberonDisparando,0,64 );
            oberon = new Heroe(TexturaOberon, TexturaOberonDisparando, oberonIzq, 0, 64);

            TexturaFondoJuego = managerUno.get("mapaInicialPrimerNivel.tmx");
            camara = new OrthographicCamera(ALTO / 2, ANCHO / 2);
            vista = new StretchViewport(ANCHO, ALTO, camara);
            batch = new SpriteBatch();
            renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego, batch);
            renderer.setView(camara);

            cargarMedKits();
            crearHUD();

            if (mx.itesm.mission.Preferencias.cargarVida() > 20 && mx.itesm.mission.Preferencias.cargarVida() < 100) {
                vida.setVida(mx.itesm.mission.Preferencias.cargarVida());
                Gdx.app.log("Vida Para jugar", mx.itesm.mission.Preferencias.cargarVida() + " ");
            } else if (mx.itesm.mission.Preferencias.cargarVida() <= 20) {
                vida.setVida(20);
            } else {
                vida.setVida(100);
            }


            //long fin=System.nanoTime();
            //Gdx.app.log("Show", "Tiempo: " +(fin-inicio)/1000); // se carga ahora desde el manager


        } else {
            pausa = false;
        }

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(escenaHUD);

    }

    private void cargarMedKits() {
        medKit = new Texture(Gdx.files.internal("medkit grande.png"));
        medkitDos = new Texture(Gdx.files.internal("medkit grande.png"));
        medKitTres = new Texture(Gdx.files.internal("medkit grande.png"));

        rectMed = new ColliderRect(3576,832,64,64);
        rectMedDos = new ColliderRect(3624,832,64,64);
        rectMedTres = new ColliderRect(6408,64,64,64);

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
                    if (mx.itesm.mission.PantallaPausa.musicaOn) {
                        if (!sonidoTocando) {
                            sonidoCaminar.loop();
                            sonidoTocando = true;

                        }
                    }


                } else if (pad.getKnobPercentX() < -0.20) {
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.MOV_IZQUIERDA);
                    if (mx.itesm.mission.PantallaPausa.musicaOn) {
                        if (!sonidoTocando) {
                            sonidoCaminar.loop();
                            sonidoTocando = true;
                        }
                    }
                } else if (pad.getKnobPercentY() > 0.20) {

                    // Gdx.app.log("Estoy Saltando:", "ahora");
                    Gdx.app.log("y personaje: ", String.valueOf(oberon.sprite.getY()));
                    if (oberon.getEstadoSalto() == Heroe.EstadoSalto.EN_PISO) {
                        oberon.setEstadoSalto(Heroe.EstadoSalto.SUBIENDO);
                        //oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.QUIETO);
                    }

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
        btnDisparar.setPosition(ANCHO - 280, 50);
        btnDisparar.setColor(1, 1, 1, 0.6f);


        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnPausa);
        escenaHUD.addActor(btnDisparar);


        //Evento del boton
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Pausa");
                estado = mx.itesm.mission.EstadoJuego.PAUSADO;
                pausa = true;
                selfDestruction.setScreen(new mx.itesm.mission.PantallaPausa(selfDestruction, PantallaJuego.this));
                musicaPrimerNivel.stop();
            }
        });

        btnDisparar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Disparar");
                oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.DISPARANDO);
                if (oberon.ViendoDerecha()) {
                    balas.add(new mx.itesm.mission.Bala(oberon.getX() + TexturaOberonDisparando.getWidth(), oberon.getY() + 188, texturaBala, false));
                } else {
                    balas.add(new mx.itesm.mission.Bala(oberon.getX(), oberon.getY() + 188, texturaBala, true));
                }
            }
        });
    }


    @Override
    public void render(float delta) {

        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        renderer.setView(camara);
        renderer.render();
        ColliderRect rectPuerta2;
        Gdx.app.log("x: ", oberon.getX() + " ");

        if (estadoNivel == EstadoNivel.PRIMERNIVEL) {
            texturaBala = "bala.png";
            balaBoolean = false;
        }


        crearNuevosEnemigos(delta);
        if (mx.itesm.mission.PantallaPausa.musicaOn) {
            musicaPrimerNivel.setLooping(true);
            musicaPrimerNivel.play();
        }
        //Actualizar
        oberon.actualizar(TexturaFondoJuego);
        actualizarMapa();
        for (mx.itesm.mission.Bala bala : balas) {
            bala.actualizarBala(delta, camara);
            if (bala.remove)
                balasQuitar.add(bala);
        }

        dibujarMedKits();
        actualizarMedKits();

        batch.begin();
        if (estadoNivel == EstadoNivel.PRIMERNIVEL) {
            batch.draw(puerta, 3765, 64);
        }

        //PARTE DOS NIVEL UNO
        if (estadoNivel == EstadoNivel.PRIMERNIVELPT2) {

            batch.draw(puertaDos, 2396, 64);
            if (!balaBoolean) {
                batch.draw(texturaMunicion, 1200, 64);
            }

            if (rectMunicion.choca(oberon.getColliderRect())) {
                balaBoolean = true;
                texturaBala = "bala2.png";
                texturaMunicion.dispose();
            }
            rectPuerta2 = new ColliderRect(2396, 64, 128, 64);
            if (rectPuerta2.choca(oberon.getColliderRect()) && llaveBooleanDos) {
                Gdx.app.log("Siguiente", "Nivel 2");
                estadoNivel = EstadoNivel.SEGUNDONIVEL;
                mx.itesm.mission.PantallaCargandoMapa.cargarNivel();
                TexturaFondoJuego = managerUno.get("NivelDos.tmx");
                renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego, batch);
                renderer.setView(camara);
                oberon.sprite.setPosition(0, 64);

            }
            llaveDos = new Texture("key.png");
            rectLlaveDos = new ColliderRect(2496, 832, 64, 32);
            if (!llaveBooleanDos) {
                batch.draw(llaveDos, 2496, 832);
            }
            if (rectLlaveDos.choca(oberon.getColliderRect())) {
                llaveBooleanDos = true;
                llaveDos.dispose();
            }

            if (rectPuerta2.choca(oberon.getColliderRect()) && !llaveBooleanDos) {
                batch.draw(llaveIcono, 2450, 400);
            }

        }
        if (!llaveBoolean) {
            batch.draw(llave, 2180, 940);
        }
        if (rect.choca(oberon.getColliderRect()) && !llaveBoolean) {
            batch.draw(llaveIcono, 3860, 400);

        }
        if (estadoNivel == EstadoNivel.SEGUNDONIVEL) {
            batch.draw(puertaWin, 8600, 832);
            if (!winBoolean) {
                batch.draw(llaveWin, 10064, 64);
            }
            rectLlaveWin = new ColliderRect(10064, 64, 42, 45);
            rectWin = new ColliderRect(8600, 832, 192, 288);
            if (rectLlaveWin.choca(oberon.getColliderRect())) {
                winBoolean = true;
                llaveWin.dispose();
            }
            if (rectWin.choca(oberon.getColliderRect()) && winBoolean) {
                selfDestruction.setScreen(new PantallaWin(selfDestruction));
            }
            if (rectWin.choca(oberon.getColliderRect()) && !winBoolean) {
                batch.draw(llaveIcono, 8700, 1150);
            }
        }
        batch.end();

        if (estadoNivel == EstadoNivel.PRIMERNIVEL) {
            if (rect.choca(oberon.getColliderRect()) && llaveBoolean) {
                Gdx.app.log("Siguiente", "Nivel");
                estadoNivel = EstadoNivel.PRIMERNIVELPT2;
                mx.itesm.mission.PantallaCargandoMapa.cargarNivel();
                TexturaFondoJuego = managerUno.get("ParteDosPrimerNivel.tmx");
                renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego, batch);
                renderer.setView(camara);
                oberon.sprite.setPosition(0, 64);
            }
        }


        batch.begin();
        if (estado == mx.itesm.mission.EstadoJuego.JUGANDO) {
            oberon.dibujar(batch);
            oberon.actualizarRect(oberon.getX(), oberon.getY());
            for (mx.itesm.mission.Enemigo enemigo : enemigos) {
                enemigo.actualizar(delta, enemigo.getX());
            }

            for (mx.itesm.mission.Enemigo enemigo : enemigos) {
                enemigo.render(batch);
            }
        }

        for (mx.itesm.mission.Bala bala : balas) {
            bala.render(batch);
        }

        batch.end();
        for (mx.itesm.mission.Bala bala : balas) {
            for (mx.itesm.mission.Enemigo enemigo : enemigos) {
                if (bala.getCollisionRect().choca(enemigo.getColliderRect())) {
                    enemigo.herir(delta, batch);
                    balasQuitar.add(bala);
                    if (balaBoolean) {
                        enemigo.setVidas(enemigo.getVidas() - 3);
                    } else {
                        enemigo.setVidas(enemigo.getVidas() - 1);
                    }
                    if (enemigo.getVidas() <= 0) {
                        if (mx.itesm.mission.PantallaPausa.musicaOn) {
                            sonidoEnemigo.play();
                        }
                        enemigoQuitar.add(enemigo);
                        Gdx.app.log("Enemigo", "Mori");
                    }
                    Gdx.app.log("Colision", "Mata");
                }
            }
        }


        balas.removeAll(balasQuitar);

        for (mx.itesm.mission.Enemigo enemigo : enemigos) {
            if (enemigo.getColliderRect().choca(oberon.getColliderRect())) {
                if (mx.itesm.mission.PantallaPausa.musicaOn) {
                    sonidoQuejido.play();
                }
                if (enemigo.tipoEnemigo == mx.itesm.mission.Enemigo.TipoEnemigo.PRIMERENEMIGO) {
                    vida.herir(20);
                } else if (enemigo.tipoEnemigo == mx.itesm.mission.Enemigo.TipoEnemigo.SEGUNDOENEMIGO) {
                    vida.herir(40);
                }
                enemigoQuitar.add(enemigo);
                if (vida.getVida() <= 0) {
                    selfDestruction.setScreen(new mx.itesm.mission.PantallaPerder(selfDestruction));
                    musicaPrimerNivel.stop();
                    sonidoCaminar.stop();
                    mx.itesm.mission.Preferencias.guardarVidas(vida.getVida());
                    estadoNivel = EstadoNivel.PRIMERNIVEL;
                }
            }
        }
        enemigos.removeAll(enemigoQuitar);
        if (rectLlave.choca(oberon.getColliderRect())) {
            llaveBoolean = true;
            llave.dispose();

        }

        //Camara HUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();


        //Color de vida
        vida.colorVida(batch);
        //Vida
        batch.begin();
        vida.actualizarVida(batch, vida.getVida());
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            selfDestruction.setScreen(new mx.itesm.mission.PantallaPausa(selfDestruction, PantallaJuego.this));
            mx.itesm.mission.Preferencias.guardarVidas(vida.getVida());
            estadoNivel = EstadoNivel.PRIMERNIVEL;
        }


    }

    private void actualizarMedKits() {
        if (vida.getVida() < 100) {
            if (rectMed.choca(oberon.getColliderRect())) {
                medKitBool = true;
                medKit.dispose();
                if (vida.getVida() < 100) {
                    vida.setVida(100);
                }
            } else if (rectMedDos.choca(oberon.getColliderRect())) {
                medKitDosBool = true;
                medkitDos.dispose();
                if (vida.getVida() < 100) {
                    vida.setVida(100);
                }
            } else if (rectMedTres.choca(oberon.getColliderRect())) {
                medKitTresBool = true;
                medKitTres.dispose();
                if (vida.getVida() < 100) {
                    vida.setVida(100);
                }
            }
        }
    }

    private void dibujarMedKits() {
        batch.begin();

        if (estadoNivel == EstadoNivel.PRIMERNIVEL && !medKitBool) {
            batch.draw(medKit, 3576, 832);
        } else if (estadoNivel == EstadoNivel.SEGUNDONIVEL && !medKitDosBool) {
            batch.draw(medkitDos, 3624, 832);
        } else if (estadoNivel == EstadoNivel.SEGUNDONIVEL && !medKitTresBool) {
            batch.draw(medKitTres, 6408, 64);
        }
        batch.end();

    }


    private void actualizarMapa() {
        int ANCHO_MAPA = ((TiledMapTileLayer) (TexturaFondoJuego.getLayers().get(0))).getWidth() * 32;
        float ALTO_MAPA = 1600;
        float posX = oberon.sprite.getX();
        float posY = oberon.sprite.getY();
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


        if ((posY >= PantallaJuego.ALTO / 2 && posY <= ALTO_MAPA - PantallaJuego.ALTO / 2)) {
            // El personaje define el centro de la cámara
            camara.position.set(camara.position.x, (int) posY, 0);
        } else if ((posY > ALTO_MAPA - PantallaJuego.ALTO / 2)) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(camara.position.x, ALTO_MAPA - PantallaJuego.ALTO / 2, 0);
        }
        camara.update();
    }


    private void borrarPantalla() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
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

    public static EstadoNivel getEstadoNivel() {
        return estadoNivel;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {


    }

    @Override
    public void dispose() {
        selfDestruction.getManager().unload("mapaInicialPrimerNivel.tmx");
        selfDestruction.getManager().unload("ParteDosPrimerNivel.tmx");
        selfDestruction.getManager().unload("NivelDos.tmx");
        TexturaOberon.dispose();

    }

    //AAA
    private void crearNuevosEnemigos(float delta) {
        tiempoEnemigo -= delta;
        if (tiempoEnemigo <= 0) {
            tiempoEnemigo = MathUtils.random(5.0f, tiempoMaximo);
            tiempoMaximo -= tiempoMaximo > 0.5f ? 10 * delta : 0;
            if (estadoNivel == EstadoNivel.PRIMERNIVEL) {
                mx.itesm.mission.Enemigo enemigo = new mx.itesm.mission.Enemigo(texturaChiquito, oberon.getX() + ANCHO + 1, 188, 5, -100, batch, 288, 128);
                enemigos.add(enemigo);
            } else {
                mx.itesm.mission.Enemigo enemigo2 = new mx.itesm.mission.Enemigo(texturaEnemigoDos, oberon.getX() + ANCHO + 1, 64, 15, batch, 128, 320);
                enemigos.add(enemigo2);
            }

        }

    }


}
