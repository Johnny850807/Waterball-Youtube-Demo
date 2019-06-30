/* MIT License
Copyright (c) 2018 WaterBall

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
==========================*/

package 初版;

import 初版.Hero;

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
