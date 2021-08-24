package ru.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.Sprite;
import ru.game.math.Rect;

/**
 * Объект <Пуля>
 */
public class Bullet extends Sprite {

    private Rect worldBounds;       // границы игрового поля
    private Vector2 v;              // вектор скорости объета
    private int damage;             // Урон, который наносит пуля
    private Sprite owner;           // владелец пули

    public Bullet() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    public void set(
            Sprite owner,               // владелец пули
            TextureRegion region,       // текстура
            Vector2 pos0,               // начальная позиция поли
            Vector2 v0,                 // начальная скорост пули
            float height,               // высота объекта Пуля => (размер)
            Rect worldBounds,           // границы мира
            int damage                  // урон от пули
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        // Если объект вылетил за границы, то его можно поместить в pool.
        if (isOutside(worldBounds)) {
            setDestroyed();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
