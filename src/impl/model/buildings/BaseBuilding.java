package impl.model.buildings;

import api.service.GUIElement;
import api.service.GameService;
import impl.service.Sprite;
import api.model.ArmorType;
import api.model.Character;
import impl.service.Vector2Int;

import static impl.service.GUI.CELL_SIZE;

/**
 * Basic class for all buildings. Which can't move and have special armor type and activity.
 *
 * @author lobseer
 * @version 14.12.2016
 */

public abstract class BaseBuilding implements Character, GUIElement, AutoCloseable {
    private volatile float health;

    protected Vector2Int position;
    protected GameService gameService;
    private Sprite icon;

    protected BaseBuilding(GameService gameService, Sprite icon, float health) {
        this.gameService = gameService;
        this.icon = icon;
        this. health = health;
    }

    @Override
    public final ArmorType getArmor() {
        return ArmorType.FORT;
    }

    @Override
    public final float getHealth() {
        return health;
    }

    @Override
    public final void modifyHealth(float val) {
        this.health += val;
        if(isDead())
            die();
    }

    @Override
    public boolean isDead() {return health<=0;}

    public abstract void die();

    @Override
    public Vector2Int getPosition() {
        return position;
    }

    @Override
    public final void setStartPosition(Vector2Int position) {
        if(position!= null) {
            this.position = position;
            gameService.getMap().putCharacter(this, position);
        }
    }

    //GUI
    @Override
    public void receiveClick() {
        System.out.println(this.getClass().getSimpleName()+": "+position);
    }

    @Override
    public Sprite getSprite() {
        if(icon!=null) return icon;
        return Sprite.BUILDING;
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
    public int getRenderY() {
        return position.y * getHeight();
    }

    @Override
    public int getRenderX() {
        return position.x * getWidth();
    }

    @Override
    public String toString() {
        return String.format("%1s: position=%2s",this.getClass().getSimpleName(), position);
    }
}
