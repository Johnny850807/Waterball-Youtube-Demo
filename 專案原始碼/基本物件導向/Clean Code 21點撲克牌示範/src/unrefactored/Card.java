package unrefactored;

import java.util.ArrayList;
import java.util.List;

public class Card implements Comparable<Card>{
	public int number;
	public char suit;
	
	public Card(int number, char suit) {
		this.number = number;
		this.suit = suit;
	}

	@Override
	public String toString() {
		return String.valueOf(suit) + number;
	}

	@Override
	public int compareTo(Card o) {
		if (number != o.number)
		{
			int r1 = number == 1 ? 14 : number;
			int r2 = o.number == 1 ? 14 : o.number;
			return r1 - r2;
		}
		
		List<Character> suits = new ArrayList<>();
		suits.add('\u2663');
		suits.add('\u2666');
		suits.add('\u2665');
		suits.add('\u2660');
		
		int r1 = suits.indexOf(suit);
		int r2 = suits.indexOf(o.suit);
		return r1 - r2;
	}
}
