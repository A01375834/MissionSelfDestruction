package mx.faam.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by angel on 03/05/2017.
 */

public class PantallaCargandoMapa implements Screen {


        private static final float TIEMPO_ENTRE_FRAMES = 0.05f;
        private Sprite spriteCargando;
        private float timerAnimacion = TIEMPO_ENTRE_FRAMES;


        private static final float ANCHO = 1280;
        private static final float ALTO = 800;

        private OrthographicCamera camara = new OrthographicCamera(ANCHO,ALTO);


        // AssetManager
        private static AssetManager manager;

        private SelfDestruction selfDestruction;
        private int avance = 0; // % de carga
        private Texto texto;

        private Texture texturaCargando;
        private SpriteBatch batch = new SpriteBatch();

        public PantallaCargandoMapa(SelfDestruction selfDestruction) {
            this.selfDestruction = selfDestruction;
            manager = selfDestruction.getManager();
        }



        @Override
        public void show() {
            texturaCargando = new Texture(Gdx.files.internal("cargando.png"));
            spriteCargando = new Sprite(texturaCargando);
            spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth()/2,ALTO/2-spriteCargando.getHeight()/2);
            texto = new Texto("Fonts/fontLoading.fnt");
            camara.position.set(ANCHO/2, ALTO/2, 0);
            camara.update();
            Gdx.app.log("Estoy cargando","tonto");
            cargarNivel();
        }


        @Override
        public void render(float delta) {
            actualizar();
            borrarPantalla(0,0,0);
            batch.setProjectionMatrix(camara.combined);
            batch.begin();
            spriteCargando.draw(batch);
            texto.mostrarMensaje(batch,avance+" %",ANCHO/2+20,ALTO/2+110);
            batch.end();

            //Actualizar
            if(avance>=100){
                selfDestruction.setScreen(new PantallaJuego(selfDestruction));
            }

            actualizar();

        }

        public static void cargarNivel(){
            if(PantallaJuego.getEstadoNivel() == EstadoNivel.PRIMERNIVEL) {
                manager.load("mapaInicialPrimerNivel.tmx", TiledMap.class);
            }
            if(PantallaJuego.getEstadoNivel() ==EstadoNivel.PRIMERNIVELPT2) {
                manager.load("ParteDosPrimerNivel.tmx", TiledMap.class);
                manager.finishLoading();
                Gdx.app.log("Cargo", "Nivel");
            }
            if(PantallaJuego.getEstadoNivel() == EstadoNivel.SEGUNDONIVEL) {
                manager.load("NivelDos.tmx", TiledMap.class);
                manager.finishLoading();
            }
        }

        private void actualizar() {
            if(manager.update()) {
                //selfDestruction.setScreen(new PantallaJuego(selfDestruction));

                avance +=1;
                spriteCargando.rotate(-30);}


        }


        private void borrarPantalla(int r, int g, int b) {
            Gdx.gl.glClearColor(r,g,b,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        }

        @Override
        public void resize(int width, int height) {

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
            texturaCargando.dispose();


        }
    }


