package blackjack_machine_learning;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import blackjack_machine_learning.Card.Suit;
import blackjack_machine_learning.Card.Value;

public class Deck {
	private LinkedList<Card> cards;
	
	public Deck() {
		cards = new LinkedList<Card>();
	}
	
	public void initializeDeck() {
		for(Suit s : Suit.values()) {
			for(Value v : Value.values()) {
				cards.add(new Card(s, v));
			}
		}
	}
	
	public void addCards(List<Card> newCards) {
		cards.addAll(newCards);
	}
	
	public LinkedList<Card> getCards() {
		return cards;
	}
	
	public Card draw() {
		return cards.removeFirst();
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
}
