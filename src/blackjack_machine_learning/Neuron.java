package blackjack_machine_learning;

import java.util.ArrayList;

public class Neuron {
	public ArrayList<NeuronConnection> inputs;
	public ArrayList<NeuronConnection> outputs;
	
	public Neuron() {
		this.inputs = new ArrayList<NeuronConnection>();
		this.outputs = new ArrayList<NeuronConnection>();
	}
	
	private double sumFunction() {
		double sum = 0;
		for(NeuronConnection input : inputs) {
			sum += input.getWeightAdjusted();
		}
		
		return sum;
	}
	
	public double getActivationAdjusted() {
		return sumFunction();
	}
}
