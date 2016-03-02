package info.sigmaproject.ga.allones;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    private final ExecutorService pool;
    private final int processors = Runtime.getRuntime().availableProcessors();

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        pool = Executors.newCachedThreadPool();
    }

    public Population initPopulation(int chromosomeLength) {
        return new Population(this.populationSize, chromosomeLength);
    }

    public double calcFitness(Individual individual) {
        long startTime = System.nanoTime();
//        List<Future<Integer>> results = new ArrayList<>(processors);
//        int step = individual.getChromosomeLength() / processors;
//        int fromIndex = 0;
//        int toIndex = step;
//
//        for (int i = 0; i < processors; i++) {
//            results.add(pool.submit(new FitnessCalcHandler(fromIndex, toIndex, individual)));
//            fromIndex += step;
//            toIndex += step;
//        }
//
//        double fitness = results.stream()
//                .map(integerFuture -> {
//                    try {
//                        return integerFuture.get();
//                    } catch (InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                        System.exit(-1);
//                    }
//                    return null;
//                })
//                .reduce(0, (result, element) -> result += element).doubleValue() / individual.getChromosomeLength();
//
//        individual.setFitness(fitness);

        int correctGenes = 0;
        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            if (individual.getGene(geneIndex) == 1) {
                correctGenes += 1;
            }
        }
        double fitness = (double) correctGenes / individual.getChromosomeLength();
        individual.setFitness(fitness);
//        System.out.println("Execution time = " + (System.nanoTime() - startTime));
        return fitness;
    }

    private static class FitnessCalcHandler implements Callable<Integer> {
        private final int fromIndex;
        private final int toIndex;
        private final Individual individual;

        public FitnessCalcHandler(int fromIndex, int toIndex, Individual individual) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.individual = individual;
        }

        @Override
        public Integer call() throws Exception {
            int correctGenes = 0;
            for (int geneIndex = fromIndex; geneIndex < toIndex; geneIndex++) {
                if (individual.getGene(geneIndex) == 1) {
                    correctGenes++;
                }
            }
            return correctGenes;
        }
    }

    public void evalPopulation(Population population) {
        double populationFitness = 0;

        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual);
        }

        population.setPopulationFitness(populationFitness);
    }

    public boolean isTerminationConditionMet(Population population) {
        for (Individual individual : population.getIndividuals()) {
            if (individual.getFitness() == 1) {
                return true;
            }
        }

        return false;
    }

    public Individual selectParent(Population population) {
        List<Individual> individuals = population.getIndividuals();

        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;
        double spinWheel = 0;
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }

        return individuals.get(population.size() - 1);
    }

    public Population crossoverPopulation(Population population) {
        Population newPopulation = new Population(population.size());

        for (int individualIndex = 0; individualIndex < population.size(); individualIndex++) {
            Individual parent1 = population.getFittest(individualIndex);
            if (this.crossoverRate > Math.random() && individualIndex > this.elitismCount) {
                Individual offspring = new Individual(parent1.getChromosome());
                Individual parent2 = selectParent(population);

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (0.5 > Math.random()) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }

                newPopulation.addIndividual(offspring);
            } else {
                newPopulation.addIndividual(parent1);
            }
        }

        return newPopulation;
    }

    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(this.populationSize);
        for (int individualIndex = 0; individualIndex < population.size(); individualIndex++) {
            Individual individual = population.getFittest(individualIndex);

            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                if (individualIndex >= this.elitismCount) {
                    if (this.mutationRate > Math.random()) {
                        int newGene = 1;
                        if (individual.getGene(geneIndex) == 1) {
                            newGene = 0;
                        }
                        individual.setGene(geneIndex, newGene);
                    }
                }
            }
            newPopulation.addIndividual(individual);
        }
        return newPopulation;
    }
}
