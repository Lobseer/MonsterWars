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
    protected float attackPower;
    protected float attackSpeed;
    protected int attackRange;

    protected BaseWeapon(float attackPower, float attackSpeed, int attackRange) {
        this.attackPower = attackPower;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
    }

    public abstract AttackType getAttackType();

    public final float getAttackPower() {
        return this.attackPower;
    }

    public final float getAttackSpeed() {
        return this.attackSpeed;
    }

    public final int getAttackRange() {
        return this.attackRange;
    }

    public void setAttackPower(float attackPower) {
        if(attackPower<0) throw new IllegalArgumentException();
        this.attackPower = attackPower;
    }

    public void setAttackSpeed(float attackSpeed) {
        if(attackSpeed<0) throw new IllegalArgumentException();
        this.attackSpeed = attackSpeed;
    }

    public void setAttackRange(int attackRange) {
        if(attackRange<0) throw new IllegalArgumentException();
        this.attackRange = attackRange;
    }

    public CharacterAction getAction() {
        return ((target) -> {
            if(target!=null) target.modifyHealth(-attackPower * target.getArmor().getResist(getAttackType()));
        });
    }
}
