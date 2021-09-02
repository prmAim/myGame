package ru.game.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.math.Rnd;
import ru.game.pool.EnemyPool;
import ru.game.sprite.EnemyShip;

public class EnemyEmitter {
    private static final float GENERATE_INTERVAL = 4f;              // МАЛЕНЬКИЙ. Частота создания корабля
    private static final int COUNT_FRAGS_TO_LEVEL = 5;             // Кол-во кораблей для перехода на следующий уровень

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;           // МАЛЕНЬКИЙ. Размер корабля
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;    // МАЛЕНЬКИЙ. частота выстрелов
    private static final int ENEMY_SMALL_HP = 1;                    // МАЛЕНЬКИЙ. здоровье корабля
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;   // МАЛЕНЬКИЙ. размер пули
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;         // МАЛЕНЬКИЙ. урон от пули

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;         // СРЕДНИЙ. Размер корабля
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;   // СРЕДНИЙ. частота выстрелов
    private static final int ENEMY_MEDIUM_HP = 5;                   // СРЕДНИЙ. здоровье корабля
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;  // СРЕДНИЙ. размер пули
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;        // СРЕДНИЙ. урон от пули

    private static final float ENEMY_BIG_HEIGHT = 0.2f;             // Большой. Размер корабля
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;      // Большой. частота выстрелов
    private static final int ENEMY_BIG_HP = 10;                     // Большой. здоровье корабля
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;     // Большой. размер пули
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;          // Большой. урон от пули

    private final TextureRegion[] enemySmallRegions;                           // МАЛЕНЬКИЙ. текстура
    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);          // МАЛЕНЬКИЙ. Скорость корабля
    private final Vector2 enemySmallBulletV = new Vector2(0f, -0.4f);    // МАЛЕНЬКИЙ. Скорость пули
    private final TextureRegion[] enemyMediumRegions;                          // СРЕДНИЙ. текстура
    private final Vector2 enemyMediumV = new Vector2(0f, -0.03f);        // СРЕДНИЙ. Скорость корабля
    private final Vector2 enemyMediumBulletV = new Vector2(0f, -0.3f);   // СРЕДНИЙ. Скорость пули
    private final TextureRegion[] enemyBigRegions;                             // Большой. текстура
    private final Vector2 enemyBigV = new Vector2(0f, -0.005f);           // Большой. Скорость корабля
    private final Vector2 enemyBigBulletV = new Vector2(0f, -0.3f);      // Большой. Скорость пули


    private final EnemyPool enemyPool;

    private final TextureRegion bulletRegion;

    private Rect worldBounds;
    private Sound bulletSound;

    private float genereteTimer;

    private int level;

    public EnemyEmitter(EnemyPool enemyPool, Rect worldBounds, Sound bulletSound, TextureAtlas atlas) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        this.bulletSound = bulletSound;
        bulletRegion = atlas.findRegion("bulletEnemy");                                            // текстура пули
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);   // текстура корабля
        enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);  // текстура корабля
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);     // текстура корабля
    }

    /**
     * Метод генерации кораблей
     */
    public void generateShip(float delta, int frags) {
        level = frags / COUNT_FRAGS_TO_LEVEL + 1;
        genereteTimer += delta;
        if (genereteTimer >= GENERATE_INTERVAL) {
            genereteTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();       // Создать корабль  = перенести корабль в <активный pool>
            float typeShip = (float) Math.random();         // Генератор выбора корабля
            if (typeShip < 0.5f) {
                enemyShip.set(
                        enemySmallRegions,                      // текстура корабля
                        enemySmallV,                            // Начальная скорость корабля
                        bulletRegion,                           // Текстура пули
                        enemySmallBulletV,                      // Скорость пули
                        ENEMY_SMALL_BULLET_HEIGHT,              // Размер пули
                        ENEMY_SMALL_BULLET_DAMAGE * level,              // урон от пули
                        bulletSound,                            // Звук пули
                        ENEMY_SMALL_RELOAD_INTERVAL,            // частота выстрелов
                        ENEMY_SMALL_HEIGHT,                     // Размер корабля
                        ENEMY_SMALL_HP * level              // Здоровье корабля
                );
            } else if (typeShip < 0.8f) {
                enemyShip.set(
                        enemyMediumRegions,                      // текстура корабля
                        enemyMediumV,                            // Начальная скорость корабля
                        bulletRegion,                            // Текстура пули
                        enemyMediumBulletV,                      // Скорость пули
                        ENEMY_MEDIUM_BULLET_HEIGHT,              // Размер пули
                        ENEMY_MEDIUM_BULLET_DAMAGE * level,              // урон от пули
                        bulletSound,                             // Звук пули
                        ENEMY_MEDIUM_RELOAD_INTERVAL,            // частота выстрелов
                        ENEMY_MEDIUM_HEIGHT,                     // Размер корабля
                        ENEMY_MEDIUM_HP * level              // Здоровье корабля
                );
            } else {
                enemyShip.set(
                        enemyBigRegions,                      // текстура корабля
                        enemyBigV,                            // Начальная скорость корабля
                        bulletRegion,                         // Текстура пули
                        enemyBigBulletV,                      // Скорость пули
                        ENEMY_BIG_BULLET_HEIGHT,              // Размер пули
                        ENEMY_BIG_BULLET_DAMAGE * level,  // урон от пули
                        bulletSound,                          // Звук пули
                        ENEMY_BIG_RELOAD_INTERVAL,            // частота выстрелов
                        ENEMY_BIG_HEIGHT,                     // Размер корабля
                        ENEMY_BIG_HP * level              // Здоровье корабля
                );
            }
            // устанавливаем положение корабля
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(), worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }

    }

    public int getLevel() {
        return level;
    }
}
