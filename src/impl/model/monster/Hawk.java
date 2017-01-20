package impl.model.monster;

import api.model.ArmorType;
import api.model.scope.Flying;
import api.service.GameService;
import impl.model.weapons.MagicAttack;
import impl.model.weapons.RangeAttack;
import impl.service.Sprite;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Hawk extends BaseMonster implements Flying{

    public Hawk(GameService area) {
        super(area);
    }

    public Hawk(GameService area, float health, float speed, int attackPower, float attackSpeed, int attackRange, int aggresiveDistance) {
        super(area, Sprite.HAVK, health, speed, new MagicAttack(attackPower, attackSpeed , attackRange), ArmorType.LIGHT, aggresiveDistance);
    }

    @Override
    public Hawk clone() {
        return new Hawk(gameService, getHealth(), moveSpeed, getAttackPower(), getAttackSpeed(), getAttackRange(), getAggressionDistance());
    }
}
