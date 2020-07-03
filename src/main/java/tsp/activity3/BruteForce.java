package tsp.activity3;

import tsp.activity1.Hamiltonian;
import tsp.activity1.IntegerPermutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BruteForce {

    private int[][] matrix;
    private List<String> cities;

    BruteForce(TSPGraph tspGraph) {
        this.matrix = tspGraph.matrix;
        this.cities = tspGraph.cities;
    }

    List<String> getShortestRoundTrip() {
        if (Hamiltonian.isHamiltonian(this.matrix)) {
            Set<List<Integer>> possiblePermutations = IntegerPermutation.allPermutationsBackTrack(cities.size() - 1);
            List<Integer> bestRouteIndexes = new ArrayList<>();
            Double minCost = Double.POSITIVE_INFINITY;
            List<String> bestRoute = new ArrayList<>();
            for (List<Integer> perm : possiblePermutations) {
                Double cost = 0.0;
                for (int i = 0; i < perm.size() - 1; i++) {
                    if (this.matrix[perm.get(i)][perm.get(i + 1)] == 0) {
                        cost = Double.POSITIVE_INFINITY;
                    } else {
                        cost += this.matrix[perm.get(i)][perm.get(i + 1)];
                    }
                }
                if (cost < minCost) {
                    minCost = cost;
                    bestRouteIndexes = perm;
                }
            }
            for (Integer index : bestRouteIndexes) {
                bestRoute.add(cities.get(index));
            }
            return bestRoute;
        } else {
            return null;
        }
    }
}