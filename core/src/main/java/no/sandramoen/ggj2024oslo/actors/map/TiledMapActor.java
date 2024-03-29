package no.sandramoen.ggj2024oslo.actors.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Iterator;

import no.sandramoen.ggj2024oslo.utils.BaseActor;
import no.sandramoen.ggj2024oslo.utils.BaseGame;

public class TiledMapActor extends Actor {
    public static float mapTileWidth;
    public static float mapTileHeight;

    private static TiledMap tiledMap;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;

    public TiledMapActor(TiledMap tiledMap, Stage stage) {
        this.tiledMap = tiledMap;

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(this.tiledMap, BaseGame.UNIT_SCALE);
        tiledMapRenderer.setBlending(true);

        setMapSize();
        centerPositionCamera(stage);
        BaseActor.setWorldBounds(mapTileWidth, mapTileHeight);
        stage.addActor(this);
    }

    public ArrayList<MapObject> getRectangleList(String propertyName) {
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : layer.getObjects()) {
                if (!(obj instanceof RectangleMapObject))
                    continue;

                MapProperties props = obj.getProperties();

                if (props.containsKey("name") && props.get("name").equals(propertyName))
                    list.add(obj);
            }
        }
        return list;
    }

    public ArrayList<MapObject> getTileList(String layerName, String propertyName) {
        ArrayList<MapObject> list = new ArrayList();

        for (MapLayer layer : tiledMap.getLayers()) {
            if (layer.getName().equalsIgnoreCase(layerName))
                for (MapObject obj : layer.getObjects()) {
                    if (!(obj instanceof TiledMapTileMapObject))
                        continue;

                    MapProperties props = obj.getProperties();

                    // Default MapProperties are stored within associated Tile object
                    // Instance-specific overrides are stored in MapObject

                    TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
                    TiledMapTile t = tmtmo.getTile();
                    MapProperties defaultProps = t.getProperties();

                    if (defaultProps.containsKey("name") && defaultProps.get("name").equals(propertyName))
                        list.add(obj);

                    // get list of default property keys
                    Iterator<String> propertyKeys = defaultProps.getKeys();

                    // iterate over keys; copy default values into props if needed
                    while (propertyKeys.hasNext()) {
                        String key = propertyKeys.next();

                        // check if value already exists; if not, create property with default value
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

    public void draw(Batch batch, float parentAlpha) {
        tiledMapRenderer.setView((OrthographicCamera) getStage().getCamera());
        tiledMapRenderer.render();
    }

    public static void centerPositionCamera(Stage stage) {
        // System.out.println(mapTileWidth / 2 + ", " + mapTileHeight / 2);
        OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
        camera.zoom = 1f;
        camera.position.set(
                mapTileWidth / 2,
                mapTileHeight / 2,
                0f
        );
        camera.update();
    }

    private void setMapSize() {
        mapTileWidth = (float) tiledMap.getProperties().get("width", Integer.class);
        mapTileHeight = (float)  tiledMap.getProperties().get("height", Integer.class);
    }

    private static Vector2 calculateAbsoluteCenterOfMap() {
        int tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        int tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);
        int numTilesHorizontal = tiledMap.getProperties().get("width", Integer.class);
        int numTilesVertical = tiledMap.getProperties().get("height", Integer.class);
        int mapWidth = tileWidth * numTilesHorizontal;
        int mapHeight = tileHeight * numTilesVertical;

        System.out.println("center of map: (" + mapWidth / 2 + ", " + mapHeight / 2 + ")");
        System.out.println("center of screen: (" + Gdx.graphics.getWidth() / 2 + ", " + Gdx.graphics.getHeight() / 2 + ")");
        return new Vector2(mapWidth / 2, mapHeight / 2);
    }
}
