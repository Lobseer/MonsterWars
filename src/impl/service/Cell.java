package impl.service;

import static impl.service.GameMonsterWarsPreview.CELL_SIZE;

import api.service.GUIElement;
import api.model.Character;

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
    private Character character;
    public String value;

    public Cell() {
        this(0, 0, Sprite.GRASS);
    }

    public Cell(int x, int y, Sprite tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public Character getCharacter() {
        return character;
    }

    public void putCharacter(Character bc) {
            character = bc;
    }

    //public void updateValue() {
    //    GUI.drawNumber(value+"", x, y, CELL_SIZE, CELL_SIZE, 0, 0, 0);
    //}

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
    @Override
    public String getParams() {
        return value;
    }
}
