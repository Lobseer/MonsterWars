package impl.model.Buildings;

import OpenGL.GUIElement;
import OpenGL.Sprite;
import api.model.ArmorType;
import api.model.Character;
import api.model.Npc;
import api.model.monster.Flying;
import api.model.monster.Movable;
import api.model.monster.Swimming;
import impl.service.GameServiceImpl;
import impl.service.Vector2Int;

import static OpenGL.Constants.CELL_SIZE;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public abstract class BaseBuilding implements Character, GUIElement, AutoCloseable {
    private float health;

    protected Vector2Int position;
    protected GameServiceImpl gameService;
    private Sprite icon;

    protected BaseBuilding(GameServiceImpl gameService, Sprite icon, Vector2Int position, float health) {
        this.gameService = gameService;
        this.icon = icon;
        this. health = health;
        this.position = position;
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
        if(health==0) return;
        this.health += val;

        if(this.health < 0) {
            this.health = 0;
        }
    }

    @Override
    public boolean isDead() {return health<=0;}

    @Override
    public Vector2Int getPosition() {
        return position;
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
