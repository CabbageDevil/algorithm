package com.example.demo;


/**
 * @ClassName heap
 * @Description TODO
 * @Author CabbageDevil
 * @Date 2021/11/2 10:51
 * @Version 1.0
 **/
public class heap {
    private int[] data;//基本结构
    public heap(int[] data) {
        this.data = data;
        buildHeap();//创建最小堆
    }
    private void buildHeap() {
        /*
         * 完全二叉树只有数组小标小于或等于（data.length）/2 -1的元素有孩子节点
         */
        for(int i = data.length / 2 -1;i>=0;i--) {
            //对有孩子节点的元素heapify
            heapify(i);
        }
    }
    private void heapify(int i) {
        //获取左右节点的数组下标:除数组第一个元素，其余元素将按照顺序2个分为1组，第一个节点的左右节点对应第一组元素，第二个节点的左右节点对应第二组元素。。。。
        //第i个节点的左右节点数组下标为 (i+1)*2-1 (i+1)*2
        int left = (i + 1) * 2 - 1;
        int right = (i + 1) * 2;
        int minIndex = i;//最小值的坐标
        //存在左结点，且左节点的值小于根节点的值
        if(left < data.length && data[left] < data[i])
            minIndex = left;
        //存在右节点，且右节点的子小于以上比较的较小值
        if(right < data.length && data[right] < data[minIndex])
            minIndex = right;
        //若该跟节点就是最小值，那么不用操作
        if(i == minIndex)
            return;
        //否则进行交换
        int temp = data[i];
        data[i] = data[minIndex];
        data[minIndex] = temp;
        //由于替换后的左右子树会被影响，所有要对受影响的子树在进行heapify
        heapify(minIndex);
    }

    //获取较小的元素
    public int getMin() {
        return data[0];
    }
    //插入元素
    public void insert(int val) {
        data[0] = val;
        heapify(0);
    }

    public static void main(String[] args) {
        int[] data={7,5,2,3,6,7,4};

        heap heap = new heap(data);
        int min = heap.getMin();
        System.out.printf(min+"");
    }


}
