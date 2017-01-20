package impl.model.monster;

import api.model.ArmorType;
import api.model.scope.Swimming;
import api.service.GameService;
import impl.model.weapons.MaleAttack;
import impl.model.weapons.RangeAttack;
import impl.service.Sprite;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Murloc extends BaseMonster implements Swimming {

    public Murloc(GameService area) {
        super(area);
    }

    public Murloc(GameService area, float health, float speed, int attackPower, float attackSpeed, int attackRange, int aggresiveDistance) {
        super(area, Sprite.MURLOC, health, speed, new MaleAttack(attackPower, attackSpeed), ArmorType.MEDIUM, aggresiveDistance);
    }

    @Override
    public Murloc clone() {
        return new Murloc(gameService, getHealth(), moveSpeed, getAttackPower(), getAttackSpeed(), getAttackRange(), getAggressionDistance());
    }
}
