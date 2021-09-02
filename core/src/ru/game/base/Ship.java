package ru.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.pool.ExplosionPool;
import ru.game.sprite.Bullet;
import ru.game.sprite.Explosion;

/**
 * Класс объкта (Корабль)
 */
public abstract class Ship extends Sprite {

    private static final float RELOAD_INTERVAL = 0.18f;         // Интервал выстелов
    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;  // Интревал повреждения <корабль>

    protected final Vector2 v0;                 // Вектор скорости - шага для перемещения объета <корабль>
    protected final Vector2 v;                  // Вектор скорости

    protected Rect worldBounds;
    protected ExplosionPool explosionPool;        // Pool объектов <Взрыв>
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

    private float damageAnimalTime = DAMAGE_ANIMATE_INTERVAL; // Имитация повреждения корабля. Переключение между спрайтами

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
        damageAnimalTime += delta;                              // впокое корабль не меняет фрайм = 0;
        if (damageAnimalTime >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    /**
     * Нанесение ущерба кораблю от полу
     */
    public void setDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            setDestroyed();
        }
        frame = 1;                  // При попадании <Пули> меняется фрайм
        damageAnimalTime = 0f;      // сбрасываем счетчик
    }

    /**
     * Метод стрельба
     */
    public void shootShip() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletSound.play(0.2f);
    }

    /**
     * Проверка на перекрещивание объекта <Пуля> и  <Корабль>
     */
    public abstract boolean isBulletCollision(Bullet bullet);

    public int getBulletDamage() {
        return bulletDamage;
    }

    /**
     * Метод вызова объекта <Взрыв>
     */
    private void isStartBoom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight());
    }

    /**
     * Уничтожение корабля
     */
    @Override
    public void setDestroyed() {
        super.setDestroyed();
        isStartBoom();              // при уничтожении объета <Корабль> вызываем взрыв
    }

    public int getHp() {
        return hp;
    }
}
