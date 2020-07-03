package tsp.activity3;

import tsp.activity1.Hamiltonian;
import tsp.activity1.IntegerPermutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Greedy {

    private int[][] matrix;
    private List<String> cities;

    Greedy(TSPGraph tspGraph) {
        this.matrix = tspGraph.matrix;
        this.cities = tspGraph.cities;
    }

    List<String> getRoundTrip(String start) {
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