package impl.model.monster;

import api.service.GameService;
import impl.service.Sprite;
import api.model.*;
import api.model.scope.Npc;
import impl.model.weapons.MaleAttack;

/**
 * Created by Denis on 5/27/2015.
 */
public class Pig extends BaseMonster implements Npc {

    public Pig(GameService area) {
        super(area);
    }

    public Pig(GameService area, float health, float speed, int attackPower, float attackSpeed, int aggresiveDistance) {
        super(area, Sprite.PIG, health, speed, new MaleAttack(attackPower, attackSpeed), ArmorType.NO_ARMOR, aggresiveDistance);
    }

    @Override
    public Pig clone() {
        return new Pig(gameService, health, moveSpeed, getAttackPower(), getAttackSpeed(), getAggressionDistance());
    }
}
