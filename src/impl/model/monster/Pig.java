package impl.model.monster;

import OpenGL.Cell;
import OpenGL.Sprite;
import api.model.*;
import impl.model.attacks.MaleAttack;

/**
 * Created by Denis on 5/27/2015.
 */
public class Pig extends BaseMonster implements Npc {

    public Pig(Cell[][] area) {
        super(area);
    }

    public Pig(int health, float speed, int attackPower, int attackDistance, Cell[][] area) {
        super(Sprite.PIG, health, speed, attackPower, attackDistance, area);
    }
}
