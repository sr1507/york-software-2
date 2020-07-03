package tsp.activity1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class HamiltonianTest {

    @Test (expected = IllegalArgumentException.class)
    public void lengthZeroMatricesAreNotAllowed(){
        int[][] inputMatrix = new int[0][0];
        try{
        Hamiltonian.isHamiltonian(inputMatrix);
        }catch(IllegalArgumentException ex){
            assertThat(ex.getMessage(), equalTo("Matrix cannot be size zero"));
            throw ex;
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void nonSymmetricalMatricesAreNotAllowed(){
        int[][] inputMatrix = new int[][]{{1,1},{0,1}};
        try{
        Hamiltonian.isHamiltonian(inputMatrix);
        }catch(IllegalArgumentException ex){
            assertThat(ex.getMessage(), equalTo("Matrix must be symmetrical"));
            throw ex;
        }
    }

    @Test
    public void hamiltonianCycleIsFound(){
        int[][] inputMatrix = new int[][]{{1,1,0,1,0},{1,1,1,1,1},{0,1,1,0,1},{1,1,0,1,1},{0,1,1,1,1}};
        assertThat(Hamiltonian.isHamiltonian(inputMatrix), equalTo(true));
    }

    @Test
    public void nonHamiltonianCycleIsNotFound(){
        int[][] inputMatrix = new int[][]{{1,1,0,1,0},{1,1,1,1,1},{0,1,1,0,1},{1,1,0,1,0},{0,1,1,0,1}};
        assertThat(Hamiltonian.isHamiltonian(inputMatrix), equalTo(false));
    }
}