package ru.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.Ship;
import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.pool.ExplosionPool;

public class EnemyShip extends Ship {

    public EnemyShip(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool) {
        super();
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if (getTop() < worldBounds.getTop()) {       // усколение объекта корабль, что бы они появились быстрее
            v.set(v0);
        } else {
            reloadTimer = reloadInterval * 0.8f;    // ускоряем таймер, что бы стрельба началась раньше
        }
        if (getBottom() < worldBounds.getBottom()) {     // если корабль перешел нижную границу экрана, то его переносим в <свободный пул>
            isDestroyed();
        }
    }

    /**
     * Установка параметров объекта Корабль
     */
    public void set(
            TextureRegion[] regions,            // текстуры
            Vector2 v0,                         // вектор начальной скорости
            TextureRegion bulletRegion,         // текстура пули
            Vector2 bulletV,                    // скорость пули
            float bulletHeight,                 // размер пули
            int bulletDamage,                   // урон от пули
            Sound bulletSound,                  // звуковой эффект
            float reloadInterval,               // период интервала
            float height,                       // размер
            int hp                              // здоровье корабля
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletV.set(bulletV);
        this.bulletHeight = bulletHeight;
        this.bulletDamage = bulletDamage;
        this.bulletSound = bulletSound;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        v.set(0f, -0.4f);
    }

    /**
     * Проверка на пересечение спрайта <Пуля> и <Корабля>
     */
    @Override
    public boolean isBulletCollision(Bullet bullet) {
        return !(
                bullet.getRight() < getLeft() || bullet.getLeft() > getRight() || bullet.getBottom() > getTop() ||
                        bullet.getTop() < pos.y     // Пуля добирается до центра корабля
        );
    }

    @Override
    public void setDestroyed() {
        super.setDestroyed();
        reloadTimer = 0f;
    }
}
