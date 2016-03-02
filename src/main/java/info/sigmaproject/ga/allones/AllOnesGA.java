package info.sigmaproject.ga.allones;

public class AllOnesGA {

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.25, 0.95, 2);
        Population population = ga.initPopulation(30);

        ga.evalPopulation(population);
        int generation = 1;
        while (!ga.isTerminationConditionMet(population)) {
            if (generation % 100 == 0) {
                System.out.println("Generation: " + generation + ";\t\tBest solution: " + population.getFittest(0).toString());
            }

            population = ga.crossoverPopulation(population);
            population = ga.mutatePopulation(population);

            ga.evalPopulation(population);
            generation++;
        }

        System.out.println("Found solution in " + generation + " generations");
        System.out.println("Best solution: " + population.getFittest(0).toString());
    }
}
