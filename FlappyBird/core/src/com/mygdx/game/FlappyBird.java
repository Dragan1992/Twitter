package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	Texture bottomTube;
	int flapState;
	int gameState;
	int birdY;
	int velocity;
	Texture topTube;
	float gap;
	Random random;
	float maxTubeOffset;
	float tubeOffSet;
	float tubeVelocity;
	float tubeX;
	@Override
	public void create() {
		batch = new SpriteBatch();
		flapState = 0;
		birds = new Texture[2];
		background = new Texture("bg.png");
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		bottomTube=new Texture("bottomtube.png");
		topTube= new Texture("toptube.png");
		velocity = 0;
		gameState = 0;
		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;
		Gdx.app.log("Visina na app",Float.toString(Gdx.graphics.getHeight()));
		Gdx.app.log("Visina na element",Float.toString(Gdx.graphics.getHeight()/2+gap/2));
		gap=400;
		random = new Random();

		maxTubeOffset=Gdx.graphics.getHeight()/2-gap/2-100;
		tubeX=Gdx.graphics.getWidth()/2 - topTube.getWidth()/2;

	}

	@Override
	public void render() {
		if(gameState ==1 && birdY<0){

			gameState=0;
			birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		}
		if (gameState == 0) {
			if (Gdx.input.isTouched()) {
				Gdx.app.log("Is touched?", "Yep!");
				birdY += 100;
				tubeOffSet=(random.nextFloat()-0.5f)*Gdx.graphics.getHeight()-gap-200;;
				gameState = 1;
			}
			}
			else {
				velocity++;
				birdY -= velocity*3;
				if (flapState == 0) {
					flapState = 1;
				} else {
					flapState = 0;
				}

			if (Gdx.input.isTouched()) {
				Gdx.app.log("Is touched?", "Yep!");
				birdY += 100;
				velocity=0;
			}
			}
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bottomTube,Gdx.graphics.getWidth()/2-bottomTube.getWidth()/2,Gdx.graphics.getHeight()/2-gap/2-bottomTube.getHeight()+tubeOffSet);
		batch.draw(topTube,Gdx.graphics.getWidth()/2-topTube.getWidth()/2,Gdx.graphics.getHeight()/2+gap/2+tubeOffSet);
		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
		batch.end();


	}

	@Override
	public void dispose() {
		batch.dispose();

	}


}