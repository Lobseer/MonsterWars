package OpenGL;
import static OpenGL.Constants.*;
import api.model.Character;
import api.model.monster.Movable;
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
    private BaseCharacter mob;

    public Cell() {
        this(0, 0, Sprite.GRASS);
    }

    public Cell(int x, int y, Sprite tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public void putCharacter(BaseCharacter bc) {
        if(bc!=null) {
            mob = bc;
        }
    }

    @Override
    public int getWidth() {
        return CELL_SIZE;
    }

    @Override
    public int getHeight() {
        return CELL_SIZE;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public Sprite getSprite() {
        return tile;
    }

}
