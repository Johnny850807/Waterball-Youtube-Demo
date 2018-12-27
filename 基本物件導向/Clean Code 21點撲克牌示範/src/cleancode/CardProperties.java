package cleancode;

import java.util.Comparator;

public class CardProperties {
	public static class CardsStandardComparator implements Comparator<Card>{
		
		public static int compareTwoCards(Card o1, Card o2){
			if (o1.number != o2.number)
				return o1.number - o2.number;
			return getStandardSuitRank(o1.suit) - getStandardSuitRank(o2.suit);
		}
		
		public static int getStandardSuitRank(CardSuit suit){
			switch (suit) {
			case CLUB:
				return 0;
			case DIAMOND:
				return 1;
			case HEART:
				return 2;
			case SPADE:
				return 3;
			default:
				throw new Error("Not reached.");
			}
		}
		
		@Override
		public int compare(Card o1, Card o2) {
			return compareTwoCards(o1, o2);
		}
		
	}
	
}
