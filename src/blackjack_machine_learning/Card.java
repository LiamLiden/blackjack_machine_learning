package blackjack_machine_learning;

public class Card {
	public enum Suit {
		SPADE,
		CLUB,
		DIAMOND,
		HEART
	}
	
	public enum Value {
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		TEN(10),
		JACK(10),
		QUEEN(10),
		KING(10),
		ACE(11);
		
		public int val;
		
		Value(int val) {
			this.val = val;
		}
	}
	
	public Suit suit;
	public Value value;
	
	public Card(Suit suit, Value value) {
		this.suit = suit;
		this.value = value;
	}

	@Override
	public String toString() {
		return "[" + suit.toString() + " " + value.toString() + "]"; 
	}
	
	
}
