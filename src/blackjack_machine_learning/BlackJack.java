package blackjack_machine_learning;

public class BlackJack {

	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.initializeDeck();
		deck.shuffle();
		System.out.printf(deck.draw().toString());
	}

}
