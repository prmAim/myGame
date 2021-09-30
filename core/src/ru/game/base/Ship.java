package ru.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.pool.BulletPool;
import ru.game.sprite.Bullet;

/**
 * Класс объкта (Корабль)
 */
public class Ship extends ActionSprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;  // Интревал повреждения <корабль>

    private float damageAnimalTime = DAMAGE_ANIMATE_INTERVAL; // Имитация повреждения корабля. Переключение между спрайтами

    protected BulletPool bulletPool;              // Pool объектов <Пуля>
    protected TextureRegion bulletRegion;         // текстура объекта <Пуля>
    protected Vector2 bulletV;                    // скорость пули
    protected Vector2 bulletPos;                  // началные координаты объета <Пуля>
    protected float bulletHeight;                 // размер пули
    protected int bulletDamage;                   // Урон от пули
    protected Sound bulletSound;                  // звуковой эффект

    public Ship() {
        super();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
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
    @Override
    public void setDamage(int damage) {
        super.setDamage(damage);
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

    public int getBulletDamage() {
        return bulletDamage;
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
}
