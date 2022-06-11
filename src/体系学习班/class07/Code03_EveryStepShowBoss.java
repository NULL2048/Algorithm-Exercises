package 体系学习班.class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 给定一个整型数组，int[] arr；和一个布尔类型数组，boolean[] op。
 * 两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作。
 * arr= [3,3,1,2,1,2,5…]
 * op = [T,T,T,T,F,T,F…]
 * 依次表示：
 * 3用户购买了一件商品
 * 3用户购买了一件商品
 * 1用户购买了一件商品
 * 2用户购买了一件商品
 * 1用户退货了一件商品
 * 2用户购买了一件商品
 * 5用户退货了一件商品…
 *
 * 一对arr[i]和op[i]就代表一个事件：
 * 用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品，op[i] == F就代表这个用户退货了一件商品。
 *
 * 现在你作为电商平台负责人，你想在每一个事件到来的时候，都给购买次数最多的前K名用户颁奖。
 * 所以每个事件发生后，你都需要一个得奖名单（得奖区）。
 *
 * 得奖系统的规则：
 * 	1. 如果某个用户购买商品数为0，但是又发生了退货事件，则认为该事件无效，得奖名单和上一个事件发生后一致，如例子中的5用户。
 * 	2. 某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1。
 * 	3. 每次都是最多K个用户得奖，K也为传入的参数。如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果。
 * 	4. 得奖系统分为得奖区和候选区，任何用户只要购买数>0，一定在这两个区域中的一个。
 * 	5. 购买数最大的前K名用户进入得奖区，在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区。
 * 	6. 如果购买数不足以进入得奖区的用户，进入候选区。
 * 	7. 如果候选区购买数最多的用户，已经足以进入得奖区，该用户就会替换得奖区中购买数最少的用户（大于才能替换）；
 * 	如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户；
 * 	如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户。
 * 	8. 候选区和得奖区是两套时间（其实就是数组的下标，这个时间是值得进入到候选区或得奖区的时间，并不是购买商品的时间），因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有从得奖区出来进入候选区的用户，得奖区时间删除，进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）从候选区出来进入得奖区的用户，候选区时间删除，进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 * 	9. 如果某用户购买数==0，不管在哪个区域都离开，区域时间删除， 离开是指彻底离开，哪个区域也不会找到该用户；
 * 	如果下次该用户又发生购买行为，产生>0的购买数，会再次根据之前规则回到某个区域中，进入区域的时间重记
 *
 * 请遍历arr数组和op数组，遍历每一步输出一个得奖名单
 */
public class Code03_EveryStepShowBoss {
    // 用户类
    public static class Customer {
        // 用户编号
        public int id;
        // 用户买了几件商品
        public int buy;
        // 进入得奖区/候选区的时间
        public int enterTime;

        // 构造方法
        public Customer(int v, int b, int o) {
            id = v;
            buy = b;
            enterTime = 0;
        }
    }

    // 候选区比较器
    // 购买数较大的放在前面，如果购买数一样，谁进入候选区早谁就放在前面。谁更有资格先进入得奖区谁就放在前面
    public static class CandidateComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    // 得奖区比较器
    // 购买数较小的放在前面，如果购买数一样，谁进入候选区早谁就放在前面。谁最应该移出得奖区谁就放在前面
    public static class DaddyComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    // 使用加强堆来解决这个问题，优化后的方法
    public static class WhosYourDaddy {
        // 客户编号和客户对象对照表，这个表里只会存储购买数大于0的客户
        private HashMap<Integer, Customer> customers;
        // 候选区的加强堆
        private Code02_HeapGreater<Customer> candHeap;
        // 得奖区的加强堆
        private Code02_HeapGreater<Customer> daddyHeap;
        // 得奖区最大人数
        private final int daddyLimit;

        // 构造方法
        public WhosYourDaddy(int limit) {
            // 初始化成员属性
            customers = new HashMap<Integer, Customer>();
            // 根据我们的排序规则来创建加强堆
            // 候选区是大根堆，谁购买数多谁在上面，如果购买数一样，谁进入时间早谁在上面
            candHeap = new Code02_HeapGreater<>(new CandidateComparator());
            // 得奖区是小根堆，谁购买数少谁在上面，如果购买数一样，谁进入时间早谁在上面
            daddyHeap = new Code02_HeapGreater<>(new DaddyComparator());
            daddyLimit = limit;
        }

