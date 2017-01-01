package impl.model.monster;

import OpenGL.Sprite;
import api.model.*;
import api.model.Character;
import impl.model.Buildings.Spawner;
import impl.model.attacks.MaleAttack;
import impl.service.GameServiceImpl;

/**
 * Created by Denis on 5/27/2015.
 */
public class Pig extends BaseMonster implements Npc {

    public Pig(GameServiceImpl area) {
        super(area);
    }

    public Pig(GameServiceImpl area, float health, float speed, int attackPower, float attackSpeed, int aggresiveDistance) {
        super(area, Sprite.PIG, health, speed, new MaleAttack(attackPower, attackSpeed), ArmorType.NO_ARMOR, aggresiveDistance);
    }

    @Override
    public Pig clone() {
        return new Pig(gameService, health, moveSpeed, getAttackPower(), getAttackSpeed(), getAggressionDistance());
    }
}
