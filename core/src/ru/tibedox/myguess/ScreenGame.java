package ru.tibedox.myguess;

import static ru.tibedox.myguess.MyGuess.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenGame implements Screen {
    MyGuess gg;

    GuButton btnNext, btnExit;
    Texture imgCross, imgArrow;

    public ScreenGame(MyGuess myGuess) {
        gg = myGuess;

        imgCross = new Texture("cross.png");
        imgArrow = new Texture("arrow.png");

        btnNext = new GuButton(imgArrow, SCR_WIDTH-70, 20, 50, 50);
        btnExit = new GuButton(imgCross, SCR_WIDTH-70, SCR_HEIGHT-70, 50, 50);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
// обработка касаний экрана
        if(Gdx.input.justTouched()) {
            gg.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            gg.camera.unproject(gg.touch);
            if (btnNext.hit(gg.touch.x, gg.touch.y)) {
                //gg.setScreen(gg.screenGame);
            }
            if(btnExit.hit(gg.touch.x, gg.touch.y)) {
                gg.setScreen(gg.screenMenu);
            }
        }

        // события

        // отрисовка всей графики
        gg.camera.update();
        gg.batch.setProjectionMatrix(gg.camera.combined);
        gg.batch.begin();
        gg.batch.draw(gg.imgPic[0], 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnNext.draw(gg.batch);
        btnExit.draw(gg.batch);
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
    }
}
