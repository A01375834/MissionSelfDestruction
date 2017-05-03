package mx.faam.mission;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class SelfDestruction extends Game {
	SpriteBatch batch;
	Texture img;
	private final AssetManager manager = new AssetManager();
	
	@Override
	public void create () {
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

		setScreen(new PantallaInicioJuego(this));
	}
	public AssetManager getManager(){
		return manager;
	}

	@Override
	public void dispose(){
        super.dispose();
        manager.clear();
    }

}
