package tsp;

import java.util.*;

import tsp.Hamiltonian;
import tsp.IntegerPermutation;

public class TSPGraph {

    List<String> cities = new ArrayList<>();
    int[][] matrix;
    Double backtrackMinCost = Double.POSITIVE_INFINITY;

    TSPGraph(List<String> cities) {
        this.cities = cities;
        this.matrix = new int[cities.size()][cities.size()];
    }

    TSPGraph(String[] cities) {
        this.cities.addAll(Arrays.asList(cities));
        this.matrix = new int[cities.length][cities.length];
    }

    TSPGraph(String[] cities, int[][] matrix) {
        this.cities.addAll(Arrays.asList(cities));
        this.matrix = matrix;
    }

    boolean addEdge(String cityA, String cityB, int cost) {
        if (isNeighbour(cityA, cityB)) {
            return false;
        } else {
            int indexA = this.cities.indexOf(cityA);
            int indexB = this.cities.indexOf(cityB);

            this.matrix[indexA][indexB] = cost;
            this.matrix[indexB][indexA] = cost;

            return true;
        }
    }

    boolean setEdge(String cityA, String cityB, int cost) {
        if (isNeighbour(cityA, cityB)) {
            int indexA = this.cities.indexOf(cityA);
            int indexB = this.cities.indexOf(cityB);

            this.matrix[indexA][indexB] = cost;
            this.matrix[indexB][indexA] = cost;

            return true;

        } else {
            return false;
        }
    }

    boolean removeEdge(String cityA, String cityB) {
        if(isNeighbour(cityA, cityB)){
            int indexA = this.cities.indexOf(cityA);
            int indexB = this.cities.indexOf(cityB);

            this.matrix[indexA][indexB] = 0;
            this.matrix[indexB][indexA] = 0;

            return true;
        } else {
            return false;
        }
    }

    boolean isNeighbour(String cityA, String cityB) {
        int indexA = this.cities.indexOf(cityA);
        int indexB = this.cities.indexOf(cityB);

        return this.matrix[indexA][indexB] > 0;
    }

    int getCost(String cityA, String cityB) {
        int indexA = this.cities.indexOf(cityA);
        int indexB = this.cities.indexOf(cityB);

        return this.matrix[indexA][indexB];
    }

    List<String> getNeighbours(String city) {

        int cityIndex = this.cities.indexOf(city);
        List<String> neighbours = new ArrayList<>();

        for(int i = 0; i < this.matrix[cityIndex].length; i++){
            if(this.matrix[cityIndex][i] > 0){
                neighbours.add(this.cities.get(i));
            }
        }
        return neighbours;
    }

    boolean isValidMatrix(String[] cities, int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            return false;
        }
        if (matrix.length != cities.length) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j] != matrix[j][i] || matrix[i][j] < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    List<String> getShortestRoundTripBrute() {
        if(Hamiltonian.isHamiltonian(this.matrix)){
            Set<List<Integer>> possiblePermutations = IntegerPermutation.allPermutationsBackTrack(cities.size()-1);
            List<Integer> bestRouteIndexes = new ArrayList<>();
            Double minCost = Double.POSITIVE_INFINITY;
            List<String> bestRoute = new ArrayList<>();
            for(List<Integer> perm: possiblePermutations) {
                Double cost = 0.0;
                for(int i = 0; i < perm.size() - 1; i++) {
                    if(this.matrix[perm.get(i)][perm.get(i+1)] == 0) {
                        cost = Double.POSITIVE_INFINITY;
                    } else {
                        cost += this.matrix[perm.get(i)][perm.get(i+1)];
                    }
                }
                if(cost < minCost) {
                    minCost = cost;
                    bestRouteIndexes = perm;
                }
            }
            for(Integer index: bestRouteIndexes) {
                bestRoute.add(cities.get(index));
            }
            return bestRoute;
        } else {
            return null;
        }
    }

    int getRoundTripCost(List<String> route) {
        int cost = 0;
            for(int i = 0; i < route.size() - 1; i++) {
                cost += this.matrix[cities.indexOf(route.get(i))][cities.indexOf(route.get(i+1))];
            }
        return cost;
    }

    List<String> getShortestRoundTripBacktrack() {
        
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

    List<Integer> citiesToIndexes(List<String> routeString) {
        List<Integer> routeIndexes = new ArrayList<>();
        for(String city: routeString) {
            routeIndexes.add(cities.indexOf(city));
        }
        return routeIndexes;
    }

    List<String> getGreedyRoundTrip(String start) {
        List<String> route = new ArrayList<>();
        if(Hamiltonian.isHamiltonian(this.matrix)){
            route.add(start);
            while(route.size() < cities.size()){
                String currentCity = route.get(route.size() -1);
                int currentCityIndex = cities.indexOf(currentCity);
                String nearestCity = null;
                for(int i = 0; i < cities.size(); i++) {
                    String city = cities.get(i);
                    if(!route.contains(city)){
                        if(nearestCity == null) {
                            nearestCity = city;
                        } else {
                            if(this.matrix[currentCityIndex][i] < this.matrix[currentCityIndex][cities.indexOf(nearestCity)] && this.matrix[currentCityIndex][i] > 0) {
                                nearestCity = city;
                            }
                        }

                    }
                    
                }
                route.add(nearestCity);
            }
        } else {
            return null;
        }
        return route;
    }
}