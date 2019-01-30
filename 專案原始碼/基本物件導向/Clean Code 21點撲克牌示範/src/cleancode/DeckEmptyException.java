package cleancode;

public class DeckEmptyException extends RuntimeException{
	public DeckEmptyException(String message){
		super(message);
	}
	
	public DeckEmptyException(){
		super("The deck is empty, the pop() method is not available.");
	}
}
