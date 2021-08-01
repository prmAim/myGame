package base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Общий класс-родитель окон. Наследует все методы интерфейс Screen
 */
public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;

    /**
     * Показать окно.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
    }

    /**
     * Одновление экрана каждые 60 сек (из настроек)
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);  // чистка экрана
    }

    /**
     * Изменения эрана
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Свернули экран
     */
    @Override
    public void pause() {
    }

    /**
     * Развернули экран
     */
    @Override
    public void resume() {
    }

    /**
     * Закрыли окно
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * Метод при событии <отпускание клавиши> на клавиатуре
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    /**
     * Метод при событии <нажатии клавиши> на клавиатуре
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Метод при событии сообщает какой сивол был нажат на клавиатуре
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Метод при событии <отпускании клавиши> на мышке
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Метод при событии <нажатии клавиши> на мышке
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Метод при событии <перетаскивании объета мышью>
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Метод при событии <перемешение курсора мышью>
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Метод при событии <изменения колесиком мышью>
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
