package blackjack_machine_learning;

import java.util.LinkedList;

import blackjack_machine_learning.Card.Value;

public class Player {
	private LinkedList<Card> cards;
	private int money;
	private int bet;
	private int aces;
	
	public Player(int money) {
		cards = new LinkedList<Card>();
		this.money = money;
		this.aces = 0;
	}
	
	public void bet(int betValue) {
		bet = betValue;
	}
	
	public void hit(Deck deck) {
		Card newCard = deck.draw();
		cards.add(newCard);
		
		if (newCard.value == Value.ACE) {
			aces++;
		}
	}
	
	public int cardsValue() {
		int val = 0;
		int usedAces = 0;
		for (Card c : cards) {
			val += c.value.val;
		}
		
		while (val > 21 && aces != usedAces) {
			val -= 10;
			usedAces++;
		}
		return val;
	}
	
	public void winBet() {
		money += bet;
		bet = 0;
	}
	
	public void loseBet() {
		money -= bet;
		bet = 0;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getAces() {
		return aces;
	}
	
	public void discardCards(Deck deck) {
		deck.discardCards(cards);
		cards.clear();
		aces = 0;
	}
}
