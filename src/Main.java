import api.service.GameService;
import impl.service.GUI;
import impl.service.GameMonsterWarsPreview;
import impl.service.Sprite;
// impl.service.GameMonsterWarsPreview;
import impl.service.Vector2Int;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static impl.service.GameMonsterWarsPreview.*;

public class Main {

    private static boolean end_of_game=false;
    private static GameService gameService;

    public static void main(String[] args) {
        GUI.init();
        gameService = new GameMonsterWarsPreview();
        GUI.start(gameService);

        try {
            while(!end_of_game){
                input();
                gameService.update();
                GUI.render();
                GUI.update();
            }
        } catch (Exception ex) {
            System.out.println("In main " + ex.getMessage());
        }
    }

    static void input() throws Exception {
        ///Если за последний такт произошли какие-то события с мышью,
        ///перебираем их по очереди
        /*
        while(Mouse.next()){
            ///Если это было нажатие кнопки мыши, а не
            ///перемещение...
            if(Mouse.getEventButton()>=0 && Mouse.getEventButtonState()){
                gameService.getMap().receiveClick(Mouse.getEventX(), Mouse.getEventY(), Mouse.getEventButton());
            }
        }*/

        ///То же самое с клавиатурой
        while(Keyboard.next()){
            if(Keyboard.getEventKeyState()){
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_ESCAPE:
                        end_of_game = true;
                        break;
                    case Keyboard.KEY_Q:

                        break;
                    case Keyboard.KEY_W:
                        Vector2Int pos = new Vector2Int(0,0);
                        Vector2Int tar = new Vector2Int(CELLS_COUNT_X-1, CELLS_COUNT_Y-1);

                        gameService.getMap().setTile(pos, Sprite.PIG);
                        gameService.getMap().setTile(tar, Sprite.HIGHT_MOUNTAIN);

                        //gameService.wavePathFinding(pos, tar);
                        break;
                    case Keyboard.KEY_E:

                        break;
                }
            }
        }

        ///Обрабатываем клик по кнопке "закрыть" окна
        if(end_of_game || Display.isCloseRequested()) {
            end_of_game = true;
            System.out.println("FULL END OFF GAME---------------------------------------------------------------------");
            //for (Thread thread :
            //        Thread.getAllStackTraces().keySet()) {
            //    System.out.println(thread.getName());
            //}

            for(AutoCloseable mob : gameService.getBuildings()) {
                mob.close();
            }
            for(AutoCloseable mob : gameService.getMonsters()) {
                mob.close();
            }
        }
    }
}
