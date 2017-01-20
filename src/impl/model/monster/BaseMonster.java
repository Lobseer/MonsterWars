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
import java.util.concurrent.atomic.AtomicInteger;

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
    private AtomicInteger counter;

    public void setCounter(AtomicInteger counter) {
        if (counter != null) {
            this.counter = counter;
        }
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
    public final int getAggressionDistance() {
        return aggressionDistance;
    }

    @Override
    public final boolean canDoAction(CharacterAction action) {
        //if(canFly(action.getActionTarget()) && ! canFly(this)) {
        //    return false;
        //}
        return false;
    }

    @Override
    public final void attack(Character target) {
        target.takeAction(weapon.getAction());
    }


    private void changeState(MonsterState newState) {
        if (newState != null)
            state = newState;
    }

    @Override
    public Character searchEnemy(int radius) {
        int x, y;
        Character target;

        for (int circle = 1; circle <= radius; circle++) {

            y = position.y - circle;
            if (y >= 0 && y < CELLS_COUNT_Y)
                for (int i = -circle; i <= circle; i++) {
                    x = position.x + i;
                    if (x >= 0 && x < CELLS_COUNT_X) {
                        target = gameService.getMap().getCell(new Vector2Int(x, y)).getCharacter();
                        if (target != null && isEnemy(target)) return target;
                    }
                }

            x = position.x + circle;
            if (x >= 0 && x < CELLS_COUNT_X)
                for (int i = -circle + 1; i <= circle; i++) {
                    y = position.y + i;
                    if (y >= 0 && y < CELLS_COUNT_Y) {
                        target = gameService.getMap().getCell(new Vector2Int(x, y)).getCharacter();
                        if (target != null && isEnemy(target)) return target;
                    }
                }

            y = position.y + circle;
            if (y >= 0 && y < CELLS_COUNT_Y)
                for (int i = circle - 1; i >= -circle; i--) {
                    x = position.x + i;
                    if (x >= 0 && x < CELLS_COUNT_X) {
                        target = gameService.getMap().getCell(new Vector2Int(x, y)).getCharacter();
                        if (target != null && isEnemy(target)) return target;
                    }
                }

            x = position.x - circle;
            if (x >= 0 && x < CELLS_COUNT_X)
                for (int i = circle - 1; i >= -circle + 1; i--) {
                    y = position.y + i;
                    if (y >= 0 && y < CELLS_COUNT_Y) {
                        target = gameService.getMap().getCell(new Vector2Int(x, y)).getCharacter();
                        if (target != null && isEnemy(target)) return target;
                    }
                }
        }
        return null;
    }

    @Override
    public abstract BaseMonster clone();

    @Override
    public final void die() {
        gameService.getMap().putCharacter(null, position);
        gameService.getMonsters().remove(this);
        System.out.printf("%1s count = %2s\n", getClass().getSimpleName(), counter.incrementAndGet());
        if (mobThread != null) close();
    }

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
            System.out.printf("Ex \"%1s\"; thread: %2s\n", ex.getMessage(), Thread.currentThread().getName());
        } catch (Exception ex) {
            System.out.println("In Monster " + ex.getMessage());
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






