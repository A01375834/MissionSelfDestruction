package mx.itesm.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Mauricio on 04/05/2017.
 */

public class PantallaWin implements Screen {

    private static final float ANCHO = 1280;
    private static final float ALTO = 800;
    private final SelfDestruction selfDestruction;

    private mx.itesm.mission.Texto texto;

    //Estado Juego
    // private EstadoJuego estado = EstadoJuego.PAUSADO;

    //camara
    private OrthographicCamera camara;
    private Viewport vista;

    //Escena
    private Stage escena;
    private SpriteBatch batch;

    //fondo
    private Texture texturaWin;
    private boolean musicaTocando = false;
    private boolean musicaTocandoRisa = true;

    public PantallaWin(SelfDestruction selfDestruction) {
        this.selfDestruction = selfDestruction;
    }


    @Override
    public void show() {
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();
        vista = new StretchViewport(ANCHO,ALTO,camara);
        //fondo
        batch = new SpriteBatch();
        escena = new Stage(vista, batch);
        texturaWin = new Texture("win.png");
        texto = new mx.itesm.mission.Texto("Fonts/fontLoading.fnt");
        Image fondoWin = new Image (texturaWin);
        escena.addActor(fondoWin);

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(new PantallaWin.tocar());
        borrarPantalla();
        escena.draw();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        texto.mostrarMensaje(batch,"Tap to continue",ANCHO/2+50,ALTO-10);
        batch.end();/*
        if(musicaTocando==false) {
            musicaFondo.play(1.0f);
            musicaTocando = true;
            //musicaTocandoRisa = false;
            musicaRisa.loop(1.0f);
        }
        */



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
        //escena.dispose();
        texturaWin.dispose();

    }

    private class tocar implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            selfDestruction.setScreen(new mx.itesm.mission.PantallaMenu(selfDestruction));
            mx.itesm.mission.Preferencias.guardarVidas(100);
            mx.itesm.mission.PantallaJuego.estadoNivel = mx.itesm.mission.EstadoNivel.PRIMERNIVEL;
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
