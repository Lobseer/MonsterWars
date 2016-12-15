package OpenGL;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public interface GUIElement {

    int getWidth();

    int getHeight();

    int getY();

    int getX();

    Sprite getSprite(); ///Создадим enum с таким именем, заполним позже

    //int receiveClick(int x, int y, int button); /// Возвращаем результат клика
    ///Параметр button определяет кнопку мыши, которой был сделан щелчок.

    /// Здесь используется фишка Java 8 --- default методы в интерфейсах.
    /// Если у вас более ранняя версия, вы можете использовать абстрактный класс
    /// вместо интерфейса.
    default boolean isHit(int xclick, int yclick){
        return ( (xclick > getX()) && (xclick < getX()+this.getWidth()) )
                &&( (yclick > getY()) && (yclick < getY()+this.getHeight()) );
    }
}