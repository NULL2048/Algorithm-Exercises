package 大厂刷题班.class07;

// 二叉树递归套路  或   贪心
// 本题测试链接 : https://leetcode.cn/problems/binary-tree-cameras/
public class Code02_MinCameraCover {

    public static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;
    }

    // 1、二叉树递归套路
    // 潜台词：x是头节点，x下方的点都被覆盖的情况下
    public class Info {
        private long uncovered;
        private long coveredNoCamera;
        private long coveredHasCamera;

        public Info(long uncovered, long coveredNoCamera, long coveredHasCamera) {
            // x没有被覆盖（指的是没有被x下层的相机覆盖，上层有没有覆盖我们不管，此时的视角就认为x是根节点，他的上面已经没有节点了），x为头的树至少需要几个相机
            this.uncovered = uncovered;
            // x被相机覆盖，但是x没相机，x为头的树至少需要几个相机
            this.coveredNoCamera = coveredNoCamera;
            // x被相机覆盖了，并且x上放了相机，x为头的树至少需要几个相机
            this.coveredHasCamera = coveredHasCamera;
        }
    }


    public int minCameraCover(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Info rootInfo = process(root);
        // data.uncovered + 1既然你这个位置没有被覆盖，但是这个位置下面给都已经能保证被覆盖了，我干脆直接在这个位置给你一个相机就行了，所以这里要加1
        return (int) Math.min(rootInfo.uncovered + 1, Math.min(rootInfo.coveredNoCamera, rootInfo.coveredHasCamera));
    }

    // 将所有可能的情况都通过递归尝试出来
    public Info process(TreeNode head) {
        // 递归出口，当递归到最底层的空节点是，这个节点是必须被覆盖的，但是这个节点上不能放相机
        // 所以这里就返回被覆盖但是没相机的数量是0，其他的都是系统最大值，表示不存在这两种情况
        if (head == null) {
            return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        }

        // 收集左树和右树的信息，然后再用收集上来的左树和右树的信息来得到当前这个节点的Info信息
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        // 计算x的uncovered
        // x节点没有被覆盖，但是x节点以下的节点全部都要被覆盖。如果x没有被覆盖，那么他的左右子节点上一定都没有相机，否则x就能被覆盖了
        long uncovered = leftInfo.coveredNoCamera + rightInfo.coveredNoCamera;

        // 计算x的coveredNoCamera
        // x下方的点都被covered，x也被cover，但x上没相机。这里我们不去考虑x的父节点，只考虑x下面的节点，所以满足这个情况，就说明x的左右子节点中至少存在一个节点是有相机的，否则x不可能被覆盖
        // 1、左子节点被覆盖有相机，右子节点被覆盖没有相机
        // 2、左子节点被覆盖没有相机，右子节点被覆盖有相机
        // 3、左子节点和右子节点都有相机
        long coveredNoCamera = Math.min(Math.min(leftInfo.coveredNoCamera + rightInfo.coveredHasCamera, rightInfo.coveredNoCamera + leftInfo.coveredHasCamera), leftInfo.coveredHasCamera + rightInfo.coveredHasCamera);

        // 计算x的coveredHasCamera
        // x下方的点都被covered，x也被cover，且x上有相机。这种情况下子节点三种可能性都可选，因为父节点有相机就能满足子节点的任何情况，就算是子节点没被下层相机覆盖，父节点的相机也可以将其覆盖掉，保证满足要求。就将左右子树所有可能的情况都罗列出来取最小值相加，最后还要再加1，把x节点的相机也算进去。
        long coveredHasCamera = Math.min(leftInfo.uncovered, Math.min(leftInfo.coveredNoCamera, leftInfo.coveredHasCamera))
                + Math.min(rightInfo.uncovered, Math.min(rightInfo.coveredNoCamera, rightInfo.coveredHasCamera))
                + 1;

        return new Info(uncovered, coveredNoCamera, coveredHasCamera);
    }


    // 2、二叉树递归套路利用贪心优化，是常数优化
    public static int minCameraCover2(TreeNode root) {
        Data data = process2(root);
        return data.cameras + (data.status == Status.UNCOVERED ? 1 : 0);
    }

    // 以x为头，x下方的节点都是被covered，x自己的状况，分三种
    public static enum Status {
        UNCOVERED, COVERED_NO_CAMERA, COVERED_HAS_CAMERA
    }

    // 以x为头，x下方的节点都是被covered，得到的最优解中：
    // x是什么状态，在这种状态下，需要至少几个相机
    public static class Data {
        public Status status;
        public int cameras;

        public Data(Status status, int cameras) {
            this.status = status;
            this.cameras = cameras;
        }
    }

    public static Data process2(TreeNode X) {
        if (X == null) {
            return new Data(Status.COVERED_NO_CAMERA, 0);
        }
        Data left = process2(X.left);
        Data right = process2(X.right);
        int cameras = left.cameras + right.cameras;

        // 可以根据左右子树的情况，通过贪心策略分析来唯一确定一个最优解返回，就不像上一个方法那样把所有情况全部都返回。

        // 左、或右，哪怕有一个没覆盖，那么当前x节点就必须要放一个相机，不然就无法满足下层全部被覆盖的要求了。
        if (left.status == Status.UNCOVERED || right.status == Status.UNCOVERED) {
            return new Data(Status.COVERED_HAS_CAMERA, cameras + 1);
        }

        // 左右孩子，不存在没被覆盖的情况，并且至少存在一个相机。那么x节点一定能被子节点覆盖，x就不用放相机了。
        if (left.status == Status.COVERED_HAS_CAMERA || right.status == Status.COVERED_HAS_CAMERA) {
            return new Data(Status.COVERED_NO_CAMERA, cameras);
        }
        // 左右孩子，不存在没被覆盖的情况，也都没有相机，那么我们就认为x没被覆盖，让x的父节点去想办法
        return new Data(Status.UNCOVERED, cameras);
    }
}
