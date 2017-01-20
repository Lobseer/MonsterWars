package impl.model.buildings;

import api.service.GameService;
import impl.service.Sprite;
import api.model.actions.CharacterAction;
import impl.model.monster.BaseMonster;
import impl.service.Vector2Int;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.*;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */

public class Spawner<T extends BaseMonster>  extends BaseBuilding implements Runnable {

    private T protoMob;
    private float spawnSpeed;

    private AtomicInteger counter;

    public Thread spawnThread;

    public Spawner(GameService map, Sprite icon, float health, T protoMob, float spawnSpeed, int mobMaxCount) {
        super(map, icon, health);
        this.protoMob = protoMob;
        this.spawnSpeed = spawnSpeed;
        this.counter = new AtomicInteger(mobMaxCount);
        //mobPool = Executors.newCachedThreadPool();
        //(r)-> new Thread(r, "Mob: " + r.getClass().getSimpleName()+" - "+Thread.activeCount()));

        //mobStatus = ((GameMonsterWarsPreview)gameService).gameStats.stream().filter((p)->p.type==getProtoClass()).findFirst().get();

        //T.count++;

        //spawnThread = new Thread(this, "Spawner: "+ getProtoClass().getSimpleName());
        //spawnThread.start();
    }

    public void Spawn() {
        BaseMonster mob = protoMob.clone();
        Vector2Int startPos;
        do {
            startPos = position.add(Vector2Int.getRandomDirection());
        } while (!mob.canMoveTo(startPos));

        counter.getAndDecrement();
        mob.setCounter(counter);

        System.out.printf("Spawn:%1s; Count:%2s\n",getProtoClass().getSimpleName(), counter.get());

        mob.setStartPosition(startPos);
        gameService.getMonsters().add(mob);

        Thread mobThread = new Thread(mob, getProtoClass().getSimpleName()+" - " + counter.get());
        mob.mobThread = mobThread;
        mobThread.start();
    }

    @Override
    public boolean canDoAction(CharacterAction action) {
        return false;
    }

    @Override
    public void takeAction(CharacterAction action) {
        if(action!=null)
            action.doAction(this);
    }

    public Class getProtoClass() {return protoMob.getClass();}

    @Override
    public void die() {
        gameService.getMap().putCharacter(null, position);
        gameService.getBuildings().remove(this);
        close();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if(counter.get()>0) {
                    //System.out.println(getProtoClass().getSimpleName()+" - "+mobStatus.count);
                    sleep((long) (spawnSpeed * 1000));
                    Spawn();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupt<<<<<<<<<<<<<<<<<<<<<<");
        } catch (Exception ex) {
            System.out.println("In Spawner "+ex.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("%1s Spawner: speed = %2s", getProtoClass().getSimpleName(), spawnSpeed);
    }

    @Override
    public void close() {
        spawnThread.interrupt();
    }
}
