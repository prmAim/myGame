package ru.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.game.base.Sprite;
import ru.game.math.Rect;

/**
 * Спрайт фонового поля
 */
public class Background extends Sprite {

    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());   // Установка ширины и высоты
        pos.set(worldBounds.pos);                       // Центровка фона
    }
}
