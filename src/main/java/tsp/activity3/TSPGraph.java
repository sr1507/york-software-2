package tsp.activity3;

import tsp.activity1.Hamiltonian;
import tsp.activity1.IntegerPermutation;

import java.util.*;

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
}