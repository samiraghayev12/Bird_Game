package com.samiraghayev.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee;
	Texture bee2;
	Texture bee3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.2f;
	float enemyVelocity = 5;
	Random random;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font, font2;
	private Sound sound;
	private Music music;




	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float [] enemyOffSet3 = new float[numberOfEnemies];
	float distance = 0;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
        bee = new Texture("bee.png");
        bee2 = new Texture("bee.png");
        bee3 = new Texture("bee.png");
		birdX = Gdx.graphics.getWidth()/2 - bird.getHeight()/2;
		birdY =	Gdx.graphics.getHeight()/3;
		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();
		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(4);
		font2 = new BitmapFont();
		font2.setColor(Color.RED);
		font2.getData().setScale(6);
		music = Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));

		music.setVolume(0.1f);
		music.setLooping(true);
        music.play();



		for (int i = 0; i<numberOfEnemies; i++){
			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() -200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() -200);


			enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;
			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (gameState == 0) {
			font2.draw(batch, " Xos Gelmisiniz ", 800, Gdx.graphics.getHeight() / 2);
			font2.draw(batch, " Oyuna Basla ", 830, Gdx.graphics.getHeight() / 3);
		}


			 if (gameState == 1) {

				if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
					score++;
					if (scoredEnemy < numberOfEnemies - 1) {
						scoredEnemy++;

					} else {
						scoredEnemy = 0;
					}
				}

				if (Gdx.input.justTouched()) {
					velocity = -6;
				}


				for (int i = 0; i < numberOfEnemies; i++) {

					if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
						enemyX[i] = enemyX[i] + numberOfEnemies * distance;

						enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					} else {
						enemyX[i] = enemyX[i] - enemyVelocity;
					}


					batch.draw(bee, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
					batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
					batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

					enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, +Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
					enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, +Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
					enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, +Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);


				}


				if (birdY > 0) {
					velocity = velocity + gravity;
					birdY = birdY - velocity;
				} else {
					gameState = 2;
				}


			} else if (gameState == 0) {
				if (Gdx.input.justTouched()) {
					gameState = 1;
				}
			} else if (gameState == 2) {


				font2.draw(batch, " Oyun Bitdi: " + score, 800, Gdx.graphics.getHeight() / 2);
				font2.draw(batch, " Yeniden Oyna!", 770, Gdx.graphics.getHeight() / 3);

				if (Gdx.input.justTouched()) {
					gameState = 1;
					birdY = Gdx.graphics.getHeight() / 3;

					for (int i = 0; i < numberOfEnemies; i++) {
						enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


						enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;
						enemyCircles[i] = new Circle();
						enemyCircles2[i] = new Circle();
						enemyCircles3[i] = new Circle();
					}
					velocity = 0;
					scoredEnemy = 0;
					score = 0;

				}
			}





		batch.draw(bird, birdX, birdY,
		Gdx.graphics.getWidth()/15,
		Gdx.graphics.getHeight()/10);
			 font.draw(batch,String.valueOf(score), 100, 200);
		batch.end();
		birdCircle.set(birdX + Gdx.graphics.getWidth()/30,birdY + Gdx.graphics.getHeight()/30,Gdx.graphics.getWidth()/30);


		for (int i = 0; i < numberOfEnemies; i++){

			if (Intersector.overlaps(birdCircle, enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])){
				gameState = 2;

			}
		}



	}
	
	@Override
	public void dispose () {
		music.dispose();

	}
}
