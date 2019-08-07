package 策略模式版;

public interface Skill {
    /**
     * @param attackingHero 攻擊者
     * @param attackedHero 被攻擊者
     * @return 回傳此次攻擊的傷害值sdfsd
     */
    int attack(Hero attackingHero, Hero attackedHero);
}
