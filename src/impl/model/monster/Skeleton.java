package impl.model.monster;

import api.service.GameService;
import impl.service.Sprite;
import api.model.ArmorType;
import api.model.scope.Swimming;
import impl.model.weapons.RangeAttack;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Skeleton extends BaseMonster {

    public Skeleton(GameService area) {
        super(area);
    }

    public Skeleton(GameService area, float health, float speed, int attackPower, float attackSpeed, int attackRange, int aggresiveDistance) {
        super(area, Sprite.SKELETON, health, speed, new RangeAttack(attackPower, attackSpeed , attackRange), ArmorType.HEAVY, aggresiveDistance);
    }

    @Override
    public Skeleton clone() {
        return new Skeleton(gameService, getHealth(), moveSpeed, getAttackPower(), getAttackSpeed(), getAttackRange(), getAggressionDistance());
    }
}
