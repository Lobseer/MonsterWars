package api.model.monster;

import impl.service.Vector2Int;

import java.awt.*;

/**
 * Created by Denis on 5/27/2015.
 */
public interface Movable {
    void moveTo(Vector2Int point);
    boolean canMoveTo(Vector2Int point);
    float getMoveSpeed();
}
