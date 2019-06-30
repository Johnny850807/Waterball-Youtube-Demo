
package 策略模式版;

public class Colliding implements Skill {
    @Override
    public int attack(Hero attackingHero, Hero attackedHero) {
        int injury = attackingHero.getStrength() - attackedHero.getDefense();
        attackedHero.lostHp(injury);
        return injury;
    }

    @Override
    public String toString() {
        return "衝撞";
    }
}
