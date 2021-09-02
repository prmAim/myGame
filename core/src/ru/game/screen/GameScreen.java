package ru.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.game.base.BaseScreen;
import ru.game.base.Font;
import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.pool.EnemyPool;
import ru.game.pool.ExplosionPool;
import ru.game.sprite.Background;
import ru.game.sprite.Bullet;
import ru.game.sprite.EnemyShip;
import ru.game.sprite.GameOver;
import ru.game.sprite.MainShip;
import ru.game.sprite.NewGameButton;
import ru.game.sprite.Star;
import ru.game.utils.EnemyEmitter;

/**
 * Класс потомом <Окно Игры>
 */
public class GameScreen extends BaseScreen {
    private static final int COUNT_STARS = 128;    // кол-во звезд
    private static final String FRAGS = "Frags: "; // Для вывода данных
    private static final String HP = "HP: "; // Для вывода данных
    private static final String LEVEL = "Level: "; // Для вывода данных
    private static final float PADDING = 0.01f;    // Отступ

    private Texture imgBg;                          // Текстура заднего фона
    private TextureAtlas atlas;                     // Сборник тексутр

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

    private GameOver gameOver;                  // Сообщение <Конец Игры>
    private NewGameButton newGameButton;        // Кнопка <Начала игры>

    private int frags;                          // кол-во убитых врагов
    private StringBuilder sbFrags;              // Объект для изменения строки <Убитых кораблей>
    private StringBuilder sbHp;                 // Объект для изменения строки <Размер жизни>
    private StringBuilder sbLevel;              // Объект для изменения строки <Уровень>
    private Font font;                          // Объект для отрисовки строковых данных в графический вид

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

        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas, this);

        frags = 0;
        sbFrags = new StringBuilder();
        sbLevel = new StringBuilder();
        sbHp = new StringBuilder();
        font = new Font("font/font.fnt", "font/font.png");      // текстуры букв
        font.setSize(0.02f);                                                    // размер букв

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
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
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
        if (mainShip.isDestroyed()) {
            newGameButton.touchDown(touch, pointer, button);
        } else {
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()) {
            newGameButton.touchUp(touch, pointer, button);
        } else {
            mainShip.touchUp(touch, pointer, button);
        }
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
            enemyEmitter.generateShip(delta, frags);
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
            gameOver.draw(batch);
            // все взрывы проигрались, то показываем новую кнопку
            if (explosionPool.getActiveSprits().isEmpty()) {
                newGameButton.draw(batch);
            }
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
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
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                }
            }
            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)) {
                mainShip.setDamage(bullet.getDamage());
                bullet.setDestroyed();
            }
        }
    }

    /**
     * Привести игру к заводским настройкам
     */
    public void startNewGame() {
        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        mainShip.startNewGame();
        frags = 0;
    }

    /**
     * Вывод данных для подсчета убитых кораблей
     */
    private void printInfo() {
        sbFrags.setLength(0);  // сброс строки
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - PADDING, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);
    }
}
