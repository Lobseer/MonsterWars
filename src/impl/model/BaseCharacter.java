package impl.model;

import api.service.GUIElement;
import api.service.GameService;
import impl.service.Sprite;

import api.model.ArmorType;
import api.model.Character;
import api.model.actions.CharacterAction;
import api.model.scope.Movable;
import impl.model.buildings.Spawner;
import impl.model.weapons.BaseWeapon;
import impl.model.weapons.MaleAttack;
import impl.service.Vector2Int;

import static impl.service.GameMonsterWarsPreview.CELLS_COUNT_X;
import static impl.service.GameMonsterWarsPreview.CELLS_COUNT_Y;
import static impl.service.GUI.CELL_SIZE;

/**
 * Basic class for all movable characters
 *
 * @author lobseer
 * @version 31.12.2016
 */
public abstract class BaseCharacter implements Character, Movable, GUIElement {
    private volatile float health;
    protected float moveSpeed;

    protected BaseWeapon weapon;
    private ArmorType armorType;

    protected Vector2Int position;
    private Sprite icon;
    protected GameService gameService;

    protected BaseCharacter(GameService gameService) {
        this.icon = Sprite.MONSTER;
        this.health = 10;
        this.moveSpeed = 1f;
        this.gameService = gameService;
        this.weapon = new MaleAttack();
        this.armorType = ArmorType.NO_ARMOR;
    }

    protected BaseCharacter(GameService gameService, Sprite icon, float health, float moveSpeed, BaseWeapon weapon, ArmorType armorType) {
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.weapon = weapon;
        this.icon = icon;
        this.gameService = gameService;
        this.armorType = armorType;
    }

    @Override
    public synchronized final void modifyHealth(float val) {
        if (isDead()) return;
        this.health += val;
        if (isDead())
            die();
    }

    @Override
    public void takeAction(CharacterAction action) {
        if (action != null)
            action.doAction(this);
    }

    protected final boolean isEnemy(Character character) {
        if (character instanceof Spawner) {
            Class home = ((Spawner) character).getProtoClass();
            if (this.getClass().isAssignableFrom(home)) return false;
        }
        return !this.getClass().isAssignableFrom(character.getClass());
    }

    @Override
    public final float getHealth() {
        return this.health;
    }

    @Override
    public ArmorType getArmor() {
        return armorType;
    }

    @Override
    public final float getMoveSpeed() {
        return this.moveSpeed;
    }

    public final int getAttackPower() {
        if (weapon != null) return this.weapon.getAttackPower();
        return 1;
    }

    public final float getAttackSpeed() {
        if (weapon != null) return this.weapon.getAttackSpeed();
        return 1;
    }

    public final int getAttackRange() {
        if (weapon != null) return this.weapon.getAttackRange();
        return 1;
    }

    @Override
    public final Vector2Int getPosition() {
        return position;
    }

    @Override
    public final void setStartPosition(Vector2Int position) {
        if (position == null) throw new NullPointerException();
        this.position = position;
        gameService.getMap().putCharacter(this, position);
    }

    @Override
    public final boolean canMoveTo(Vector2Int point) {
        if (point.x < 0 || point.x >= CELLS_COUNT_X || point.y < 0 || point.y >= CELLS_COUNT_Y) return false;
        Sprite tile = gameService.getMap().getTile(point);
        switch (tile) {
            case HIGHT_MOUNTAIN:
                return false;
            case MOUNTAIN:
                if (!canFly()) return false;
            case WATER:
                if (!(canFly() || canSwim())) return false;
        }
        return !gameService.getMap().isOccupied(point);
    }

    @Override
    public void moveTo(Vector2Int point) {
        gameService.getMap().putCharacter(null, position);
        this.position = point;
        gameService.getMap().putCharacter(this, point);
    }

    @Override
    public final boolean isDead() {
        return health <= 0;
    }

    @Override
    public abstract void die();

    //GUI
    @Override
    public final void receiveClick() {
        System.out.println(this.getClass().getSimpleName() + ": " + position);
    }

    @Override
    public final int getWidth() {
        return CELL_SIZE;
    }

    @Override
    public final int getHeight() {
        return CELL_SIZE;
    }

    @Override
    public final int getRenderY() {
        return position.y * getHeight();
    }

    @Override
    public final int getRenderX() {
        return position.x * getWidth();
    }

    @Override
    public final Sprite getSprite() {
        return icon;
    }

    @Override
    public String toString() {
        return String.format("%1s: position=%2s", this.getClass().getSimpleName(), position);
    }
}
