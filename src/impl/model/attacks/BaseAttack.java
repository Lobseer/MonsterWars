package impl.model.attacks;

import api.model.*;
import api.model.Character;

/**
 * Created by Denis on 5/27/2015.
 */
public class BaseAttack implements CharacterAction {

    protected AttackType type;
    protected int attackPower;
    protected int attackSpeed;
    protected int attackRange;

    public BaseAttack(int attackPower, int attackSpeed, int attackRange) {
        //this.targetCharacter = targetCharacter;
        this.attackPower = attackPower;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
    }

    public AttackType getAttackType() {
        return type;
    }

    public final int getAttackPower() {
        return this.attackPower;
    }

    public final int getAttackSpeed() {
        return this.attackSpeed;
    }

    public final int getAttackRange() {
        return this.attackRange;
    }

    public void doAction(Character character) {
        if(character!=null)
            character.modifyHealth(attackPower);
    }
}
