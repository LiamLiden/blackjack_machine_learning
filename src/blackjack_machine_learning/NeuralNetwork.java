package blackjack_machine_learning;

import java.util.ArrayList;

public class NeuralNetwork {
	public ArrayList<Neuron> inputNodes;
	public ArrayList<NeuronLayer> hiddenLayers;
	public ArrayList<Neuron> outputNodes;
		
	public NeuralNetwork(ArrayList<Neuron> inputNodes, ArrayList<NeuronLayer> hiddenLayers, ArrayList<Neuron> outputNodes) {
		this.inputNodes = inputNodes;
		this.hiddenLayers = hiddenLayers;
		this.outputNodes = outputNodes;
	}
}
