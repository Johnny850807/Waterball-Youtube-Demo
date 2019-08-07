package 策略模式版;

public class 天外飛仙 implements Skill {
    @Override
    public int attack(Hero attackingHero, Hero attackedHero) {
        /**
         *
         *
         *
         *
         *
         * 演算法複雜
         *
         *
         *
         *
         *
         *
         *
         *
         */
        attackedHero.lostHp(10000);
        return 10000;
    }

    @Override
    public String toString() {
        return "天外飛仙";
    }
}
