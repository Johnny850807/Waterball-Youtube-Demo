
package 初版;

public class Battle {
    private Hero[] heroes = new Hero[2];

    public Battle(Hero hero1, Hero hero2) {
        heroes[0] = hero1;
        heroes[1] = hero2;
    }

    public void start() {
        int turn = 1;

        while(!isGameOver())
        {
            turn = turn == 0 ? 1 : 0;
            processHeroTurn(turn);
        }

        System.out.printf("勝利者為%s。\n", heroes[turn].getName());
    }

    private boolean isGameOver() {
        return !heroes[0].isAlive() || !heroes[1].isAlive();
    }

    private void processHeroTurn(int heroIndex) {
        Hero attackingHero = heroes[heroIndex];
        Hero attackedHero = heroes[heroIndex == 0 ? 1 : 0];
        System.out.printf("輪到%s。\n", attackingHero.getName());
        attackingHero.attack(attackedHero);
    }
}
