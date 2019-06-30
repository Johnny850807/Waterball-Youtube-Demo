package 策略模式版;

import 初版.MpNotEnoughException;

public class Hero {
    private String name; // 名子
    private int hp = 500;  //生命
    private int mp = 200;  //魔力
    private int strength = 150; //力量
    private int wisdom = 80;  //智力
    private int defense = 50; //防禦力
    private Skill skill;

    public Hero(String name, Skill skill) {
        this.name = name;
        this.skill = skill;
    }

    public void attack(Hero attackedHero) {
        int injury = getSkill().attack(this, attackedHero);
        System.out.printf("%s 使用了 %s，傷害值為 %d。\n", getName(), skill, injury);
        System.out.printf("%s 的Hp剩下 %d。\n", attackedHero.getName(), attackedHero.getHp());
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void lostHp(int hp) {
        setHp(getHp() - hp);
    }

    public void lostMp(int mp) {
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
