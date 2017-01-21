package impl.model.weapons;

import api.model.*;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public class RangeAttack extends BaseWeapon {
    public RangeAttack() {
        super(1, 1, 2);
    }

    public RangeAttack(float attackPower, float attackSpeed, int attackRange) {
        super(attackPower, attackSpeed, attackRange);
    }

    @Override
    public AttackType getAttackType() {
        return AttackType.RANGE;
    }
}
