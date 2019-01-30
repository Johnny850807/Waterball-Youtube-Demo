package unrefactored;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Game21Points {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] argv){
		List<Card> handCards = new ArrayList<>();
		int maxScore = 0;
		int score = 0;
		Session session = new Session();
		session.shuffle("cards1");
		do{
			do {
				Card card = session.draw();
				if (card == null)
				{
					System.out.println("重新洗牌..");
					session.sort();
					card = session.draw();
				}
				System.out.println("抽到了：" + card);
				handCards.add(card);
				System.out.print("手牌： [");
				for (Card c : handCards)
					System.out.print(c + " ");
				System.out.println("]");
				score = 0;
				for (Card c : handCards)
					score += c.number;
				if (score > 21)
				{
					System.out.println("超過21點！");
					score = 0;
					break;
				}
				System.out.println("還要再抽一張嗎? (y/n)");
				if (scanner.nextLine().equals("n")) break;
			} while (true);
			maxScore = score > maxScore ? score : maxScore;
			System.out.println("得到分數: " + score);
			System.out.println("還要再玩一次嗎? (y/n)");
			if (scanner.nextLine().equals("n")) break;
			session.sort();
			handCards.clear();
		}while(true);
		System.out.println("最高分數: " + maxScore);
	}
}
