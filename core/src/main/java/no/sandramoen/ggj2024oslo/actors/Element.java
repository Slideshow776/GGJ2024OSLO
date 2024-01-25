package no.sandramoen.ggj2024oslo.actors;

import static no.sandramoen.ggj2024oslo.utils.BaseGame.BOX2D_ALL;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import no.sandramoen.ggj2024oslo.actors.components.BodyComponent;
import no.sandramoen.ggj2024oslo.actors.components.PlayerControlComponent;
import no.sandramoen.ggj2024oslo.actors.map.TiledMapActor;
import no.sandramoen.ggj2024oslo.utils.BaseActor;
import no.sandramoen.ggj2024oslo.utils.BaseGame;

public class Element extends BaseActor {
    public boolean isDead;
    public enum Type {RED, YELLOW, BLUE}

    private final Vector2 bodyOffset = new Vector2(0f, 0f);
    private Body body;

    public Element(float x, float y, Stage stage, Engine engine, World world) {
        super(x, y, stage, engine);

        loadImage("whitePixel");
        setSize(.35f, .35f);

        body = createBody(world);
        entity.add(new BodyComponent(body));
        entity.add(new PlayerControlComponent());
        engine.addEntity(entity);
    }

    private void shakeCamera(float duration) {
        isShakyCam = true;
        new BaseActor(0f, 0f, getStage()).addAction(Actions.sequence(
            Actions.delay(duration),
            Actions.run(() -> {
                isShakyCam = false;
                TiledMapActor.centerPositionCamera(getStage());
            })
        ));
    }

    private Body createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = (getX() + getWidth() / 2) + bodyOffset.x;
        bodyDef.position.y = (getY() + getHeight() / 2) + bodyOffset.y;

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(true);
        body.setUserData(this);

        CircleShape circle = new CircleShape();
        circle.setRadius(getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;
        fixtureDef.restitution = .1f;

        fixtureDef.filter.categoryBits = BaseGame.BOX2D_ALL;
        fixtureDef.filter.maskBits = BaseGame.BOX2D_ONE;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("element");
        circle.dispose();

        return body;
    }

}
