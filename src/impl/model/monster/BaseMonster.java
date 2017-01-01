package impl.model.monster;

import OpenGL.Sprite;
import static OpenGL.Constants.*;
import static java.lang.Thread.*;

import api.model.*;
import api.model.Character;
import api.model.monster.*;
import impl.model.BaseCharacter;
import impl.model.attacks.BaseAttack;
import impl.service.GameServiceImpl;
import impl.service.Vector2Int;

/**
 * Created by Denis on 5/27/2015.
 */
public abstract class BaseMonster extends BaseCharacter implements Monster, Runnable, Cloneable, AutoCloseable{
    private int aggressionDistance;
    private Thread mobThread;

    protected BaseMonster(GameServiceImpl gameService) {
        super(gameService);
        this.aggressionDistance = 3;
    }

    protected BaseMonster(GameServiceImpl area, Sprite icon, float health, float speed, BaseAttack weapon, ArmorType armorType, int aggressionDistance) {
        super(area, icon, health, speed, weapon, armorType);
        this.aggressionDistance = aggressionDistance;
    }

    @Override
    public final boolean canDoAction(CharacterAction action) {
        //if(canFly(action.getActionTarget()) && ! canFly(this)) {
        //    return false;
        //}
        return true;
    }

    @Override
    public void doAction(CharacterAction action) {

    }

    @Override
    public final void attack(Character character) {
        weapon.doAction(character);
    }

    @Override
    public final int getAggressionDistance() {
        return aggressionDistance;
    }

    public final synchronized boolean isDead() {return health<=0;}

    public final void die() {
        gameService.getMap().putCharacter(null, position);
        gameService.getMonsters().remove(this);
        try {
            close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        mobThread.interrupt();
    }

    @Override
    public void run() {
        try {
            Character target = null;
            while (!Thread.currentThread().isInterrupted()) {
                if(getHealth()==0) {
                    die();
                    break;
                }
                System.out.println(Thread.currentThread().getName());
                target = searchEnemy(aggressionDistance);

                if(target==null) {
                    System.out.println("Move");
                    System.out.println("My pos: "+position);
                    Vector2Int point;
                    do {
                        point = position.add(Vector2Int.getRandomDirection());
                    } while(!canMoveTo(point));
                    moveTo(point);
                    System.out.println("My pos: "+position);
                    sleep((long)( 1 / getMoveSpeed() * 1000 ));// t = s/v * 1000ms
                }
                else {
                    int dist = Vector2Int.getDistance(position, target.getPosition());
                    System.out.println("dist = "+dist);
                    if(dist > getAttackRange()) {
                        Vector2Int dir = Vector2Int.getDirection(position, target.getPosition());
                        System.out.println("Move to ");
                        System.out.println("My pos: "+position);
                        System.out.println("Tar pos: "+target.getPosition());
                        System.out.println("Dir = "+dir);
                        dir.normalize();
                        System.out.println("Dir norm = "+dir);
                        Vector2Int point;
                        do {
                            point = position.add(dir);
                        } while(!canMoveTo(point));
                        moveTo(point);
                        System.out.println("My pos: "+position);
                        sleep((long)( 1 / getMoveSpeed() * 1000 ));// t = s/v * 1000ms


                    } else {
                        System.out.println("My health = "+health);
                        System.out.println("Enemy health = "+target.getHealth());
                        attack(target);
                        System.out.println("Attack: "+target);
                        sleep((long)( 1 / getAttackSpeed() * 1000 ));// t = s/v * 1000ms
                        if(target.isDead()) {
                            target = null;
                            continue;
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("Close "+Thread.currentThread().getName());
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Character searchEnemy(int radius) {
        int x, y;
        Character target;
        for(int circle = 1; circle<=radius; circle++) {
            for (int i = -circle; i<=circle; i++){
                for(int j = -circle; j<=circle; j++) {
                    if(i==0 && j==0);
                }
            }
        }

        for (int i = -radius; i <= radius; i++) {
            x = position.x + i;
            if(x>0 && x<CELLS_COUNT_X) {
                for (int j = -radius; j <= radius; j++) {
                    y = position.y + j;
                    if (y > 0 && y < CELLS_COUNT_Y) {
                        target = gameService.getMap().getCell(new Vector2Int(x, y)).getCharacter();
                        if (target != null && isEnemy(target)) {
                            return target;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public abstract BaseMonster clone();
}

