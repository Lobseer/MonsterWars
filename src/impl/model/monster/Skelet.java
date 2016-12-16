package impl.model.monster;

import OpenGL.Cell;
import OpenGL.Sprite;
import api.model.AttackType;
import api.model.Character;
import api.model.CharacterAction;
import impl.model.attacks.MaleAttack;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Skelet extends BaseMonster {

    private AttackType attackType;

    public Skelet(Cell[][] area) {
        super(area);
    }

    public Skelet(int health, float speed, int attackPower, int attackDistance, Cell[][] area) {
        super(Sprite.SKELET, health, speed, attackPower, attackDistance, area);
    }
}
