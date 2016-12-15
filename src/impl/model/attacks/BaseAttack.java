package impl.model.attacks;

import api.model.*;
import api.model.Character;

/**
 * Created by Denis on 5/27/2015.
 */
public abstract class BaseAttack implements CharacterAction {
    private Character targetCharacter;
    protected int attackPower;

    public BaseAttack(Character targetCharacter, int attackPower) {
        this.targetCharacter = targetCharacter;
        this.attackPower = attackPower;
    }
    @Override
    public final api.model.Character getActionTarget() {
        return this.targetCharacter;
    }

    public abstract AttackType getAttackType();

    public void doAction(){
        this.targetCharacter.modifyHealth(-this.attackPower);
    }
}
