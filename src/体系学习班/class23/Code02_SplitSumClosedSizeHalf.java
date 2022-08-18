package 体系学习班.class23;

public class Code02_SplitSumClosedSizeHalf {
    // 1、暴力递归
    public static int right(int[] arr) {
        // 判空
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // 计算原集合加和
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // 根据数据的个数奇偶情况来分类   递归入口
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length / 2, sum / 2);
        } else {
            // 奇数情况会有两整个分支，一个是分裂出来的个数少的那个集合，一个是分裂出来个数多的那个集合，在这两个里面选最大的，因为能更接近sum / 2
            return Math.max(process(arr, 0, arr.length / 2, sum / 2), process(arr, 0, arr.length / 2 + 1, sum / 2));
        }
    }

    // arr[i....]自由选择，挑选的个数一定要是picks个，累加和<=rest, 离rest最近的返回
    public static int process(int[] arr, int i, int picks, int rest) {
        // basecase   当遍历完所有的数据时
        if (i == arr.length) {
            // 如果此时还剩下要凑的数量为0，说明正好凑出来这些数量了，并且此时已经没有数据了，所以可以直接返回0，也就是没有数可以用来凑了
            // 如果此时picks不为0，但是已经没有数了，所以肯定凑不出来了，直接返回-1
            return picks == 0 ? 0 : -1;
            // 还没有遍历完
        } else {
            // 决策1：不选用当前的数
            int p1 = process(arr, i + 1, picks, rest);


            // 决策2L：就是要使用arr[i]这个数
            // 先将默认值设置为-1
            int p2 = -1;
            int next = -1;
            // 需要保证当前选择的数不能超过rest，否则就不符合情况了
            if (arr[i] <= rest) {
                // 继续后续的决策得到的加和总数
                next = process(arr, i + 1, picks - 1, rest - arr[i]);
            }
            // 如果后续的加和不是-1，说明这个分支是可以凑出来的，那么就计算当前这一层的结果p2，否则p2就是-1,说明这个决策走不通
            if (next != -1) {
                p2 = arr[i] + next;
            }
            // 从两个决策中选取较小的一个数
            return Math.max(p1, p2);
        }
    }

    // 2、动态规划
    public static int dp(int[] arr) {
        // 判空
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // 计算原集合加和
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // 除以2
        sum /= 2;
        int N = arr.length;
        // 个数取一半，并且向上取整
        int M = (N + 1) / 2;
        // 三个可变参数，创建三维数组
        int[][][] dp = new int[N + 1][M + 1][sum + 1];
        // 赋初值，一开始将所有的格子设置为-1
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        // 根据basecase赋初值，最后一层的最左边一列时0，其他都是-1
        for (int rest = 0; rest <= sum; rest++) {
            dp[N][0][rest] = 0;
        }
        // 赋值，按照初值和依赖方向决定赋值方向
        // 从最下面一层开始向上层赋值
        for (int i = N - 1; i >= 0; i--) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    // 这里直接用暴力递归的改就行了
                    int p1 = dp[i + 1][picks][rest];
                    // 就是要使用arr[i]这个数
                    int p2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    if (next != -1) {
                        p2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(p1, p2);
                }
            }
        }
        // 因为递归入口根据不同的情况有两个，所以这里也要根据不同的情况返回不同的dp数组元素
        if ((arr.length & 1) == 0) {
            return dp[0][arr.length / 2][sum];
        } else {
            return Math.max(dp[0][arr.length / 2][sum], dp[0][(arr.length / 2) + 1][sum]);
        }
    }

//	public static int right(int[] arr) {
//		if (arr == null || arr.length < 2) {
//			return 0;
//		}
//		int sum = 0;
//		for (int num : arr) {
//			sum += num;
//		}
//		return process(arr, 0, 0, sum >> 1);
//	}
//
//	public static int process(int[] arr, int i, int picks, int rest) {
//		if (i == arr.length) {
//			if ((arr.length & 1) == 0) {
//				return picks == (arr.length >> 1) ? 0 : -1;
//			} else {
//				return (picks == (arr.length >> 1) || picks == (arr.length >> 1) + 1) ? 0 : -1;
//			}
//		}
//		int p1 = process(arr, i + 1, picks, rest);
//		int p2 = -1;
//		int next2 = -1;
//		if (arr[i] <= rest) {
//			next2 = process(arr, i + 1, picks + 1, rest - arr[i]);
//		}
//		if (next2 != -1) {
//			p2 = arr[i] + next2;
//		}
//		return Math.max(p1, p2);
//	}
//
//	public static int dp1(int[] arr) {
//		if (arr == null || arr.length < 2) {
//			return 0;
//		}
//		int sum = 0;
//		for (int num : arr) {
//			sum += num;
//		}
//		sum >>= 1;
//		int N = arr.length;
//		int M = (arr.length + 1) >> 1;
//		int[][][] dp = new int[N + 1][M + 1][sum + 1];
//		for (int i = 0; i <= N; i++) {
//			for (int j = 0; j <= M; j++) {
//				for (int k = 0; k <= sum; k++) {
//					dp[i][j][k] = -1;
//				}
//			}
//		}
//		for (int k = 0; k <= sum; k++) {
//			dp[N][M][k] = 0;
//		}
//		if ((arr.length & 1) != 0) {
//			for (int k = 0; k <= sum; k++) {
//				dp[N][M - 1][k] = 0;
//			}
//		}
//		for (int i = N - 1; i >= 0; i--) {
//			for (int picks = 0; picks <= M; picks++) {
//				for (int rest = 0; rest <= sum; rest++) {
//					int p1 = dp[i + 1][picks][rest];
//					int p2 = -1;
//					int next2 = -1;
//					if (picks + 1 <= M && arr[i] <= rest) {
//						next2 = dp[i + 1][picks + 1][rest - arr[i]];
//					}
//					if (next2 != -1) {
//						p2 = arr[i] + next2;
//					}
//					dp[i][picks][rest] = Math.max(p1, p2);
//				}
//			}
//		}
//		return dp[0][0][sum];
//	}

    public static int dp2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum >>= 1;
        int N = arr.length;
        int M = (arr.length + 1) >> 1;
        int[][][] dp = new int[N][M + 1][sum + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int k = 0; k <= sum; k++) {
                dp[i][0][k] = 0;
            }
        }
        for (int k = 0; k <= sum; k++) {
            dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= Math.min(i + 1, M); j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (k - arr[i] >= 0) {
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
                    }
                }
            }
        }
        return Math.max(dp[N - 1][M][sum], dp[N - 1][N - M][sum]);
    }

    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            int ans3 = dp2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
