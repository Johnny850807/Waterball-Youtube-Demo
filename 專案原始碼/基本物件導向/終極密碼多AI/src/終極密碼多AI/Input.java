package �׷��K�X�hAI;

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
				System.out.println("�ЦA��J�@�� ! ");
		}while( input.isEmpty() );  
		return input;
	}

	public static int nextInt(String message){
		System.out.println(message);
		int input = scanner.nextInt();
		scanner.nextLine(); //�Y������Ÿ�
		return input;
	}
	
	public static int nextInt(int min, int max){
		int input = 0;
		do
		{
			input = scanner.nextInt();
			scanner.nextLine(); //�Y������Ÿ�
			if ( input <= min || input >= max || input < 0 )
				System.out.println("�ЦA��J�@�� ! ");
		}while( input <= min || input >= max || input < 0  );  
		return input;
	}
	
	public static int nextInt(String message, int max){
		int input = 0;
		do
		{
			System.out.println(message);
			input = scanner.nextInt();
			scanner.nextLine(); //�Y������Ÿ�
			if ( input >= max || input < 0 )
				System.out.println("�ЦA��J�@�� ! ");
		}while( input >= max || input < 0  );  
		return input;
	}
}
