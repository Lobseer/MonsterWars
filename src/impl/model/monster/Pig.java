package impl.model.monster;

import api.model.*;
import impl.model.attacks.MaleAttack;

/**
 * Created by Denis on 5/27/2015.
 */
public class Pig extends BaseMonster implements Npc {

    public Pig() {
        super();
    }

    @Override
    protected CharacterAction getAttack(api.model.Character character) {
        return new MaleAttack(character, attackPower);
    }
}
