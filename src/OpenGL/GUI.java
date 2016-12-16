package OpenGL;

import api.service.GameService;

import impl.service.GameServiceImpl;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import static OpenGL.Constants.*;
import static org.lwjgl.opengl.GL11.*;


/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class GUI {
    private static GameService gameLogic;

    public static void init(){
        initializeOpenGL();
        gameLogic = new GameServiceImpl();
    }

    //Этот метод будет вызываться извне
    public static void update() {
        gameLogic.update();
        updateOpenGL();
    }

    //А этот метод будет использоваться только локально,
    // т.к. базовым другие классы должны работать на более высоком уровне
    private static void updateOpenGL() {
        Display.update();
        Display.sync(60);
    }

    private static void initializeOpenGL(){
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
    public static void draw(){
        ///Очищает экран от старого изображения
        glClear(GL_COLOR_BUFFER_BIT);

        for(GUIElement[] line : gameLogic.getCells()){
            for(GUIElement cell : line){
                drawElement(cell);
            }
        }
        for(GUIElement mob : gameLogic.getMonsters()) {
            drawElement(mob);
        }
    }

    ///Рисует элемент, переданный в аргументе
    private static void drawElement(GUIElement elem){
        elem.getSprite().getTexture().bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0,0);
        glVertex2f(elem.getRenderX(),elem.getRenderY()+elem.getHeight());
        glTexCoord2f(1,0);
        glVertex2f(elem.getRenderX()+elem.getWidth(),elem.getRenderY()+elem.getHeight());
        glTexCoord2f(1,1);
        glVertex2f(elem.getRenderX()+elem.getWidth(), elem.getRenderY());
        glTexCoord2f(0,1);
        glVertex2f(elem.getRenderX(), elem.getRenderY());
        glEnd();
    }

}
