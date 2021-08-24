package ru.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.Sprite;
import ru.game.math.Rect;

public class MainShip extends Sprite {

    private static final float PADDING = 0.02f;     // Отступ по границы
    private final float SIZE_HEIGHT = 0.2f;         // Размер корабля по ширене экрана 2%
    private static final float V_LEN = 0.005f;      // Константа перемещения скорости корабля

    private final Vector2 V0 = new Vector2(0.5f, 0);  // Вектор скорости - шага
    private final Vector2 v = new Vector2();               // Вектор скорости

    private Rect worldBounds;

    private boolean pressKeyLeft;
    private boolean pressKeyRight;
    private final int INVALID_STATUS_POINTER = -1;
    private int pressLeftPointer = INVALID_STATUS_POINTER;
    private int pressRightPointer = INVALID_STATUS_POINTER;

    /**
     * Указываем текстуру объекта Корабль
     */
    public MainShip(TextureAtlas atlas) {
        // Тестура карабля, кол-во строк, кол-во колонок, кол-во фреймов
        super(atlas.findRegion("main_ship"), 1, 2, 2);
    }

    /**
     * Установка пропорции объекта и его размеров
     */
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SIZE_HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    /**
     * Метод для изменения спринта. Его действий.
     * delta - это время изменения экрана
     */
    @Override
    public void update(float delta) {
        // Если длина вектора Mouse до вектора объета больше, то двигаемся, иначе объекта становиться в точке mouse.
        pos.mulAdd(v, delta);
        checkLimitBounds();
    }

    /**
     * Проверка границ, что объект Корабль не заходит за границы экрана
     */
    private void checkLimitBounds() {
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (pressLeftPointer != INVALID_STATUS_POINTER) { // Если левай палец не нажат на экране, то
                return false;
            }
            pressLeftPointer = pointer;
            moveLeft();
        } else {
            if (pressRightPointer != INVALID_STATUS_POINTER) {   // Если правый палец не нажат на экране, то
                return false;
            } else {
                pressRightPointer = pointer;
                moveRight();
            }

        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == pressLeftPointer) {
            pressLeftPointer = INVALID_STATUS_POINTER;      // отпустили левый палец
            if (pressRightPointer != INVALID_STATUS_POINTER) {         // Если правый палец еще на экране, то еще двигаемся в право
                moveRight();
            } else {
                stop();
            }
        }
        if (pointer == pressRightPointer) {
            pressRightPointer = INVALID_STATUS_POINTER;
            if (pressLeftPointer != INVALID_STATUS_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    /**
     * Метод при событии <отпускание клавиши> на клавиатуре
     */
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressKeyLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressKeyRight = true;
                moveRight();
                break;
        }
        return false;
    }

    /**
     * Метод при событии <нажатии клавиши> на клавиатуре
     */
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressKeyLeft = false;
                if (pressKeyRight) {
                    moveRight();
                } else {
                    stop();                     // Как только отпустили клавишу, вектор обнулился.
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressKeyRight = false;
                if (pressKeyLeft) {
                    moveLeft();
                } else {
                    stop();                     // Как только отпустили клавишу, вектор обнулился.
                }
                break;
        }
        return false;
    }

    /**
     * Движение вправо
     */
    private void moveRight() {
        v.set(V0);
    }

    /**
     * Движение влево
     */
    private void moveLeft() {
        v.set(V0).rotateDeg(180);          // поворот вектора на 180 град.
    }

    /**
     * Остановка объекта корабли
     */
    private void stop() {
        v.setZero();                        // Обноление вектора
    }
}
