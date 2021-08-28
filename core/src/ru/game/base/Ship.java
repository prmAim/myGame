package ru.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.sprite.Bullet;

/**
 * Класс объкта (Корабль)
 */
public class Ship extends Sprite {

    private static final float RELOAD_INTERVAL = 0.18f;  // Интервал выстелов

    protected final Vector2 v0;                 // Вектор скорости - шага для перемещения объета <корабль>
    protected final Vector2 v;                  // Вектор скорости

    protected Rect worldBounds;
    protected BulletPool bulletPool;              // Pool объектов <Пуля>
    protected TextureRegion bulletRegion;         // текстура объекта <Пуля>
    protected Vector2 bulletV;                    // скорость пули
    protected Vector2 bulletPos;                  // началные координаты объета <Пуля>
    protected float bulletHeight;                 // размер пули
    protected int bulletDamage;                   // Урон от пули
    protected float reloadTimer;                  // таймер
    protected float reloadInterval;               // Интервал выстелов
    protected Sound bulletSound;                  // звуковой эффект
    protected int hp;                             // размер здоровья

    public Ship() {
        super();
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }

    /**
     * Метод для изменения спринта. Его действий.
     * delta - это время изменения экрана
     */
    @Override
    public void update(float delta) {
        // Если длина вектора Mouse до вектора объета больше, то двигаемся, иначе объекта становиться в точке mouse.
        pos.mulAdd(v, delta);
        reloadTimer += delta;                   // счетчик времени
        if (reloadTimer >= reloadInterval) {
            shootShip();
            reloadTimer = 0f;
        }
    }

    /**
     * Метод стрельба
     */
    public void shootShip() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletSound.play(0.2f);
    }
}
