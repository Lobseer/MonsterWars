package api.service;

import api.model.Character;
import impl.model.buildings.BaseBuilding;
import impl.model.monster.BaseMonster;
import impl.service.GameMap;
import impl.service.MobsStatus;

import java.util.List;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public interface GameService {
    Character getUserCharacter();
    List<BaseMonster> getMonsters();
    List<BaseBuilding> getBuildings();
    GameMap getMap();
    MobsStatus getCharacterStatus(Class mobType);
    void startNewGame();
    void update();
}
