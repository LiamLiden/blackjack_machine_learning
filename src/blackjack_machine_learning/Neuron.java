package blackjack_machine_learning;

import java.util.ArrayList;

public class Neuron {
	// Input connections
	public ArrayList<NeuronConnection> inputConnections;
	// Output connections
	public ArrayList<NeuronConnection> outputConnections;
	
	public Double input;
		
	public Neuron() {
		this.inputConnections = new ArrayList<NeuronConnection>();
		this.outputConnections = new ArrayList<NeuronConnection>();
	}
	
	// Returns the weighted sum of inputs
	private double getWeightedSum() {
		double sum = 0;
		for(NeuronConnection input : inputConnections) {
			sum += input.getWeightAdjusted();
		}
		
		return sum;
	}
	
	
	
	// Returns the sum adjusted by the activation function
	public double getActivationAdjusted() {
		if(input != null) {
			return input;
		}
		// Sigmoid function
		return 1 / (1 + Math.exp(-getWeightedSum()));
	}
	
	public void backpropogate(double error, double learningRate) {
		for(NeuronConnection connection : inputConnections) {
			connection.backpropogate(error, learningRate);
		}
	}
	
	// Returns real - predicted
	public double getError(double real) {
		return real - getActivationAdjusted();
	}
}
