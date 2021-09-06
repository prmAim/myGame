package ru.game.pool;

import ru.game.base.SpritePool;
import ru.game.math.Rect;
import ru.game.sprite.Bonus;

/**
 * Pool объетов <Бонус>
 */
public class BonusPool extends SpritePool<Bonus> {
    private final Rect worldBounds;
    private final ExplosionPool explosionPool;

    public BonusPool(Rect worldBounds, ExplosionPool explosionPool) {
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    protected Bonus newSprite() {
        return new Bonus(worldBounds, explosionPool);
    }
}
