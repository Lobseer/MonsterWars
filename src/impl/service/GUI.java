package impl.service;

import api.service.GUIElement;
import api.service.GameService;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import static impl.service.GameMonsterWarsPreview.*;
import static org.lwjgl.opengl.GL11.*;


/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class GUI {
    private static GameService gameLogic;

    public static void init() {
        initializeOpenGL();
    }

    public static void start(GameService game) {
        gameLogic = game;
    }

    //Этот метод будет вызываться извне
    public static void update() {
        updateOpenGL();
    }

    //А этот метод будет использоваться только локально,
    // т.к. базовым другие классы должны работать на более высоком уровне
    private static void updateOpenGL() {

        Display.update();
        Display.sync(60);
    }

    private static void initializeOpenGL() {
        try {
            //Задаём размер будущего окна
            Display.setDisplayModeAndFullscreen(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));

            //Задаём имя будущего окна
            Display.setTitle(SCREEN_NAME);

            //Создаём окно
            Display.create();



        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
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
        glClearColor(1, 1, 1, 1);
    }

    ///Рисует все клетки
    public static void render() {
        ///Очищает экран от старого изображения
        glClear(GL_COLOR_BUFFER_BIT);

        for (GUIElement[] line : gameLogic.getMap().getDrawMap()) {
            for (GUIElement cell : line) {
                drawElement(cell);
            }
        }
        //drawNumber("38", CELL_SIZE * 5, CELL_SIZE * 3 + CELL_SIZE*0.1f, CELL_SIZE*0.5f, CELL_SIZE*0.8f, 0, 0, 0);
        for (GUIElement mob : gameLogic.getMonsters()) {
            drawElement(mob);
        }
        //gameLogic.getBuildings().stream().map((p)->drawElement(p)).close();
        for (GUIElement mob : gameLogic.getBuildings()) {
            drawElement(mob);
        }
    }

    ///Рисует элемент, переданный в аргументе
    private static void drawElement(GUIElement elem) {
        elem.getSprite().getTexture().bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(elem.getRenderX(), elem.getRenderY() + elem.getHeight());
        glTexCoord2f(1, 0);
        glVertex2f(elem.getRenderX() + elem.getWidth(), elem.getRenderY() + elem.getHeight());
        glTexCoord2f(1, 1);
        glVertex2f(elem.getRenderX() + elem.getWidth(), elem.getRenderY());
        glTexCoord2f(0, 1);
        glVertex2f(elem.getRenderX(), elem.getRenderY());
        glEnd();

        String val = elem.getParams();
        if(val!=null)
            drawNumber(val, elem.getRenderX(), elem.getRenderY(), elem.getWidth(), elem.getHeight(), 0, 0, 0);
    }

    public static void drawElement(Sprite sprite, float x, float y, float width, float height) {
        sprite.getTexture().bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y);
        glEnd();
    }

    public static void drawNumber(String number, float x, float y, float width, float height, float r, float g, float b) {
        GL11.glColor3f(r, g, b);
        float realWidth = width/number.toCharArray().length;
        for (char c : number.toCharArray()) {
            Sprite choice = Sprite.ERROR;
            switch (c) {
                case '0':
                    choice = Sprite.N0;
                    break;
                case '1':
                    choice = Sprite.N1;
                    break;
                case '2':
                    choice = Sprite.N2;
                    break;
                case '3':
                    choice = Sprite.N3;
                    break;
                case '4':
                    choice = Sprite.N4;
                    break;
                case '5':
                    choice = Sprite.N5;
                    break;
                case '6':
                    choice = Sprite.N6;
                    break;
                case '7':
                    choice = Sprite.N7;
                    break;
                case '8':
                    choice = Sprite.N8;
                    break;
                case '9':
                    choice = Sprite.N9;
                    break;
                default:
                    System.out.println("NOT NUMBER");
            }
            drawElement(choice, x, y, realWidth, height);
            x = x + realWidth;
        }
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    /*
    private static void drawText(char[] text, int length, int x, int y) {
        glMatrixMode(GL_PROJECTION);
	    double []matrix = new double[16];
        glGetDoublev(GL_PROJECTION_MATRIX, matrix);
        glLoadIdentity();
        glOrtho(0,400,0,400,-5,5);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glPushMatrix();
        glLoadIdentity();
        glRasterPos2i(x,y);
        for(int i=0; i<length; i++)
        {
            glutBitmapCharacter(GLUT_BITMAP_9_BY_15,(int)text[i]);
        }
        glPopMatrix();
        glMatrixMode(GL_PROJECTION);
        glLoadMatrixd(matrix);
        glMatrixMode(GL_MODELVIEW);

    }
    */
}
