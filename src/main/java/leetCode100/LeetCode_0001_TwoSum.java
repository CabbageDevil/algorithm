package leetCode100;

import java.util.HashMap;

/**
 * @ClassName TwoSum
 * @Description 两数之和
 * @Author CabbageDevil
 * @Date 2023/3/10 0:10
 * @Version 1.0
 **/
public class LeetCode_0001_TwoSum {
    class Solution {
        /**
         * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
         *
         * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
         *
         * 你可以按任意顺序返回答案。
         *
         * 来源：力扣（LeetCode）
         * 链接：https://leetcode.cn/problems/two-sum
         * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
         *
         *  方法 利用HashMap记录位置跟值，然后回溯匹配
         *
         * @param nums
         * @param target
         * @return
         */
        public int[] twoSum(int[] nums, int target) {
            //key 某个之前的数， voalue 这个数的index
            HashMap<Integer,Integer> map=new HashMap<Integer, Integer>();
            for(int i = 0; i <nums.length; i++){
                if(map.containsKey(target-nums[i])){
                    return new int[]{map.get(target-nums[i]),i};
                }
                map.put(nums[i],i);
            }
            return new int[]{-1,-1};
        }
    }
}
