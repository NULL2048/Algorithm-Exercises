package 测试;

import java.util.HashMap;

class LFUCache {
    // 桶内节点类   同一个桶内的节点他们的使用次数times都是一样的，使用双向链表连接，桶内节点的双向链表再按照最久未使用的时间来进行排列，最新使用过的在尾部，最久未使用的在头部
    public class Node {
        public int key;
        public int value;
        public int times;
        public Node next;
        public Node pre;

        public Node() {

        }
        public Node(int key, int value, int times) {
            this.key = key;
            this.value = value;
            this.times = times;
        }
    }

    // 桶节点类   桶节点也是用双向链表连接的，每一个桶代表不同的节点使用次数，按照使用次数的大小从头到尾排列，靠近尾部的桶使用次数就多，靠近头部的桶使用次数就少
    public class NodeList {
        public Node head;
        public Node tail;

        public NodeList next;
        public NodeList pre;

        public NodeList(Node node) {
            this.head = new Node();
            this.head.next = node;
            this.tail = head;
        }


        public void remove(Node node) {
            if (node == head.next && node == tail) {
                head.next = null;
                tail = null;
            } else if (node == head.next) {
                head.next = node.next;
                node.next.pre = head;
            } else if (node == tail) {
                tail = tail.pre;
                tail.next = null;
            } else {
                Node preNode = node.pre;
                Node nextNode = node.next;
                preNode.next = nextNode;
                nextNode.pre = preNode;
            }


        }

        public void addLast(Node node) {
            if (head.next != null) {
                tail.next = node;
                node.pre = tail;
                tail = node;
            } else {
                head.next = node;
                tail = node;
            }

        }
    }

    public int capacity;
    public int cnt;
    public HashMap<Integer, Node> nodeMap;
    public HashMap<Integer, NodeList> timesMap;
    public NodeList first;
    public NodeList last;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.nodeMap = new HashMap<>();
        this.timesMap = new HashMap<>();
    }

    public int get(int key) {
        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            NodeList nodeList = timesMap.get(node.times);
            node.times++;

            if (timesMap.containsKey(node.times)) {
                nodeList.remove(node);
                NodeList nextNodeList = timesMap.get(node.times);
                nextNodeList.addLast(node);
            } else {
                NodeList nextNodeList = new NodeList(node);
                nextNodeList.pre = nodeList;
                nodeList.next = nextNodeList;
            }

            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (nodeMap.containsKey(key)) {
            Node node = nodeMap.get(key);
            NodeList nodeList = timesMap.get(node.times);
            node.times++;

            if (timesMap.containsKey(node.times)) {
                nodeList.remove(node);
                NodeList nextNodeList = timesMap.get(node.times);
                nextNodeList.addLast(node);
            } else {
                NodeList nextNodeList = new NodeList(node);
                nextNodeList.pre = nodeList;
                nodeList.next = nextNodeList;
            }

            node.value = value;
        } else {
            Node node = new Node(key, value, 1);
            if (timesMap.containsKey(1)) {
                NodeList nodeList = timesMap.get(1);
                nodeList.addLast(node);
            } else {
                first = new NodeList(node);
                last = first;
                timesMap.put(1, first);
            }
            nodeMap.put(key, node);
        }
    }

    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(2);

        lfuCache.put(2, 1);
        lfuCache.put(2, 2);
        lfuCache.get(1);
        lfuCache.put(3,3);
        lfuCache.get(2);
        lfuCache.get(3);
        lfuCache.put(4,4);
        lfuCache.get(1);
        lfuCache.get(3);
        lfuCache.get(4);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
