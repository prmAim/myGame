package ru.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGameIsShoot extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create () {			// Создание изображения при первом открытии
		batch = new SpriteBatch();
	}

	@Override
	public void render () {			// Отрисовка каждые 60 секунл
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.end();
	}
	
	@Override
	public void dispose () {		// Закрытие изображения и освобождение ресурсов
		batch.dispose();
		img.dispose();
	}
}
