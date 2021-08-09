package ru.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.base.BaseScreen;
import ru.game.sprite.Background;
import ru.game.sprite.Logo;

/**
 * Класс потоком  <Меню>
 */
public class MenuScreen extends BaseScreen {
    private Texture img;
    private Texture imgMove;

    private Background background;
    private Logo moveImg;

    /**
     * Показать экран Меню
     */
    @Override
    public void show() {
        super.show();
        img = new Texture("textures/bg.png");
        background = new Background(img);                   // splite для фоновой картинки
        imgMove = new Texture("badlogic.jpg");
        moveImg = new Logo(imgMove);                        // splite для объекта, который перемещаем
    }

    /**
     * Переопределяем метод для того, чтобы объект находился к координатной сетке (а не координаты LBX)
     */
    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        moveImg.resize(worldBounds);
    }

    /**
     * Обновление экрана 60 секунд экрана меню
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        moveImg.update(delta);
        batch.begin();
        background.draw(batch);
        moveImg.draw(batch);
        batch.end();
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        imgMove.dispose();
        moveImg = null;
    }

    /**
     * Отпускании клавиши на мышке
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        moveImg.touchDown(touch, pointer, button);
        return false;
    }
}
