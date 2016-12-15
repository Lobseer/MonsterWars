package impl.model;

import OpenGL.GUIElement;
import OpenGL.Sprite;
import static OpenGL.Constants.*;
import api.model.AttackType;
import api.model.Character;
import api.model.CharacterAction;
import api.model.Npc;
import api.model.monster.*;

import java.awt.*;

/**
 * Created by Denis on 5/27/2015.
 */
public abstract class BaseCharacter implements Character, Movable, GUIElement {
    private int health;
    private float moveSpeed;
    private AttackType attackType;
    private int damage;
    private float attackSpeed;
    protected volatile Point position;
    private Sprite icon;

    @Override
    public int getWidth() {
        return CELL_SIZE;
    }

    @Override
    public int getHeight() {
        return CELL_SIZE;
    }

    @Override
    public int getY() {
        return position.y * getHeight();
    }

    @Override
    public int getX() {
        return position.x * getWidth();
    }

    @Override
    public final Sprite getSprite() {
        if(icon!= null) {
            return icon;
        }
        return null;
    }

    @Override
    public final int getHealth() {
        return this.health;
    }

    @Override
    public final float getSpeed() {
        return this.moveSpeed;
    }

    @Override
    public final Point getPosition() {
        return position;
    }

    public BaseCharacter() {
        this(Sprite.MONSTER, 10, 1f);
    }

    public BaseCharacter(Sprite icon, int health, float moveSpeed ) {
        this.icon = icon;
        this.health = health;
        this.moveSpeed = moveSpeed;
    }

    public BaseCharacter(int health, float moveSpeed, AttackType attackType, int damage, float attackSpeed, Point position, Sprite icon) {
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.attackType = attackType;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.position = position;
        this.icon = icon;
    }

    public boolean canDoAction(CharacterAction action) {
        return false;
    }

    public final void modifyHealth(int val) {
        if(health==0) return;
        this.health += val;
        if(this.health < 0) {
            this.health = 0;
            //System.out.println(getClass().getName() + " is DEAD");
        }
    }

    public final void doAction(CharacterAction action){
        //implementing default algorithm for doing any action and making it immutable
        if(canDoAction(action) && health > 0) {
            action.doAction();
        }
    }

    public final boolean isNpc() {
        return Npc.class.isAssignableFrom(this.getClass());
    }

    public final boolean canMove() {
        return Movable.class.isAssignableFrom(this.getClass());
    }

    protected boolean canFly(Object o){
        return Flying.class.isAssignableFrom(o.getClass());
    }

    protected boolean canSwim(Object o) {
        return Swimming.class.isAssignableFrom(o.getClass());
    }
}
