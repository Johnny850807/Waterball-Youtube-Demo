
package 策略模式版;


public class Main {
    public static void main(String[] args) {
        Hero hero1 = new Hero("小王", new 天外飛仙());
        Hero hero2 = new Hero("小明", new Waterball());

        Battle battle = new Battle(hero1, hero2);
        battle.start();
    }
}
