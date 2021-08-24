package ru.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.game.math.Rect;
import ru.game.utils.Regions;

/**
 * СОздание и описание графического объекта = спрайт
 */
public class Sprite extends Rect {
    protected float angle;              // Угол поворота
    protected float scale = 1f;         // Скаляр. 1f = натуральная величина
    protected TextureRegion[] regions;  // Текстуры объектов
    protected int frame;                //

    private boolean destroyed;          // Флаг для пула объектов. Можно удалить или нельзя удалять объект.

    public Sprite() {

    }


    public Sprite(TextureRegion region) {
        regions = new TextureRegion[1];     // Одна текстура
        regions[0] = region;
    }

    /**
     * Конструктор - разрезаем Атлас-текстур на несколько текстур
     */
    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        regions = Regions.split(region, rows, cols, frames);
    }

    /**
     * Установка ширины и высоты спрайта
     */
    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update(float delta) {
    }

    /**
     * Отрисовка объекта
     */
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),     // Начальные коорданаты объекты
                halfWidth, halfHeight,      // Размер объекта
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    // Обработка событий
    public void resize(Rect worldBounds) {

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer, int button) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Установка флага объекта - Пометить объект на удаление. Для перемещать объект в pool
     */
    public void setDestroyed() {
        destroyed = true;
    }

    /**
     * Установка флага объекта - Пометить объект на переиспользование. Вытащить объект из pool
     */
    public void setUnDestroyed() {
        destroyed = false;
    }

    /**
     * Получить значение свойства destroyed объекта
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
