package 体系学习班.class29;

public class Code03_ReservoirSampling {

    public static class RandomBox {
        // 袋子
        private int[] bag;
        // 袋子容量
        private int N;
        // 已经流出第几号球了
        private int count;

        public RandomBox(int capacity) {
            bag = new int[capacity];
            N = capacity;
            count = 0;
        }

        private int rand(int max) {
            return (int) (Math.random() * max) + 1;
        }

        public void add(int num) {
            count++;
            // 如果袋子还没有装满，就直接将球加入到袋子
            if (count <= N) {
                bag[count - 1] = num;
            } else {
                // 按照10/i的概率来决定要不要将这个球放入袋子中
                if (rand(count) <= N) {
                    bag[rand(N) - 1] = num;
                }
            }
        }

        public int[] choices() {
            int[] ans = new int[N];
            for (int i = 0; i < N; i++) {
                ans[i] = bag[i];
            }
            return ans;
        }

    }

    // 请等概率返回1~i中的一个数字
    public static int random(int i) {
        return (int) (Math.random() * i) + 1;
    }

    public static void main(String[] args) {
        System.out.println("hello");
        // 下面是实验，进行多次实验最终能发现每一个球能进到袋子中的次数都是差不多一样的，说明这个算法确实可以保证等概率
        int test = 10000;
        int ballNum = 17;
        int[] count = new int[ballNum + 1];
        for (int i = 0; i < test; i++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int num = 1; num <= ballNum; num++) {
                if (num <= 10) {
                    bag[bagi++] = num;
                } else { // num > 10
                    if (random(num) <= 10) { // 一定要把num球入袋子
                        bagi = (int) (Math.random() * 10);
                        bag[bagi] = num;
                    }
                }

            }
            for (int num : bag) {
                count[num]++;
            }
        }
        for (int i = 0; i <= ballNum; i++) {
            System.out.println(count[i]);
        }

        System.out.println("hello");
        int all = 100;
        int choose = 10;
        int testTimes = 50000;
        int[] counts = new int[all + 1];
        for (int i = 0; i < testTimes; i++) {
            RandomBox box = new RandomBox(choose);
            for (int num = 1; num <= all; num++) {
                box.add(num);
            }
            int[] ans = box.choices();
            for (int j = 0; j < ans.length; j++) {
                counts[ans[j]]++;
            }
        }

        for (int i = 0; i < counts.length; i++) {
            System.out.println(i + " times : " + counts[i]);
        }

    }
}

