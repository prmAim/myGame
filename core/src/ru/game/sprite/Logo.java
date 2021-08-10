package ru.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.base.Sprite;
import ru.game.math.Rect;
/**
 * Спрайт объкта с движением
 */
public class Logo extends Sprite {

    private static final float V_LEN = 0.001f;

    private Vector2 vectorMouse;        // Вектор клика точки мышки
    private Vector2 v;                  // Вектор скорости


    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        this.vectorMouse = new Vector2();
        this.v = new Vector2();
    }

    /**
     * Установка пропорции объекта и его размеров
     */
    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
    }

    /**
     * Метод для изменения спринта. Его действий.
     * delta - это время изменения экрана
     */
    @Override
    public void update(float delta) {
        // Если длина вектора Mouse до вектора объета больше, то двигаемся, иначе объекта становиться в точке mouse.
        if (vectorMouse.dst(pos) > V_LEN) {
            pos.add(v);
        } else {
            pos.set(vectorMouse);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.vectorMouse.set(touch);
        // Расчет вектора скорости. Поиск длины от вектора нажатия мыши, до текущего нахождения объекта.
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }
}

