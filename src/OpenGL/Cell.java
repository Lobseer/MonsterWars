package OpenGL;
import static OpenGL.Constants.*;

import impl.model.BaseCharacter;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Cell implements GUIElement {
    private int x;
    private int y;
    private Sprite tile;
    private BaseCharacter character;

    public Cell() {
        this(0, 0, Sprite.GRASS);
    }

    public Cell(int x, int y, Sprite tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public BaseCharacter getCharacter() {
        return character;
    }

    public void putCharacter(BaseCharacter bc) {
            character = bc;
    }

    public int getX() { return x;}

    public int getY() { return y;}

    @Override
    public int getWidth() {
        return CELL_SIZE;
    }

    @Override
    public int getHeight() {
        return CELL_SIZE;
    }

    @Override
    public int getRenderX() {
        return x * getWidth();
    }

    @Override
    public int getRenderY() {
        return y * getHeight();
    }

    @Override
    public Sprite getSprite() {
        //if(character!=null) return Sprite.SPAWN1;
        return tile;
    }

}
