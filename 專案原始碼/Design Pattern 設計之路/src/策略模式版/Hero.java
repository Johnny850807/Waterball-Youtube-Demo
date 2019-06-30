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

public class Hero {
    private String name; // 名子
    private int hp = 500;  //生命
    private int mp = 200;  //魔力
    private int strength = 150; //力量
    private int wisdom = 80;  //智力
    private int defense = 50; //防禦力
    private Skill skill;

    public enum Skill {
        COLLIDING("衝撞"), WATERBALL("水球");

        private String name;

        Skill(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public Hero(String name, Skill skill) {
        this.name = name;
        this.skill = skill;
    }


    public void attack(Hero attackedHero) {
        int injury = 0;

        switch (skill)
        {
            case COLLIDING:
                injury = getStrength() - attackedHero.getDefense();
                break;
            case WATERBALL:
                lostMp(5);
                injury = getWisdom()*2;
                break;
        }

        System.out.printf("%s 使用了 %s，傷害值為 %d。\n", getName(), skill, injury);
        attackedHero.lostHp(injury);
        System.out.printf("%s 的Hp剩下 %d。\n", attackedHero.getName(), attackedHero.getHp());
    }

    public boolean isAlive() {
        return hp > 0;
    }

    private void lostHp(int hp) {
        setHp(getHp() - hp);
    }

    private void lostMp(int mp) {
        if (getMp() < mp)
            throw new MpNotEnoughException();
        setMp(getMp() - mp);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp <= 0 ? 0 : hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (!isAlive())
            System.out.printf("%s 已陣亡。\n", getName());
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
