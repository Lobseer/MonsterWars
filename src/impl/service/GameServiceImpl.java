package impl.service;

import OpenGL.Cell;
import OpenGL.Sprite;
import api.model.Character;
import api.model.monster.*;
import api.service.GameService;
import impl.model.monster.*;

import javax.annotation.processing.Processor;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static OpenGL.Constants.*;

/**
 * Created by denis.selutin on 5/29/2015.
 */

public class GameServiceImpl implements GameService {

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

    private Cell[][] cells;
    private Character userChar;
    private List<BaseMonster> monsters = new ArrayList<>();

    public GameServiceImpl() {
        worldGen();
        generateRandomMonsters(15);
    }

    public GameServiceImpl(Character player) {
        worldGen();
        userChar = player;
        generateRandomMonsters(MAX_MONSTERS);
    }

    private void worldGen() {
        Random rnd = new Random();
        cells = new Cell[CELLS_COUNT_X][CELLS_COUNT_Y];
        for (int i = 0; i < CELLS_COUNT_X; i++)
            for (int j = 0; j < CELLS_COUNT_Y; j++) {
                cells[i][j] = new Cell(i, j, Sprite.MOUNTAIN);
            }
    }

    private <T extends BaseMonster> void generateRandomMonsters(int count) {
        Random rnd = new Random();
        BaseMonster monster;
        for(int i=0; i<count; i++) {
            if(rnd.nextInt(100)>50)
                monster = new Pig(10, 1 + rnd.nextInt(5), 1, 1, cells);
            else
                monster = new Skelet(20, 1 + rnd.nextInt(5), 3, 1, cells);

            monster.startPosition(new Point(rnd.nextInt(CELLS_COUNT_X), rnd.nextInt(CELLS_COUNT_Y)));
            new Thread(monster).start();
            monsters.add(monster);
        }
    }

    @Override
    public void update() {
        monsters.stream().filter((p) -> p.getHealth()==0).peek((p)->monsters.remove(p)).close();
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
    public Cell[][] getCells() {
        return cells;
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
}
