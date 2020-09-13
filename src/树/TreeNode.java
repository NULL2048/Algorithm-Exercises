package 树;

public class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public String val;

    public TreeNode() {}
    public TreeNode(TreeNode leftNode, TreeNode rightNode, String value) {
        this.left = leftNode;
        this.right = rightNode;
        this.val = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
