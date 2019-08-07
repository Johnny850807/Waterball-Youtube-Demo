

package 初版;

import commons.MpNotEnoughException;

public class Hero {
    private String name; // 名子
    private int hp = 500;  //生命
    private int mp = 200;  //魔力
    private int strength = 150; //力量
    private int wisdom = 80;  //智力
    private int defense = 50; //防禦力
    private Skill skill;

    public enum Skill {
        COLLIDING, WATERBALL, A, B, C, D, E, F, G, H
    }

    public Hero(String name, Skill skill) {
        this.name = name;
        this.skill = skill;
    }

    public void attack(Hero targetHero) {
        switch (skill) {
            case COLLIDING:
                System.out.println("%s 使用了 衝撞攻擊。");
                targetHero.lostHp(getStrength() - targetHero.getDefense());
                break;
            case WATERBALL:
                System.out.println("%s 使用了 水球攻擊。");
                lostMp(5);
                targetHero.lostHp(getWisdom() * 2);
                break;
            case A:
                /**
                 * 新增絕招A
                 */
                break;
            case B:
                /**
                 * 新增絕招B
                 */
                break;
        }

        System.out.printf("%s 的Hp剩下 %d。\n", targetHero.getName(), targetHero.getHp());
    }

    private void lostHp(int hp) {
        setHp(getHp() - hp);
    }

    private void lostMp(int mp) {
        if (getMp() < mp)
            throw new MpNotEnoughException();
        setMp(getMp() - mp);
    }

    public boolean isAlive() {
        return hp > 0;
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
