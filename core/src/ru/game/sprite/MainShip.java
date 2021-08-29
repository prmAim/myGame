package ru.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.Ship;
import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.pool.ExplosionPool;

public class MainShip extends Ship {
    private static final float RELOAD_INTERVAL = 0.38f;  // Интервал выстелов
    private static final float PADDING = 0.02f;          // Отступ по границы

    private final float SIZE_HEIGHT = 0.2f;                 // Размер корабля по ширене экрана 2%
    private final int INVALID_STATUS_POINTER = -1;          // Констатнта, что кнопка не нажата

    private boolean pressKeyLeft;                           // Состояние нажатие кнопки влево
    private boolean pressKeyRight;                          // Состояние нажатие кнопки вправо
    private int pressLeftPointer = INVALID_STATUS_POINTER;
    private int pressRightPointer = INVALID_STATUS_POINTER;

    /**
     * Указываем текстуру объекта Корабль
     */
    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        // Тестура карабля, кол-во строк, кол-во колонок, кол-во фреймов
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = bulletSound;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV.set(0f, 0.5f);
        v0.set(0.5f, 0f);
        this.bulletHeight = 0.01f;
        this.bulletDamage = 1;
        this.bulletPos = new Vector2();
        reloadInterval = RELOAD_INTERVAL;
        hp = 1;
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
        super.update(delta);
        checkLimitBounds();
        reloadTimer += delta;                   // счетчик времени
        if (reloadTimer >= reloadInterval){
            shootShip();
            reloadTimer = 0f;
        }
        bulletPos.set(pos.x, pos.y + getHalfHeight());
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
//            case Input.Keys.UP:
//                shootShip();
        }
        return false;
    }

    /**
     * Движение вправо
     */
    private void moveRight() {
        v.set(v0);
    }

    /**
     * Движение влево
     */
    private void moveLeft() {
        v.set(v0).rotateDeg(180);          // поворот вектора на 180 град.
    }

    /**
     * Остановка объекта корабли
     */
    private void stop() {
        v.setZero();                        // Обноление вектора
    }

    /**
     * Проверка на перекрещивание объекта <Пуля> и  <Корабль>
     */
    @Override
    public boolean isBulletCollision(Bullet bullet){
        return !(bullet.getLeft() > getRight() || bullet.getRight() < getLeft() || bullet.getBottom() > pos.y ||
                bullet.getTop() < getBottom());
    }
}
