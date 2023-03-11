package leetCode100;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LeetCode_0003_LongSubstringWithoutRepeatingCharacters
 * @Description 无重复字符的最长子串
 * @Author CabbageDevil
 * @Date 2023/3/11 9:06
 * @Version 1.0
 **/
public class LeetCode_0003_LongSubstringWithoutRepeatingCharacters {
    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 子串解题思想： 计算出每次以I结尾的时候最长子串且复合题意要求
     * <p>
     * 方法1  计算出每次以I结尾的时候最长无重复子串
     * <p>
     * 判断重复条件 1 记录每次字符出现的位置，并且每次重复后更新，这样在获得i的时候就可以判断是否有匹配位置直接算出长度
     *             2 i-1位置上用条件1判断时候的长度，i是无法越界的，因为里面已经包含重复
     *             3 1和2条件取最大值（因为i是尾index，所以取最大值为最无重复区域）即可获得无重复字符子串头节点，尾下标-头下标=子串长度
     *             4 通过max值记录每次子串长度，返回最大值即可
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    static class Solution {
        public static int lengthOfLongestSubstring(String s) {
            //边界过滤
            if (null == s || "".equals(s)) {
                return 0;
            }
            //转换成数组
            char[] str = s.toCharArray();
            //生成hash表
            //Map<String,Integer> map=new HashMap<String, Integer>();
            //使用数组+ASCII的方式代替map表效率更快 例如map（a，6） 数组则是 map[97]=6
            int[] map = new int[256];
            for (int i = 0; i < map.length; i++) {
                //最开始每次都出现在-1位置
                map[i] = -1;
            }
            //收集答案
            int len = 0;
            //i-1位置的子串的重复下标
            int pre = -1;
            int cur = 0;
            for(int i=0;i!=str.length;i++){
                //找到结尾是i位置下，重复的下标（因为str数组是char类型，所以转换成int类型会直接翻译成ascii）
                pre=Math.max(pre,map[str[i]]);
                cur=i-pre;
                len=Math.max(len,cur);
                //更新重复下标位置
                map[str[i]]=i;
            }
            return len;
        }
        public static void main(String[] args) {
            System.out.println(lengthOfLongestSubstring("abcabcbb"));
        }
    }


}
