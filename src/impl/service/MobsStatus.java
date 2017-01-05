package impl.service;

/**
 * Class description
 *
 * @author lobseer
 * @version 05.01.2017
 */

public class MobsStatus {
    public Class type;
    public volatile int count = 0;

    public MobsStatus(Class type) {
        this.type = type;
    }
}
