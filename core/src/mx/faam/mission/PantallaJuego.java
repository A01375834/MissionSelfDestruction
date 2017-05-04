package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
    public EstadoJuego estado = EstadoJuego.JUGANDO;

    public static EstadoNivel estadoNivel = EstadoNivel.PRIMERNIVEL;

    //vida
    public static Vida vida;
    private MedKit medKit;

    //Sonido Caminar
    private Sound sonidoCaminar = Gdx.audio.newSound(Gdx.files.internal("SonidoCaminar1.wav"));
    //Sonido oberon Quejido
    private Sound sonidoQuejido = Gdx.audio.newSound(Gdx.files.internal("quejidoOberon.wav"));
    //Sonido enemigo Muriendo
    private  Sound sonidoEnemigo = Gdx.audio.newSound(Gdx.files.internal("enemigoMuriendo.wav"));
    //Musica primer nivel
    private  Music musicaPrimerNivel = Gdx.audio.newMusic(Gdx.files.internal("msicaPrimerNivel.wav"));
    //Sonido MedKit
    private  Sound sonidoMedKit = Gdx.audio.newSound(Gdx.files.internal("medKit.wav"));


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

    //Textura llave
    private Texture llave;
    private ColliderRect rectLlave;
    private Boolean llaveBoolean = false;

    //Texttura llave Parte Dos
    private  Texture llaveDos;
    private ColliderRect rectLlaveDos;
    private Boolean llaveBooleanDos = false;
    private Texture llaveIcono;
    private AssetManager managerUno;

    //Textura enemigos
    private Texture texturaChiquito;
    private Texture texturaEnemigoDos;
    private Texture texturaTercerEnemigo;
    private Texture textraFinalBoss;

    //Comentario

    //Textura y colisione pueta siguiente parte del nivel
    private Texture puerta;
    private ColliderRect rect;

    private  Texture puertaDos;

    //AssetManager
    private AssetManager manager = new AssetManager();


    //Arreglo de balas
    private ArrayList<Bala> balas = new ArrayList<Bala>();
    private String texturaBala;
    private Boolean balaBoolean;
    //Municion
    private Texture texturaMunicion;
    private ColliderRect rectMunicion = new ColliderRect(1200,64,176,96);

    //Arreglo Enemigo
    private ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
    private float tiempoEnemigo;
    private float tiempoMaximo = 10f;

    //Arreglo MedKits
    ArrayList<MedKit> medKits = new ArrayList<MedKit>();



    public PantallaJuego(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }


    @Override
    public void show() {
        managerUno = selfDestruction.getManager();
        if (!pausa) {

            long inicio=System.nanoTime();
            //Heroe
            TexturaOberon = new Texture("prueba tamaño derecha.png");
            TexturaOberonDisparando = new Texture("posicion disparo derecha.png");
            oberonIzq = new Texture("prueba tamaño izquierda.png");
            puerta = new Texture("puerta.png");
            rect = new ColliderRect(3765, 64, 192, 288);

            puertaDos = new Texture("puerta.png");

            //Llave
            llave = new Texture(Gdx.files.internal("key.png"));
            rectLlave = new ColliderRect(2180,940,64,32);
            llaveIcono = new Texture("key.png");

            //Enemigos
            texturaChiquito = new Texture("enemigo 2 animacion izquierda.png");
            texturaEnemigoDos = new Texture("enemigoDos.png");

            tiempoEnemigo = MathUtils.random(1.5f, 5.0f);

            texturaMunicion = new Texture("municion.png");

            //Vida
            vida = new Vida();
            medKit = new MedKit(batch, 3000, 832);
            medKits.add(medKit);

            //oberonDisparando = new Heroe(TexturaOberonDisparando,0,64 );
            oberon = new Heroe(TexturaOberon, TexturaOberonDisparando, oberonIzq, 0, 64);



            //manager.load("mapaInicialPrimerNivel.tmx", TiledMap.class);
            //manager.load("ParteDosPrimerNivel.tmx", TiledMap.class);
            //manager.load("NivelDos.tmx", TiledMap.class);

            //manager.finishLoading();    //cargar Recursos
            TexturaFondoJuego = managerUno.get("mapaInicialPrimerNivel.tmx");
            camara = new OrthographicCamera(ALTO / 2, ANCHO / 2);
            vista = new StretchViewport(ANCHO, ALTO, camara);
            batch = new SpriteBatch();
            renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego, batch);
            renderer.setView(camara);

            crearHUD();

            if(Preferencias.cargarVida() > 20 && Preferencias.cargarVida() <100){
                vida.setVida(Preferencias.cargarVida());
                Gdx.app.log("Vida Para jugar" ,Preferencias.cargarVida()+" ");
            }else if (Preferencias.cargarVida() <=  20){
                vida.setVida(20);
            }
            else {
                vida.setVida(100);
            }



            long fin=System.nanoTime();
            Gdx.app.log("Show", "Tiempo: " +(fin-inicio)/1000); // se carga ahora desde el manager


        }

        else {
            pausa = false;
        }

        Gdx.input.setCatchBackKey(true);
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
                    if(PantallaPausa.musicaOn){
                        if (sonidoTocando == false) {
                            sonidoCaminar.loop();
                            sonidoTocando = true;

                        }
                    }


                } else if (pad.getKnobPercentX() < -0.20) {
                    oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.MOV_IZQUIERDA);
                    if(PantallaPausa.musicaOn) {
                        if (sonidoTocando == false) {
                            sonidoCaminar.loop();
                            sonidoTocando = true;
                        }
                    }
                } else if (pad.getKnobPercentY() > 0.20) {

                    // Gdx.app.log("Estoy Saltando:", "ahora");
                    Gdx.app.log("y personaje: ", String.valueOf(oberon.sprite.getY()));
                    if(oberon.getEstadoSalto() == Heroe.EstadoSalto.EN_PISO){
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
                estado = EstadoJuego.PAUSADO;
                pausa = true;
                selfDestruction.setScreen(new PantallaPausa(selfDestruction, PantallaJuego.this));
                musicaPrimerNivel.stop();
            }
        });


        //AAAAAAAAA
        btnDisparar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Disparar");
                oberon.setEstadoMovimiento(Heroe.EstadoMovimiento.DISPARANDO);
                if(oberon.ViendoDerecha()==true) {
                    balas.add(new Bala(oberon.getX() + TexturaOberonDisparando.getWidth(), oberon.getY() + 188,texturaBala));
                }else{
                    balas.add(new Bala(oberon.getX() , oberon.getY()+188, texturaBala ));
                }
            }
        });
    }


    @Override
    public void render(float delta) {


        //Arreglo Balas para quitar
        ArrayList<Bala> balasQuitar = new ArrayList<Bala>();
        //Arreglo Enemigos por quitar
        ArrayList<Enemigo> enemigoQuitar = new ArrayList<Enemigo>();
        //Arreglo Medkit por quitas
        ArrayList<MedKit> medKitsQuitar = new ArrayList<MedKit>();
        Gdx.app.log("x: ", oberon.getX() + " ");

        if(estadoNivel == EstadoNivel.PRIMERNIVEL){
            texturaBala = "bala.png";
            balaBoolean = false;
        }

        ColliderRect rectPuerta2;

        //Gdx.app.log("X",oberon.getX()+"");


        crearNuevosEnemigos(delta);
        if(PantallaPausa.musicaOn) {
            musicaPrimerNivel.setLooping(true);
            musicaPrimerNivel.play();
        }
        //Actualizar
        oberon.actualizar(TexturaFondoJuego);
        actualizarMapa();
        for (Bala bala : balas) {
            if(oberon.ViendoDerecha()==true){
                bala.actualizarBala(delta, camara);
            }else{
                bala.actualizarBalaIzq(delta,camara);
            }
            if (bala.remove)
                balasQuitar.add(bala);

        }
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        renderer.setView(camara);
        renderer.render();
        //Medkits
        for (MedKit medkit : medKits) {
            batch.begin();
            medKit.ActualizarMedKit(batch);
            batch.end();
        }
        for (MedKit medKit : medKits) {
            if (medKit.getCollisionRect().choca(oberon.getColliderRect())) {
                if (vida.getVida() < 100) {
                    medKitsQuitar.add(medKit);
                    if(PantallaPausa.musicaOn) {
                        sonidoMedKit.play(0.5f);
                    }
                    vida.setVida(100);
                }
            }
        }
        medKits.removeAll(medKitsQuitar);
        batch.begin();
        batch.draw(puerta, 3765, 64);

        //PARTE DOS NIVEL UNO
        if(estadoNivel == EstadoNivel.PRIMERNIVELPT2) {


            batch.draw(puertaDos, 2396, 64);
            if(!balaBoolean) {
                batch.draw(texturaMunicion, 1200, 64);
            }
            if(rectMunicion.choca(oberon.getColliderRect())){
                balaBoolean = true;
                texturaBala = "bala2.png";
                texturaMunicion.dispose();
            }
            rectPuerta2 = new ColliderRect(2396,64,128,64);
            if(rectPuerta2.choca(oberon.getColliderRect())&&llaveBooleanDos){
                Gdx.app.log("Siguiente","Nivel 2");
                estadoNivel = EstadoNivel.SEGUNDONIVEL;
                PantallaCargandoMapa.cargarNivel();
                TexturaFondoJuego = managerUno.get("NivelDos.tmx");
                renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego, batch);
                renderer.setView(camara);
                oberon.sprite.setPosition(0,64);



            }
            llaveDos = new Texture("key.png");
            rectLlaveDos = new ColliderRect(2496,832,64,32);
            if(llaveBooleanDos==false){
                batch.draw(llaveDos,2496,832);
            }
            if(rectLlaveDos.choca(oberon.getColliderRect())){
                llaveBooleanDos = true;
                llaveDos.dispose();
            }

        }
        if(!llaveBoolean){
            batch.draw(llave,2180,940);
        }
        if(rect.choca(oberon.getColliderRect())&& !llaveBoolean){
            batch.draw(llaveIcono,3860,400);

        }
        batch.end();


        if (rect.choca(oberon.getColliderRect())&& llaveBoolean==true) {
            Gdx.app.log("Siguiente", "Nivel");
            estadoNivel = EstadoNivel.PRIMERNIVELPT2;
            PantallaCargandoMapa.cargarNivel();
            TexturaFondoJuego = managerUno.get("ParteDosPrimerNivel.tmx");

            renderer = new OrthogonalTiledMapRenderer(TexturaFondoJuego, batch);
            renderer.setView(camara);
            oberon.sprite.setPosition(0,64);
        }


        batch.begin();
        if (estado == EstadoJuego.JUGANDO) {
            oberon.dibujar(batch);
            oberon.actualizarRect(oberon.getX(), oberon.getY());
            for (Enemigo enemigo : enemigos) {
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
                    enemigo.herir(delta,batch);
                    balasQuitar.add(bala);
                    if(balaBoolean){
                        enemigo.setVidas(enemigo.getVidas()-3);
                    }else {
                        enemigo.setVidas(enemigo.getVidas() - 1);
                    }
                    if (enemigo.getVidas() <= 0) {
                        if(PantallaPausa.musicaOn) {
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

        for (Enemigo enemigo : enemigos) {
            if (enemigo.getColliderRect().choca(oberon.getColliderRect())) {
                if(PantallaPausa.musicaOn) {
                    sonidoQuejido.play();
                }
                if(enemigo.tipoEnemigo == Enemigo.TipoEnemigo.PRIMERENEMIGO) {
                    vida.herir(20);
                }else if(enemigo.tipoEnemigo == Enemigo.TipoEnemigo.SEGUNDOENEMIGO){
                    vida.herir(40);
                }
                enemigoQuitar.add(enemigo);
                if (vida.getVida() <= 0) {
                    selfDestruction.setScreen(new PantallaPerder(selfDestruction));
                    musicaPrimerNivel.stop();
                    sonidoCaminar.stop();
                    Preferencias.guardarVidas(vida.getVida());
                }
            }
        }
        enemigos.removeAll(enemigoQuitar);
        if(rectLlave.choca(oberon.getColliderRect())){
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            selfDestruction.setScreen(new PantallaMenu(selfDestruction));
            Preferencias.guardarVidas(vida.getVida());
        }



    }




    private void actualizarMapa() {
        int ANCHO_MAPA = ((TiledMapTileLayer)(TexturaFondoJuego.getLayers().get(0))).getWidth() * 32;
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

    public  static EstadoNivel getEstadoNivel(){
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
            if(estadoNivel == EstadoNivel.PRIMERNIVEL) {
                Enemigo enemigo = new Enemigo(texturaChiquito, oberon.getX() + ANCHO + 1, 188, 5, -100, batch, 288, 128);
                enemigos.add(enemigo);
            }else{
                Enemigo enemigo2 = new Enemigo(texturaEnemigoDos,oberon.getX()+ANCHO+1,64,15,batch,128,320);
                enemigos.add(enemigo2);
            }

        }

    }


}
