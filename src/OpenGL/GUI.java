package OpenGL;

import api.model.Character;
import api.model.monster.Monster;
import api.model.monster.Movable;
import api.service.GameService;
import impl.model.BaseCharacter;
import impl.model.monster.BaseMonster;
import impl.model.monster.Pig;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static OpenGL.Constants.*;
import static org.lwjgl.opengl.GL11.*;


/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class GUI {
    ///CELLS_COUNT_X и CELLS_COUNT_Y -- константы
    //Cell -- класс, который реализует GUIElement; им займёмся немного позже
    private static Cell[][] cells;

    private Character userChar;
    private List<BaseMonster> monsters = new ArrayList<>();

    public void init(){
        initializeOpenGL();
        Random rnd = new Random();
        cells = new Cell[CELLS_COUNT_X][CELLS_COUNT_Y];
        for(int i=0; i < CELLS_COUNT_X; i++)
            for(int j=0; j < CELLS_COUNT_Y; j++) {
                cells[i][j] = new Cell(i*CELL_SIZE, j*CELL_SIZE, Sprite.GRASS);
            }
        BaseMonster pig = new Pig();
        pig.moveTo(new Point(rnd.nextInt(CELLS_COUNT_X), rnd.nextInt(CELLS_COUNT_Y)));
        new Thread(pig).start();

        BaseMonster pig2 = new Pig();
        pig2.moveTo(new Point(rnd.nextInt(CELLS_COUNT_X), rnd.nextInt(CELLS_COUNT_Y)));
        new Thread(pig2).start();

        BaseMonster pig3 = new Pig();
        pig3.moveTo(new Point(rnd.nextInt(CELLS_COUNT_X), rnd.nextInt(CELLS_COUNT_Y)));
        new Thread(pig3).start();

        monsters.add(pig);
        monsters.add(pig2);
        monsters.add(pig3);
    }
    //Этот метод будет вызываться извне
    public void update() {
        //Random r = new Random();
        for(int i = 0; i < monsters.size(); i++) {
            Point mPos = (monsters.get(i)).getPosition();
            cells[mPos.x][mPos.y].putCharacter(monsters.get(i));
        }

        updateOpenGL();
    }

    //А этот метод будет использоваться только локально,
    // т.к. базовым другие классы должны работать на более высоком уровне
    private void updateOpenGL() {
        Display.update();
        Display.sync(60);
    }

    private void initializeOpenGL(){
        try {
            //Задаём размер будущего окна
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));

            //Задаём имя будущего окна
            Display.setTitle(SCREEN_NAME);

            //Создаём окно
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0,SCREEN_WIDTH,0,SCREEN_HEIGHT,1,-1);
        glMatrixMode(GL_MODELVIEW);

        /*
         * Для поддержки текстур
         */
        glEnable(GL_TEXTURE_2D);

        /*
         * Для поддержки прозрачности
         */
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        /*
         * Белый фоновый цвет
         */
        glClearColor(1,1,1,1);
    }

    ///Рисует все клетки
    public void draw(){
        ///Очищает экран от старого изображения
        glClear(GL_COLOR_BUFFER_BIT);

        for(GUIElement[] line : cells){
            for(GUIElement cell:line){
                drawElement(cell);
            }
        }
        for(GUIElement mob : monsters) {
            drawElement(mob);
        }
    }

    ///Рисует элемент, переданный в аргументе
    private void drawElement(GUIElement elem){
        elem.getSprite().getTexture().bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0,0);
        glVertex2f(elem.getX(),elem.getY()+elem.getHeight());
        glTexCoord2f(1,0);
        glVertex2f(elem.getX()+elem.getWidth(),elem.getY()+elem.getHeight());
        glTexCoord2f(1,1);
        glVertex2f(elem.getX()+elem.getWidth(), elem.getY());
        glTexCoord2f(0,1);
        glVertex2f(elem.getX(), elem.getY());
        glEnd();
    }

    public Character getUserCharacter() {
        return userChar;
    }

    public List<BaseMonster> getsMonsters() {
        return monsters;
    }
}
