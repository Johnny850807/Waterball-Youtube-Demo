package 多型影片;

public class Dog extends Animal{

	public Dog(String name) {
		super(name);
	}

	@Override
	public void talk() {
		System.out.println("汪汪");
	}

	@Override
	public void eat() {
		System.out.println("汪汪");
	}

	@Override
	public void mating(Animal animal) {
		System.out.println("汪汪");
	}

}
