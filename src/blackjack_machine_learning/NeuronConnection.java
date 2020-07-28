package blackjack_machine_learning;

public class NeuronConnection {
	public Neuron in;
	public Neuron out;
	private double weight;
	
	public NeuronConnection(Neuron in, Neuron out, double startWeight) {
		this.in = in;
		this.out = out;
		this.weight = startWeight;
	}
	
	// Returns the current weight
	public double getWeight() {
		return weight;
	}
	
	// Sets the weight to a new double value
	public void setWeight(double newWeight) {
		this.weight = newWeight;
	}
	
	// Returns Neuron in's output adjusted by the weight
	public double getWeightAdjusted() {
		return in.getActivationAdjusted() * weight;
	}
}
