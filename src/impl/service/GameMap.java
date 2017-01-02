package impl.service;

import api.service.GUIElement;
import api.model.Character;

import java.sql.Array;
import java.util.Arrays;
import java.util.Random;

import static impl.service.GameMonsterWarsPreview.*;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */

public class GameMap {
    private Cell[][] map;

    public GameMap(int mapWidth, int mapHeight, int mountainCount) {
        map = new Cell[mapWidth][mapHeight];
        worldGen(mountainCount);
    }

    public void worldGen(int barrierCount) {

        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Cell(i, j, Sprite.GRASS);
            }

        for(int i = 0; i<barrierCount; i++) {
            setTile(getRandomPosition(),Sprite.MOUNTAIN);
        }
    }

    private Vector2Int getRandomPosition() {
        Random rnd = new Random();
        Vector2Int result;
        do {
            result= new Vector2Int(1+rnd.nextInt(map.length - 3), rnd.nextInt(1+map[1].length - 3));
        } while (getTile(result)!=Sprite.GRASS || isOccupied(result));
        return result;
    }

    public GUIElement[][] getDrawMap() {
        return map;
    }

    public boolean putCharacter(Character character, Vector2Int point) {
        if(point.x<0 || point.x >= map.length || point.y<0 || point.y >= map[1].length) throw new IndexOutOfBoundsException();
        map[point.x][point.y].putCharacter(character);
        return true;
    }

    public Cell getCell(Vector2Int point) {
        if(point.x<0 || point.x >= map.length || point.y<0 || point.y >= map[1].length) throw new IndexOutOfBoundsException();
        return map[point.x][point.y];
    }

    public Cell getCell(int x, int y) {
        if(x<0 || x >= map.length || y<0 || y >= map[1].length) throw new IndexOutOfBoundsException();
        return map[x][y];
    }

    public boolean isOccupied(Vector2Int point) {
        if(point.x<0 || point.x >= map.length || point.y<0 || point.y >= map[1].length) throw new IndexOutOfBoundsException();
        return map[point.x][point.y].getCharacter()!=null;
    }

    public boolean isOccupied(int x, int y) {
        if(x<0 || x >= map.length || y<0 || y >= map[1].length) throw new IndexOutOfBoundsException();
        return map[x][y].getCharacter()!=null;
    }

    public Sprite getTile(Vector2Int point) {
        if(point.x<0 || point.x >= map.length || point.y<0 || point.y >= map[1].length) throw new IndexOutOfBoundsException();
        return map[point.x][point.y].getSprite();
    }

    public Sprite getTile(int x, int y) {
        if(x<0 || x >= map.length || y<0 || y >= map[1].length) throw new IndexOutOfBoundsException();
        return map[x][y].getSprite();
    }

    public void setTile(Vector2Int point, Sprite tile) {
        if(point.x<0 || point.x >= map.length || point.y<0 || point.y >= map[1].length) throw new IndexOutOfBoundsException();
        map[point.x][point.y] = new Cell(point.x, point.y, tile);
    }

    public void setTile(int x, int y, Sprite tile) {
        if(x<0 || x >= map.length || y<0 || y >= map[1].length) throw new IndexOutOfBoundsException();
        map[x][y] = new Cell(x, y, tile);
    }
}
