package ru.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.game.base.BaseScreen;
import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.pool.EnemyPool;
import ru.game.pool.ExplosionPool;
import ru.game.sprite.Background;
import ru.game.sprite.Bullet;
import ru.game.sprite.EnemyShip;
import ru.game.sprite.GameOverButton;
import ru.game.sprite.MainShip;
import ru.game.sprite.NewGameButton;
import ru.game.sprite.Star;
import ru.game.utils.EnemyEmitter;

/**
 * Класс потомом <Окно Игры>
 */
public class GameScreen extends BaseScreen {
    private final int COUNT_STARS = 128;        // кол-во звезд

    private Texture imgBg;                      // Текстура заднего фона
    private TextureAtlas atlas;

    private Background spiteBackground;         // объект задний фон
    private Star[] stars;                       // массив объектов Звезда
    private BulletPool bulletPool;              // pool объектов <Пуля>
    private EnemyPool enemyPool;                // pool объектов <Корабль>
    private ExplosionPool explosionPool;        // pool объектов <Взрыв>

    private MainShip mainShip;                  // Объект летающий корабль

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemyEmitter enemyEmitter;          // создание вражеского корабля
    private GameOverButton gameOverButton;      // создание спрайта <Конец игры>
    private NewGameButton newGameBotton;        // создание спрайта <Начало игры>

    /**
     * Показать экран Меню
     */
    @Override
    public void show() {
        super.show();
        imgBg = new Texture("textures/bg.png");
        spiteBackground = new Background(imgBg);               // splite для фоновой картинки с тестурой

        atlas = new TextureAtlas("textures/mainAtlas.tpack");   // Подключение текстур атласа.
        stars = new Star[COUNT_STARS];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));        // Вызов звуков выстрелов
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));      // Вызов звуков выстрелов
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")); // Вызов звуков взрыва

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyPool, worldBounds, bulletSound, atlas);

        gameOverButton = new GameOverButton(atlas);
        newGameBotton = new NewGameButton(atlas, bulletPool, enemyPool, mainShip);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));          // Вызов фоновой музыки
        music.play();
    }

    /**
     * Переопределяем метод для того, чтобы объект находился к координатной сетке (а не координаты LBX)
     */
    @Override
    public void resize(Rect worldBounds) {
        spiteBackground.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOverButton.resize(worldBounds);
        newGameBotton.resize(worldBounds);
    }

    /**
     * Обновление экрана 60 секунд экрана меню
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void dispose() {
        super.dispose();
        imgBg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        newGameBotton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        newGameBotton.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    /**
     * Изменение положения объектов
     */
    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generateShip(delta);
        }
    }

    /**
     * Отрисовка объектов
     */
    private void draw() {
        batch.begin();
        spiteBackground.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            newGameBotton.draw(batch);
            gameOverButton.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    /**
     * Освобождаем все объеты из <активным pool> в <свободный pool>
     */
    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    /**
     * Проверка на взаимодействие объектов
     */
    public void checkCollisions() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveSprits();
        // проверка на столкновение кораблей и игрового корабля
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDistance = mainShip.getHalfWidth() + enemyShip.getHalfWidth();
            if (mainShip.pos.dst(enemyShip.pos) < minDistance) {
                mainShip.setDamage(enemyShip.getBulletDamage() * 2);
                enemyShip.setDestroyed();
            }
        }
        // Проверка на перекрещивание спрайтов <Корабль> и <Пуля>
        List<Bullet> bulletList = bulletPool.getActiveSprits();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed() || bullet.getOwner() != mainShip) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.setDamage(bullet.getDamage());
                    bullet.setDestroyed();
                }
            }
            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)) {
                mainShip.setDamage(bullet.getDamage());
                bullet.setDestroyed();
            }
        }
    }
}
