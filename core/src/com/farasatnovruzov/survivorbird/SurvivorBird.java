package com.farasatnovruzov.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import jdk.internal.jline.internal.Log;

public class SurvivorBird extends ApplicationAdapter {


    SpriteBatch batch;
    Texture bird, background;
    Texture bee1, bee2, bee3;
    Texture bumbleBee1, bumbleBee2, bumbleBee3;
    Texture mushroom;
    float birdX, birdY = 0;
    int gameState = 0;
    float velocity = 0;
    float gravity = 0.3f;
    float enemyVelocity = 5;
    Random random;
    int score = 0;
    int scoredEnemy = 0;
    BitmapFont fontScore, fontGame;

    Circle birdCircle;
//    ShapeRenderer shapeRenderer;

    float distance = 0;
    int numberOfEnemies = 3;

    float[] enemyX = new float[numberOfEnemies];
    float[] enemyOffSet1 = new float[numberOfEnemies];
    float[] enemyOffSet2 = new float[numberOfEnemies];
    float[] enemyOffSet3 = new float[numberOfEnemies];


    float[] bumbleX = new float[numberOfEnemies];
    float[] bumbleOffSet1 = new float[numberOfEnemies];
    float[] bumbleOffSet2 = new float[numberOfEnemies];
    float[] bumbleOffSet3 = new float[numberOfEnemies];

    float[] mushroomX = new float[numberOfEnemies];
    float[] mushroomOffSet = new float[numberOfEnemies];
//    float[] cherryOffSet2 = new float[numberOfEnemies];
//    float[] cherryOffSet3 = new float[numberOfEnemies];


    Circle[] enemyCircles1;
    Circle[] enemyCircles2;
    Circle[] enemyCircles3;

    Circle[] bumbleBeeCircles1;
    Circle[] bumbleBeeCircles2;
    Circle[] bumbleBeeCircles3;

    Circle[] mushroomCircle;
//    Circle[] cherryCircle2;
//    Circle[] cherryCircle3;




