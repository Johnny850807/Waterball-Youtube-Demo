package �h���v��;

public class Main {
	public static void main(String[] argv){
		Animal[] animals = new Animal[]{ new Cat("�p"), new Dog("��") };
		Cat cat = new Cat("ha");
		cat.scratch();
		Cat[] cats = new Cat[]{new Dog("")};
		for (Animal animal : animals)
		{
			System.out.print(animal.getName() + ":");
			animal.talk();
		}
	}
}
