package impl.model;

import OpenGL.Sprite;
import api.model.Character;
import api.model.CharacterAction;
import impl.model.attacks.Spawn;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public abstract class BaseBuilding implements Character {
    private int health;
    private Sprite icon;

    @Override
    public boolean canDoAction(CharacterAction action) {
        return action instanceof Spawn;
    }

    @Override
    public void doAction(CharacterAction action) {

    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void modifyHealth(int val) {
        if(health==0) return;
        this.health += val*0.75;
        if(this.health < 0) {
            this.health = 0;
            //System.out.println(getClass().getName() + " is DEAD");
        }
    }

    @Override
    public boolean isNpc() {
        return false;
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        if(icon!=null) return icon;
        return null;
    }
}
