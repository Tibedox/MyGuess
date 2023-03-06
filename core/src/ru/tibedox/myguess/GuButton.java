package ru.tibedox.myguess;

import static ru.tibedox.myguess.MyGuess.SCR_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class GuButton {
    float x, y;
    float width, height;
    String text;
    BitmapFont font;
    int type;
    final int TYPE_TEXT = 0, TYPE_TEXT_CENTER = 1, TYPE_IMG = 2, TYPE_IMG_TEXT = 3;
    Texture img;

    public GuButton(BitmapFont font, String text, float x, float y) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
        GlyphLayout gl = new GlyphLayout(font, text);
        width = gl.width;
        height = gl.height;
        type = TYPE_TEXT;
    }

    public GuButton(BitmapFont font, String text, float y) {
        this.font = font;
        this.text = text;
        this.y = y;
        GlyphLayout gl = new GlyphLayout(font, text);
        width = gl.width;
        height = gl.height;
        this.x = SCR_WIDTH/2f - width/2;
        type = TYPE_TEXT_CENTER;
    }

    public GuButton(Texture img, BitmapFont font, String text, float x, float y, float width, float height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = font;
        type = TYPE_IMG_TEXT;
    }

    public GuButton(Texture img, float x, float y, float width, float height){
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        type = TYPE_IMG;
    }

    boolean hit(float tx, float ty){
        if(type == TYPE_TEXT || type == TYPE_TEXT_CENTER)
            return x < tx && tx < x + width && y > ty && ty > y - height;
        else
            return x < tx && tx < x + width && y < ty && ty < y + height;
    }

    void draw(SpriteBatch batch){
        switch (type){
            case TYPE_TEXT: font.draw(batch, text, x, y); break;
            case TYPE_TEXT_CENTER: font.draw(batch, text, 0, y, SCR_WIDTH, Align.center, true); break;
            case TYPE_IMG: batch.draw(img, x, y, width, height); break;
            case TYPE_IMG_TEXT: batch.draw(img, x, y, width, height);
                font.draw(batch, text, x, y, width, Align.center, true); break;
        }
    }

    void setText(String text) {
        this.text = text;
        GlyphLayout gl = new GlyphLayout(font, text);
        width = gl.width;
        if(type == TYPE_TEXT_CENTER) x = SCR_WIDTH/2f - width/2;
    }
}
