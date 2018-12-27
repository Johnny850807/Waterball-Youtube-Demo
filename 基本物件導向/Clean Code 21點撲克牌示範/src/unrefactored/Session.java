package unrefactored;

import java.util.Collections;
import java.util.Stack;

/**
 * - 2018/7/1 (Johnny) init the class
 * - 2018/7/2 (Alex) correct the shuffling problems
 * - 2018/7/2 (Johnny) complement some comments
 * 
 * The session object manages the states of one pokecard game.
 * Managing two card stacks, cards1 is the main deck that the players would draw from,
 * cards2 consists of cards which are discarded, e.g. the cards that just played.
 */
public class Session {
	/**
	 * the main deck players would draw from
	 */
	private Stack<Card> cards1 = new Stack<>();
	
	/**
	 * discarded cards
	 */
	private Stack<Card> cards2 = new Stack<>();
	
	public Session(){
		/**
		 * here we initialize the cards 1
		 */
		char[] suits = new char[]{ '\u2663', '\u2666', '\u2665', '\u2660'};
		for (int i = 1 ; i <= 13; i ++)
			for (int j = 0; j < 4; j ++)
				cards1.push(new Card(i, suits[j]));
	}
	
	/**
	 * 
	 * @return
	 */
	public Card draw(){
		if (cards1.isEmpty())
			return null;
		return cards1.pop();
	}
	
	public void discard(Card card){
		cards2.push(card);
	}
	
	/**
	 * @param cardsName the name of the cards, cards1 or cards2
	 */
	public void shuffle(String cardsName){
		if (cardsName.equals("cards1"))
			Collections.shuffle(cards1);
		else if (cardsName.equals("cards2"))
			Collections.shuffle(cards2);
	}
	
	public void sort(){
		int size = cards2.size();
		for(int i = 0 ; i < size ; i ++)
			cards1.push(cards2.pop());
		for(int i = 0 ; i < 5; i ++)
			Collections.shuffle(cards1);
	}
	
	public Stack<Card> getC1() {
		return cards1;
	}
	
	public Stack<Card> getC2() {
		return cards2;
	}
	
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append("[");
		for(int i = 0 ; i < cards1.size(); i ++)
		{
			Card card = cards1.get(i);
			strb.append(card).append(" ");
			if ((i+1) % 4 == 0)
				strb.append("\n");
		}
		strb.append("]\n");
		strb.append("[");
		for(int i = 0 ; i < cards2.size(); i ++)
		{
			Card card = cards2.get(i);
			strb.append(card).append(" ");
			if ((i+1) % 4 == 0)
				strb.append("\n");
		}
		strb.append("]\n");
		return strb.toString();
	}
	
	public static void main(String[] argv){
		Session session = new Session();
		System.out.println(session);
	}
}
