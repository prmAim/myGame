package ru.game.pool;

import ru.game.base.SpritePool;
import ru.game.math.Rect;
import ru.game.sprite.EnemyShip;

/**
 * Пул объетов
 */
public class EnemyPool extends SpritePool<EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(worldBounds, bulletPool);
    }
}
