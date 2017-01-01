package api.model;

/**
 * Class description
 *
 * @author lobseer
 * @version 31.12.2016
 */

public enum ArmorType {
    NO_ARMOR(1.5f, 1f, 1.5f), LIGHT(0.9f, 0.8f, 1.3f), MEDIUM(0.9f, 1.1f, 0.9f), HEAVY(0.9f, 1.4f, 0.7f), FORT(0.8f, 0.8f, 0.8f);

    float male;
    float magic;
    float range;

    ArmorType(float male, float magic, float range) {
        this.male = male;
        this.magic = magic;
        this.range = range;
    }

    public float getResist(AttackType attack) {
        switch (attack) {
            case MALE: return male;
            case RANGE: return range;
            case MAGIC: return magic;
            default: return 0;
        }
    }
}
