package blackjack_machine_learning;

import java.util.ArrayList;

public class Neuron {
	// Input connections
	public ArrayList<NeuronConnection> inputs;
	// Output connections
	public ArrayList<NeuronConnection> outputs;
		
	public Neuron() {
		this.inputs = new ArrayList<NeuronConnection>();
		this.outputs = new ArrayList<NeuronConnection>();
	}
	
	// Returns the weighted sum of inputs
	private double getWeightedSum() {
		double sum = 0;
		for(NeuronConnection input : inputs) {
			sum += input.getWeightAdjusted();
		}
		
		return sum;
	}
	
	// Returns the sum adjusted by the activation function
	public double getActivationAdjusted() {
		// Sigmoid function
		return 1 / (1 + Math.exp(-getWeightedSum()));
	}
	
	// Returns real - predicted
	public double getError(double real) {
		return real - getActivationAdjusted();
	}
}
