package �h���v��;

public class Cat extends Animal{

	public Cat(String name) {
		super(name);
	}

	@Override
	public void talk() {
		System.out.println("�p�p");
	}

	@Override
	public void eat() {
		System.out.println("�p�p");
	}

	@Override
	public void mating(Animal animal) {
		System.out.println("�p�p");
	}

	public void scratch(){
		System.out.println("���");
	}
}
