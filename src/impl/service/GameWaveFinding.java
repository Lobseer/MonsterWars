package impl.service;

import api.model.Character;
import api.service.GameService;
import impl.model.buildings.BaseBuilding;
import impl.model.monster.BaseMonster;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 * @author lobseer
 * @version 02.01.2017
 */

public class GameWaveFinding implements GameService {
    private GameMap map;
    private Character userChar;
    private List<BaseMonster> monsters = new ArrayList<>();
    private List<BaseBuilding> buildings = new ArrayList<>();

    public GameWaveFinding() {
        map = new GameMap(CELLS_COUNT_X, CELLS_COUNT_Y, 15, 0);
    }

    //don't touch it
    public void wavePathFinding(Vector2Int from, Vector2Int to) {
        int[][] way = new int[CELLS_COUNT_X][CELLS_COUNT_Y];
        Vector2Int pointer = from.clone();
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

    @Override
    public MobsStatus getCharacterStatus(Class mobType) {
        return null;
    }

    @Override
    public void startNewGame() {

    }

    @Override
    public void update() {

    }

    ///Размер игровой ячейки
    public static final int CELL_SIZE = 50;

    ///Размеры игрового поля в ячейках
    public static final int CELLS_COUNT_X = 11;
    public static final int CELLS_COUNT_Y = 11;

    ///Константы для создания окна, названия достаточно говорящие.
    public static final int SCREEN_WIDTH = CELLS_COUNT_X * CELL_SIZE;
    public static final int SCREEN_HEIGHT = CELLS_COUNT_Y * CELL_SIZE;
    public static final String SCREEN_NAME = "My Game";
}
