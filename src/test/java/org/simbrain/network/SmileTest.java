package org.simbrain.network;

import org.junit.Test;
import org.opencv.core.Mat;
import smile.math.matrix.Matrix;
import static org.junit.Assert.assertEquals;
import smile.math.matrix.Matrix.EVD;
import smile.stat.distribution.GaussianDistribution;

/**
 * @return
 */
public class SmileTest {

    @Test
    public void basics() {

        var a = Matrix.eye(3);
        var b = Matrix.diag(new double[]{1, 4, 5});
        // System.out.println(a);
        var c = b.mm(b);
        //System.out.println(c);

        //Todo: Just put this through the paces.
        //  Try as much matrix stuff possible
        long start_time;
        long stop_time;
        long difference;
        // Initial code to create a 2d matrix of floats

        System.out.println("Basic tests:");
        // Create a 2x3 zero matrix
        start_time = System.nanoTime();
        var matrix_a = Matrix.eye(2,3);

        // Get its shape

        var rows = matrix_a.nrows();
        var cols = matrix_a.ncols();
        var result = "Shape: "+rows+" x "+cols;
        System.out.println("Shape: "+rows+" x "+cols);
        assertEquals("Shape: 2 x 3", result);
        stop_time = System.nanoTime();
        difference = stop_time - start_time;
        System.out.println("Compute Time: "+difference/1e6+" ms");
        // Create a 2x3 matrix of ones

        double[][] ones = {{1.0,1.0,1.0},{1.0,1.0,1.0}};
        var matrix_ones = new Matrix(2,3,ones);
        System.out.println(matrix_ones);


        // Confirm sum of entries is 6

        start_time = System.nanoTime();
        var sums = matrix_ones.sum();
        System.out.println("Sums: "+sums);
        assertEquals(6.0, sums,0.0);
        stop_time = System.nanoTime();
        difference = stop_time - start_time;
        System.out.println("Compute Time: "+difference/1e6+" ms");

        // Set the value of entry 1,3 to 3

        matrix_ones.set(0,2,3);

        // Retrieve the value of that entry and confirm it is 3

        System.out.println("Value at 1,3: "+matrix_ones.get(0,2));
        assertEquals(3,matrix_ones.get(0,2),0.0);

        // Perform a matrix multiplication using simple values and confirm correct outputs

        start_time = System.nanoTime();
        double[][] twos = {{2.0,2.0,2.0},{2.0,2.0,2.0}};
        var matrix_twos = new Matrix(2,3,twos);
        double[][] threes = {{3.0,3.0},{3.0,3.0},{3.0,3.0}};
        var matrix_threes = new Matrix(3,2,threes);
        var result_matrix = matrix_twos.mm(matrix_threes);
        stop_time = System.nanoTime();
        System.out.println(result_matrix);
        difference = stop_time - start_time;
        System.out.println("Compute Time: "+difference/1e6+" ms");

        //TODO: Perform a dot product and confirm correct outputs

        //--------------WeightMatrixTest implementation----
        System.out.println("WeightMatrix tests:");
        // Set first entry to 4

        var weightMatrix = new Matrix(2,2);
        weightMatrix.set(0,0,4.0);
        assertEquals(4.0, weightMatrix.get(0,0),0.0);

        // Set to ((0,0);(0,0))
        weightMatrix.set(0,0,0.0);
        assertEquals(0.0,weightMatrix.sum(),0.0);


        // Add 1 to each entry. Should get ((1,1);(1,1))

        weightMatrix.add(1.0);
        assertEquals(4.0, weightMatrix.sum(),0.0);


        // Multiply by itself.  Should get ((2,2);(2,2))
        start_time = System.nanoTime();
        result_matrix = weightMatrix.mm(weightMatrix);
        stop_time = System.nanoTime();
        System.out.println(result_matrix);
        difference = stop_time - start_time;
        System.out.println("MATRIX MULTIPLICATION:");
        System.out.println("Compute Time: "+difference/1e6+" ms");
        assertEquals(8.0, result_matrix.sum(),0.0);

        //Matrix instantiation for large computations; Creating a 50x50 matrix of 5's

        var large_matrix =  Matrix.rand(50,50, new GaussianDistribution(0,1));
        //large_matrix.add(5.0);

        // Testing for eigenvalue

        start_time = System.nanoTime();
        var eigenvalue = large_matrix.eigen();
        stop_time = System.nanoTime();
        difference = stop_time - start_time;
        var eigen_matrix = new Matrix.EVD(eigenvalue.wr, eigenvalue.wi, eigenvalue.Vl, eigenvalue.Vr);
        //double[] res = eigen_matrix.wr;
        /*for(double x:res){
            if(x < 1e-10){
                System.out.println(0.0);
            }
            else {
                System.out.println(x);
            }
        }*/
        System.out.println("MATRIX EIGENVALUES:");
        System.out.println("Compute Time: "+difference/1e6+" ms");
        System.out.println(eigen_matrix.diag());

        //Matrix Decomposition
        start_time = System.nanoTime();
        var decompose = large_matrix.lu();
        stop_time = System.nanoTime();
        difference = stop_time - start_time;
        System.out.println("MATRIX LU DECOMPOSITION:");
        System.out.println("Compute Time: "+difference/1e6+" ms");
        System.out.println(decompose.lu);
    }
}