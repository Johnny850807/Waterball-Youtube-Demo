package animal;

public class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void talk() {
        System.out.println("喵喵");
    }

    @Override
    public void sleep() {
        System.out.println("貓睡覺");
    }

    @Override
    public void eat() {

    }

    public void scratch() {
        System.out.println("抓抓");
    }
}
