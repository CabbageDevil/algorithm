package leetCode100;

/**
 * @ClassName LeetCode_0004_MedianTwoSortedArrays
 * @Description TODO
 * @Author CabbageDevil
 * @Date 2023/3/11 10:56
 * @Version 1.0
 **/
public class LeetCode_0004_MedianTwoSortedArrays {
    /**
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     *
     * 算法的时间复杂度应该为 O(log (m+n)) 。
     *
     * 解题思路 通过 getUpMedian找到上中位数的方式找到findKNum函数中指定位置小的数，可以获取指定位置小的数后，判断原题数组长度的关系就可以获取答案
     *
     */
    static class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int size=nums1.length+nums2.length;
            //判断总长度是偶数还是奇数
            boolean even=(size & 1)==0;
            //两个数组都有值
            if(nums1.length!=0&&nums2.length!=0){
                //这里调用findKNum方法是拿到具体的从都从1开始计算所以要+1
                if (even){
                    return (double) (findKNum(nums1,nums2,size/2)+findKNum(nums1,nums2,size/2+1))/2D;
                }else{
                    return (double)findKNum(nums1,nums2,size/2+1);
                }
            }
            if(nums1.length!=0){
                if (even){
                    return (double) (nums1[size/2]+nums1[(size-1)/2])/2D;
                }else{
                    //这里是用数组下标计算所以不需要+1
                    return (double)nums1[size/2];
                }
            }
            if(nums2.length!=0){
                if (even){
                    return (double) (nums2[size/2]+nums2[(size-1)/2])/2D;
                }else{
                    //这里是用数组下标计算所以不需要+1
                    return (double)nums2[size/2];
                }
            }
            return 0;
        }

        /**
         * 两个一定有序但是不一定等长的数组排完顺序后第K小的数
         * @param arr1 数组
         * @param arr2 数组
         * @param kth
         * @return
         */
        public static int findKNum(int[] arr1,int[]arr2,int kth){
            //定义一下长短数据，好编写逻辑
            int[] longs=arr1.length>=arr2.length?arr1:arr2;
            int[] shorts=arr1.length<arr2.length?arr1:arr2;
            int l=longs.length;
            int s=shorts.length;
            //如果K的值大于等于短数组
            if(kth<=s){
                //合并可能出现K的段取出上中位数
                return getUpMedian(shorts,0 ,kth-1,longs,0,kth-1);
            }
            //K大于长数组长
            if(kth>l){
                if(shorts[kth-l-1]>=longs[l-1]){
                    return shorts[kth-l-1];
                }
                if(longs[kth-s-1]>=shorts[s-1]){
                    return longs[kth-s-1];
                }
                //合并可能出现K的段取出上中位数
                return getUpMedian(shorts,kth-l ,s-1,longs,kth-s,l-1);
            }
            //K大于短数组，但是小于长数组，说明K落点在长数组上
            if(longs[kth-s-1]>=shorts[s-1]){
                return longs[kth-s-1];
            }

            return getUpMedian(shorts,0 ,s-1,longs,kth-s,kth-1);
        }

        /**
         *
         * 找到两个等长数组中的上中位数
         * O（logN）
         *
         * @param a1 一个数组
         * @param s1 开始下标
         * @param e1 结束下标
         * @param a2 一个数组
         * @param s2 开始下标
         * @param e2 结束下标
         * @return 上中位数
         */
        public static int getUpMedian(int[] a1,int s1,int e1,int[] a2,int s2,int e2 ){
            int mad1=0;
            int mad2=0;
            while(s1<e1){
                mad1=(s1+e1)/2;
                mad2=(s2+e2)/2;
                if(a1[mad1]==a2[mad2]){
                    return a1[mad1];
                }
                //数组奇数长度
                if(((e1-s1+1)&1)==1){
                    if(a1[mad1]>a2[mad2]){
                        if(a2[mad2]>=a1[mad1-1]){
                            return a2[mad2];
                        }
                        e1=mad1-1;
                        s2=mad2+1;
                    }else{
                        //a1[mad1]<a2[mad2]
                        if(a1[mad1]>=a2[mad2-1]){
                            return a1[mad1];
                        }
                        e2=mad2-1;
                        s1=mad1+1;
                    }

                }else{//偶数长度
                    if(a1[mad1]>a2[mad2]){
                        e1=mad1;
                        s2=mad2+1;
                    }else{
                        e2=mad2;
                        s1=mad1+1;
                    }
                }

            }
            return Math.min(a1[s1],a2[s2]);
        }
    }
}
