package 测试;

import java.util.HashMap;

public class LRUCache {
    class Node {
        int key;
        int value;
        int time;
        Node next;
        Node pre;

        public Node(int key, int value, int time) {
            this.key = key;
            this.value = value;
            this.time = time;
        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    //public int curTime;
    public static int cnt;
    public static int capacity;
    public static HashMap<Integer, Node> map;
    public static Node head;
    public static Node tail;

    public LRUCache(int capacity) {
        this.map = new HashMap<>();
        this.capacity = capacity;
        this.cnt = 0;
        this.head = null;
        this.tail = null;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);

            if (node != tail) {
                if (node != head) {
                    Node preNode = node.pre;
                    preNode.next = node.next;
                    node.next.pre = preNode;
                } else {
                    head = node.next;
                    head.pre = null;
                }

                node.pre = tail;
                tail.next = node;
                tail = node;
            }

            return node.value;
        } else {
            return -1;
        }

        //curTime++;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);

            if (node != head) {
                Node preNode = node.pre;
                preNode.next = node.next;
                node.next.pre = preNode;
            } else {
                head = node.next;
                head.pre = null;
            }

            node.pre = tail;
            tail.next = node;
            tail = node;

            node.value = value;
        } else {
            if (cnt == capacity) {
                remove();
            }

            add(key, value);
            cnt++;
        }
    }

    public void remove() {
        map.remove(head.key);
        if (head != tail) {
            head = head.next;
            head.pre = null;
        } else {
            head = null;
            tail = null;
        }

        cnt--;
    }

    public void add(int key, int value) {
        if (head == null) {
            Node node = new Node(key, value);
            head = node;
            tail = head;
            map.put(key, node);
        } else {
            Node node = new Node(key, value);

            tail.next = node;
            node.pre = tail;
            tail = tail.next;
            map.put(key, node);
        }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);

        lruCache.put(2, 1);
        lruCache.put(2, 2);
        lruCache.get(2);
        lruCache.put(1,1);
        lruCache.put(4,1);
        lruCache.get(2);
    }
}
