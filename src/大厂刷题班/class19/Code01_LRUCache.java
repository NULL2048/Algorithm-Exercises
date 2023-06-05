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



    // 这个是左神写的版本，基本思路是一样的。但是这个版本使用了泛型，可以适配各种数据类型。
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

// 下面这个完全是我自己写的，相对更符合我自己的思路，更容易理解
class LRUCache {
    // 双向链表节点
    class Node {
        int key;
        int value;
        Node next;
        Node pre;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // 当前缓存链表中的节点数量（分片数量）
    public int cnt;
    // 容器的大小，最多放多少个节点
    public int capacity;
    // 可以通过key来快速找到node
    public HashMap<Integer, Node> map;
    // 链表头节点和尾节点   最近使用的排在尾部，越长时间没有使用过的节点就离头部越近
    public Node head;
    public Node tail;

    // 初始化缓存
    public LRUCache(int capacity) {
        this.map = new HashMap<>();
        this.capacity = capacity;
        this.cnt = 0;
        this.head = null;
        this.tail = null;
    }

    // 根据key获取value
    public int get(int key) {
        // 存在该key
        if (map.containsKey(key)) {
            // 获取node
            Node node = map.get(key);

            // 我们此时用到了这个node，所以该node是最新使用过的，应该将其转移到链表尾部
            moveToTail(node);

            return node.value;
            // 缓存中不存在该key就返回-1
        } else {
            return -1;
        }
    }

    // 加入新的键值对
    public void put(int key, int value) {
        // 如果已经存在该key，只修改对应的value
        if (map.containsKey(key)) {
            // 获取该node
            Node node = map.get(key);
            // 需要将最新使用过的node移动到链表尾部
            moveToTail(node);
            // 修改value
            node.value = value;
            // 如果是全新的key，就将该键值对加入到缓存中
        } else {
            // 先判断是否已经满了，如果满了，则出发LRU算法，将最久未使用的节点移除
            if (cnt == capacity) {
                remove();
            }
            // 将新节点加入
            add(key, value);
            // 增加节点数量
            cnt++;
        }
    }

    // 将最新使用到的节点node移动到链表尾部
    public void moveToTail(Node node) {
        // 如果该node就是尾节点，那么就不用移动了
        if (node != tail) {
            // 该节点不是头节点，说明该节点还存在前驱节点
            if (node != head) {
                Node preNode = node.pre;
                preNode.next = node.next;
                node.next.pre = preNode;
                // 该节点如果是头节点，就不存在前驱节点了，直接将头节点后移一位即可
            } else {
                head = node.next;
                // help GC
                head.pre = null;
            }

            // 将node追加到tail后面，成为新的tail
            node.pre = tail;
            tail.next = node;
            tail = node;
        }
    }

    // 移除最久没有使用的节点，也就是移除头节点
    public void remove() {
        // 从Map中移除
        map.remove(head.key);
        // 如果此时链表不止一个节点
        if (head != tail) {
            // 将头节点后移一位
            head = head.next;
            // help GC
            head.pre = null;
            // 如果此时链表中只有一个节点
        } else {
            // 直接将head和tail设置为null即可
            head = null;
            tail = null;
        }

        // 节点数减1
        cnt--;
    }

    // 加入新的键值对
    public void add(int key, int value) {
        // 创建新节点
        Node node = new Node(key, value);

        // 如果是第一次加入键值对
        if (head == null) {
            // 创建该节点，将作为链表头尾节点
            head = node;
            tail = head;
            // 如果缓存中已经存在节点了
        } else {
            // 将其追加到链表尾部，作为新的尾节点
            tail.next = node;
            node.pre = tail;
            tail = tail.next;
        }
        // 将其加入到哈希表中
        map.put(key, node);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */




