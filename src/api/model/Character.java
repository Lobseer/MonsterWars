package api.model;

import api.model.actions.CharacterAction;
import api.model.scope.Flying;
import api.model.scope.Movable;
import api.model.scope.Npc;
import api.model.scope.Swimming;
import impl.service.Vector2Int;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */
public interface Character {
    boolean canDoAction(CharacterAction action);
    void takeAction(CharacterAction action);
    float getHealth();
    void modifyHealth(float val);
    ArmorType getArmor();
    Vector2Int getPosition();
    boolean isDead();

    default boolean isNpc() {
        return Npc.class.isAssignableFrom(this.getClass());
    }

    default boolean canMove() {
        return Movable.class.isAssignableFrom(this.getClass());
    }

    default boolean canFly(){
        return Flying.class.isAssignableFrom(this.getClass());
    }

    default boolean canSwim() {
        return Swimming.class.isAssignableFrom(this.getClass());
    }
}
