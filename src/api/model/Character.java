package api.model;

import api.model.monster.Flying;
import api.model.monster.Movable;
import api.model.monster.Swimming;
import impl.service.Vector2Int;

/**
 * Created by Denis on 5/27/2015.
 */
public interface Character {
    boolean canDoAction(CharacterAction action);
    void doAction(CharacterAction action);
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
