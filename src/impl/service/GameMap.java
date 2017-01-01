package impl.service;

import OpenGL.GUIElement;
import OpenGL.Sprite;
import OpenGL.Cell;
import api.model.Character;
import impl.model.BaseCharacter;

import java.awt.*;

import static OpenGL.Constants.CELLS_COUNT_X;
import static OpenGL.Constants.CELLS_COUNT_Y;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */

public class GameMap {
    private Cell[][] map;

    public GameMap() {
        worldGen();
    }

    private void worldGen() {
        map = new Cell[CELLS_COUNT_X][CELLS_COUNT_Y];
        for (int i = 0; i < CELLS_COUNT_X; i++)
            for (int j = 0; j < CELLS_COUNT_Y; j++) {
                map[i][j] = new Cell(i, j, Sprite.GRASS);
            }
    }

    public GUIElement[][] getDrawMap() {
        return map;
    }

    public boolean putCharacter(Character character, Vector2Int point) {
        if(point.x<0 || point.x >= CELLS_COUNT_X || point.y<0 || point.y >= CELLS_COUNT_Y) throw new IndexOutOfBoundsException();
        map[point.x][point.y].putCharacter(character);
        return true;
    }

    public Cell getCell(Vector2Int point) {
        if(point.x<0 || point.x >= CELLS_COUNT_X || point.y<0 || point.y >= CELLS_COUNT_Y) throw new IndexOutOfBoundsException();
        return map[point.x][point.y];
    }

    public Cell getCell(int x, int y) {
        if(x<0 || x >= CELLS_COUNT_X || y<0 || y >= CELLS_COUNT_Y) throw new IndexOutOfBoundsException();
        return map[x][y];
    }

    public boolean isOccupied(Vector2Int point) {
        if(point.x<0 || point.x >= CELLS_COUNT_X || point.y<0 || point.y >= CELLS_COUNT_Y) throw new IndexOutOfBoundsException();
        return map[point.x][point.y].getCharacter()!=null;
    }

    public Sprite getTile(Vector2Int point) {
        return map[point.x][point.y].getSprite();
    }

    public Sprite getTile(int x, int y) {
        return map[x][y].getSprite();
    }

    public void setTile(Vector2Int point, Sprite tile) {map[point.x][point.y] = new Cell(point.x, point.y, tile);}
}
