package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import base.BaseScreen;

/**
 * Класс потоком  <Меню>
 */
public class MenuScreen extends BaseScreen {
    private Texture img;
    private Vector2 posImg;             // вектор позиции изображения
    private Vector2 posMouse;           // вектор координат мышки
    private Vector2 v;                  // вектор скорости
    private final float STEP = 0.01f;   // шаг

    /**
     * Показать экран Меню
     */
    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        posImg = new Vector2(0, 0);
        posMouse = new Vector2(0, 0);
        v = new Vector2();
    }

    /**
     * Обновление экрана 60 секунд экрана меню
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        if (v.x > 0 && posImg.x <= posMouse.x && v.y > 0 && posImg.y <= posMouse.y ||
                v.x > 0 && posImg.x <= posMouse.x && v.y < 0 && posImg.y >= posMouse.y ||
                v.x < 0 && posImg.x >= posMouse.x && v.y < 0 && posImg.y >= posMouse.y ||
                v.x < 0 && posImg.x >= posMouse.x && v.y > 0 && posImg.y <= posMouse.y ){
            posImg.add(v);
        }
        batch.draw(img, posImg.x, posImg.y);
        batch.end();
    }

    /**
     * Освобождение ресурсов
     */
    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        posImg = null;
        posMouse = null;
    }

    /**
     * Отпускании клавиши на мышке
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        posMouse.set(screenX, Gdx.graphics.getHeight() - screenY);
        v.set(posMouse).sub(posImg).scl(STEP);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
