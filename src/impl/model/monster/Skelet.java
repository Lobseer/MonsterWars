package impl.model.monster;

import OpenGL.Sprite;
import api.model.Character;
import api.model.CharacterAction;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Skelet extends BaseMonster {

    public Skelet() {
        super();
    }

    public Skelet(int health, float speed, int attackPower, int attackDistance) {
        super(Sprite.SKELET, health, speed, attackPower, attackDistance);
    }

    @Override
    protected CharacterAction getAttack(Character character) {
        return null;
    }
}
