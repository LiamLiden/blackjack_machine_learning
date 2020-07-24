package blackjack_machine_learning;

import java.util.LinkedList;

public class Player {
	private LinkedList<Card> cards;
	private int money;
	private int bet;
	
	public Player(int money) {
		cards = new LinkedList<Card>();
		this.money = money;
	}
	
	public void bet(int betValue) {
		bet = betValue;
	}
	
	public void hit(Deck deck) {
		cards.add(deck.draw());
	}
	
	public int cardsValue() {
		int val = 0;
		for (Card c : cards) {
			val += c.value.val;
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
}
