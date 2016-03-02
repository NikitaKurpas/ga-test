package info.sigmaproject.ga.util;

public abstract class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    public Population initPopulation(int chromosomeLength) {
        return new Population(this.populationSize, chromosomeLength);
    }

    public abstract double calcFitness(Individual individual);

    public abstract void evalPopulation(Population population);

    public abstract boolean isTerminationConditionMet(Population population);

    public abstract Individual selectParent(Population population);

    public abstract Population crossoverPopulation(Population population);

    public abstract Population mutatePopulation(Population population);
}
