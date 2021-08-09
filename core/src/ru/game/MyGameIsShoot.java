package ru.game;

import com.badlogic.gdx.Game;
import ru.game.screen.MenuScreen;

public class MyGameIsShoot extends Game {

    /**
     * Инициализация окна
     */
    @Override
    public void create() {                // Создание изображения при первом открытии
        setScreen(new MenuScreen());    // Инициалихация класса Меню
    }
}