package tsp;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TSPGraphTest {

    private final int[][] singlePathMatrix = new int[][]{{0, 3, 13, 21}, {3, 0, 0, 5}, {13, 0, 0, 8}, {21, 5, 8, 0}};
    private final String[] singlePathCities = new String[]{"A", "B", "C", "D"};
    private final int[][] multiPathMatrix = new int[][]{{0, 12, 29, 22, 13, 24}, {12, 0, 19, 3, 25, 6
    }, {29, 19, 0, 21, 23, 28}, {22, 3, 21, 0, 4, 5}, {13, 25, 23, 4, 0, 16}, {24, 6, 28, 5, 16, 0}};
    private final String[] multiPathCities = new String[]{"A", "B", "C", "D", "E", "F"};

    @Test
    public void testValidMatrix() {
        TSPGraph tspGraph = new TSPGraph(singlePathCities, singlePathMatrix);
        assertThat(tspGraph.isValidMatrix(singlePathCities, singlePathMatrix), equalTo(true));
    }

    @Test
    public void testSinglePathBrute() {

        TSPGraph tspGraph = new TSPGraph(singlePathCities, singlePathMatrix);
        List<String> expected = Arrays.asList("C", "D", "B", "A");
        assertEquals(expected, tspGraph.getShortestRoundTripBrute());
    }

    @Test
    public void testSinglePathBackTrack() {

        TSPGraph tspGraph = new TSPGraph(singlePathCities, singlePathMatrix);
        List<String> expected = Arrays.asList("B", "A", "C", "D");
        assertEquals(expected, tspGraph.getShortestRoundTripBacktrack());
    }

    @Test
    public void testSinglePathGreedy() {

        TSPGraph tspGraph = new TSPGraph(singlePathCities, singlePathMatrix);
        List<String> expected = Arrays.asList("D", "B", "A", "C");
        assertEquals(expected, tspGraph.getGreedyRoundTrip("D"));
    }

    @Test
    public void testMultiPathBrute() {

        TSPGraph tspGraph = new TSPGraph(multiPathCities, multiPathMatrix);
        List<String> expected = Arrays.asList("A", "E", "D", "F", "B", "C");
        assertEquals(expected, tspGraph.getShortestRoundTripBrute());
    }

    @Test
    public void testMultiPathBackTrack() {

        TSPGraph tspGraph = new TSPGraph(multiPathCities, multiPathMatrix);
        List<String> expected = Arrays.asList("A", "C", "B", "F", "D", "E");
        assertEquals(expected, tspGraph.getShortestRoundTripBacktrack());
    }

    @Test
    public void testMultiPathGreedy() {

        TSPGraph tspGraph = new TSPGraph(multiPathCities, multiPathMatrix);
        List<String> expected = Arrays.asList("A", "B", "D", "E", "F", "C");
        assertEquals(expected, tspGraph.getGreedyRoundTrip("A"));
    }
}