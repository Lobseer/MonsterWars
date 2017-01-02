package impl.model.weapons;

import api.model.*;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public class MaleAttack extends BaseWeapon {
    public MaleAttack() {
        super(1, 1, 1);
    }

    public MaleAttack(int attackPower, float attackSpeed) {
        super(attackPower, attackSpeed, 1);
    }

    @Override
    public AttackType getAttackType() {
        return AttackType.MALE;
    }


}
