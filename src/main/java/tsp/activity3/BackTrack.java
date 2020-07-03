package tsp.activity3;

import tsp.activity1.Hamiltonian;
import tsp.activity1.IntegerPermutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BackTrack {

    private int[][] matrix;
    private List<String> cities;
    private Double backtrackMinCost = Double.POSITIVE_INFINITY;

    BackTrack(TSPGraph tspGraph) {
        this.matrix = tspGraph.matrix;
        this.cities = tspGraph.cities;
    }

    public List<String> getShortestRoundTrip() {
        
        List<Integer> citiesRemaining = createDecisions(cities.size()-1);
        List<Integer> minCostIndexes = getMinCostRoute(citiesRemaining);
        
        return indexesToCities(minCostIndexes);
    }

    void updateMinCostRoute(List<Integer> minCostRoute, Double currentCost, List<Integer> currentRoute, List<Integer> citiesRemaining) {
        if(citiesRemaining.size() == 0){
            if(this.matrix[currentRoute.get(0)][currentRoute.get(currentRoute.size()-1)] > 0) {
                currentCost += this.matrix[currentRoute.get(0)][currentRoute.get(currentRoute.size()-1)];
            } else {
                currentCost = Double.POSITIVE_INFINITY;
            }
            if(currentCost < this.backtrackMinCost) {
                this.backtrackMinCost = Double.valueOf(currentCost);
                specialDeepCopy(currentRoute, minCostRoute);
            }
        } else {
            for(int city: citiesRemaining){
                currentRoute.add(city);
                Double currentCostCopy = Double.valueOf(currentCost);
                if(currentRoute.size() >= 2) {
                    if(this.matrix[currentRoute.get(currentRoute.size()-1)][currentRoute.get(currentRoute.size()-2)] > 0) {

                        currentCostCopy += this.matrix[currentRoute.get(currentRoute.size()-1)][currentRoute.get(currentRoute.size()-2)];
                    } else {
                        currentCost = Double.POSITIVE_INFINITY;
                    }
                    if(currentCost > this.backtrackMinCost) {
                        currentRoute.remove(currentRoute.size() - 1);
                        continue;
                    }
                }
                List<Integer> newCitiesRemaining = copyList(citiesRemaining);
                newCitiesRemaining.remove(Integer.valueOf(city));
                updateMinCostRoute(minCostRoute, currentCostCopy, currentRoute, newCitiesRemaining);
            }
        }
        if(currentRoute.size() != 0){
            currentRoute.remove(currentRoute.size() - 1);
        }
    }

    void specialDeepCopy (List<Integer> sourceList, List<Integer> targetList) {
        while(targetList.size() > 0){
            targetList.remove(0);
        }
        for(int i: sourceList) {
            targetList.add(i);
        }
    }

    List<Integer> getMinCostRoute(List<Integer> citiesRemaining) {
        List<Integer> minCostRoute = new ArrayList<>();
        Double currentCost = 0.0;
        List<Integer> currentRoute = new ArrayList<>();

        updateMinCostRoute(minCostRoute, currentCost, currentRoute, citiesRemaining);

        return minCostRoute;
    }

    private static List<Integer> copyList(List<Integer> listMaster){
        List<Integer> copy = new ArrayList<Integer>();
        for(Integer item:listMaster){
            copy.add(item);
        }
        return copy;
    }

    private static List<Integer> createDecisions(int n) {
        List<Integer> decisions = new ArrayList<Integer>();
        for(int i = 0; i <= n; i++){
            decisions.add(i);
        }
        return decisions;
    }

    List<String> indexesToCities(List<Integer> routeIndexes) {
        List<String> routeString = new ArrayList<>();
        for(int index: routeIndexes) {
            routeString.add(cities.get(index));
        }
        return routeString;
    }
}