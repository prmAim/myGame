package ru.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.game.base.BaseButton;
import ru.game.math.Rect;
import ru.game.pool.BulletPool;
import ru.game.pool.EnemyPool;
import ru.game.utils.EnemyEmitter;

public class NewGameButton extends BaseButton {
    private static final float HEIGHT_SIZE = 0.05f; // Размер сообщения

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private MainShip mainShip;

    public NewGameButton(TextureAtlas atlas, BulletPool bulletPool, EnemyPool enemyPool, MainShip mainShip) {
        super(atlas.findRegion("button_new_game"));
        this.bulletPool = bulletPool;
        this.enemyPool = enemyPool;
        this.enemyEmitter = enemyEmitter;
        this.mainShip = mainShip;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT_SIZE);
        setTop(-1 * worldBounds.getHalfHeight() / 3);
    }

    @Override
    public void action() {
        bulletPool.freeAllDestroyedAllSprites();
        enemyPool.freeAllDestroyedAllSprites();
        mainShip.setDefaultSetup();
    }
}
