package impl.model.monster;

import static impl.service.GameMonsterWarsPreview.*;
import static java.lang.Thread.*;

import api.model.*;
import api.model.Character;
import api.model.actions.CharacterAction;
import api.service.GameService;
import impl.model.BaseCharacter;
import impl.model.weapons.BaseWeapon;
import impl.service.GameMonsterWarsPreview;
import impl.service.Vector2Int;
import impl.service.Sprite;

import java.util.Arrays;


/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */
public abstract class BaseMonster extends BaseCharacter implements Monster, Runnable, Cloneable, AutoCloseable {
    private int aggressionDistance;

    private MonsterState state = new WalkState();

    public Thread mobThread;

    public final void changeState(MonsterState newState) {
        if (newState != null)
            state = newState;
    }

    protected BaseMonster(GameService gameService) {
        super(gameService);
        this.aggressionDistance = 3;
    }

    protected BaseMonster(GameService area, Sprite icon, float health, float speed, BaseWeapon weapon, ArmorType armorType, int aggressionDistance) {
        super(area, icon, health, speed, weapon, armorType);
        this.aggressionDistance = aggressionDistance;
    }

    @Override
    public final boolean canDoAction(CharacterAction action) {
        //if(canFly(action.getActionTarget()) && ! canFly(this)) {
        //    return false;
        //}
        return false;
    }

    @Override
    public final void attack(Character character) {
        character.takeAction(weapon.getAction());
    }

    @Override
    public final int getAggressionDistance() {
        return aggressionDistance;
    }

    @Override
    public final void die() {
        gameService.getMap().putCharacter(null, position);
        gameService.getMonsters().remove(this);
        gameService.getCharacterStatus(this.getClass()).count--;
        if (mobThread != null) close();
    }

    @Override
    public Character searchEnemy(int radius) {
        int x, y;
        Character target;
        //for(int circle = 1; circle<=radius; circle++) {
        //    for (int i = -circle; i<=circle; i++){
        //        for(int j = -circle; j<=circle; j++) {
        //            if(i==0 && j==0);
        //        }
        //    }
        //}

        for (int i = -radius; i <= radius; i++) {
            x = position.x + i;
            if (x > 0 && x < CELLS_COUNT_X) {
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

    @Override
    public void close() {
        if (mobThread != null)
            mobThread.interrupt();
    }

    @Override
    public void run() {
        try {
            while (!mobThread.isInterrupted()) {
                //System.out.println(state);
                state.update();
            }

        } catch (InterruptedException ex) {
            System.out.printf("Ex \"%1s\"; class: %2s; thread: %3s\n", ex.getMessage(), this.getClass().getSimpleName(), Thread.currentThread().getName());
        } catch (Exception ex) {
            System.out.println("In Monster "+ex.getMessage());
        }
    }

    interface MonsterState {
        void update() throws InterruptedException;
    }

    private class WalkState implements MonsterState {
        @Override
        public void update() throws InterruptedException {

            Character target = searchEnemy(getAggressionDistance());
            if (target == null) {
                Vector2Int point;
                do {
                    point = getPosition().add(Vector2Int.getRandomDirection());
                }
                while (!canMoveTo(point));
                moveTo(point);
                sleep((long) (1 / getMoveSpeed() * 1000));// t = s/v * 1000ms
            } else {
                if (Vector2Int.getArrayDistance(getPosition(), target.getPosition()) <= getAttackRange()) {
                    changeState(new AttackState(target));
                } else {
                    changeState(new FollowState(target));
                }
            }
        }

        @Override
        public String toString() {
            return String.format("State: " + this.getClass().getSimpleName());
        }
    }

    private class FollowState implements MonsterState {
        Character target;

        public FollowState(Character target) {
            this.target = target;
        }

        @Override
        public void update() throws InterruptedException {
            if (target != null) {
                int dist = Vector2Int.getArrayDistance(getPosition(), target.getPosition());
                if (dist <= getAttackRange()) {
                    changeState(new AttackState(target));
                } else if (dist <= getAggressionDistance()) {
                    //Vector2Int dir = Vector2Int.getDirection(getPosition(), target.getPosition());
                    //dir.normalize();
                    //Vector2Int point = getPosition().add(dir);
                    Vector2Int[] rad = new Vector2Int[]{
                            new Vector2Int(position.x + 1, position.y),
                            new Vector2Int(position.x - 1, position.y),
                            new Vector2Int(position.x, position.y + 1),
                            new Vector2Int(position.x, position.y - 1),
                    };
                    Vector2Int point = Arrays.stream(rad).
                            filter((r) -> canMoveTo(r)).
                            min((r1, r2) -> Vector2Int.getArrayDistance(r1, target.getPosition()) > Vector2Int.getArrayDistance(r2, target.getPosition()) ? 1 : -1).get();
                    moveTo(point);
                    sleep((long) (1 / getMoveSpeed() * 1000));// t = s/v * 1000ms
                } else {
                    changeState(new WalkState());
                }
            } else {
                changeState(new WalkState());
            }
        }

        @Override
        public String toString() {
            return String.format("State: " + this.getClass().getSimpleName());
        }
    }

    private class AttackState implements MonsterState {
        Character target;

        AttackState(Character target) {
            this.target = target;
        }

        @Override
        public void update() throws InterruptedException {
            if (target != null) {
                int dist = Vector2Int.getArrayDistance(getPosition(), target.getPosition());
                if (dist <= getAttackRange()) {
                    attack(target);
                    if (target.isDead()) {
                        changeState(new WalkState());
                        return;
                    }
                    sleep((long) (1 / getAttackSpeed() * 1000));// t = s/v * 1000ms
                } else if (dist <= getAggressionDistance()) {
                    changeState(new FollowState(target));
                } else {
                    changeState(new WalkState());
                }
            } else {
                changeState(new WalkState());
            }
        }

        @Override
        public String toString() {
            return String.format("State: " + this.getClass().getSimpleName());
        }
    }

}






