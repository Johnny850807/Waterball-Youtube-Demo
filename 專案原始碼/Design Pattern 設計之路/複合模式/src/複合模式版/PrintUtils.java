package 複合模式版;

public class PrintUtils {
    public static void print(String str, int times) {
        for (int i = 0; i < times; i++)
            System.out.print(str);
        if (times == 0)
            System.out.print("- ");
        else
            System.out.print(" ");
    }
}
