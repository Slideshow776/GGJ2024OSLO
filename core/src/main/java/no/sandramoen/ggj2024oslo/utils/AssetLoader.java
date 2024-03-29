package no.sandramoen.ggj2024oslo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class AssetLoader implements AssetErrorListener {

    public static TextureAtlas textureAtlas;
    public static Skin mySkin;

    public static String defaultShader;
    public static String shockwaveShader;

    public static Sound click1Sound;
    public static Sound hoverOverEnterSound;
    public static Sound clockTickingSound;
    public static Sound gameOverSound;
    public static Array<Array<Sound>> fartSounds = new Array();

    public static Array<Music> music;
    public static Music levelMusic;

    public static Array<TiledMap> maps;
    public static TiledMap testMap;
    public static TiledMap currentLevel;

    static {
        long time = System.currentTimeMillis();
        BaseGame.assetManager = new AssetManager();
        BaseGame.assetManager.setErrorListener(new AssetLoader());

        loadAssets();
        BaseGame.assetManager.finishLoading();
        assignAssets();

        Gdx.app.log(AssetLoader.class.getSimpleName(), "Asset manager took " + (System.currentTimeMillis() - time) + " ms to load all game assets.");
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(AssetLoader.class.getSimpleName(), "Could not load asset: " + asset.fileName, throwable);
    }

    public static Label.LabelStyle getLabelStyle(String fontName) {
        return new Label.LabelStyle(AssetLoader.mySkin.get(fontName, BitmapFont.class), null);
    }

    private static void loadAssets() {
        // images
        BaseGame.assetManager.setLoader(Text.class, new TextLoader(new InternalFileHandleResolver()));
        BaseGame.assetManager.load("images/included/packed/images.pack.atlas", TextureAtlas.class);

        // music
        BaseGame.assetManager.load("audio/music/FartInJar_sketsj1_Bsw.wav", Music.class);

        // sounds
        BaseGame.assetManager.load("audio/sound/click1.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/hoverOverEnter.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/clock ticking.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/Game over FIJ Bsw.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/1/Fart.1..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/1/Fart.1.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/2/Fart.2...wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/2/Fart.2..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/2/Fart.2.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/3/Fart.3...wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/3/Fart.3..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/3/Fart.3.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/4/Fart.4...wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/4/Fart.4.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/4/Fart.4.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/5/Fart.5..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/5/Fart.5.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/6/Fart.6..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/6/Fart.6.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/7/Fart.7..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/7/Fart.7.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/8/Fart.8...wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/8/Fart.8..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/8/Fart.8.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/9/Fart.9..wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/9/Fart.9.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/10/Fart.10.wav", Sound.class);
        BaseGame.assetManager.load("audio/sound/farts/11/Fart.11..wav", Sound.class);

        // i18n

        // shaders
        BaseGame.assetManager.load(new AssetDescriptor("shaders/default.vs", Text.class, new TextLoader.TextParameter()));
        BaseGame.assetManager.load(new AssetDescriptor("shaders/shockwave.fs", Text.class, new TextLoader.TextParameter()));

        // skins

        // fonts

        // tiled maps
        BaseGame.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        BaseGame.assetManager.load("maps/test.tmx", TiledMap.class);

        // other
        // BaseGame.assetManager.load(AssetDescriptor("other/jentenavn.csv", Text::class.java, TextLoader.TextParameter()))
    }

    private static void assignAssets() {
        // images
        textureAtlas = BaseGame.assetManager.get("images/included/packed/images.pack.atlas");

        // music
        music = new Array();
        levelMusic = BaseGame.assetManager.get("audio/music/FartInJar_sketsj1_Bsw.wav", Music.class);
        music.add(levelMusic);

        // sounds
        click1Sound = BaseGame.assetManager.get("audio/sound/click1.wav", Sound.class);
        hoverOverEnterSound = BaseGame.assetManager.get("audio/sound/hoverOverEnter.wav", Sound.class);
        clockTickingSound = BaseGame.assetManager.get("audio/sound/clock ticking.wav", Sound.class);
        gameOverSound = BaseGame.assetManager.get("audio/sound/Game over FIJ Bsw.wav", Sound.class);

        loadFartSounds();

        // i18n

        // shaders
        defaultShader = BaseGame.assetManager.get("shaders/default.vs", Text.class).getString();
        shockwaveShader = BaseGame.assetManager.get("shaders/shockwave.fs", Text.class).getString();

        // skins
        mySkin = new Skin(Gdx.files.internal("skins/mySkin/mySkin.json"));

        // fonts
        loadFonts();

        // tiled maps
        loadTiledMap();

        // other
    }

    private static void loadFonts() {
        float scale = Gdx.graphics.getHeight() * .000656f; // magic number ensures scale ~= 1, based on screen width
        // float scale = Gdx.graphics.getWidth() * .000656f; // magic number ensures scale ~= 1, based on screen width
        scale *= 1.01f; // make x percent bigger, bigger = more fuzzy

        mySkin.getFont("Play-Bold20white").getData().setScale(scale);
        mySkin.getFont("Play-Bold40white").getData().setScale(scale);
        mySkin.getFont("Play-Bold59white").getData().setScale(scale);
    }

    private static void loadTiledMap() {
        testMap = BaseGame.assetManager.get("maps/test.tmx", TiledMap.class);
        maps = new Array();
        maps.add(testMap);
    }

    private static void loadFartSounds() {
        Array<Sound> temp;

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/1/Fart.1..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/1/Fart.1.wav", Sound.class));
        fartSounds.add(temp);


        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/2/Fart.2...wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/2/Fart.2..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/2/Fart.2.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/3/Fart.3...wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/3/Fart.3..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/3/Fart.3.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/4/Fart.4...wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/4/Fart.4.wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/4/Fart.4.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/5/Fart.5..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/5/Fart.5.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/6/Fart.6..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/6/Fart.6.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/7/Fart.7..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/7/Fart.7.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/8/Fart.8...wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/8/Fart.8..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/8/Fart.8.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/9/Fart.9..wav", Sound.class));
        temp.add(BaseGame.assetManager.get("audio/sound/farts/9/Fart.9.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/10/Fart.10.wav", Sound.class));
        fartSounds.add(temp);

        temp = new Array<>();
        temp.add(BaseGame.assetManager.get("audio/sound/farts/11/Fart.11..wav", Sound.class));
        fartSounds.add(temp);
    }
}
