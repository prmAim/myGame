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
 * Класс объкта (Активный спрайт)
 */
public abstract class ActionSprite extends Sprite {
    protected final Vector2 v0;                 // Вектор скорости - шага для перемещения объета <корабль>
    protected final Vector2 v;                  // Вектор скорости

    protected Rect worldBounds;
    protected ExplosionPool explosionPool;        // Pool объектов <Взрыв>
    protected float reloadTimer;                  // таймер
    protected float reloadInterval;               // Интервал выстелов
    protected int hp;                             // размер здоровья

    public ActionSprite() {
        super();
        v0 = new Vector2();
        v = new Vector2();
    }

    public ActionSprite(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
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
    }

    /**
     * Проверка на перекрещивание объекта <Пуля> и  <Корабль>
     */
    public abstract boolean isBulletCollision(Bullet bullet);

    /**
     * Уничтожение корабля
     */
    @Override
    public void setDestroyed() {
        super.setDestroyed();
        isStartBoom();              // при уничтожении объета <Корабль> вызываем взрыв
    }

    /**
     * Метод вызова объекта <Взрыв>
     */
    private void isStartBoom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight());
    }

    public int getHp() {
        return hp;
    }
}
