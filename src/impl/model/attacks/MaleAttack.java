package impl.model.attacks;

import api.model.*;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public class MaleAttack extends BaseAttack {
    public MaleAttack(api.model.Character targetCharacter, int attackPower) {
        super(targetCharacter, attackPower);
    }

    @Override
    public AttackType getAttackType() {
        return AttackType.MALE;
    }
}
