package impl.service;

import api.model.Character;
import api.service.GameService;
import impl.model.buildings.BaseBuilding;
import impl.model.buildings.Spawner;
import impl.model.monster.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class description
 *
 * @author lobseer
 * @version 02.01.2017
 */

public class GameMonsterWarsPreview implements GameService {
    ///Размеры игрового поля в ячейках
    public static final int CELLS_COUNT_X = 18;
    public static final int CELLS_COUNT_Y = 13;

    //Игровые коэффициенты
    public static final float GAME_SPEED_RATIO = 1f;
    public static final float MOUNTAIN_RATIO = 0.07f;
    public static final float WATER_RATIO = 0.1f;

    ///Константы параметров персонажей                      hp    m_spd   dmg  at_spd   at_rng  agr_rng
    private final BaseMonster skeleton = new Skeleton(this, 35,   0.6f,   3,    0.8f,      2,     4);
    private final BaseMonster pig = new Pig          (this, 10,   1.3f,   1,    1.5f,             2);
    private final BaseMonster murloc = new Murloc    (this, 12,   1f,     2,    1f,               3);
    private final BaseMonster hawk = new Hawk        (this, 5,    2f,     10,   0.5f,      2,     5);


    public GameMonsterWarsPreview() {
        map = new GameMap(CELLS_COUNT_X, CELLS_COUNT_Y,
                (int)(CELLS_COUNT_X*CELLS_COUNT_Y * MOUNTAIN_RATIO),
                (int)(CELLS_COUNT_X*CELLS_COUNT_Y * WATER_RATIO));
        monsters = new CopyOnWriteArrayList<>();
        buildings = new CopyOnWriteArrayList<>();

        startNewGame();
        //generateManikens(10);
        //generateRandomMonsters(skeleton, 3);
        //generateRandomMonsters(pig, 10);
    }

    //Начальное состояние игры
    @Override
    public void startNewGame() {
        //                      count    spawn_speed
        createSpawner(skeleton,  5,         10f,       new Vector2Int(CELLS_COUNT_X - 2, CELLS_COUNT_Y - 2));
        createSpawner(pig,       15,        5f,        new Vector2Int(1, 1));
        createSpawner(murloc,    8,         3f,        new Vector2Int(1, CELLS_COUNT_Y - 2));
        createSpawner(hawk,      3,         7f,        new Vector2Int(CELLS_COUNT_X - 2, 1));
    }

    //не реализовано
    @Override
    public void update() {

    }

    public GameMonsterWarsPreview(Character player) {
        map = new GameMap(CELLS_COUNT_X, CELLS_COUNT_Y, 8, 12);
        userChar = player;
    }

    // геттеры
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

    //Колекции содержащие игровые объекты
    private GameMap map;
    private Character userChar;
    private List<BaseMonster> monsters;
    private List<BaseBuilding> buildings;

    //Методы содания игровых обьектов
    private void createSpawner(BaseMonster mob, int mobMaxCount, float spawnSpeed, Vector2Int position) {
        Spawner spawn;
        Thread spawnThread;

        Sprite spawnSprite;
        switch (mob.getSprite()) {
            case PIG:
                spawnSprite = Sprite.SPAWN_PIG;
                break;
            case SKELETON:
                spawnSprite = Sprite.SPAWN_SKELETON;
                break;
            case MURLOC:
                spawnSprite = Sprite.SPAWN_MURLOC;
                break;
            case HAVK:
                spawnSprite = Sprite.SPAWN_HAWK;
                break;
            default:
                spawnSprite = Sprite.BUILDING;
        }

        spawn = new Spawner(this, spawnSprite, 100, mob, spawnSpeed, mobMaxCount);
        spawn.setStartPosition(position);
        buildings.add(spawn);
        map.putCharacter(spawn, spawn.getPosition());
        spawnThread = new Thread(spawn, "Spawner: " + spawn.getProtoClass().getSimpleName());
        spawn.spawnThread = spawnThread;
        spawnThread.start();
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
        for (int i = 0; i < count; i++) {
            monster = new Pig(this, 10, 0, 0, 0, 0);
            monster.setStartPosition(getRandomPosition());
            monsters.add(monster);
        }
    }

    private Vector2Int getRandomPosition() {
        Random rnd = new Random();
        Vector2Int result;
        do {
            result = new Vector2Int(1 + rnd.nextInt(CELLS_COUNT_X - 3), rnd.nextInt(1 + CELLS_COUNT_Y - 3));
        } while (map.getTile(result) != Sprite.GRASS || map.isOccupied(result));
        return result;
    }

}
