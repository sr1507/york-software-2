package tsp.activity3;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BackTrackTest {

    private final int[][] singlePathMatrix = new int[][]{{0, 3, 13, 21}, {3, 0, 0, 5}, {13, 0, 0, 8}, {21, 5, 8, 0}};
    private final String[] singlePathCities = new String[]{"A", "B", "C", "D"};
    private final int[][] multiPathMatrix = new int[][]{{0, 12, 29, 22, 13, 24}, {12, 0, 19, 3, 25, 6
    }, {29, 19, 0, 21, 23, 28}, {22, 3, 21, 0, 4, 5}, {13, 25, 23, 4, 0, 16}, {24, 6, 28, 5, 16, 0}};
    private final String[] multiPathCities = new String[]{"A", "B", "C", "D", "E", "F"};

    @Test
    public void testSinglePathBackTrack() {

        TSPGraph tspGraph = new TSPGraph(singlePathCities, singlePathMatrix);
        List<String> expected = Arrays.asList("B", "A", "C", "D");
        assertEquals(expected, new BackTrack(tspGraph).getShortestRoundTrip());
    }

    @Test
    public void testMultiPathBackTrack() {

        TSPGraph tspGraph = new TSPGraph(multiPathCities, multiPathMatrix);
        List<String> expected = Arrays.asList("A", "C", "B", "F", "D", "E");
        assertEquals(expected, new BackTrack(tspGraph).getShortestRoundTrip());
    }
}