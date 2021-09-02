package ru.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.game.base.BaseButton;
import ru.game.math.Rect;
import ru.game.screen.GameScreen;

/**
 * Кнопка <Начала игры>
 */
public class NewGameButton extends BaseButton {
    private static final float HEIGHT = 0.05f;              // Размер
    private static final float BOTTOM_MARGIN = -0.015f;     // Отступ от центра

    private GameScreen gameScreen;                          // Ссылка на окно игры game

    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(BOTTOM_MARGIN);
    }

    /**
     * Действие при нажатии на кнопку -> Запуск новой игры
     */
    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