        // 当前处理i号事件，arr[i] -> id,  buyOrRefund
        public void operate(int time, int id, boolean buyOrRefund) {
            // 如果该用户是退货，并且这个用户本来购买数就是0，那么这种情况直接返回，当作没发生
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }

            /**
             * 如果走到了这里，说明没有出现用户购买数为0然后又退货的情况，
             * 这里一共可能有三种情况：
             * 1、用户之前购买数是0，此时买货事件
             * 2、用户之前购买数>0，此时买货
             * 3、用户之前购买数>0, 此时退货
             */

            // 如果此时该用户购买数为0，说明此时是买事件，则提前在向map中添加该用户
            // 因为买事件完成之后该用户的购买书就大于0了
            // 如果此时用户购买时大于0，则该用户已经在map中了
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }

            Customer c = customers.get(id);
            // 买、卖。根据该用户此时是买还是卖修改该用户的购买数
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            // 如果调整完购买数后该用户购买0件，则将其从map中移除
            if (c.buy == 0) {
                customers.remove(id);
            }

            // 如果此时该用户在候选区也没有，得奖区也没有，就说明该用户一定是新来的用户，并且还是买商品
            if (!candHeap.contains(c) && !daddyHeap.contains(c)) {
                // 如果得奖区还没有满，则将该用户直接放入得奖区
                if (daddyHeap.size() < daddyLimit) {
                    // 设置进入得奖区的时间
                    c.enterTime = time;
                    // 入堆
                    daddyHeap.push(c);
                    // 如果得奖区满了，则先暂时将该用户放入候选区，后面再根据实际情况做调整		
                } else {
                    // 设置进入候选区的时间
                    c.enterTime = time;
                    // 入堆
                    candHeap.push(c);
                }
                // 当前的客户在候选区	
            } else if (candHeap.contains(c)) {
                // 如果当前客户在完成了事件之后购买数为0了，则将该用户移出候选区
                if (c.buy == 0) {
                    candHeap.remove(c);
                    // 如果当前客户在完成了事件之后购买数仍大于0，则将该用户继续保留在候选区堆中。
                } else {
                    // 但是因为这个节点的购买数变了，则有可能当前的候选区大根堆结构已经被破坏了，需要将这个有变化的节点重新进行调整，维护堆结构
                    candHeap.resign(c);
                }
                // 当前的客户在得奖区
            } else {
                // 如果当前客户在完成了事件之后购买数为0了，则将该用户移出得奖区
                if (c.buy == 0) {
                    daddyHeap.remove(c);
                    // 如果当前客户在完成了事件之后购买数仍大于0，则将该用户继续保留在得奖区堆中。	
                } else {
                    // 但是因为这个节点的购买数变了，则有可能当前的得奖区小根堆结构已经被破坏了，需要将这个有变化的节点位置重新进行调整，维护堆结构
                    daddyHeap.resign(c);
                }
            }

