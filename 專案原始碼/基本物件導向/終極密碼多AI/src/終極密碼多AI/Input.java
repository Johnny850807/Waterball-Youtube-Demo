package 終極密碼多AI;

import java.util.Scanner;

public class Input {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String next(String message){
		String input = "-";
		do
		{
			System.out.println(message);
			input = scanner.nextLine();
			if (input.isEmpty())
				System.out.println("請再輸入一次 ! ");
		}while( input.isEmpty() );  
		return input;
	}

	public static int nextInt(String message){
		System.out.println(message);
		int input = scanner.nextInt();
		scanner.nextLine(); //吃掉換行符號
		return input;
	}
	
	public static int nextInt(int min, int max){
		int input = 0;
		do
		{
			input = scanner.nextInt();
			scanner.nextLine(); //吃掉換行符號
			if ( input <= min || input >= max || input < 0 )
				System.out.println("請再輸入一次 ! ");
		}while( input <= min || input >= max || input < 0  );  
		return input;
	}
	
	public static int nextInt(String message, int max){
		int input = 0;
		do
		{
			System.out.println(message);
			input = scanner.nextInt();
			scanner.nextLine(); //吃掉換行符號
			if ( input >= max || input < 0 )
				System.out.println("請再輸入一次 ! ");
		}while( input >= max || input < 0  );  
		return input;
	}
}
