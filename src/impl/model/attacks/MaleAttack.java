package impl.model.attacks;

import api.model.*;
import api.model.Character;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public class MaleAttack extends BaseAttack {
    public MaleAttack() {
        super(1, 1, 1);
    }

    public MaleAttack(int attackPower, int attackSpeed) {
        super(attackPower, attackSpeed, 1);
    }

    @Override
    public AttackType getAttackType() {
        return AttackType.MALE;
    }


}
