package no.sandramoen.ggj2024oslo.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.github.tommyettinger.textra.TypingLabel;

import no.sandramoen.ggj2024oslo.utils.AssetLoader;
import no.sandramoen.ggj2024oslo.utils.BaseGame;
import no.sandramoen.ggj2024oslo.utils.GameUtils;

public class MadeByLabel extends TypingLabel {

    public MadeByLabel() {
        super("Made by Sandra Moen 2024", new Label.LabelStyle(AssetLoader.mySkin.get("Play-Bold20white", BitmapFont.class), null));
        setAlignment(Align.center);
        setColor(Color.GRAY);
        addClickListener();
        GameUtils.setWidgetHoverColor(this);
    }

    private void addClickListener() {
        addListener(
                (Event event) -> {
                    if (GameUtils.isTouchDownEvent(event))
                        openURIWithDelay();
                    return false;
                }
        );
    }

    private void openURIWithDelay() {
        AssetLoader.click1Sound.play(BaseGame.soundVolume);
        addAction(Actions.sequence(
                Actions.delay(.5f),
                Actions.run(() -> Gdx.net.openURI("https://sandramoen.no"))
        ));
    }
}