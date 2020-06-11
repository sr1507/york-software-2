package tsp;

import java.util.Set;
import java.util.List;

public class Hamiltonian {

    public static boolean isHamiltonian(int[][] adjacencyMatrix){
        validateMatrix(adjacencyMatrix);
        int numberOfVertices = adjacencyMatrix.length -1;
        Set<List<Integer>> allPaths = IntegerPermutation.allPermutationsBackTrack(numberOfVertices);

        for(List<Integer> path:allPaths){
            if(isCycle(path, adjacencyMatrix)){
                return true;
            }
        }
        
        return false;
    }

    private static boolean isCycle(List<Integer> path, int[][] adjacencyMatrix){
        for(int index = 0; index < path.size()-1; index++){
            if(adjacencyMatrix[path.get(index)][path.get(index+1)] == 0){
                return false;
            }
        }
        return adjacencyMatrix[path.get(0)][path.get(path.size()-1)] != 0;
    }

    private static void validateMatrix(int[][] adjacencyMatrix){
        if(adjacencyMatrix.length == 0){
            throw new IllegalArgumentException("Matrix cannot be size zero");
        }
        for(int i = 0; i < adjacencyMatrix.length; i++){
            for(int j = 0; j <= i; j++){
                if(adjacencyMatrix[i][j] != adjacencyMatrix[j][i]){
                    throw new IllegalArgumentException("Matrix must be symmetrical");
                }
            }
        }
    }

    //public boolean isHamiltonianBAcktrack(int[][] adjacencyMatrix){
        
    //}
}