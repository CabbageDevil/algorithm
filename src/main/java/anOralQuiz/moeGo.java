package anOralQuiz;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class moeGo {
    public static void main(String[] args) {

        Function<int[], WeightedRandom>  function=new Function<int[], WeightedRandom>() {
            @Override
            public WeightedRandom apply(int[] ints) {
                WeightedRandom WeightedRandom=new WeightedRandom(ints);
                return WeightedRandom;
            }
        };
        TestWeightedRandom testWeightedRandom=new TestWeightedRandom();
        testWeightedRandom.testWeightedRandom(function);
    }

    public static class WeightedRandom {
        private int[] prefixSums;
        private int total;
        private Random random;

        public WeightedRandom(int[] input) {
            prefixSums = new int[input.length];
            random = new Random();
            int sum = 0;
            for (int i = 0; i < input.length; i++) {
                sum += input[i];
                prefixSums[i] = sum;
            }
            total = sum;
        }

        public int next() {
            int target = random.nextInt(total) + 1;
            int left = 0, right = prefixSums.length - 1;

            while (left < right) {
                int mid = left + (right - left) / 2;
                if (prefixSums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            return left;
        }
    }


    public static class TestWeightedRandom {

        public void testWeightedRandom(Function<int[], WeightedRandom> factory) {
            int[] input = {4, 2, 1, 3, 3};
            WeightedRandom random = factory.apply(input);
            int[] count = new int[input.length];
            for (int i = 0; i < 10000; i++) {
                int v = random.next();
                if (v < 0 || v >= input.length) {
                    throw new RuntimeException("invalid random value: " + v);
                }
                count[v]++;
            }
            int sum = Arrays.stream(input).sum();
            for (int i = 0; i < input.length; i++) {
                double expectedWeight = (double) input[i] / (double) sum;
                double realWeight = (double) count[i] / 10000D;
                if (Math.abs(expectedWeight - realWeight) > 0.01) {
                    throw new RuntimeException(
                            "invalid weight " + realWeight + " for index " + i + ", expected is " + expectedWeight
                    );
                }
            }
        }
    }

}