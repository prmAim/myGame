package ru.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.game.math.Rect;

/**
 * Класс кнопок
 */
public abstract class BaseButton extends Sprite {
    private static final float PRESS_SCALE = 0.9f;  // изменение размера при нажатии кнопки

    private int pointer;        // номер нажатия кнопки
    private boolean pressed;    // состояние нажатие кнопки

    public BaseButton(TextureRegion region) {
        super(region);
    }

    /**
     * Отпускание клавиши кнопки
     */
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        // Если кнопка нажата или не попали по кнопке, то игнорируем, иначе ...
        if (pressed || !isMe(touch)){
           return false;
        }
        this.pointer = pointer;     // Номер кнопки
        scale = PRESS_SCALE;        // изменение нажатие кнопки
        pressed = true;
        return false;
    }

    /**
     * Нажатие на кнопку
     */
    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        // Если кнопка, на которую нажали, отпущена (а не другая) или  не нажата кнопка, то игнорируем.
        if (this.pointer != pointer || !pressed){
            return false;
        }
        // Если мы нажали и отпустили кнопку в других координатах, отличных от кнопки, то игнорируем, иначе выполяем дкйствие
        if (isMe(touch)){
            action();
        }
        pressed = false;
        scale = 1f;
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
    }

    /**
     * Метод действия с кнопкой
     */
    public abstract void action();
}
