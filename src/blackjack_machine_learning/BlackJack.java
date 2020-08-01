package blackjack_machine_learning;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlackJack {
	private static final double LEARNING_RATE = 1;
	private static LinkedList<Card> dealer = new LinkedList<Card>();

	public static void main(String[] args) {
		// Setup neural network
		ArrayList<Neuron> inputNodes = new ArrayList<Neuron>();
		ArrayList<NeuronLayer> hiddenLayers = new ArrayList<NeuronLayer>();
		ArrayList<Neuron> outputNodes = new ArrayList<Neuron>();
		for (int i = 0; i < 2; i++) {
			Neuron n = new Neuron();
			inputNodes.add(n);
		}

		for (int i = 0; i < 2; i++) {
			Neuron n = new Neuron();
			outputNodes.add(n);
			for (Neuron input : inputNodes) {
				NeuronConnection newConnection = new NeuronConnection(input, n, Math.random());
				n.inputConnections.add(newConnection);
				input.outputConnections.add(newConnection);
			}
		}
		NeuralNetwork network = new NeuralNetwork(inputNodes, hiddenLayers, outputNodes);

		// Setup players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(100));

		// Setup deck and discard
		Deck deck = new Deck();
		deck.initializeDeck();
		deck.shuffle();
		Deck discard = new Deck();

		for (int i = 0; i < 100000; i++) {

			// Player bets
			for (Player p : players) {
				p.bet(10);
			}

			// Deal 2 cards to each player and dealer
			deal(players, deck);

			// Player decisions
			System.out.println("START HAND");
			while (players.get(0).cardsValue() <= 21) {
				System.out.println("Player: " + players.get(0).cardsValue());
				ArrayList<Double> inputs = new ArrayList<Double>();
				inputs.add((double) dealerCardValue());
				inputs.add((double) players.get(0).cardsValue());
				ArrayList<Double> results = network.forwardpropogate(inputs);
				if (results.get(0) > results.get(1)) {
					players.get(0).hit(deck);
					System.out.println("HIT");
					if (players.get(0).cardsValue() <= 21) {
						network.backpropogate(1, LEARNING_RATE);
					}
				} else {
					break;
				}
			}
			// Dealer plays
			System.out.println("Dealer Showing: " + getDealerFaceUpCard().value.val);
			while (dealerCardValue() < 17) {
				dealer.add(deck.draw());
			}

			// Compare and adjust money accordingly
			for (Player p : players) {
				System.out.println("Player Final: " + players.get(0).cardsValue());
				int playerValue = p.cardsValue();
				int dealerValue = dealerCardValue();
				if (playerValue > 21 || (dealerValue > playerValue && dealerValue <= 21)) {
					p.loseBet();
					network.backpropogate(0, LEARNING_RATE);
				} else if (dealerValue > 21 || playerValue > dealerValue) {
					p.winBet();
					network.backpropogate(1, LEARNING_RATE);
				}
				p.discardCards(discard);
			}

			System.out.println("Dealer: " + dealerCardValue());
			dealerDiscard(discard);
			// System.out.println("Hit: " + results.get(0) + " Stay: " + results.get(1));
			deck.addCards(discard.getCards());
			discard.clearDeck();
			deck.shuffle();
		}
		System.out.println("Accuracy: " + network.getAccuracy());
		System.out.println("Money: " + players.get(0).getMoney());
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

	public static void dealerDiscard(Deck discard) {
		discard.addCards(dealer);
		dealer.clear();
	}
}
