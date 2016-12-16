package impl.model.monster;

import OpenGL.Cell;
import OpenGL.Sprite;
import static OpenGL.Constants.*;
import static java.lang.Thread.*;

import api.model.*;
import api.model.Character;
import api.model.monster.*;
import impl.model.BaseCharacter;

import java.awt.*;
import java.util.Random;

/**
 * Created by Denis on 5/27/2015.
 */
public abstract class BaseMonster extends BaseCharacter implements Monster, Runnable {

    private int aggressionDistance;

    public BaseMonster(Cell[][] area) {
        this(Sprite.MONSTER, 10, 1f, 1, 1, area);
    }

    public BaseMonster(Sprite icon, int health, float speed, int attackPower, int attackDistance, Cell[][] area) {
        super(icon, health, speed, area);
    }

    @Override
    public final boolean canDoAction(CharacterAction action) {
        //if(canFly(action.getActionTarget()) && ! canFly(this)) {
        //    return false;
        //}
        return true;
    }

    @Override
    public final void attack(Character character) {
        weapon.doAction(character);
    }

    @Override
    public int getAggressionDistance() {
        return aggressionDistance;
    }

    public final void moveTo(Point point) {
        area[position.x][position.y].putCharacter(null);
        this.position = point;
        area[position.x][position.y].putCharacter(this);
    }

    public final void startPosition(Point point) {
        this.position = point;
        area[position.x][position.y].putCharacter(this);
    }

    public final boolean canMoveTo(Point point) {
        return false;
    }


    @Override
    public void run() {
        try {
            BaseCharacter target;
            while (getHealth() != 0) {
                target = searchEnemy(aggressionDistance);
                if(target!=null) {
                    attack(target);
                    sleep((int)( 1 / getAttackSpeed() * 1000 ));// t = s/v * 1000ms
                }
                else {
                    randomMove();
                    sleep((int)( 1 / getMoveSpeed() * 1000 ));// t = s/v * 1000ms
                }

            }

        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private BaseCharacter searchEnemy(int radius) {
        int x, y;
        BaseCharacter target;
        for (int i = -radius; i <= radius; i++) {
            x = position.x + i;
            if(x>0 && x<CELLS_COUNT_X) {
                for (int j = -radius; j <= radius; j++) {
                    y = position.y + j;
                    if (y > 0 && y < CELLS_COUNT_Y) {
                        target = area[x][y].getCharacter();
                        if (target != null && !(this.getClass().isAssignableFrom(target.getClass()))) {
                            return target;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void randomMove() {
        Random rnd = new Random();
        int x = position.x + rnd.nextInt(3)-1;
        int y = position.y + rnd.nextInt(3)-1;

        Point newPos = new Point(
                (x>0 && x<CELLS_COUNT_X)?x:position.x,
                (y>0 && y<CELLS_COUNT_Y)?y:position.y);
        moveTo(newPos);
    }
}

