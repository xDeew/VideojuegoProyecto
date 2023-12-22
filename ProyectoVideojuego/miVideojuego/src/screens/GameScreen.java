package screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import elements.Barril;
import elements.Element;

import elements.Player;
import elements.Solid;
import game.Demo;
import game.Parametros;
import managers.AudioManager;
import managers.ResourceManager;

public class GameScreen extends BScreen {

	Stage mainStage;

	public Array<Solid> suelo;
	public Array<Barril> barriles;

	Solid end;

	OrthographicCamera camara;
	private TiledMap map;
	private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles, mapWidthInPixels, mapHeightInPixels;

	private Label etiquetaVida;

	private OrthogonalTiledMapRenderer renderer;

	public Player player;

	public GameScreen(Demo game) {

		super(game);

		mainStage = new Stage();
		float inicioX;
		float inicioY;

		camara = (OrthographicCamera) mainStage.getCamera();
		camara.setToOrtho(false, Parametros.getAnchoPantalla() * Parametros.zoom,
				Parametros.getAltoPantalla() * Parametros.zoom);

		inicioX = 0;
		inicioY = 0;

		player = new Player(inicioX, inicioY, mainStage);

		uiStage = new Stage();
		barriles = new Array<Barril>();
		Barril barril;
		for (int i = 0; i < 4; i++) {
			barril = new Barril(50 + inicioX + 20 * i, inicioY + 100, mainStage);
			barriles.add(barril);

		}

		AudioManager.playMusic("audio/music/swing.mp3");

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		mainStage.act();
		uiStage.act();
		colide();

		Parametros.jugadorx = player.getX();
		Parametros.jugadory = player.getY();

		// centrarCamara();
		/*
		 * renderer.setView(camara); renderer.render();
		 */

		actualizarInterfaz();

		mainStage.draw();
		uiStage.draw();

	}

	public void colide() {
		for (Barril barril : barriles) {
			if (player.overlaps(barril)) {
				// player.preventOverlap(barril);
				barril.preventOverlap(player);
			}

		}

	}

	public void centrarCamara() {
		this.camara.position.x = player.getX();
		this.camara.position.y = player.getY();
		camara.update();

	}

	public ArrayList<MapObject> getRectangleList(String propertyName) {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof RectangleMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("name") && props.get("name").equals(propertyName)) {
					list.add(obj);
				}

			}

		}

		return list;
	}

	public ArrayList<Polygon> getPolygonList(String propertyName) {

		Polygon poly;
		ArrayList<Polygon> list = new ArrayList<Polygon>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {

				if (!(obj instanceof PolygonMapObject))
					continue;
				MapProperties props = obj.getProperties();
				if (props.containsKey("name") && props.get("name").equals(propertyName)) {

					poly = ((PolygonMapObject) obj).getPolygon();
					list.add(poly);
				}

			}

		}

		return list;
	}

	public ArrayList<MapObject> getEnemyList() {
		ArrayList<MapObject> list = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			for (MapObject obj : layer.getObjects()) {
				if (!(obj instanceof TiledMapTileMapObject))
					continue;
				MapProperties props = obj.getProperties();

				TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
				TiledMapTile t = tmtmo.getTile();
				MapProperties defaultProps = t.getProperties();
				if (defaultProps.containsKey("Enemy")) {
					list.add(obj);

				}

				Iterator<String> propertyKeys = defaultProps.getKeys();
				while (propertyKeys.hasNext()) {
					String key = propertyKeys.next();

					if (props.containsKey(key))
						continue;
					else {
						Object value = defaultProps.get(key);
						props.put(key, value);
					}

				}

			}

		}

		return list;
	}

	private void actualizarInterfaz() {

	}

}
