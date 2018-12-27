package cleancode;

public enum CardSuit {
	CLUB('\u2663'),	DIAMOND('\u2666'), HEART('\u2665'), SPADE('\u2660');
	
	public char image;
	
	private CardSuit(char image){
		this.image = image;
	}
	
	public String toString() {
		return String.valueOf(image);
	};
}
