package ru.game.pool;

import ru.game.base.SpritePool;
import ru.game.sprite.Bullet;

/**
 * Poll объектов <Пуля>
 */
public class BulletPool extends SpritePool<Bullet> {

    /**
     * Создание нового объекта <Пуля>
     */
    @Override
    protected Bullet newSprite() {
        return new Bullet();
    }
}
