package impl.model.buildings;

import api.service.GameService;
import impl.service.Sprite;
import api.model.actions.CharacterAction;
import impl.model.weapons.Spawn;
import impl.model.monster.BaseMonster;
import impl.service.Vector2Int;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.*;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */

public class Spawner extends BaseBuilding implements Runnable{
    private BaseMonster monsterPrototype;
    private int spawnSpeed;

    private int mobCount;
    private int mobMaxCount;

    ExecutorService mobPool;
    Thread spawnThread;

    public Spawner(GameService map, Sprite icon, Vector2Int position, float health, BaseMonster monsterPrototype, int spawnSpeed, int mobMaxCount) {
        super(map, icon, position, health);
        this.monsterPrototype = monsterPrototype;
        this.spawnSpeed = spawnSpeed;
        this.mobMaxCount = mobMaxCount;

        mobPool = Executors.newCachedThreadPool(); //(r)-> new Thread(r, "Mob: " + r.getClass().getSimpleName()+" - "+Thread.activeCount()));

        gameService.getBuildings().add(this);
        gameService.getMap().putCharacter(this, position);

        spawnThread = new Thread(this, "Spawner: "+ getProtoClass().getSimpleName());
        spawnThread.start();
    }

    public void Spawn() {
        BaseMonster mob = monsterPrototype.clone();
        Vector2Int startPos;
        do {
            startPos = position.add(Vector2Int.getRandomDirection());
        } while (!mob.canMoveTo(startPos));

        mob.setStartPosition(startPos);
        gameService.getMonsters().add(mob);

        Thread mobThread = new Thread(mob);
        mob.mobThread = mobThread;
        mobPool.submit(mobThread);
        mobCount++;
    }

    @Override
    public boolean canDoAction(CharacterAction action) {
        return action instanceof Spawn;
    }

    @Override
    public void takeAction(CharacterAction action) {
        if(canDoAction(action)){

        }
    }

    public Class getProtoClass() {return monsterPrototype.getClass();}

    public void die() {
        gameService.getMap().putCharacter(null, position);
        gameService.getBuildings().remove(this);
        close();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if(mobCount<=mobMaxCount) {
                    Spawn();
                    sleep((long) (spawnSpeed * 1000));
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupt<<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    @Override
    public String toString() {
        return String.format("%1s Spawner: speed = %2s", monsterPrototype.getClass().getSimpleName(), spawnSpeed);
    }

    @Override
    public void close() {
        spawnThread.interrupt();
    }
}
