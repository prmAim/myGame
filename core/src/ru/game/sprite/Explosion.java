package ru.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.Sprite;

/**
 * Объект <Взрыв>
 */
public class Explosion extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.017f;   // интревал работы анимации

    private final Sound explosionSound;
    private float animateTimer;

    public Explosion(TextureAtlas atlas, Sound explosionSound) {
        super(atlas.findRegion("explosion"), 9, 9, 74); // подключение текстуры
        this.explosionSound = explosionSound;
    }

    /**
     * Установка позиций объекта <Взрыв>
     */
    public void set(Vector2 pos, float height) {
        this.pos.set(pos);                  // координаты взрыва
        setHeightProportion(height);        // размер взрыва
        explosionSound.play(0.2f);   // воспроизведение взрыва
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                setDestroyed();                 // установить в деактивацию взрыва
            }
        }
    }

    @Override
    public void setDestroyed() {
        super.setDestroyed();
        frame = 0;
    }
}
