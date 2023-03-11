package leetCode100;

/**
 * @ClassName addTwoNumbers
 * @Description 两数相加
 * @Author CabbageDevil
 * @Date 2023/3/10 0:29
 * @Version 1.0
 **/
public class LeetCode_0002_AddTwoNumbers {

    /**
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头
     * <p>
     * <p>
     * 方法 1 先把链表转换为真实数字然后相加（由于数据类型长度有限，所以这样的方式只能支持到long基础类型）
     * <p>
     * 方法 2 转换容器，把listNode转换成array，生成一个比链表大1的长度array，然后每个index的数字相加，进位往前，依次写大1的array中，最好再把array转换为链表即可
     * （取巧，把链表问题降为到了数组，使用了多的额外空间）
     *
     * 方法3 把链表按需排列生成新的链表，保留进位信息，最后再反转链表
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            //进位信息
            int ca = 0;
            //节点相加和
            int n = 0;
            int n1 = 0;
            int n2 = 0;
            ListNode c1 = l1;
            ListNode c2 = l2;
            //新的节点
            ListNode node = null;
            //之前的节点
            ListNode pre = null;
            //只要两个链表其中一个还有节点就继续向下执行
            while (null != c1 || null != c2) {
                //节点为空时补0
                n1 = c1 != null ? c1.val : 0;
                n2 = c2 != null ? c2.val : 0;
                //节点和
                n = n1 + n2 + ca;
                //抓住之前的节点
                pre = node;
                //生成新节点
                node = new ListNode(n % 10);
                //设置前节点
                node.next = pre;
                //判断有没有进位信息
                ca = n / 10;
                //不为空则向下继续执行
                c1 = c1 != null ? c1.next : null;
                c2 = c2 != null ? c2.next : null;
            }
            if (ca == 1) {
                pre = node;
                node = new ListNode(1);
                node.next = pre;
            }
            //链表翻转后返回
            return reversList(node);
        }

        public ListNode reversList(ListNode node) {
            ListNode pre = null;
            ListNode next = null;
            while (null != node) {
                next = node.next;
                node.next = pre;
                pre = node;
                node = next;
            }
            return pre;
        }
    }
}
