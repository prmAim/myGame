package ru.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.base.BaseScreen;
import ru.game.splite.Background;

/**
 * Класс потоком  <Меню>
 */
public class MenuScreen extends BaseScreen {
    private Texture img;
    private Background background;

    private Vector2 posImg;

    /**
     * Показать экран Меню
     */
    @Override
    public void show() {
        super.show();
        img = new Texture("textures/bg.png");
        background = new Background(img);
        posImg = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    /**
     * Обновление экрана 60 секунд экрана меню
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        posImg = null;
    }

    /**
     * Отпускании клавиши на мышке
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }
}
