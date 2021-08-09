package ru.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import ru.game.math.MatrixUtils;
import ru.game.math.Rect;

/**
 * Общий класс-родитель окон. Наследует все методы интерфейс Screen
 */
public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;
    /**
     * Показать окно.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
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
    System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.println("worldBounds width = " + worldBounds.getWidth() + " height = " + worldBounds.getHeight());
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
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("touchDown touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    /**
     * Метод при событии <нажатии клавиши> на мышке
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.println("touchUp touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    /**
     * Метод при событии <перетаскивании объета мышью>
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touchX = " + touch.x + " touchY = " + touch.y);
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
