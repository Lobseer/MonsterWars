package impl.model.weapons;

import api.model.*;
import api.model.actions.CharacterAction;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */
public abstract class BaseWeapon {
    protected int attackPower;
    protected float attackSpeed;
    protected int attackRange;

     protected BaseWeapon(int attackPower, float attackSpeed, int attackRange) {
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

    public CharacterAction getAction() {
        return ((target) -> {
            if(target!=null) target.modifyHealth(-attackPower * target.getArmor().getResist(getAttackType()));
        });
    }
}
