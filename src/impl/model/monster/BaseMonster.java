package impl.model.monster;

import OpenGL.Sprite;
import static OpenGL.Constants.*;
import static java.lang.Thread.*;

import api.model.*;
import api.model.monster.*;
import impl.model.BaseCharacter;

import java.awt.*;
import java.util.Random;

/**
 * Created by Denis on 5/27/2015.
 */
public abstract class BaseMonster extends BaseCharacter implements Monster, Runnable {
    protected int attackPower;
    private int attackDistance;

    public BaseMonster() {
        this(Sprite.MONSTER, 10, 1f, 1, 1);
    }

    public BaseMonster( Sprite icon, int health, float speed, int attackPower, int attackDistance) {
        super(icon, health, speed);
        this.attackPower = attackPower;
        this.attackDistance = attackDistance;
    }

    @Override
    public final boolean canDoAction(CharacterAction action) {
        if(canFly(action.getActionTarget()) && ! canFly(this)) {
            return false;
        }
        return true;
    }

    public final void attack(api.model.Character character) {
        getAttack(character).doAction();
    }

    protected abstract CharacterAction getAttack(api.model.Character character);

    public final void moveTo(Point point) {
        this.position = point;

    }

    public final boolean canMoveTo(Point point) {
        return false;
    }

    @Override
    public int getAttackDistance() {
        return this.attackDistance;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        try {
            while (getHealth() != 0) {
                int xdir = rnd.nextInt(3)-1;
                int x = position.x + xdir;
                int ydir = rnd.nextInt(3)-1;
                int y = position.y + ydir;

                Point newPos = new Point(
                        (x>0 && x<CELLS_COUNT_X)?x:position.x,
                        (y>0 && y<CELLS_COUNT_Y)?y:position.y);
                moveTo(newPos);
                int time = (int)( 1 / getSpeed() * 1000 );
                sleep(time);// t = s/v * 1000ms
            }
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

