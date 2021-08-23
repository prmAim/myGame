package ru.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.Sprite;
import ru.game.math.Rect;

public class MainShip extends Sprite {

    private static final float PADDING = 0.02f;     // Отступ по границы
    private static final float V_LEN = 0.005f;      // Константа перемещения скорости корабля

    private Vector2 vectorMouse;        // Вектор клика точки мышки
    private Vector2 v;                  // Вектор скорости

    private Rect worldBounds;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 390/2, 287);
        vectorMouse = new Vector2();
        v = new Vector2();
    }

    /**
     * Установка пропорции объекта и его размеров
     */
    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    /**
     * Метод для изменения спринта. Его действий.
     * delta - это время изменения экрана
     */
    @Override
    public void update(float delta) {
        // Если длина вектора Mouse до вектора объета больше, то двигаемся, иначе объекта становиться в точке mouse.
        if (vectorMouse.dst(pos) >= V_LEN) {
            pos.add(v);
        } else {
            pos.set(vectorMouse);
        }
        checkLimitBounds();
    }

    /**
     * Проверка границ, что объект Корабль не заходит за границы экрана
     */
    private void checkLimitBounds() {
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        touch.set(touch.x, pos.y);
        // Расчет вектора скорости. Поиск длины от вектора нажатия мыши, до текущего нахождения объекта.
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        vectorMouse.set(v.cpy().add(pos));
        return false;
    }



}
