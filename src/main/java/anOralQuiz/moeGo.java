package anOralQuiz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

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

//        testCharDance(CharDance::new);
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

    static class CharDance {
        public static String processInstructions(String input, String instructionsStr) {
            StringBuilder sb = new StringBuilder(input);
            String[] instructions = instructionsStr.split(",");

            for (String instruction : instructions) {
                String[] parts = instruction.split(":");
                char operation = parts[0].charAt(0);
                String argument = parts[1];

                switch (operation) {
                    case 'M':
                        int moveSteps = Integer.parseInt(argument);
                        sb = move(sb, moveSteps);
                        break;
                    case 'X':
                        String[] charsToSwap = argument.split("/");
                        sb = exchange(sb, charsToSwap[0].charAt(0), charsToSwap[1].charAt(0));
                        break;
                    case 'I':
                        String[] indicesToSwap = argument.split("/");
                        int index1 = Integer.parseInt(indicesToSwap[0]);
                        int index2 = Integer.parseInt(indicesToSwap[1]);
                        sb = indexChange(sb, index1, index2);
                        break;
                }
            }
            return sb.toString();
        }

        private static StringBuilder move(StringBuilder sb, int steps) {
            int length = sb.length();
            steps = steps % length;
            return new StringBuilder(sb.substring(length - steps)).append(sb.substring(0, length - steps));
        }

        private static StringBuilder exchange(StringBuilder sb, char a, char b) {
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == a) {
                    sb.setCharAt(i, b);
                } else if (sb.charAt(i) == b) {
                    sb.setCharAt(i, a);
                }
            }
            return sb;
        }

        private static StringBuilder indexChange(StringBuilder sb, int index1, int index2) {
            char temp = sb.charAt(index1);
            sb.setCharAt(index1, sb.charAt(index2));
            sb.setCharAt(index2, temp);
            return sb;
        }

        public static String transferMultipleTimes(String src, String ops, long count) {
            Set<String> seen = new HashSet<>();
            String current = src;
            long cycleLength = 0;

            while (!seen.contains(current)) {
                seen.add(current);
                current = processInstructions(current, ops);
                cycleLength++;

                if (current.equals(src)) {
                    break;
                }
            }

            long effectiveCount = count % cycleLength;
            current = src;

            for (long i = 0; i < effectiveCount; i++) {
                current = processInstructions(current, ops);
            }

            return current;
        }
    }

    public static void testCharDance(Supplier<CharDance> factory) {
        CharDance charDance = factory.get();
        String src = "wosjgncxyakdbefh";
        String ops = "M:3,I:7/2,X:o/h";
        String dst = "efgwhsjoncxyakdb";
        String realDst = CharDance.processInstructions(src, ops);
        if (!dst.equals(realDst)) {
            throw new RuntimeException("invalid transfer result " + realDst + ", expected is " + dst);
        }
        String dst100 = src;
        for (int i = 0; i < 100; i++) {
            dst100 = CharDance.processInstructions(dst100, ops);
        }
        String realDst100 = CharDance.transferMultipleTimes(src, ops, 100);
        if (!dst100.equals(realDst100)) {
            throw new RuntimeException("invalid transfer result " + realDst100 + " after 100 times, expected is " + dst100);
        }
    }

}