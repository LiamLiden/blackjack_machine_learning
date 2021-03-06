package blackjack_machine_learning;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/// NOTES
//  * Scaling input caused much better accuracy and increased the speed in which the model reached good results.

public class BlackJack {
	private static final double LEARNING_RATE = .25;
	private static final int NUMBER_OF_DECKS = 6;
	private static final int ITERATIONS = 100000;
	private static final int NUMBER_OF_HIDDEN_LAYERS = 3;
	private static final int HIDDEN_LAYER_WIDTH = 3;
	private static LinkedList<Card> dealer = new LinkedList<Card>();

	private static int targetOutputNodeIndex;
	private static double trueResult;

	public static void main(String[] args) {
		// Setup neural network
		ArrayList<Neuron> inputNodes = new ArrayList<Neuron>();
		ArrayList<ArrayList<Neuron>> hiddenLayers = new ArrayList<ArrayList<Neuron>>();
		ArrayList<Neuron> outputNodes = new ArrayList<Neuron>();
		for (int i = 0; i < 3; i++) {
			Neuron n = new Neuron();
			inputNodes.add(n);
		}

		for (int i = 0; i < NUMBER_OF_HIDDEN_LAYERS; i++) {
			ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();

			if (i == 0) {
				for (int j = 0; j < HIDDEN_LAYER_WIDTH; j++) {
					Neuron n = new Neuron();
					hiddenLayer.add(n);
					for (Neuron input : inputNodes) {
						NeuronConnection newConnection = new NeuronConnection(input, n, Math.random());
						n.inputConnections.add(newConnection);
						input.outputConnections.add(newConnection);
					}
				}
			} else {
				for (int j = 0; j < HIDDEN_LAYER_WIDTH; j++) {
					Neuron n = new Neuron();
					hiddenLayer.add(n);
					for (Neuron input : hiddenLayers.get(i - 1)) {
						NeuronConnection newConnection = new NeuronConnection(input, n, Math.random());
						n.inputConnections.add(newConnection);
						input.outputConnections.add(newConnection);
					}
				}
			}
			hiddenLayers.add(hiddenLayer);

		}

		for (int i = 0; i < 2; i++) {
			Neuron n = new Neuron();
			outputNodes.add(n);
			for (Neuron input : hiddenLayers.get(0)) {
				NeuronConnection newConnection = new NeuronConnection(input, n, Math.random());
				n.inputConnections.add(newConnection);
				input.outputConnections.add(newConnection);
			}
		}
		NeuralNetwork network = new NeuralNetwork(inputNodes, hiddenLayers, outputNodes);

		// Setup players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(0));

		// Setup deck and discard
		Deck deck = new Deck();
		for (int i = 0; i < NUMBER_OF_DECKS; i++) {
			Deck tempDeck = new Deck();
			tempDeck.initializeDeck();
			deck.addCards(tempDeck.getCards());
		}
		deck.shuffle();

		for (int i = 0; i < ITERATIONS; i++) {

			// Player bets
			for (Player p : players) {
				p.bet(1);
			}

			// Deal 2 cards to each player and dealer
			deal(players, deck);

			// Player decisions
			System.out.println("START HAND");
			while (players.get(0).cardsValue() <= 21) {
				System.out.println("Player: " + players.get(0).cardsValue());
				ArrayList<Double> inputs = new ArrayList<Double>();
				// Inputs: DealerValue, PlayerValue, AcePresent
				// Scale the dealer input of range (0, 11) to range (0, 1)
				inputs.add(scale(0, 11, 0, 1, (double) dealerCardValue()));
				// Scale the player input of range (0, 21) to range (0, 1)
				inputs.add(scale(0, 21, 0, 1, (double) players.get(0).cardsValue()));
				if (players.get(0).getAces() == 0) {
					inputs.add((double) 0);
				}
				else {
					inputs.add((double) 1);
				}
				
				ArrayList<Double> estimates = network.forwardpropogate(inputs);
				if (estimates.get(0) > estimates.get(1)) {
					targetOutputNodeIndex = 0;
					players.get(0).hit(deck);
					System.out.println("HIT");
					if (players.get(0).cardsValue() <= 21) {
						network.backpropogate(targetOutputNodeIndex, 1, LEARNING_RATE);
					}
				} else {
					targetOutputNodeIndex = 1;
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
					trueResult = 0;
				} else if (dealerValue > 21 || playerValue > dealerValue) {
					p.winBet();
					trueResult = 1;
				} else {
					trueResult = .5;
				}
				network.backpropogate(targetOutputNodeIndex, trueResult, LEARNING_RATE);
				p.discardCards(deck);
			}

			System.out.println("Dealer: " + dealerCardValue());
			System.out.println("True Result: " + trueResult);
			dealerDiscard(deck);
			// System.out.println("Hit: " + results.get(0) + " Stay: " + results.get(1));
			if (deck.countDiscard() * 1.25 > deck.countCards()) {
				deck.shuffle();
			}
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

	public static void dealerDiscard(Deck deck) {
		deck.discardCards(dealer);
		dealer.clear();
	}

	public static double scale(double originalMin, double originalMax, double newMin, double newMax, double value) {
		return ((originalMax - originalMin) * (value - newMin)) / (newMax - newMin);
	}
}
