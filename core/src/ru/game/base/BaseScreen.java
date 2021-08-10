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
    private Rect screenBounds;      // координаты экрана в px
    private Rect worldBounds;       // переход на "мировую" систему координат. экран 1f / 1f * аспект (высота/ширина). Переход через матрицу проекции
    private Rect glBounds;          // итоговая координатная сетка. Переход через матрицу проекции

    private Matrix4 worldToGl;      // Матрица проекции. для перехода из "мировой" системы в итоговую сетку.
    private Matrix3 screenToWorld;  //

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
        glBounds = new Rect(0, 0, 1f, 1f);      // 1f - условная величина половины экрана
        worldToGl = new Matrix4();          // Матрица для проектирование Объекта
        screenToWorld = new Matrix3();      // Матрица для проектирование Объекта при событии
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
     * При изменении экрана получаем высоту и ширину текущего экрана.
     */
    @Override
    public void resize(int width, int height) {
    System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width, height);        // задаем размер прямоугольника Экрана в px (высота / 2 и ширина / 2)
        screenBounds.setLeft(0);                    // начальные координаты
        screenBounds.setBottom(0);                  // начальные координаты

        /**
         * Для сохранения пропорции объектов, проецируем его квадратом. используем формулу:
         * высота = 1f;
         * ширина = 1f * aspect;
         */
        float aspect = width / (float) height;              // соотношение сторон ширина/высоту
        worldBounds.setHeight(1f);                          // высота = перехода на проектируемую проекту
        worldBounds.setWidth(1f * aspect);                  // ширина = перехода на проектируемую проекту

        /**
         * Получаем матрицу worldToGl = из worldBounds проецируемся через glBounds
         */
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);     // для событий
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        // Получем объект уже в новой системе координат.
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
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);      // Высота системы коордиат при событии начинается с вверху, а объект рисуется с нижней точки
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
        // mul(screenToWorld) = умножение на мартцу проекции
        // вектор, которые имеет координаты, при отпускании кнопки Мыши в Итогой сетке
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
