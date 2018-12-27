package cleancode;

public class Card{
	public int number;
	public CardSuit suit;

	public Card(int number, CardSuit suit) {
		this.number = number;
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		return String.valueOf(suit) + number;
	}
}
