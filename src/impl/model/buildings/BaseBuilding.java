package impl.model.buildings;

import api.service.GUIElement;
import api.service.GameService;
import impl.service.Sprite;
import api.model.ArmorType;
import api.model.Character;
import impl.service.Vector2Int;

import static impl.service.GameMonsterWarsPreview.*;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public abstract class BaseBuilding implements Character, GUIElement, AutoCloseable {
    private float health;

    protected Vector2Int position;
    protected GameService gameService;
    private Sprite icon;

    protected BaseBuilding(GameService gameService, Sprite icon, Vector2Int position, float health) {
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
