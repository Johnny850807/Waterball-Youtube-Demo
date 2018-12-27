package 多型影片;

public class Main {
	public static void main(String[] argv){
		Animal[] animals = new Animal[]{ new Cat("喵"), new Dog("狗") };
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
