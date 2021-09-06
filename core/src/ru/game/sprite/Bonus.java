package ru.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.ActionSprite;
import ru.game.math.Rect;
import ru.game.pool.ExplosionPool;

/**
 * Класс объкта <Бонус>
 */
public class Bonus extends ActionSprite {
    private static final float ANIMATE_INTERVAL = 0.25f;         // интревал работы анимации
    private static final String BONUS_PLANET_ACTION = "minusHP"; // действие Бонус-<Планета>
    private static final String BONUS_SHINE_ACTION = "plusHP";   // действие Бонус-<Сияние>
    private static final int BONUS_HP_ACTION = 10;               // действие Бонус-<Сияние>

    private float animateTimer;
    private String action;
    private MainShip mainShip;

    public Bonus(Rect worldBounds, ExplosionPool explosionPool) {
        super();
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        frame = 0;
    }

    /**
     * Установка параметров объекта
     */
    public void set(
            TextureRegion[] regions,  // текстуры
            Vector2 v0,               // вектор начальной скорости
            float reloadInterval,     // период интервала
            float height,             // размер
            int hp,                   // здоровье объкта <Бонус>
            String action,            // действие Бонуса
            MainShip mainShip

    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        v.set(0f, -0.4f);
        this.action = action;
        this.mainShip = mainShip;
    }

    /**
     * Метод для изменения спринта. Его действий.
     * delta - это время изменения экрана
     */
    @Override
    public void update(float delta) {
        // Если длина вектора Mouse до вектора объета больше, то двигаемся, иначе объекта становиться в точке mouse.
        pos.mulAdd(v, delta);
        if (getTop() < worldBounds.getTop()) {       // усколение объкта <Бонус>, что бы они появились быстрее
            v.set(v0);
        } else {
            reloadTimer = reloadInterval * 0.8f;    // ускоряем таймер, что бы стрельба началась раньше
        }
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                frame = 0;                 // установить в деактивацию взрыва
            }
        }
        if (getBottom() < worldBounds.getBottom()) {     // если объкт <Бонус> перешел нижную границу экрана, то его переносим в <свободный пул>
            isDestroyed();
        }
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
        action();
        reloadTimer = 0f;
        frame = 0;
    }

    /**
     * Действие бонуса
     */
    public void action() {
        // Отнимаем здоровье
        if (action == BONUS_PLANET_ACTION) {
            mainShip.setDamage(BONUS_HP_ACTION);
        }
        // Добавялем здоровье
        if (action == BONUS_SHINE_ACTION) {
            mainShip.setDamage(-1 * BONUS_HP_ACTION);
        }
    }
}
