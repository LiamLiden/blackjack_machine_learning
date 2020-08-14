package blackjack_machine_learning;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import blackjack_machine_learning.Card.Suit;
import blackjack_machine_learning.Card.Value;

public class Deck {
	private LinkedList<Card> cards;
	private LinkedList<Card> discard;
	
	public Deck() {
		cards = new LinkedList<Card>();
		discard = new LinkedList<Card>();
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
	
	public void discardCards(List<Card> cards) {
		discard.addAll(cards);
	}
	
	public int countCards() {
		return cards.size();
	}
	
	public int countDiscard() {
		return discard.size();
	}
	
	public LinkedList<Card> getCards() {
		return cards;
	}
	
	public Card draw() {
		return cards.removeFirst();
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
		cards.addAll(discard);
		discard.clear();
	}
	
	public void clearDeck() {
		cards.clear();
	}
}
