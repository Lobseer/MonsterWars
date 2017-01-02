package api.model;

import api.model.Character;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */
public interface Monster {
    void attack(Character character);
    int getAggressionDistance();
    Character searchEnemy(int radius);
}
