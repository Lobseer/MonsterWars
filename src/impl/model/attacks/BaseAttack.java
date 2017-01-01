package impl.model.attacks;

import api.model.*;
import api.model.Character;

/**
 * Created by Denis on 5/27/2015.
 */
public abstract class BaseAttack implements CharacterAction {
    protected int attackPower;
    protected float attackSpeed;
    protected int attackRange;

    public BaseAttack(int attackPower, float attackSpeed, int attackRange) {
        this.attackPower = attackPower;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
    }

    public abstract AttackType getAttackType();

    public final int getAttackPower() {
        return this.attackPower;
    }

    public final float getAttackSpeed() {
        return this.attackSpeed;
    }

    public final int getAttackRange() {
        return this.attackRange;
    }

    @Override
    public void doAction(Character character) {
        if(character!=null) {
            character.modifyHealth(-attackPower * character.getArmor().getResist(getAttackType()));
        }
    }
}
