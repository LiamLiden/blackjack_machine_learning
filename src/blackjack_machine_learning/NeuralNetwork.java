package blackjack_machine_learning;

import java.util.ArrayList;

public class NeuralNetwork {
	public ArrayList<Neuron> inputNodes;
	public ArrayList<NeuronLayer> hiddenLayers;
	public ArrayList<Neuron> outputNodes;
	
	private double success;
	private double total;
		
	public NeuralNetwork(ArrayList<Neuron> inputNodes, ArrayList<NeuronLayer> hiddenLayers, ArrayList<Neuron> outputNodes) {
		this.inputNodes = inputNodes;
		this.hiddenLayers = hiddenLayers;
		this.outputNodes = outputNodes;
		this.success = 0;
		this.total = 0;
	}
	
	public ArrayList<Double> forwardpropogate(ArrayList<Double> inputs) {
		try {
			for(int i = 0; i < inputs.size(); i++) {
				inputNodes.get(i).input = inputs.get(i);
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Not enough inputs to match input nodes");
		}
		
		
		ArrayList<Double> results = new ArrayList<Double>();
		for(int i = 0; i < outputNodes.size(); i++) {
			results.add(outputNodes.get(i).getActivationAdjusted());
		}
		return results;
	}
	
	// Result is equal to 1, .5, or 0 if the play won, tied, or lost respectively.
	public void backpropogate(int outputNodeIndex, double trueResult, double learningRate) {
		Neuron outputNode = outputNodes.get(outputNodeIndex);
		outputNode.backpropogate(outputNode.getError(trueResult), learningRate);
	}
	
	public double getAccuracy() {
		return success / total;
	}
}
