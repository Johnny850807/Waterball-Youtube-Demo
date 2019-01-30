package 多型影片;

public class Cat extends Animal{

	public Cat(String name) {
		super(name);
	}

	@Override
	public void talk() {
		System.out.println("喵喵");
	}

	@Override
	public void eat() {
		System.out.println("喵喵");
	}

	@Override
	public void mating(Animal animal) {
		System.out.println("喵喵");
	}

	public void scratch(){
		System.out.println("抓抓");
	}
}
