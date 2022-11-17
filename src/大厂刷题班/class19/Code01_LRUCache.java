package 大厂刷题班.class19;

import java.util.HashMap;

// 数据结构设计题
// 本题测试链接 : https://leetcode.cn/problems/lru-cache/
// 提交时把类名和构造方法名改成 : LRUCache
public class Code01_LRUCache {
    public MyCache cache;
    public Code01_LRUCache(int capacity) {
        cache = new MyCache(capacity);
    }

    public int get(int key) {
        return cache.get(key);
    }

    public void put(int key, int value) {
        cache.put(key, value);
    }

    // 双端链表节点类
    public static class Node {
        public Integer key;
        public Integer value;
        public Node next;
        public Node last;

        public Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    // 双端链表类
    public static class NodeLinkedList {
        // 头尾指针直接指向头节点和尾节点   head为空说明链表为空
        public Node head;
        public Node tail;

        // 初始化
        public NodeLinkedList() {
            this.head = null;
            this.tail = null;
        }

        // 将node节点加到链表尾部
        public void add(Node node) {
            // node为空直接返回
            if (node == null) {
                return;
            }
            // head为空说明此时链表为空
            if (head == null) {
                // 直接将头尾指针指向这个节点
                head = node;
                tail = node;
                // 链表不为空
            } else {
                // 查到链表尾部
                tail.next = node;
                node.last = tail;
                tail = node;
            }
        }

        // 移除头节点
        public void remove() {
            // 链表存在同节点
            if (head != null) {
                // 链表只有一个节点
                if (head == tail) {
                    head = null;
                    tail = null;
                    // 链表有多个节点
                } else {
                    head.next.last = null;
                    head = head.next;
                }
            }
        }

        // 将node节点移动到尾部
        public void moveNodeToTail(Node node) {
            // 如果该节点就是尾节点，直接返回
            if (tail == node) {
                return;
            }

            // 链表不为空
            if (head != null) {
                // node不是头节点
                if (node != head) {
                    // 将node从链表原位置分离
                    node.last.next = node.next;
                    node.next.last = node.last;
                    // node是头节点
                } else {
                    // 将node从链表原位置分离
                    head = head.next;
                    head.last = null;
                }

                // 将node移动到尾部
                tail.next = node;
                node.last = tail;
                node.next = null;
                tail = node;
            }
        }
    }

    // 缓存类
    public static class MyCache {
        // 记录当前链表有哪些节点
        public HashMap<Integer, Node> cacheMap;
        // 双端链表
        public NodeLinkedList nodeLinkedList;
        // 缓存大小
        public int capacity;

        // 初始化
        public MyCache(int capacity) {
            this.cacheMap = new HashMap<>();
            this.nodeLinkedList = new NodeLinkedList();
            this.capacity = capacity;
        }

        // 获取指定数据
        public int get(int key) {
            Node node = cacheMap.get(key);
            if (node != null) {
                // 因为对该数据操作了，将其移动到尾部
                nodeLinkedList.moveNodeToTail(node);
                return node.value;
            }
            // 没有该数据返回-1
            return -1;
        }

        // 将数据加入到缓存    如果已存在就更新
        public void put(int key, int value) {
            Node node = cacheMap.get(key);

            // 修改已存在的数据
            if (node != null) {
                node.value = value;
                // 因为对该数据操作了，将其移动到尾部
                nodeLinkedList.moveNodeToTail(node);
                // 该数据是新加入的
            } else {
                // 为其创建node
                node = new Node(key, value);

                // 缓存还没有满
                if (cacheMap.size() < capacity) {
                    // 直接加到尾部
                    nodeLinkedList.add(node);
                    // 加入到Map
                    cacheMap.put(key, node);
                } else {
                    // 将链表头移除，他是最久没有操作过的数据
                    // 从Map移除
                    cacheMap.remove(nodeLinkedList.head.key);
                    // 从链表移除
                    nodeLinkedList.remove();

                    // 将新数据加入
                    nodeLinkedList.add(node);
                    cacheMap.put(key, node);
                }
            }
        }
    }



    // 这个是左神写的版本，基本思路是一样的
    static class  ClassDemo {
        public ClassDemo(int capacity) {
            cache = new MyCache<>(capacity);
        }

        private MyCache<Integer, Integer> cache;

        public int get(int key) {
            Integer ans = cache.get(key);
            return ans == null ? -1 : ans;
        }

        public void put(int key, int value) {
            cache.set(key, value);
        }

        public static class Node<K, V> {
            public K key;
            public V value;
            public Node<K, V> last;
            public Node<K, V> next;

            public Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        public static class NodeDoubleLinkedList<K, V> {
            private Node<K, V> head;
            private Node<K, V> tail;

            public NodeDoubleLinkedList() {
                this.head = null;
                this.tail = null;
            }

            // 现在来了一个新的node，请挂到尾巴上去
            public void addNode(Node<K, V> newNode) {
                if (newNode == null) {
                    return;
                }
                if (head == null) {
                    head = newNode;
                    tail = newNode;
                } else {
                    tail.next = newNode;
                    newNode.last = tail;
                    tail = newNode;
                }
            }

            // node 入参，一定保证！node在双向链表里！
            // node原始的位置，左右重新连好，然后把node分离出来
            // 挂到整个链表的尾巴上
            public void moveNodeToTail(Node<K, V> node) {
                if (tail == node) {
                    return;
                }
                if (head == node) {
                    head = node.next;
                    head.last = null;
                } else {
                    node.last.next = node.next;
                    node.next.last = node.last;
                }
                node.last = tail;
                node.next = null;
                tail.next = node;
                tail = node;
            }

            public Node<K, V> removeHead() {
                if (head == null) {
                    return null;
                }
                Node<K, V> res = head;
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    head = res.next;
                    res.next = null;
                    head.last = null;
                }
                return res;
            }

        }

        public static class MyCache<K, V> {
            private HashMap<K, Node<K, V>> keyNodeMap;
            private NodeDoubleLinkedList<K, V> nodeList;
            private final int capacity;

            public MyCache(int cap) {
                keyNodeMap = new HashMap<K, Node<K, V>>();
                nodeList = new NodeDoubleLinkedList<K, V>();
                capacity = cap;
            }

            public V get(K key) {
                if (keyNodeMap.containsKey(key)) {
                    Node<K, V> res = keyNodeMap.get(key);
                    nodeList.moveNodeToTail(res);
                    return res.value;
                }
                return null;
            }

            // set(Key, Value)
            // 新增  更新value的操作
            public void set(K key, V value) {
                if (keyNodeMap.containsKey(key)) {
                    Node<K, V> node = keyNodeMap.get(key);
                    node.value = value;
                    nodeList.moveNodeToTail(node);
                } else { // 新增！
                    Node<K, V> newNode = new Node<K, V>(key, value);
                    keyNodeMap.put(key, newNode);
                    nodeList.addNode(newNode);
                    if (keyNodeMap.size() == capacity + 1) {
                        removeMostUnusedCache();
                    }
                }
            }

            private void removeMostUnusedCache() {
                Node<K, V> removeNode = nodeList.removeHead();
                keyNodeMap.remove(removeNode.key);
            }

        }
    }
}




