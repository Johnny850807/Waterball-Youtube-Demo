package animal;

public class Fish extends Animal {
    public Fish(String name) {
        super(name);
    }

    @Override
    public void talk() {
        System.out.println("吱吱");
    }

    @Override
    public void sleep() {
        System.out.println("魚睡覺");
    }

    @Override
    public void eat() {

    }
}
