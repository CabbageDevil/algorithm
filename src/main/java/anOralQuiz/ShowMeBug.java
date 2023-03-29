package anOralQuiz;// 注意: 题目有四道, 请认真仔细读题,
//      如果有不理解的地方, 请联系 HR 或面试官,
//      如果有不会的, 请留空, 不要求做完, 不要盲目答题.

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Q1: 加权随机函数生成器
 * <p>
 * 给定一个正整数数组 input, 构造一个加权随机类实例,
 * 该实例的 {@link #next()} 方法被调用时, 随机返回一个该数组的下标, 下标 i 被返回的概率
 * 为该下标对应的元素的值 / 所有元素之和.
 * <p>
 * 要求: {@link #next()} 方法的时间复杂度不超过 O(log(N))
 */

/**
 * 解题思路：为了实现加权随机函数生成器，我们可以使用前缀和数组和二分搜索。首先计算输入数组的前缀和数组，然后在调用next()方法时，生成一个介于0和总和之间的随机数，再使用二分搜索找到这个随机数所在的区间，返回相应的下标。
 * <p>
 * 构造函数中，计算输入数组的前缀和数组。
 * 初始化一个随机数生成器。
 * 在next()方法中，生成一个在 [1, total] 区间的随机数。
 * 使用二分搜索找到随机数所在的区间，返回相应的下标
 */
class WeightedRandom {
    // 存储输入数组的前缀和
    private int[] prefixSums;
    // 总权重值
    private int total;
    // 随机数生成器
    private Random random;

    /**
     * 构造函数，接收一个整型数组作为输入
     * 数组中的每个元素代表一个权重值
     *
     * @param input 整型数组，代表权重值
     */
    public WeightedRandom(int[] input) {
        // 初始化前缀和数组
        prefixSums = new int[input.length];
        // 初始化随机数生成器
        random = new Random();
        int sum = 0;
        // 计算前缀和，并保存在 prefixSums 数组中
        for (int i = 0; i < input.length; i++) {
            sum += input[i];
            prefixSums[i] = sum;
        }
        // 记录总权重值
        total = sum;
    }

    /**
     * 生成一个加权随机数
     *
     * @return 返回随机生成的索引值，即代表权重值的数组下标
     */
    public int next() {
        // 生成一个位于 [1, total] 范围内的随机数
        int target = random.nextInt(total) + 1;
        // 二分查找 prefixSums 数组中第一个大于等于 target 的元素的下标
        int left = 0, right = prefixSums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (prefixSums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // 返回随机生成的索引值
        return left;
    }
}

/**
 * Q2: 字符跳动
 * <p>
 * 给定一个由不重复的小写字母组成的字符串，通过一系列跳动指令变换成一个新的字符串。
 * 跳动指令有：
 * * Move(移动)，简写：M，例如：M:7 表示将字符串向右移动7位。
 * * eXchange(交换)，简写：X，例如：X:a/c 表示将字符串中的 a c 位置交换。
 * * IndexChange(按位置交换)，简写：I，例如：I:2/4 表示将位置2和位置4的字符交换，位置的索引从0开始计数。
 * 示例：
 * 给定初始字符串为：wosjgncxyakdbefh
 * 给定如下指令：
 * M:3   则变换后的新字符串为：efhwosjgncxyakdb
 * I:7/2  则变换后的新字符串为：efgwosjhncxyakdb
 * X:o/h  则变换后的新字符串为：efgwhsjoncxyakdb
 * 因此给定初始字符串：wosjgncxyakdbefh，在经过指令 M:3,I:7/2,X:o/h 的变换后得到新的字符串：efgwhsjoncxyakdb。
 */
class CharDance {

    /**
     * 题目1：
     * 给定一个随机的初始字符串: src，给定一组随机的指令: ops，(src 和 ops 一定是合法的)，求经过转换后得到的新字符串。
     */

    /**
     * 解题思路：
     * <p>
     * 对每个指令，将指令分解为操作类型和参数。
     * 根据操作类型，执行相应的操作，并传入参数。
     * 对于 Move 操作，我们将字符串的末尾移动到开头，实现循环移动。
     * 对于 eXchange 操作，我们遍历字符串，交换指定的字符。
     * 对于 IndexChange 操作，我们交换指定索引位置的字符。
     * 在所有指令处理完毕后，返回处理后的字符串
     */
    public String transfer(String src, String ops) {
        // 创建 StringBuilder 对象，用于保存操作后的字符串
        StringBuilder sb = new StringBuilder(src);
        // 分割指令，得到每个操作的类型和参数
        String[] instructions = ops.split(",");
        // 遍历每个指令
        for (String instruction : instructions) {
            String[] parts = instruction.split(":");
            // 获取操作类型
            char operation = parts[0].charAt(0);
            // 获取参数
            String argument = parts[1];
            // 根据操作类型调用相应的函数进行字符串操作
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
        // 返回操作后的字符串
        return sb.toString();
    }

    // 左右移动字符串
    private static StringBuilder move(StringBuilder sb, int steps) {
        // 计算左移后的偏移量
        int length = sb.length();
        steps = steps % length;
        // 返回左移后的字符串
        return new StringBuilder(sb.substring(length - steps)).append(sb.substring(0, length - steps));
    }

    // 交换字符串中的字符
    private static StringBuilder exchange(StringBuilder sb, char a, char b) {
        // 遍历字符串，找到要交换的字符
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == a) {
                // 将 a 替换为 b
                sb.setCharAt(i, b);
            } else if (sb.charAt(i) == b) {
                // 将 b 替换为 a
                sb.setCharAt(i, a);
            }
        }
        // 返回交换后的字符串
        return sb;
    }

    // 交换字符串中的两个位置的字符
    private static StringBuilder indexChange(StringBuilder sb, int index1, int index2) {
        // 记录第一个位置的字符
        char temp = sb.charAt(index1);
        // 将第二个位置的字符放到第一个位置上
        sb.setCharAt(index1, sb.charAt(index2));
        // 将第一个位置的字符放到第二个位置上
        sb.setCharAt(index2, temp);
        // 返回交换后的字符串
        return sb;
    }

    /**
     * 题目2：
     * 将上一次转换后得到的新字符串作为初始字符串，使用相同的跳动指令集再进行转换，如此重复执行 count 次，求得到的最终字符串是什么？
     * 注意: count 足够大, 比如可能超过 2^32.
     */
    /**
     * 解题思路：
     * 为了处理大量的 count，我们需要找到一个优化方法，以便在不进行所有重复操作的情况下找到最终结果。
     * 首先，要观察所有指令类型以查看它们是否具有周期性。
     * 对于给定的指令集，可以确定 Move 操作具有周期性。
     * 对于这个问题，可以计算出 Move 操作的周期，然后在循环中只处理必要的部分。
     *
     * @param src
     * @param ops
     * @param count
     * @return
     */
    public String transferMultipleTimes(String src, String ops, long count) {
        Set<String> seen = new HashSet<>(); // 记录已经出现的字符串
        String current = src; // 当前字符串为初始字符串
        long cycleLength = 0; // 周期长度

        while (!seen.contains(current)) { // 只要当前字符串没有出现过就继续进行操作
            seen.add(current); // 将当前字符串加入已经出现的字符串集合中
            current = transfer(current, ops); // 使用操作字符串进行一次转换，更新当前字符串
            cycleLength++; // 周期长度加 1

            if (current.equals(src)) { // 如果当前字符串等于初始字符串，说明一个周期已经结束
                break;
            }
        }

        long effectiveCount = count % cycleLength; // 实际需要进行转换的次数，取余数避免重复转换多个周期
        current = src; // 重新将当前字符串设置为初始字符串

        for (long i = 0; i < effectiveCount; i++) { // 进行实际需要进行转换的次数
            current = transfer(current, ops); // 使用操作字符串进行一次转换，更新当前字符串
        }

        return current; // 返回转换后的字符串
    }
}

/**
 * Q3: 并发任务控制器
 * <p>
 * 注意: 不可使用 java 提供的线程池相关接口
 */
class AsyncWorker {
    private final int capacity;  // 并发数量上限
    private final Queue<Runnable> taskQueue = new LinkedList<>();  // 任务队列，用于存储未执行的任务
    private final AtomicInteger runningTasks = new AtomicInteger(0);  // 正在运行的任务数量
    private final Object lock = new Object();  // 同步锁

    /**
     * 构造函数
     *
     * @param capacity 最大并发数量
     */
    public AsyncWorker(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 任务提交函数: 当前正在执行的任务数小于 capacity 时, 立即异步执行, 否则
     * 等到任意一个任务执行完成后立即执行.
     *
     * @param task 任务函数
     * @param <T>  返回值类型
     * @return 返回由 Future 包装的任务函数的返回值, 其状态应该和 task 的执行结果一致
     */
    public <T> Future<T> submit(Callable<T> task) {
        CompletableFuture<T> future = new CompletableFuture<>();  // 创建 CompletableFuture 对象，用于获取任务结果
        taskQueue.offer(() -> {  // 将任务包装成一个 Runnable 对象，放入任务队列
            try {
                T result = task.call();  // 执行任务
                future.complete(result);  // 设置 CompletableFuture 对象的执行结果
            } catch (Exception e) {
                future.completeExceptionally(e);  // 设置 CompletableFuture 对象的异常信息
            } finally {
                decrementRunningTasks();  // 执行完毕，减少正在运行的任务数量
            }
        });
        executeTasks();  // 尝试执行任务
        return future;  // 返回 CompletableFuture 对象，可以通过它获取任务的执行结果
    }

    // 尝试执行任务
    private void executeTasks() {
        synchronized (lock) {  // 加锁，保证多线程操作的线程安全
            while (runningTasks.get() < capacity) {  // 如果正在运行的任务数量小于并发数量上限，就可以执行任务
                Runnable task = taskQueue.poll();  // 从任务队列中获取一个任务
                if (task == null) {  // 如果任务队列中没有任务了，就跳出循环
                    break;
                }
                runningTasks.incrementAndGet();  // 增加正在运行的任务数量
                new Thread(task).start();  // 创建一个新线程，执行任务
            }
        }
    }

    // 减少正在运行的任务数量，并尝试执行新的任务
    private void decrementRunningTasks() {
        synchronized (lock) {  // 加锁，保证多线程操作的线程安全
            runningTasks.decrementAndGet();  // 减少正在运行的任务数量
            executeTasks();  // 尝试执行新的任务
        }
    }
}

/* ----------------- 以下是测试用例 -----------------*/

class TestWeightedRandom {

    public static void testWeightedRandom(Function<int[], WeightedRandom> factory) {
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

class TestCharDance {

    public static void testCharDance(Supplier<CharDance> factory) {
        CharDance charDance = factory.get();
        String src = "wosjgncxyakdbefh";
        String ops = "M:3,I:7/2,X:o/h";
        String dst = "efgwhsjoncxyakdb";
        String realDst = charDance.transfer(src, ops);
        if (!dst.equals(realDst)) {
            throw new RuntimeException("invalid transfer result " + realDst + ", expected is " + dst);
        }
        String dst100 = src;
        for (int i = 0; i < 100; i++) {
            dst100 = charDance.transfer(dst100, ops);
        }
        String realDst100 = charDance.transferMultipleTimes(src, ops, 100);
        if (!dst100.equals(realDst100)) {
            throw new RuntimeException("invalid transfer result " + realDst100 + " after 100 times, expected is " + dst100);
        }
    }
}

class TestAsyncWorker {

    private static AsyncWorker worker;
    private static List<Task> tasks;

    public static void testAsyncWorker(Function<Integer, AsyncWorker> factory) {
        worker = factory.apply(2);
        tasks = new ArrayList<>();

        runTask(1, 100, 100, false);
        runTask(2, 200, 200, true);
        runTask(3, 300, 400, false);
        runTask(4, 400, 600, true);
        runTask(5, 100, 500, false);
        runTask(6, 200, 700, true);
        runTask(7, 100, 700, false);
        runTask(8, 200, 900, false);

        for (Task task : tasks) {
            check(task);
        }
    }

    private static void runTask(int id, int delay, int expectedDelay, boolean fail) {
        Task task = new Task();
        task.id = id;
        task.expectedDelay = expectedDelay;
        task.fail = fail;
        long now = System.currentTimeMillis();
        task.value =
                worker.submit(() -> {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ignored) {
                    }
                    long realDelay = System.currentTimeMillis() - now;
                    if (fail) {
                        throw new RuntimeException(String.valueOf(realDelay));
                    } else {
                        return (int) realDelay;
                    }
                });
        tasks.add(task);
    }

    private static class Task {

        private int id;
        private int expectedDelay;
        private boolean fail;
        private Future<Integer> value;
    }

    private static void check(Task task) {
        int realDelay;
        boolean realFail = false;
        try {
            realDelay = task.value.get();
        } catch (Exception e) {
            realDelay = Integer.parseInt(e.getCause().getMessage());
            realFail = true;
        }
        if (realFail != task.fail) {
            throw new RuntimeException(
                    "status of task " +
                            task.id +
                            " should be " +
                            (task.fail ? "failed" : "succeeded") +
                            ", rather than " +
                            (realFail ? "failed" : "succeeded")
            );
        }
        // 忽略延时误差
        if (realDelay / 100 != task.expectedDelay / 100) {
            throw new RuntimeException(
                    "delay of task " + task.id + " should be " + task.expectedDelay + ", rather than " + realDelay
            );
        }
    }
}

public class ShowMeBug {

    public static void main(String[] args) {
        TestWeightedRandom.testWeightedRandom(WeightedRandom::new);
        TestCharDance.testCharDance(CharDance::new);
        TestAsyncWorker.testAsyncWorker(AsyncWorker::new);
    }
}
