package animal;

public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void talk() {
        System.out.println("汪汪");
    }

    @Override
    public void sleep() {
        System.out.println("狗睡覺");
    }

    @Override
    public void eat() {

    }
}
