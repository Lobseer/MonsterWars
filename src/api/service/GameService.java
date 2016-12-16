package api.service;

import OpenGL.Cell;
import api.model.*;
import api.model.Character;
import api.model.monster.Monster;
import impl.model.monster.BaseMonster;

import java.util.List;

/**
 * Created by denis.selutin on 5/29/2015.
 */
public interface GameService {
    public Character getUserCharacter();
    public List<BaseMonster> getMonsters();
    public Cell[][] getCells();
    public void update();
}
