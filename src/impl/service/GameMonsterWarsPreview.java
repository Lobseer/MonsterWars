package impl.service;

import api.model.Character;
import api.service.GameService;
import impl.model.buildings.BaseBuilding;
import impl.model.buildings.Spawner;
import impl.model.monster.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class description
 *
 * @author lobseer
 * @version 02.01.2017
 */
public class GameMonsterWarsPreview implements GameService {

    private GameMap map;
    private Character userChar;
    private List<BaseMonster> monsters = new ArrayList<>();
    private List<BaseBuilding> buildings = new ArrayList<>();

    public GameMonsterWarsPreview() {
        map = new GameMap(CELLS_COUNT_X, CELLS_COUNT_Y, 15);

        //startNewGame();
        //generateManikens(10);
        generateRandomMonsters(skeleton, 3);
        generateRandomMonsters(pig, 10);
        startNewGame();
    }

    @Override
    public void startNewGame() {
        new Spawner(this, Sprite.SPAWN3, new Vector2Int(1, 1), 100, pig, 7, 30);
        new Spawner(this, Sprite.SPAWN1, new Vector2Int(CELLS_COUNT_X - 2, CELLS_COUNT_Y - 2), 100, skeleton, 15, 10);
    }


    public GameMonsterWarsPreview(Character player) {
        map = new GameMap(CELLS_COUNT_X, CELLS_COUNT_Y, 15);
        userChar = player;
    }

    private void generateRandomMonsters(BaseMonster prototype, int count) {
        BaseMonster mob;
        for (int i = 0; i < count; i++) {
            mob = prototype.clone();
            monsters.add(mob);
            mob.setStartPosition(getRandomPosition());

            Thread thread = new Thread(mob, mob.getClass().getSimpleName() + ": " + i);
            mob.mobThread = thread;
            thread.start();
        }
    }

    private void generateManikens(int count) {
        BaseMonster monster;
        for(int i=0; i<count; i++) {
            monster = new Pig(this, 10, 0, 0, 0, 0);
            monster.setStartPosition(getRandomPosition());
            monsters.add(monster);
        }
    }

    private Vector2Int getRandomPosition() {
        Random rnd = new Random();
        Vector2Int result;
        do {
            result= new Vector2Int(1+rnd.nextInt(CELLS_COUNT_X - 3), rnd.nextInt(1+CELLS_COUNT_Y - 3));
        } while (map.getTile(result)!=Sprite.GRASS || map.isOccupied(result));
        return result;
    }

    @Override
    public void update() {

    }

    @Override
    public Character getUserCharacter() {
        return userChar;
    }

    @Override
    public List<BaseMonster> getMonsters() {
        return monsters;
    }

    @Override
    public List<BaseBuilding> getBuildings() {
        return buildings;
    }

    @Override
    public GameMap getMap() {
        return map;
    }



    /*
    private static final Character generateRandomUserCharacter() {
        Character result;
        Random r = new Random();
        if(r.nextInt(2) == 0) {
            result = new Angel(ANGEL_HEALTH, ANGEL_POWER, ANGEL_MOVE_DISTANCE, ANGEL_ATTACK_DISTANCE);
        } else {
            result = new Warrior(WARRIOR_HEALTH, WARRIOR_POWER, WARRIOR_MOVE_DISTANCE, WARRIOR_ATTACK_DISTANCE);
        }
        if(result.canMove()) {
            ((Movable) result).moveTo(new Point(0, 0));
        }
        return result;
    }*/

    ///Размер игровой ячейки
    public static final int CELL_SIZE = 50;

    ///Размеры игрового поля в ячейках
    public static final int CELLS_COUNT_X = 26;
    public static final int CELLS_COUNT_Y = 13;

    ///Константы для создания окна, названия достаточно говорящие.
    public static final int SCREEN_WIDTH = CELLS_COUNT_X * CELL_SIZE;
    public static final int SCREEN_HEIGHT = CELLS_COUNT_Y * CELL_SIZE;
    public static final String SCREEN_NAME = "My Game";

    ///Константы параметров персонажей
    private final BaseMonster skeleton = new Skeleton(this, 25, 0.5f, 3, 0.8f, 2, 4);
    private final BaseMonster pig = new Pig(this, 10, 1f, 1, 1.5f, 3);
}
