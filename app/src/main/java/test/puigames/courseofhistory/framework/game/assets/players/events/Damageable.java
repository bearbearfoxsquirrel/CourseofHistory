package test.puigames.courseofhistory.framework.game.assets.players.events;

/**
 * Created by Michael on 27/03/2017.
 */

public interface Damageable {
    int getHealth();
    void applyDamage(int damage);
    boolean isDeaders();

    interface Attackable extends Damageable {
        void attack(Damageable recipientOfThyFatalBlow);
        int getAttack();
        boolean hasEnergyToAttack();
    }
}
