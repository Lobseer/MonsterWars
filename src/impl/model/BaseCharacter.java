package impl.model;

import OpenGL.Cell;
import OpenGL.GUIElement;
import OpenGL.Sprite;
import static OpenGL.Constants.*;
import api.model.AttackType;
import api.model.Character;
import api.model.CharacterAction;
import api.model.Npc;
import api.model.monster.*;
import impl.model.attacks.BaseAttack;
import impl.model.attacks.MaleAttack;

import java.awt.*;

/**
 * Created by Denis on 5/27/2015.
 */
public abstract class BaseCharacter implements Character, Movable, GUIElement {
    protected int health;
    protected float moveSpeed;
    protected BaseAttack weapon;


    protected volatile Point position;
    private Sprite icon;
    protected Cell[][] area;

    public BaseCharacter(Cell[][] area) {
        this(Sprite.MONSTER, 10, 1f, area);
    }

    public BaseCharacter(Sprite icon, int health, float moveSpeed, Cell[][] area) {
        this.icon = icon;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.area = area;
        this.weapon = new MaleAttack();
    }

    public BaseCharacter(Sprite icon, int health, float moveSpeed, BaseAttack weapon, Point position,  Cell[][] area) {
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.weapon = weapon;
        this.position = position;
        this.icon = icon;
        this.area = area;
    }

    @Override
    public boolean canDoAction(CharacterAction action) {
        return false;
    }

    @Override
    public final void modifyHealth(int val) {
        if(health==0) return;
        this.health += val;
        if(this.health < 0) {
            this.health = 0;
            //System.out.println(getClass().getName() + " is DEAD");
        }
    }

    @Override
    public final void doAction(CharacterAction action){
        //implementing default algorithm for doing any action and making it immutable
        //if(canDoAction(action) && health > 0) {
        //    action.doAction();
        //}
    }

    @Override
    public final int getHealth() {
        return this.health;
    }

    @Override
    public final float getMoveSpeed() {
        return this.moveSpeed;
    }

    public final float getAttackSpeed() {
        return this.weapon.getAttackSpeed();
    }

    @Override
    public final Point getPosition() {
        return position;
    }

    @Override
    public final boolean isNpc() {
        return Npc.class.isAssignableFrom(this.getClass());
    }

    @Override
    public final boolean canMove() {
        return Movable.class.isAssignableFrom(this.getClass());
    }

    protected boolean canFly(Object o){
        return Flying.class.isAssignableFrom(o.getClass());
    }

    protected boolean canSwim(Object o) {
        return Swimming.class.isAssignableFrom(o.getClass());
    }

    @Override
    public int getWidth() {
        return CELL_SIZE;
    }

    @Override
    public int getHeight() {
        return CELL_SIZE;
    }

    @Override
    public int getRenderY() {
        return position.y * getHeight();
    }

    @Override
    public int getRenderX() {
        return position.x * getWidth();
    }

    @Override
    public final Sprite getSprite() {
        return icon;
    }
}
