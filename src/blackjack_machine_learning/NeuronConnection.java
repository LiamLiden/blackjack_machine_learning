package blackjack_machine_learning;

public class NeuronConnection {
	public Neuron in;
	public Neuron out;
	private double weight;
	
	public NeuronConnection(Neuron out, double startWeight) {
		this.out = out;
		this.weight = startWeight;
	}
	
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
	
	public void backpropogate(double error, double learningRate) {
		if(in == null)
			return;
		
		double outNeuronOutput = out.getActivationAdjusted();
		// Set new weight to: weight + learning rate * output error * output of in * output of out * (1 - output of out)
		setWeight(getWeight() + (learningRate * error * in.getActivationAdjusted() * outNeuronOutput * (1 - outNeuronOutput)));
		in.backpropogate(error, learningRate);
	}
}
