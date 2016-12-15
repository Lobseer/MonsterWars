package api.service;

import api.model.*;
import api.model.monster.Monster;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public interface GameService {
    public api.model.Character getUserCharacter();
    public Monster[] getMonsters();
    public void calculateNextStep();
}
