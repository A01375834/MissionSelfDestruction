package mx.itesm.mission;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Mauricio on 02/05/2017.
 */

public class Preferencias {



    public static int cargarVida(){
        Preferences p = Gdx.app.getPreferences("Vida");
        return p.getInteger("vida", 100);
    }

    public static void guardarVidas(int vida){
        Preferences p = Gdx.app.getPreferences("Vida");
        p.putInteger("vida",vida);
        p.flush();
    }
    public static int cargarNivel(){
        Preferences p = Gdx.app.getPreferences("Nivel");
        return p.getInteger("nivel",1);
    }

    public static void guardarNivel(int nivel){
        Preferences p = Gdx.app.getPreferences("Nivel");
        p.putInteger("nivel",nivel);
        p.flush();
    }

    public static void guardarSonido(boolean bol){
        Preferences p = Gdx.app.getPreferences("Sonido");
        p.putBoolean("sonido",bol);
        p.flush();
    }

    public static boolean cargarSonido(){
        Preferences p = Gdx.app.getPreferences("Sonido");
        return p.getBoolean("sonido",true);
    }

}
