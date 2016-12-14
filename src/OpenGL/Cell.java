package OpenGL;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public class Cell implements GUIElement {
    private int x;
    private int y;
    private Sprite tile;

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public Sprite getSprite() {
        return tile;
    }

    @Override
    public int receiveClick(int x, int y, int button) {
        return 0;
    }
}
