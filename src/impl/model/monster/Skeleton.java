package impl.model.monster;

import OpenGL.Sprite;
import api.model.ArmorType;
import api.model.AttackType;
import api.model.Character;
import api.model.monster.Swimming;
import impl.model.Buildings.Spawner;
import impl.model.attacks.RangeAttack;
import impl.service.GameMap;
import impl.service.GameServiceImpl;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Skeleton extends BaseMonster implements Swimming {

    private AttackType attackType;

    public Skeleton(GameServiceImpl area) {
        super(area);
    }

    public Skeleton(GameServiceImpl area, float health, float speed, int attackPower, float attackSpeed, int attackRange, int aggresiveDistance) {
        super(area, Sprite.SKELETON, health, speed, new RangeAttack(attackPower, attackSpeed , attackRange), ArmorType.LIGHT, aggresiveDistance);
    }

    @Override
    public Skeleton clone() {
        return new Skeleton(gameService, health, moveSpeed, getAttackPower(), getAttackSpeed(), getAttackRange(), getAggressionDistance());
    }
}
