package com.java;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;
    //image
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdwidth = 34;
    int birdheight = 24;

    //toppipe
    int pipeX = boardWidth;
    int PipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdwidth;
        int height = birdheight;
        Image img;

        Bird(Image img){
            this .img = img;
        }
    }
    class pipe {
        int x = pipeX;
        int y = 0;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        pipe(Image img){
            this . img = img;
        }
    }
    // game logic
    Bird bird ;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<pipe> pipes;
    Random random = new Random();
    Timer gameloop;
    Timer placepipesTimer;
    double score =0;

    boolean gameOver = false;
    FlappyBird (){
        setPreferredSize(new Dimension(360,640));
        setFocusable(true);
        addKeyListener(this);
        // setBackground(Color.blue);

        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg  = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg  = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg  =new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<pipe>();
        // palcepipe timer
        placepipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placepipe();
            }
        });
        placepipesTimer.start();

        // game timer
        gameloop = new Timer(1000/60,this);
        gameloop .start();


    }
    public void placepipe(){
        int randomPipeY = (int)(0 - pipeHeight/4 - Math.random()*(pipeHeight /2));
        int openingspace = boardHeight/6;
        pipe toppipe = new pipe(topPipeImg);
        toppipe.y = randomPipeY;
        pipes.add(toppipe);

        pipe bottompipe = new pipe(bottomPipeImg);
        bottompipe.y = toppipe.y + pipeHeight +openingspace;
        pipes.add(bottompipe);
    }
    public void paintComponent(Graphics g){
        super .paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //background
        g.drawImage(backgroundImg , 0 ,0,360 ,640,null);
        // bird
        g.drawImage(bird.img,bird.x ,bird.y,30,30,null);

        for (int i = 0; i <pipes.size() ; i++) {
            pipe pipe = pipes.get(i);
            g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over: " + String.valueOf((int )score),10,35);;

        }
        else {
            g.drawString(String.valueOf((int)score),10,35);
        }
    }
    public void move (){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y,0);

        // pipe
        for (int i = 0; i <pipes.size() ; i++) {
            pipe pipe = pipes.get(i);
            pipe .x += velocityX;

            if (!pipe.passed && bird.x> pipe.x+pipe.width){
                pipe.passed = true;
                score +=0.5;
            }

            if( collision (bird ,pipe)){
                gameOver = true;
            }
        }
        if(bird.y > boardHeight){
            gameOver = true;
        }
    }
    public boolean collision (Bird a ,pipe b){
        return a.x <b.x + b.width &&
                a.x + a.width > b.x &&
                a.y <b.y + b.height &&
                a.y + a.height >b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            placepipesTimer.stop();
            gameloop.stop();
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE){
        velocityY = -9;
        if(gameOver){
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            score =0;
            gameOver = false;
            gameloop.start();
            placepipesTimer.start();
        }
    }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}