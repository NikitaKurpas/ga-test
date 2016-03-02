package info.sigmaproject.ga.allones;

import java.util.*;

public class Population {
    private final List<Individual> population;
    private double populationFitness = -1;
    private final int populationSize;

    public Population(int populationSize) {
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);
    }

    public Population(int populationSize, int chromosomeLength) {
        this.populationSize = populationSize;
        population = new ArrayList<>(this.populationSize);

        for (int i = 0; i < populationSize; i++) {
            population.add(new Individual(chromosomeLength));
        }
    }

    public List<Individual> getIndividuals() {
        return Collections.unmodifiableList(population);
    }

    public Individual getFittest(int offset) {
        Collections.sort(population, (o1, o2) -> {
            if (o1.getFitness() > o2.getFitness()) {
                return -1;
            } else if (o1.getFitness() < o2.getFitness()) {
                return 1;
            }
            return 0;
        });

        return population.get(offset);
    }

    public double getPopulationFitness() {
        return populationFitness;
    }

    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }

    public int size() {
        return population.size();
    }

    public Individual addIndividual(Individual individual) {
        if (population.size() >= populationSize) {
            return null;
        }
        population.add(individual);
        return individual;
    }

    public Individual setIndividual(int offset, Individual individual) {
        return population.set(offset, individual);
    }

    public Individual getIndividual(int offset) {
        return population.get(offset);
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = size() - 1; i > 0; i--) {
            int idx = random.nextInt(i + 1);
            Collections.swap(population, idx, i);
        }
    }
}
