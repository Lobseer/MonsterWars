package api.model.actions;

import api.model.Character;

/**
 * Created by Denis on 5/27/2015.
 */
@FunctionalInterface
public interface CharacterAction {
    void doAction(Character target);
}
