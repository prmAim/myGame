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
    private Texture imgBg;
    private Texture imgMove;

    private Background spiteBackground;
    private Logo spriteLogo;

    /**
     * Показать экран Меню
     */
    @Override
    public void show() {
        super.show();
        imgBg = new Texture("textures/bg.png");
        spiteBackground = new Background(imgBg);               // splite для фоновой картинки с тестурой
        imgMove = new Texture("badlogic.jpg");
        spriteLogo = new Logo(imgMove);                        // splite для объекта с тестурой, который перемещаем
    }

    /**
     * Переопределяем метод для того, чтобы объект находился к координатной сетке (а не координаты LBX)
     */
    @Override
    public void resize(Rect worldBounds) {
        spiteBackground.resize(worldBounds);
        spriteLogo.resize(worldBounds);
    }

    /**
     * Обновление экрана 60 секунд экрана меню
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        spriteLogo.update(delta);
        batch.begin();
        spiteBackground.draw(batch);
        spriteLogo.draw(batch);
        batch.end();
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void dispose() {
        super.dispose();
        imgBg.dispose();
        imgMove.dispose();
        spriteLogo = null;
    }

    /**
     * Отпускании клавиши на мышке
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spriteLogo.touchDown(touch, pointer, button);
        return false;
    }
}
