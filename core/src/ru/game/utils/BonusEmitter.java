package ru.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.math.Rnd;
import ru.game.pool.BonusPool;
import ru.game.sprite.Bonus;
import ru.game.sprite.MainShip;

/**
 * Генератор объетов <Бонус>
 */
public class BonusEmitter {
    private static final float GENERATE_INTERVAL = 6f;                  // Частота создания объетов <Бонус>

    private static final float BONUS_PLANET_HEIGHT = 0.08f;             // Бонус-<Планета>. Размер объета
    private static final float BONUS_PLANET_RELOAD_INTERVAL = 1f;       // Бонус-<Планета>. частота выстрелов
    private static final int BONUS_PLANET_HP = 1;                       // Бонус-<Планета>. здоровье объета
    private static final String BONUS_PLANET_ACTION = "minusHP";        // действие Бонус-<Планета>

    private static final float BONUS_SHINE_HEIGHT = 0.06f;              // Бонус-<Сияние>. Размер объета
    private static final float BONUS_SHINE_RELOAD_INTERVAL = 1f;        // Бонус-<Сияние>. частота выстрелов
    private static final int BONUS_SHINE_HP = 10;                       // Бонус-<Сияние>. здоровье объета
    private static final String BONUS_SHINE_ACTION = "plusHP";          // действие Бонус-<Сияние>

    private final TextureRegion[] bonusPlanetRegions;                   // текстура Бонус-<Планета>
    private final Vector2 bonusPlanetV = new Vector2(0f, -0.2f);  // Скорость Бонус-<Планета>
    private final TextureRegion[] bonusShineRegions;                    // текстура Бонус-<Сияние>
    private final Vector2 bonusShineV = new Vector2(0f, -0.1f);   // Скорость Бонус-<Сияние>


    private final BonusPool bonusPool;
    private final MainShip mainShip;

    private Rect worldBounds;

    private float genereteTimer;

    public BonusEmitter(BonusPool bonusPool, Rect worldBounds, TextureAtlas atlasBonus, MainShip mainShip) {
        this.bonusPool = bonusPool;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
        this.bonusPlanetRegions = Regions.split(atlasBonus.findRegion("planet"), 4, 4, 13);   // текстура объетов <Бонус>
        this.bonusShineRegions = Regions.split(atlasBonus.findRegion("shine"), 4, 4, 15);   // текстура объетов <Бонус>
    }

    /**
     * Метод генерации кораблей
     */
    public void generateBonus(float delta) {
        genereteTimer += delta;
        if (genereteTimer >= GENERATE_INTERVAL) {
            genereteTimer = 0f;
            Bonus bonus = bonusPool.obtain();           // Создать объект Бонус-<Планета>  = перенести в <активный pool>
            float typeBonus = (float) Math.random();    // Генератор выбора корабля
            if (typeBonus < 0.6f) {
                bonus.set(
                        bonusPlanetRegions,            // текстура Бонус-<Планета>
                        bonusPlanetV,                  // Начальная скорость Бонус-<Планета>
                        BONUS_PLANET_RELOAD_INTERVAL,  // частота
                        BONUS_PLANET_HEIGHT,           // Размер Бонус-<Планета>
                        BONUS_PLANET_HP,               // Здоровье Бонус-<Планета>
                        BONUS_PLANET_ACTION,           // действие Бонус-<Планета>
                        mainShip
                );
            } else {
                bonus.set(
                        bonusShineRegions,            // текстура Бонус-<Сияние>
                        bonusShineV,                  // Начальная скорость Бонус-<Планета>
                        BONUS_SHINE_RELOAD_INTERVAL,  // частота
                        BONUS_SHINE_HEIGHT,           // Размер Бонус-<Сияние>
                        BONUS_SHINE_HP,               // Здоровье Бонус-<Сияние>
                        BONUS_SHINE_ACTION,           // действие Бонус-<Сияние>
                        mainShip
                );
            }
            // устанавливаем положение Бонус-<Планета>
            bonus.pos.x = Rnd.nextFloat(worldBounds.getLeft() + bonus.getHalfWidth(), worldBounds.getRight() - bonus.getHalfWidth());
            bonus.setBottom(worldBounds.getTop());
        }

    }
}
