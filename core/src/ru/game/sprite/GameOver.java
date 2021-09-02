package ru.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.game.base.Sprite;
import ru.game.math.Rect;

/**
 * Спрайт <Конец игры>
 */
public class GameOver extends Sprite {

    private static final float HEIGHT = 0.08f;          // Размер
    private static final float BOTTOM_MARGIN = 0.01f;   // Отступ от центра

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(BOTTOM_MARGIN);
    }
}
