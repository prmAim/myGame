package ru.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import ru.game.screen.MenuScreen;

public class MyGameIsShoot extends Game {
    private Music music;
    /**
     * Инициализация окна
     */
    @Override
    public void create() {                        // Создание изображения при первом открытии
        setScreen(new MenuScreen(this));    // Инициалихация класса Меню
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setVolume(0.9f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void dispose() {
        music.dispose();
    }
}