    @Override
    public void create() {


        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        bee1 = new Texture("enemybee.png");
        bee2 = new Texture("enemybee.png");
        bee3 = new Texture("enemybee.png");
        bumbleBee1 = new Texture("bumblebee.png");
        bumbleBee2 = new Texture("bumblebee.png");
        bumbleBee3 = new Texture("bumblebee.png");
        mushroom = new Texture("mushroom.png");
//        cherry2 = new Texture("cherry.png");
//        cherry3 = new Texture("cherry.png");


        distance = Gdx.graphics.getWidth()/2;
        random = new Random();

        birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 3;
        birdY = Gdx.graphics.getWidth() / 3;

//        shapeRenderer = new ShapeRenderer();

        birdCircle = new Circle();
        enemyCircles1 = new Circle[numberOfEnemies];
        enemyCircles2 = new Circle[numberOfEnemies];
        enemyCircles3 = new Circle[numberOfEnemies];
        bumbleBeeCircles1 = new Circle[numberOfEnemies];
        bumbleBeeCircles2 = new Circle[numberOfEnemies];
        bumbleBeeCircles3 = new Circle[numberOfEnemies];
        mushroomCircle = new Circle[numberOfEnemies];
//        cherryCircle2 = new Circle[numberOfEnemies];
//        cherryCircle3 = new Circle[numberOfEnemies];



        fontScore = new BitmapFont();
        fontGame = new BitmapFont();
        fontScore.setColor(Color.WHITE);
        fontGame.setColor(Color.WHITE);
        fontScore.getData().setScale(8);
        fontGame.getData().setScale(6);

        for (int i = 0; i < numberOfEnemies; i++) {
            enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

            enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2  + i * distance;

            enemyCircles1[i] = new Circle();
            enemyCircles2[i] = new Circle();
            enemyCircles3[i] = new Circle();


        }

        for (int i = 0; i < numberOfEnemies; i++) {

            bumbleOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            bumbleOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            bumbleOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

            bumbleX[i] = Gdx.graphics.getWidth() - bumbleBee1.getWidth()/2 + i * distance;

            bumbleBeeCircles1[i] = new Circle();
            bumbleBeeCircles2[i] = new Circle();
            bumbleBeeCircles3[i] = new Circle();

        }

        for (int i = 0; i < numberOfEnemies; i++) {

            mushroomOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
//            cherryOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
//            cherryOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

            mushroomX[i] = Gdx.graphics.getWidth() - mushroom.getWidth()/2 + i * distance;

            mushroomCircle[i] = new Circle();
//            cherryCircle2[i] = new Circle();
//            cherryCircle3[i] = new Circle();

        }


    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        if (gameState == 1) {

            if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 1.5f || bumbleX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 1.5f || mushroomX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 1.5f) {
                score++;

                if (scoredEnemy < numberOfEnemies - 1) {
                    scoredEnemy++;
                } else {
                    scoredEnemy = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                if (birdY < Gdx.graphics.getHeight() - 140) {
                    velocity = -9;

                }
            }

            for (int i = 0; i < numberOfEnemies; i++) {

                if (enemyX[i] < 0 || bumbleX[i] < 0 || mushroomX[i] < 0) {
                    enemyVelocity += 0.05;
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;
                    bumbleX[i] = bumbleX[i] + numberOfEnemies * distance;
                    mushroomX[i] = mushroomX[i] + numberOfEnemies *distance;


                    if (enemyOffSet1[i] > Gdx.graphics.getHeight() / 7 && enemyOffSet2[i] > Gdx.graphics.getHeight() / 7 && enemyOffSet3[i] > Gdx.graphics.getHeight() / 7 || bumbleOffSet1[i] > Gdx.graphics.getHeight() / 7 && bumbleOffSet2[i] > Gdx.graphics.getHeight() / 7 && bumbleOffSet3[i] > Gdx.graphics.getHeight() / 7 || mushroomOffSet[i] > Gdx.graphics.getHeight() / 7) {

                        enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                        enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                        enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                        bumbleOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                        bumbleOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                        bumbleOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                        mushroomOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
//                        cherryOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
//                        cherryOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    }

                } else {

                    enemyX[i] = enemyX[i] - enemyVelocity;
                    bumbleX[i] = bumbleX[i] - enemyVelocity;
                    mushroomX[i] = mushroomX[i] - enemyVelocity;

                }


                if (score > 10 && score <= 25) {


                    batch.draw(bumbleBee1, bumbleX[i] , Gdx.graphics.getHeight() / 2 + bumbleOffSet1[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
                    batch.draw(bumbleBee2, bumbleX[i] , Gdx.graphics.getHeight() / 2 + bumbleOffSet2[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
                    batch.draw(bumbleBee3, bumbleX[i] , Gdx.graphics.getHeight() / 2 + bumbleOffSet3[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);

                    bumbleBeeCircles1[i] = new Circle(bumbleX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + bumbleOffSet1[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
                    bumbleBeeCircles2[i] = new Circle(bumbleX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + bumbleOffSet2[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
                    bumbleBeeCircles3[i] = new Circle(bumbleX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + bumbleOffSet3[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);



                    batch.draw(mushroom, mushroomX[i], Gdx.graphics.getHeight() / 2 + mushroomOffSet[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
//                    batch.draw(cherry2, cherryX[i], Gdx.graphics.getHeight() / 2 + cherryOffSet2[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
//                    batch.draw(cherry3, cherryX[i], Gdx.graphics.getHeight() / 2 + cherryOffSet3[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);

                    mushroomCircle[i] = new Circle(mushroomX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + mushroomOffSet[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
//                    cherryCircle2[i] = new Circle(cherryX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + cherryOffSet2[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
//                    cherryCircle3[i] = new Circle(cherryX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + cherryOffSet3[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);

                } else if (score > 25) {
                    batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet1[i], Gdx.graphics.getWidth() / 14, Gdx.graphics.getHeight() / 9);
                    batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], Gdx.graphics.getWidth() / 14, Gdx.graphics.getHeight() / 9);
                    batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], Gdx.graphics.getWidth() / 14, Gdx.graphics.getHeight() / 9);

                    enemyCircles1[i] = new Circle(enemyX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 47);
                    enemyCircles2[i] = new Circle(enemyX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 47);
                    enemyCircles3[i] = new Circle(enemyX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 47);

                    batch.draw(bumbleBee1, bumbleX[i] , Gdx.graphics.getHeight() / 2 + bumbleOffSet1[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
                    batch.draw(bumbleBee2, bumbleX[i], Gdx.graphics.getHeight() / 2 + bumbleOffSet2[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
                    batch.draw(bumbleBee3, bumbleX[i] , Gdx.graphics.getHeight() / 2 + bumbleOffSet3[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);

                    bumbleBeeCircles1[i] = new Circle(bumbleX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + bumbleOffSet1[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
                    bumbleBeeCircles2[i] = new Circle(bumbleX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + bumbleOffSet2[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
                    bumbleBeeCircles3[i] = new Circle(bumbleX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + bumbleOffSet3[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);

                    batch.draw(mushroom, mushroomX[i], Gdx.graphics.getHeight() / 2 + mushroomOffSet[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
//                    batch.draw(cherry2, cherryX[i], Gdx.graphics.getHeight() / 2 + cherryOffSet2[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
//                    batch.draw(cherry3, cherryX[i], Gdx.graphics.getHeight() / 2 + cherryOffSet3[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);

                    mushroomCircle[i] = new Circle(mushroomX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + mushroomOffSet[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
//                    cherryCircle2[i] = new Circle(cherryX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + cherryOffSet2[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
//                    cherryCircle3[i] = new Circle(cherryX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + cherryOffSet3[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);


                } else {

                    batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet1[i], Gdx.graphics.getWidth() / 14, Gdx.graphics.getHeight() / 9);
                    batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], Gdx.graphics.getWidth() / 14, Gdx.graphics.getHeight() / 9);
                    batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], Gdx.graphics.getWidth() / 14, Gdx.graphics.getHeight() / 9);

                    enemyCircles1[i] = new Circle(enemyX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 47);
                    enemyCircles2[i] = new Circle(enemyX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 47);
                    enemyCircles3[i] = new Circle(enemyX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 47);

                    batch.draw(mushroom, mushroomX[i], Gdx.graphics.getHeight() / 2 + mushroomOffSet[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
//                    batch.draw(cherry2, cherryX[i], Gdx.graphics.getHeight() / 2 + cherryOffSet2[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);
//                    batch.draw(cherry3, cherryX[i], Gdx.graphics.getHeight() / 2 + cherryOffSet3[i], Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 7);

                    mushroomCircle[i] = new Circle(mushroomX[i]- 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + mushroomOffSet[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
//                    cherryCircle2[i] = new Circle(cherryX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + cherryOffSet2[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);
//                    cherryCircle3[i] = new Circle(cherryX[i] - 10 + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + cherryOffSet3[i] + Gdx.graphics.getHeight() / 18, Gdx.graphics.getWidth() / 44);


                }

            }


            if (birdY > Gdx.graphics.getHeight() / 8) {
//                && Gdx.graphics.getHeight() - 50 > birdY
                velocity = velocity + gravity;
                birdY = birdY - velocity;

//                if(birdY < Gdx.graphics.getHeight()-100){
//                    velocity = 0;
//                }
//                velocity = velocity + gravity;
//                birdY = birdY - velocity;

            } else {
                gameState = 2;
            }


        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }

        } else if (gameState == 2) {
//
//            if (mInterstitialAd != null) {
//                mInterstitialAd.show(AndroidLauncher.this);
//            } else {
//            }

            fontGame.draw(batch, "Game Over! Tap to Play Again", Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 1.8f);
            enemyVelocity = 5;
            if (Gdx.input.justTouched()) {
                gameState = 1;

                birdY = Gdx.graphics.getWidth() / 3;

                for (int i = 0; i < numberOfEnemies; i++) {
                    enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

                    enemyCircles1[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                    enemyCircles3[i] = new Circle();

                }

                for (int i = 0; i < numberOfEnemies; i++) {

                    bumbleOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    bumbleOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    bumbleOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    bumbleX[i] = Gdx.graphics.getWidth() - bumbleBee1.getWidth() / 2 + i * distance;

                    bumbleBeeCircles1[i] = new Circle();
                    bumbleBeeCircles2[i] = new Circle();
                    bumbleBeeCircles3[i] = new Circle();

                }

                for (int i = 0; i < numberOfEnemies; i++) {

                    mushroomOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
//                    cherryOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
//                    cherryOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

                    mushroomX[i] = Gdx.graphics.getWidth() - mushroom.getWidth()/2 + i * distance;

                    mushroomCircle[i] = new Circle();
//                    cherryCircle2[i] = new Circle();
//                    cherryCircle3[i] = new Circle();

                }

                velocity = 0;
                scoredEnemy = 0;
                score = 0;

            }
        }

//        if (birdY == 0) {
//        }
        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
        fontScore.draw(batch, String.valueOf(score), Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 7);
        batch.end();
        birdCircle.set(birdX + Gdx.graphics.getWidth() / 30 + 5, birdY + Gdx.graphics.getHeight() / 10 - 50, Gdx.graphics.getWidth() / 45);


//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.BLACK);
//        shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
//        shapeRenderer.end();

        for (int i = 0; i < numberOfEnemies; i++) {
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//            shapeRenderer.setColor(Color.BLACK);
//
//            shapeRenderer.circle(enemyX[i]-10 + Gdx.graphics.getWidth() / 28,Gdx.graphics.getHeight() / 2  + enemyOffSet1[i]+Gdx.graphics.getHeight() / 18,Gdx.graphics.getWidth() / 47);
//            shapeRenderer.circle(enemyX[i]-10 + Gdx.graphics.getWidth() / 28,Gdx.graphics.getHeight() / 2  + enemyOffSet2[i]+Gdx.graphics.getHeight() / 18,Gdx.graphics.getWidth() / 47);
//            shapeRenderer.circle(enemyX[i]-10 + Gdx.graphics.getWidth() / 28,Gdx.graphics.getHeight() / 2  + enemyOffSet3[i]+Gdx.graphics.getHeight() / 18,Gdx.graphics.getWidth() / 47);

            if (Intersector.overlaps(birdCircle, mushroomCircle[i])) {
                score = 0;
//                gameState = 1;
            }


            if (score > 10 && score <= 25) {
                if (Intersector.overlaps(birdCircle, bumbleBeeCircles1[i]) || Intersector.overlaps(birdCircle, bumbleBeeCircles2[i]) || Intersector.overlaps(birdCircle, bumbleBeeCircles3[i])) {
                    gameState = 2;
                }
            } else if (score > 25) {
                if (Intersector.overlaps(birdCircle, bumbleBeeCircles1[i]) || Intersector.overlaps(birdCircle, bumbleBeeCircles2[i]) || Intersector.overlaps(birdCircle, bumbleBeeCircles3[i])) {
                    gameState = 2;
                }
                if (Intersector.overlaps(birdCircle, enemyCircles1[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])) {
                    gameState = 2;
                }

            } else {
                if (Intersector.overlaps(birdCircle, enemyCircles1[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])) {
//                System.out.println("collision");
                    gameState = 2;
                }

            }


//            shapeRenderer.end();
        }

    }

    @Override
    public void dispose() {

    }
}

