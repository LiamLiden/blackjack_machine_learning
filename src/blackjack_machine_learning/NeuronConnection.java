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
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double newWeight) {
		this.weight = newWeight;
	}
	
	public double getWeightAdjusted() {
		return in.getActivationAdjusted() * weight;
	}
}
