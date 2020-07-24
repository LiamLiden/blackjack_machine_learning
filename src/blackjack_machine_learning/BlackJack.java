package blackjack_machine_learning;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {

	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(100));
		Deck deck = new Deck();
		deck.initializeDeck();
		deck.shuffle();
		for (Player p : players) {
			p.bet(10);
		}
		deal(players, deck);
		//players.get(0).hit(deck);
		//players.get(0).hit(deck);
		System.out.print(players.get(0).cardsValue());
	}
	
	public static void deal(List<Player> players, Deck deck) {
		for (Player p : players) {
			p.hit(deck);
			p.hit(deck);
		}
	}

}
