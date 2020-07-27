package blackjack_machine_learning;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlackJack {
	private static LinkedList<Card> dealer = new LinkedList<Card>();
	
	
	public static void main(String[] args) {
		// Setup players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(100));
		
		// Setup deck
		Deck deck = new Deck();
		deck.initializeDeck();
		deck.shuffle();
		
		// Player bets
		for (Player p : players) {
			p.bet(10);
		}
		
		// Deal 2 cards to each player and dealer
		deal(players, deck);
		
		// Player decisions
		
		
		// Dealer plays
		while(dealerCardValue() < 17) {
			dealer.add(deck.draw());
		}
		
		// Compare and adjust money accordingly
		for (Player p : players) {
			int playerValue = p.cardsValue();
			int dealerValue = dealerCardValue();
			if (playerValue > 21 || (dealerValue > playerValue && dealerValue <= 21))
				p.loseBet();
			else if (dealerValue > 21 || playerValue > dealerValue)
				p.winBet();
		}
		System.out.println(dealerCardValue());
		System.out.println(players.get(0).cardsValue());
	}
	
	public static void deal(List<Player> players, Deck deck) {
		for (Player p : players) {
			p.hit(deck);
			p.hit(deck);
		}
		dealer.add(deck.draw());
		dealer.add(deck.draw());
	}
	
	public static Card getDealerFaceUpCard() {
		return dealer.peekFirst();
	}
	
	public static int dealerCardValue() {
		int val = 0;
		for (Card c : dealer) {
			val += c.value.val;
		}
		return val;
	}
}
