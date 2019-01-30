package cleancode;

import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

/**
 * Manager manages and keeps the states of one poke card game such like: 
 * deck, discarded cards.
 */
public class PokeCardGameStatesManager {
	
	private CardDeck mainDeck = new CardDeck();
	private CardDeck discards = new CardDeck();
	
	public PokeCardGameStatesManager(){
		initializeMainDeck();
	}
	
	private void initializeMainDeck(){
		CardSuit[] cardSuits = CardSuit.values();
		for (int i = 1 ; i <= 13; i ++)
			for (int j = 0; j < 4; j ++)
				mainDeck.push(new Card(i, cardSuits[j]));
	}

	public Card drawCard(){
		if (mainDeck.isEmpty())
			throw new DeckEmptyException();
		return mainDeck.pop();
	}
	
	public void discardCard(Card card){
		discards.push(card);
	}
	
	public void shuffleMainDeck(){
		mainDeck.shuffle(5);
	}
	
	public void shuffleDiscards(){
		discards.shuffle(5);
	}
	
	public void sortDiscardsIntoMainDeck(){
		int size = discards.size();
		for(int i = 0 ; i < size ; i ++)
			mainDeck.push(discards.pop());
		mainDeck.shuffle(5);
	}
	
	public CardDeck getMainDeck() {
		return mainDeck;
	}

	public CardDeck getDiscards() {
		return discards;
	}

	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append(mainDeck).append(discards);
		return strb.toString();
	}
	
	public static void main(String[] argv){
		PokeCardGameStatesManager session = new PokeCardGameStatesManager();
		System.out.println(session);
	}
}
