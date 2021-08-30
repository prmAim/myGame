package ru.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.game.base.Sprite;
import ru.game.math.Rect;

public class GameOverButton extends Sprite {
    private static final float HEIGHT_SIZE = 0.08f; // Размер сообщения

    public GameOverButton(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"), 1, 1, 1);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT_SIZE);
    }
}
