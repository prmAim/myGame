package ru.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.game.base.BaseScreen;
import ru.game.math.Rect;
import ru.game.sprite.Background;
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
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    /**
     * Изменение положения объектов
     */
    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
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
        batch.end();
    }
}
