package ru.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Пул объетов
 */
public abstract class SpritePool<T extends Sprite> {
    protected final List<T> activeSprits = new ArrayList<>();   // лист активных объектов
    protected final List<T> freeSprits = new ArrayList<>();     // лист свободных объектов

    protected abstract T newSprite();

    /**
     * Управление pool объектов. Перемещение объекта между <свободным pool> и <активным pool>
     */
    public T obtain() {
        T sprite;
        if (freeSprits.isEmpty()) {       // Если <свободный pool> объектов пустой, то создать новый объект
            sprite = newSprite();
        } else {
            sprite = freeSprits.remove(freeSprits.size() - 1);  // Удалили из <свободный pool>
        }
        activeSprits.add(sprite);                                     // Добавили в <используемый pool>
        System.out.println("LOG: " + getClass().getName() + " Pool active/free:" + activeSprits.size() + "/" + freeSprits.size());
        return sprite;
    }

    /**
     * Движение объектов на экране из  <активным pool>
     */
    public void updateActiveSprites(float delta) {
        for (Sprite sprite : activeSprits) {
            if (!sprite.isDestroyed()) {
                sprite.update(delta);
            }
        }
    }

    /**
     * Отрисовка объектов на экране из  <активным pool>
     */
    public void drawActiveSprites(SpriteBatch batch) {
        for (Sprite sprite : activeSprits) {
            if (!sprite.isDestroyed()) {
                sprite.draw(batch);
            }
        }
    }

    /**
     * Освобождаем все объеты из <активным pool> в <свободный pool>
     */
    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeSprits.size(); i++) {
            T sprite = activeSprits.get(i);
            if (sprite.isDestroyed()) {     // если установлен флаг - переиспользование
                freeSprit(sprite);
                i--;                        // так как сместились объеты в Листе, то нужно снова проверить этот
                sprite.setUnDestroyed();    // меняем флаг
            }
        }
    }

    /**
     * Освобождаем все объеты из <активным pool> в <свободный pool>
     */
    public void freeAllDestroyedAllSprites() {
        for (int i = 0; i < activeSprits.size(); i++) {
            T sprite = activeSprits.get(i);
                freeSprit(sprite);
                i--;                        // так как сместились объеты в Листе, то нужно снова проверить этот
                sprite.setUnDestroyed();    // меняем флаг
        }
    }

    /**
     * Перемещение объекта из <активным pool> в <свободный pool>
     */
    public void freeSprit(T spite) {
        // если мы удалили объект из <активным pool>, то переносим его в <свободный pool>
        if (activeSprits.remove(spite)) {
            freeSprits.add(spite);
            System.out.println("LOG: " + getClass().getName() + " Pool active/free:" + activeSprits.size() + "/" + freeSprits.size());
        }
    }

    /**
     * Освобождение ресурсов
     */
    public void dispose() {
        activeSprits.clear();
        freeSprits.clear();
    }

    public List<T> getActiveSprits() {
        return activeSprits;
    }
}
