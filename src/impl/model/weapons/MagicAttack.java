package impl.model.weapons;


import api.model.AttackType;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public class MagicAttack extends BaseWeapon {
    public MagicAttack() {
        super(2, 2, 2);
    }

    public MagicAttack(float attackPower, float attackSpeed, int attackRange) {
        super(attackPower, attackSpeed, attackRange);
    }

    @Override
    public AttackType getAttackType() {
        return AttackType.MAGIC;
    }
}
