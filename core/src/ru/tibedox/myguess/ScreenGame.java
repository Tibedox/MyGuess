package ru.tibedox.myguess;

import static ru.tibedox.myguess.MyGuess.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ScreenGame implements Screen {
    MyGuess gg;

    GuButton btnNext, btnExit, btnView;
    GuButton[][] boxes;
    Texture imgCross, imgArrow, imgEye;
    Texture imgBox, imgBoxTransp;
    Texture[] imgPic = new Texture[12];
    int currentPic;

    public ScreenGame(MyGuess myGuess) {
        gg = myGuess;

        imgCross = new Texture("cross.png");
        imgArrow = new Texture("arrow.png");
        imgEye = new Texture("eye.png");
        imgBox = new Texture("box.png");
        imgBoxTransp = new Texture("boxtransp.png");
        for (int i = 0; i < imgPic.length-1; i++) imgPic[i] = new Texture("pics/pic"+i+".jpg");
        imgPic[imgPic.length-1] = new Texture("pics/imgend.png");

        btnView = new GuButton(imgEye, 20, 20, 50, 50);
        btnNext = new GuButton(imgArrow, SCR_WIDTH-70, 20, 50, 50);
        btnExit = new GuButton(imgCross, SCR_WIDTH-70, SCR_HEIGHT-70, 50, 50);
    }

    @Override
    public void show() {
        currentPic = 0;
        boxes = new GuButton[gg.masN][gg.masM];
        float width = SCR_WIDTH/(float)gg.masN;
        float height = SCR_HEIGHT/(float)gg.masM;
        for (int i = 0; i < gg.masN; i++) {
            for (int j = 0; j < gg.masM; j++) {
                boxes[i][j] = new GuButton(imgBox, width*i, height*j, width, height);
            }
        }
    }

    @Override
    public void render(float delta) {
        // обработка касаний экрана
        if(Gdx.input.justTouched()) {
            gg.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gg.camera.unproject(gg.touch);
            if (btnNext.hit(gg.touch.x, gg.touch.y)) {
                nextPic();
            } else if(btnExit.hit(gg.touch.x, gg.touch.y)) {
                gg.setScreen(gg.screenMenu);
            } else if(btnView.hit(gg.touch.x, gg.touch.y)) {
                clearAll();
            } else {
                for (int i = 0; i < gg.masN; i++) {
                    for (int j = 0; j < gg.masM; j++) {
                        if (boxes[i][j].hit(gg.touch.x, gg.touch.y)) {
                            boxes[i][j].img = imgBoxTransp;
                        }
                    }
                }
            }
        }

        // события

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        gg.batch.draw(imgPic[currentPic], 0, 0, SCR_WIDTH, SCR_HEIGHT);
        for (int i = 0; i < gg.masN; i++) {
            for (int j = 0; j < gg.masM; j++) {
                boxes[i][j].draw(gg.batch);
            }
        }
        btnNext.draw(gg.batch);
        btnExit.draw(gg.batch);
        btnView.draw(gg.batch);
        gg.font.draw(gg.batch, ""+(currentPic+1), 20, SCR_HEIGHT-20);
        gg.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        imgArrow.dispose();
        imgCross.dispose();
        imgBox.dispose();
        imgBoxTransp.dispose();
        for (int i = 0; i < imgPic.length; i++) imgPic[i].dispose();
    }

    void nextPic(){
        for (int i = 0; i < gg.masN; i++) {
            for (int j = 0; j < gg.masM; j++) {
                boxes[i][j].img = imgBox;
            }
        }
        if(currentPic<imgPic.length-1) currentPic++;
    }

    void clearAll(){
        for (int i = 0; i < gg.masN; i++) {
            for (int j = 0; j < gg.masM; j++) {
                boxes[i][j].img = imgBoxTransp;
            }
        }
    }
}
