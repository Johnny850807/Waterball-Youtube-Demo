package cleancode;

import java.util.Scanner;

public class Game21Points {
	
	static Scanner scanner = new Scanner(System.in);
	static CardDeck handCards = new CardDeck();
	static int maxScore = 0;
	static int currentRoundScore = 0;
	static PokeCardGameStatesManager pokeCardGameStatesManager = new PokeCardGameStatesManager();
	
	public static void main(String[] argv){
		do{
			resetGameStates();
			executeOneRoundGame();
			showAndUpdateMaxScore();
		}while(askingYesOrNoFor("還要再玩一次嗎? (y\n)"));
		System.out.println("最高分數: " + maxScore);
	}

	private static void resetGameStates() {
		handCards.clear();
		pokeCardGameStatesManager.sortDiscardsIntoMainDeck();
	}
	

	private static void executeOneRoundGame() {
		do {
			Card nextCard = drawNextCardFromMainDeck();
	
			System.out.println("抽到了：" + nextCard);
			handCards.add(nextCard);
			System.out.println(handCards);
			
			currentRoundScore = countRoundScoreFromHandCards();
		} while (determineIfTheRoundKeeping());
	}
	
	private static Card drawNextCardFromMainDeck(){
		Card nextCard = null;
		try{
			nextCard = pokeCardGameStatesManager.drawCard();
		}catch (DeckEmptyException e) {
			System.out.println("牌堆空了，正在重新洗牌...");
			pokeCardGameStatesManager.sortDiscardsIntoMainDeck();
			nextCard = pokeCardGameStatesManager.drawCard();
		}
		return nextCard;
	}
	
	private static int countRoundScoreFromHandCards(){
		currentRoundScore = 0;
		for (Card c : handCards)
			currentRoundScore += c.number;
		return currentRoundScore;
	}
	
	private static boolean determineIfTheRoundKeeping(){
		if (currentRoundScore > 21)
		{
			System.out.println("超過21點！");
			currentRoundScore = 0;
			return false;
		}
		return askingYesOrNoFor("還要再抽一張卡嗎? (y/n)");
	}
	
	private static void showAndUpdateMaxScore() {
		maxScore = currentRoundScore > maxScore ? currentRoundScore : maxScore;
		System.out.println("得到分數: " + currentRoundScore);
	}
	
	private static boolean askingYesOrNoFor(String message) {
		System.out.println(message);
		String input = scanner.nextLine().toUpperCase();
		return input.equals("Y");
	}
}
