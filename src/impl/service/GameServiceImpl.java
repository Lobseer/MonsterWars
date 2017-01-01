package impl.service;

import OpenGL.GUI;
import OpenGL.Sprite;
import api.model.Character;
import api.model.CharacterAction;
import api.service.GameService;
import impl.model.BaseCharacter;
import impl.model.Buildings.BaseBuilding;
import impl.model.Buildings.Spawner;
import impl.model.monster.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static OpenGL.Constants.*;

/**
 * Created by denis.selutin on 5/29/2015.
 */

public class GameServiceImpl implements GameService {

    private GameMap map;
    private Character userChar;
    private List<BaseMonster> monsters = new ArrayList<>();
    private List<BaseBuilding> buildings = new ArrayList<>();

    public GameServiceImpl() {
        map = new GameMap();
        new Spawner(this, Sprite.SPAWN3, new Vector2Int(1,1), 100, new Pig(this, 5, 3, 1, 1, 2), 7, MAX_MONSTERS*2);
        new Spawner(this, Sprite.SPAWN1, new Vector2Int(CELLS_COUNT_X-2, CELLS_COUNT_Y-2), 100, new Skeleton(this, 15, 2, 3, 0.8f, 3, 5), 15, MAX_MONSTERS);

        int maxBarrier = 15;

        Vector2Int point;
        for(int i = 0; i<maxBarrier; i++) {
            point=getRandomPosition();
            map.setTile(point,Sprite.MOUNTAIN);
        }
    }

    //don't touch it
    public void wavePathFinding(Vector2Int from, Vector2Int to) {
        int[][] way = new int[CELLS_COUNT_X][CELLS_COUNT_Y];
        Vector2Int pointer = (Vector2Int)from.clone();
        way[pointer.x][pointer.y] = 1;
        int x, y;

        map.getCell(pointer.x, pointer.y).value=""+1;
        int dist = 2;

        do {
            Vector2Int nextPointer = Vector2Int.ZERO;
            int min = 100;
            for (int i = -1; i <= 1; i++) {
                x = pointer.x + i;
                if(x>=0 && x<CELLS_COUNT_X) {
                    for (int j = -1; j <= 1; j++) {
                        y = pointer.y + j;
                        if (y >= 0 && y < CELLS_COUNT_Y) {
                            if(i==0 && j==0) continue;
                            if(map.getTile(x, y)==Sprite.GRASS && (way[x][y]>dist||way[x][y]==0)) {
                                way[x][y]=dist;
                                map.getCell(x, y).value=""+dist;
                                if(way[x][y]<min) {
                                    min = way[x][y];
                                    nextPointer.x = x;
                                    nextPointer.y = y;
                                }
                            }
                        }
                    }
                }
            }
            pointer=nextPointer;
            dist++;
        }while(way[to.x][to.y]==0 || dist<10);
    }

    Vector2Int getRandomPosition() {
        Random rnd = new Random();
        Vector2Int result;
        do {
            result= new Vector2Int(rnd.nextInt(CELLS_COUNT_X - 2), rnd.nextInt(CELLS_COUNT_Y - 2));
        } while (map.getTile(result)!=Sprite.GRASS);
        return result;
    }

    public GameServiceImpl(Character player) {
        map = new GameMap();
        userChar = player;
        generateRandomMonsters(MAX_MONSTERS);
    }

    private void generateRandomMonsters(int count) {
        Random rnd = new Random();
        BaseMonster monster;
        for(int i=0; i<count; i++) {
            if(rnd.nextInt(100)>50)
                monster = new Pig(this, 10, 3, 1, 1, 2);
            else
                monster = new Skeleton(this, 15, 2, 3, 0.5f, 2, 3);

            monster.setStartPosition(new Vector2Int(rnd.nextInt(CELLS_COUNT_X), rnd.nextInt(CELLS_COUNT_Y)));

            Thread mob = new Thread(monster);
            mob.setName(monster.getClass().getSimpleName() + ": " +i);
            mob.start();

            monsters.add(monster);
        }
    }

    private void generateRandomMonsters() {
        BaseMonster monster;
        monster = new Pig(this, 10, 3, 1, 1, 2);
        monster.setStartPosition(new Vector2Int(0, 0));
        Thread mob = new Thread(monster);
        mob.setName(monster.getClass().getSimpleName() + ": " +1);
        mob.start();
        monsters.add(monster);

        monster = new Skeleton(this, 15, 2, 3, 0.5f, 2, 3);
        monster.setStartPosition(new Vector2Int(CELLS_COUNT_X-1, CELLS_COUNT_Y-1));
        mob = new Thread(monster);
        mob.setName(monster.getClass().getSimpleName() + ": " +2);
        mob.start();
        monsters.add(monster);
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

    private static final int ANGEL_POWER = 50;
    private static final int ANGEL_HEALTH = 200;
    private static final int ANGEL_MOVE_DISTANCE = 20;
    private static final int ANGEL_ATTACK_DISTANCE = 8;
    private static final int WARRIOR_POWER = 70;
    private static final int WARRIOR_MOVE_DISTANCE = 8;
    private static final int WARRIOR_ATTACK_DISTANCE  = 5;
    private static final int WARRIOR_HEALTH = 500;
    private static final int MAX_MONSTERS = 15;
    private static final int MAX_MOVE_LENGTH = 2;
}
