package ru.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.base.BaseScreen;
import ru.game.sprite.Background;
import ru.game.sprite.ExitButton;
import ru.game.sprite.PlayButton;
import ru.game.sprite.Star;

/**
 * Класс потоком  <Окно Меню>
 */
public class MenuScreen extends BaseScreen {
    private final int COUNT_STARS = 512;        // кол-во звезд

    private final Game game;                    // ссылка на игру

    private Texture imgBg;
    private TextureAtlas atlas;

    private Background spiteBackground;         // объект задний фон
    private Star[] stars;                       // массив объектов Звезда

    private ExitButton extButton;               // кнопка выхода
    private PlayButton playButton;              // кнопка начала игрыыыы

    public MenuScreen(Game game) {              // конструктор ссылку на объект Игра. Для создания нового окна
        this.game = game;
    }

    /**
     * Показать экран Меню
     */
    @Override
    public void show() {
        super.show();
        imgBg = new Texture("textures/bg.png");
        spiteBackground = new Background(imgBg);               // splite для фоновой картинки с тестурой

        atlas = new TextureAtlas("textures/menuAtlas.tpack");   // Подключение текстур атласа.
        stars = new Star[COUNT_STARS];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        extButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);
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
        extButton.resize(worldBounds);
        playButton.resize(worldBounds);
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

    /**
     * Отпускании клавиши на мышке
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        extButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        extButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
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
        extButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }
}
