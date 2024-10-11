package optimisation.assignment.domain;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AlgorithmTests {

    @Test
    void emptyTest() {

    }

    @Test
    void unbounded1() {

        double[][] a = {
                {0, 2},
                {-8, 2}
        };
        double[] c = {8, 10};
        double[] b = {14, 10};

        Algorithm algorithmTest = new Algorithm(c, a, b);
        SimplexResult simplexResult = algorithmTest.simplex(a, c, b);

        assertTrue(simplexResult.isUnbounded);

        assertEquals(0, simplexResult.value, 1e-5);


        assertArrayEquals(null, simplexResult.x, 1e-5);
    }

    @Test
    void unbounded2() {
        double[][] a = {
                {1, 0},
                {1, -1},
        };
        double[] c = {5, 4};
        double[] b = {7, 8};

        Algorithm algorithmTest = new Algorithm(c, a, b);
        SimplexResult simplexResult = algorithmTest.simplex(a, c, b);


        assertTrue(simplexResult.isUnbounded);

        assertEquals(0, simplexResult.value, 1e-5);


        assertArrayEquals(null, simplexResult.x, 1e-5);
    }


    @Test
    void correct1() {

        double[][] a = {
                {2, 3, 6},
                {4, 2, 4},
                {4, 6, 8}
        };

        double[] c = {4, 5, 4};

        double[] b = {240, 200, 160};


        Algorithm algorithmTest = new Algorithm(c, a, b);
        SimplexResult simplexResult = algorithmTest.simplex(a, c, b);

        assertFalse(simplexResult.isUnbounded);

        assertEquals(160, simplexResult.value, 1e-5);

        double[] expectedVariables = {40, 0, 0};
        assertArrayEquals(expectedVariables, simplexResult.x, 1e-5);
    }

    @Test
    void correct2() {

        double[][] a = {
                {1, 3, 5, 3},
                {2, 6, 1, 0},
                {2, 3, 2, 5}
        };


        double[] c = {7, 8, 6, 5};


        double[] b = {40, 50, 30};


        Algorithm algorithmTest = new Algorithm(c, a, b);
        SimplexResult simplexResult = algorithmTest.simplex(a, c, b);

        assertFalse(simplexResult.isUnbounded);

        assertEquals(105, simplexResult.value, 1e-5);

        double[] expectedVariables = {15, 0, 0, 0};
        assertArrayEquals(expectedVariables, simplexResult.x, 1e-5);
    }

    @Test
    void correct3() {

        double[][] a = {
                {2, 3, 2},
                {4, 1, 5},
                {5, 6, 3}
        };


        double[] c = {7, 8, 7};


        double[] b = {30, 40, 50};


        Algorithm algorithmTest = new Algorithm(c, a, b);
        SimplexResult simplexResult = algorithmTest.simplex(a, c, b);

        assertFalse(simplexResult.isUnbounded);

        assertEquals((double) 790 /9, simplexResult.value, 1e-5);

        double[] expectedVariables = {0, (double) 130 /27, (double) 190 /27};
        assertArrayEquals(expectedVariables, simplexResult.x, 1e-5);
    }
}
