import OpenGL.Cell;
import OpenGL.GUI;
import OpenGL.GUIElement;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Main {

    private static boolean end_of_game=false;

    public static void main(String[] args) {
        GUI.init();
        Cell c = new Cell();
        while(!end_of_game){
            input();
            GUI.update();
            //GUI.draw();
        }
    }

    static void input() {
        ///Если за последний такт произошли какие-то события с мышью,
        ///перебираем их по очереди
        while(Mouse.next()){
            ///Если это было нажатие кнопки мыши, а не
            ///перемещение...
            if(Mouse.getEventButton()>=0 && Mouse.getEventButtonState()){
                int result;
                ///Отсылаем это на обработку в GUI
                //result = GUI.receiveClick(Mouse.getEventX(), Mouse.getEventY(), Mouse.getEventButton());
            }
        }

        ///То же самое с клавиатурой
        while(Keyboard.next()){
            if(Keyboard.getEventKeyState()){
                if(Keyboard.getEventKey()==Keyboard.KEY_ESCAPE){
                    end_of_game = true;
                }
            }
        }

        ///Обрабатываем клик по кнопке "закрыть" окна
        end_of_game=end_of_game || Display.isCloseRequested();
    }
}
