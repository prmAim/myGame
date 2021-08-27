package ru.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.BaseScreen;
import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.sprite.Background;
import ru.game.sprite.MainShip;
import ru.game.sprite.Star;

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

    private MainShip mainShip;                  // Объект летающий корабль

    private MainShip mainShip;                  // Объект летающий корабль

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
      
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool);

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
    }

    /**
     * Обновление экрана 60 секунд экрана меню
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
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
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);

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
        mainShip.update(delta);
        freeAllDestroyed();
        bulletPool.updateActiveSprites(delta);
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
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }

    /**
     * Освобождаем все объеты из <активным pool> в <свободный pool>
     */
    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
    }
}
