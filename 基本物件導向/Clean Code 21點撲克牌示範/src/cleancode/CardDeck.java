package cleancode;

import java.util.Collections;
import java.util.Stack;

public class CardDeck extends Stack<Card>{
	
	public void shuffle(int times){
		for (int i = 0 ; i < times; i ++)
			Collections.shuffle(this);
	}
	
	public void shuffle(){
		shuffle(1);
	}
	
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append("[");
		for(int i = 0 ; i < this.size(); i ++)
		{
			Card card = this.get(i);
			strb.append(card).append(" ");
			if ((i+1) % 4 == 0)
				strb.append("\n");
		}
		strb.append("]\n");
		return strb.toString();
	}
}