            // 更新得奖区的客户名单
            daddyMove(time);
        }

        // 获得得奖区的全部元素
        public List<Integer> getDaddies() {
            // 返回得奖区堆中的所有元素
            List<Customer> customers = daddyHeap.getAllElements();
            // 将得奖区所有的客户名单添加进ans中
            List<Integer> ans = new ArrayList<>();
            for (Customer c : customers) {
                ans.add(c.id);
            }
            return ans;
        }

        private void daddyMove(int time) {
            // 候选区如果为空，则直接返回
            if (candHeap.isEmpty()) {
                return;
            }

            // 候选区不为空
            // 得奖区没有满，直接将候选区最靠前的客户放入到得奖区
            if (daddyHeap.size() < daddyLimit) {
                // 将候选区堆顶弹出
                Customer p = candHeap.pop();
                // 设置该客户进入得奖区的时间
                p.enterTime = time;
                // 将该客户添加进得奖区
                daddyHeap.push(p);
                // 得奖区满了，候选区不为空	
            } else {
                // 如果候选区最前面客户的购买数大于得奖区最前面客户的购买数，就需要将候选去中的该用户和得奖区中的该用户交换
                if (candHeap.peek().buy > daddyHeap.peek().buy) {
                    // 将两个用户交换
                    Customer oldDaddy = daddyHeap.pop();
                    Customer newDaddy = candHeap.pop();
                    // 更新两个用户进入对应区的时间
                    oldDaddy.enterTime = time;
                    newDaddy.enterTime = time;
                    // 将两个用户分别添加进得奖区和候选区
                    daddyHeap.push(newDaddy);
                    candHeap.push(oldDaddy);
                }
            }
        }
    }

    // 加强对优化
    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        // 要返回的得奖名单
        List<List<Integer>> ans = new ArrayList<>();

        WhosYourDaddy whoDaddies = new WhosYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            // i时间点，arr[i]客户发生了op[i]事件，通过这个方法进行调整
            whoDaddies.operate(i, arr[i], op[i]);
            // getDaddies()返回调整之后得到的本轮得奖名单
            ans.add(whoDaddies.getDaddies());
        }
        return ans;
    }


    //==========================================================

    // 暴力模拟的写法：干完所有的事，模拟，不优化
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        // 用户id与用户对象的对照表，这里指挥存储购买数大于0的用户
        HashMap<Integer, Customer> map = new HashMap<>();
        // 候选区数组
        ArrayList<Customer> cands = new ArrayList<>();
        // 得奖区数组
        ArrayList<Customer> daddy = new ArrayList<>();
        // 要返回的得奖名单
        List<List<Integer>> ans = new ArrayList<>();

        // 模拟
        for (int i = 0; i < arr.length; i++) {
            // 当前用户编号
            int id = arr[i];
            // 获取当前用户是买了还是退货了
            boolean buyOrRefund = op[i];
            // 如果这个用户是退货，但是在map并不存在这个用户
            // 说明这个用户本身购买商品数量为0，他现在又退货，则直接忽略本次事件
            // 直接返回上一次的获奖名单
            if (!buyOrRefund && !map.containsKey(id)) {
                // 将上一次获奖名单添加到ans中
                ans.add(getCurAns(daddy));
                // 跳过本次循环
                continue;
            }

            /**
             * 如果走到了这里，说明没有出现用户购买数为0然后又退货的情况，
             * 这里一共可能有三种情况：
             * 1、用户之前购买数是0，此时买货事件
             * 2、用户之前购买数>0，此时买货
             * 3、用户之前购买数>0, 此时退货
             */

            // 如果此时该用户购买数为0，说明此时是买事件，则提前在向map中添加该用户
            // 因为买事件完成之后该用户的购买书就大于0了
            // 如果此时用户购买时大于0，则该用户已经在map中了
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }

            // 买、卖。根据该用户此时是买还是卖修改该用户的购买数
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            // 如果调整完购买数后该用户购买0件，则将其从map中移除
            if (c.buy == 0) {
                map.remove(id);
            }

            // 如果此时该用户在候选区也没有，得奖区也没有，就说明该用户一定是新来的用户，并且还是买商品
            if (!cands.contains(c) && !daddy.contains(c)) {
                // 如果得奖区还没有满，则将该用户直接放入得奖区
                if (daddy.size() < k) {
                    // 设置进入得奖区的时间
                    c.enterTime = i;
                    daddy.add(c);
                    // 如果得奖区满了，则先暂时将该用户放入候选区，后面再根据实际情况做调整	
                } else {
                    // 设置进入候选区的时间
                    c.enterTime = i;
                    cands.add(c);
                }
            }

            // 清除得奖区和候选区所有购买书为0的客户，遍历着去清除
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            // 候选区排序
            cands.sort(new CandidateComparator());
            // 得奖区排序
            daddy.sort(new DaddyComparator());


            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
        // 候选区为空，就说明目前不涉及到候选区和得奖区的交换。直接返回得奖区即可
        if (cands.isEmpty()) {
            return;
        }

        // 候选区不为空
        // 得奖区没有满，直接将候选区最靠前的客户放入到得奖区
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            // 设置该客户进入得奖区的时间
            c.enterTime = time;
            // 将该客户添加进得奖区
            daddy.add(c);
            // 将该客户在的候选区中删除
            cands.remove(0);
            // 得奖区满了，候选区不为空
        } else {
            // 如果候选区最前面客户的购买数大于得奖区最前面客户的购买数，就需要将候选去中的该用户和得奖区中的该用户交换
            if (cands.get(0).buy > daddy.get(0).buy) {
                // 将两个用户交换
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                // 更新两个用户进入对应区的时间
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
                // 将两个用户分别添加进得奖区和候选区
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
    }

    // 移除候选区/得奖区所有购买数为0的客户
    public static void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<Customer>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }

    // 获取得奖区用户名单
    public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy) {
            ans.add(c.id);
        }
        return ans;
    }

    // 为了测试
    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
