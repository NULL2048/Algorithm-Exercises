package 大厂刷题班.class19;

import java.util.HashMap;

// 数据结构设计题
// 本题测试链接 : https://leetcode.cn/problems/lfu-cache/
// 提交时把类名和构造方法名改为 : LFUCache
public class Code02_LFUCache {
    // 节点的数据结构
    public static class Node {
        public Integer key;
        public Integer value;
        // 这个节点发生get或者set的次数总和
        public Integer times;
        // 节点之间是双向链表所以有上一个节点
        public Node up;
        // 节点之间是双向链表所以有下一个节点
        public Node down;

        public Node(int k, int v, int t) {
            key = k;
            value = v;
            times = t;
        }
    }

    // 桶结构
    public static class NodeList {
        // 桶的头节点
        public Node head;
        // 桶的尾节点
        public Node tail;
        // 桶之间是双向链表所以有前一个桶
        public NodeList last;
        // 桶之间是双向链表所以有后一个桶
        public NodeList next;

        // 初始化桶，并加入一个node
        public NodeList(Node node) {
            head = node;
            tail = node;
        }

        // 把一个新的节点加入这个桶，新的节点都放在桶内部链表的顶端，变成新的头部
        public void addNodeFromHead(Node newHead) {
            newHead.down = head;
            head.up = newHead;
            head = newHead;
        }

        // 判断这个桶是不是空的
        public boolean isEmpty() {
            return head == null;
        }

        // 删除node节点并保证node的上下环境重新连接
        public void deleteNode(Node node) {
            // 如果此时链表只有一个节点，就直接将该链表置空
            if (head == tail) {
                head = null;
                tail = null;
                // 如果此时链表有多个节点
            } else {
                // 如果此时要删除的节点是链表的头节点
                if (node == head) {
                    // 直接将head指针后移一位，并将新的头节点的up置为空
                    head = node.down;
                    head.up = null;
                    // 如果node是尾节点
                } else if (node == tail) {
                    // 直接将tail指针前移一位，并将新的尾节点的down置为空
                    tail = node.up;
                    tail.down = null;
                    // 如果node是中间节点
                } else {
                    // 最基本的链表删除节点操作
                    node.up.down = node.down;
                    node.down.up = node.up;
                }
            }
            // 将删除节点的up和down都置为空，将其和原有的结构彻底断开
            node.up = null;
            node.down = null;
        }
    }



    // 缓存的大小限制，即K
    private int capacity;
    // 缓存目前有多少个节点
    private int size;
    // 表示key(Integer)由哪个节点(Node)代表
    private HashMap<Integer, Node> records;
    // 表示节点(Node)在哪个桶(NodeList)里
    private HashMap<Node, NodeList> heads;
    // 整个结构中位于最左边的桶
    private NodeList headList;


    public Code02_LFUCache(int K) {
        capacity = K;
        size = 0;
        records = new HashMap<>();
        heads = new HashMap<>();
        headList = null;
    }

    // 这个方法是伴随着删除Node节点操作的
    // 这个函数的功能是，判断刚刚减少了一个节点的桶是不是已经空了，如果空了，需要去做桶链表的删除节点的操作。记住删除桶节点的操作一定是和删除桶内部节点操作绑定在一起的，删除了桶内部节点之后不一定会触发删除桶节点操作，但是如果没有删除桶内部节点，就一定不会有删除桶节点的操作。
    // 1）如果不空，什么也不做
    //
    // 2)如果空了，并且removeNodeList还是整个缓存结构最左的桶(headList)。
    // 删掉这个桶的同时也要让最左的桶变成removeNodeList的下一个。
    //
    // 3)如果空了，removeNodeList不是整个缓存结构最左的桶(headList)。
    // 把这个桶删除，并保证上一个的桶和下一个桶之间还是双向链表的连接方式。最基本的链表删除节点的操作
    //
    // 参数removeNodeList：刚刚减少了一个节点的桶
    // 函数的返回值表示刚刚减少了一个节点的桶是不是已经空了，空了返回true；不空返回false
    private boolean modifyHeadList(NodeList removeNodeList) {
        // 判断removeNodeList这个桶在删除了一个内部节点之后是否为空，如果为空就去要做桶链表删除接待您的操作
        if (removeNodeList.isEmpty()) {
            // 如果该桶为桶链表的头节点
            if (headList == removeNodeList) {
                // 将头桶指针向后移动一位，并且将新的头桶的last指针置为空
                headList = removeNodeList.next;
                // 要判断一下新的headList不为空，才能将新的头桶的last指针置为空（因为有可能此时只有一个桶节点）
                if (headList != null) {
                    headList.last = null;
                }
                // 如果该桶为桶链表的非头节点
            } else {
                // 就是最基本的删除链表节点的操作
                removeNodeList.last.next = removeNodeList.next;
                // 注意要判断删除节点的后继节点不为空才能进行将后继节点和前序节点连接的操作，因为删除的节点有可能是尾节点
                if (removeNodeList.next != null) {
                    removeNodeList.next.last = removeNodeList.last;
                }
            }
            // 删除了桶节点返回true（也就是刚刚删除内部节点的桶已经变为空了）
            return true;
        }
        // 没删除返回false（也就是刚刚删除内部节点的桶没有空）
        return false;
    }

    // 这个方法是伴随着添加或查询Node节点操作的
    // 函数的功能
    // node这个节点的次数+1了，这个节点原来在oldNodeList里。
    // 把node从oldNodeList删掉，然后放到次数+1的桶中
    // 整个过程既要保证桶之间仍然是双向链表，也要保证节点之间仍然是双向链表
    private void move(Node node, NodeList oldNodeList) {
        // 将node从原桶中删除
        oldNodeList.deleteNode(node);
        // preList表示次数+1的桶的前一个桶是谁
        // 如果oldNodeList删掉node之后还有节点，oldNodeList就是次数+1的桶的前一个桶
        // 如果oldNodeList删掉node之后空了，oldNodeList是需要删除的，所以次数+1的桶的前一个桶，是oldNodeList的前一个
        NodeList preList = modifyHeadList(oldNodeList) ? oldNodeList.last : oldNodeList;
        // nextList表示次数+1的桶的后一个桶是谁，这个还需要判断一下，因为oldNodeList后面的桶并不一定是次数+1的桶
        NodeList nextList = oldNodeList.next;
        // 如果后面没有桶了，就新建一个次数+1的桶
        if (nextList == null) {
            // 新建桶，并将node加入
            NodeList newList = new NodeList(node);
            // 将新建的桶加入到桶链表中，插入到preList后面，并连接好双向指针。需要判断preList不为空
            if (preList != null) {
                preList.next = newList;
            }
            newList.last = preList;
            if (headList == null) {
                headList = newList;
            }
            // 将node和桶的对应关系加入到Map中
            heads.put(node, newList);
            // 如果后面还有桶
        } else {
            // 如果后面的桶正好是node.times，就直接将node加入该桶
            if (nextList.head.times.equals(node.times)) {
                nextList.addNodeFromHead(node);
                // 将node和桶的对应关系加入到Map中
                heads.put(node, nextList);
                // 如果后面的桶不是node的times，就需要自己创建一个新桶来存放node，并且插入到preList和nextList之间
            } else {
                // 创建新桶，并插入链表。基本的链表插入节点的操作。就是要注意判断preList不为空，因为有可能当前节点会作为头节点插入
                NodeList newList = new NodeList(node);
                if (preList != null) {
                    preList.next = newList;
                }
                newList.last = preList;
                newList.next = nextList;
                nextList.last = newList;
                // 如果原本nextList节点是头节点，就需要更新headList指针
                if (headList == nextList) {
                    headList = newList;
                }
                // 将node和桶的对应关系加入到Map中
                heads.put(node, newList);
            }
        }
    }

    // 新的数据加入到内存中
    public void put(int key, int value) {
        // 如果内存为空，直接返回
        if (capacity == 0) {
            return;
        }
        // 判断内存中是否已经有该数据了
        if (records.containsKey(key)) {
            // 如果有该数据，就将该数据的次数++并更新新的value值，然后将其转移到新桶中
            Node node = records.get(key);
            node.value = value;
            node.times++;
            // 找到这个数据原本存在的桶
            NodeList curNodeList = heads.get(node);
            // 将该node移动到新的times的桶中
            move(node, curNodeList);
            // 如果数据是第一次加入到内存中
        } else {
            // 如果此时内存满了，就按照LFU的原则删除最头部的桶中的最上面的节点
            if (size == capacity) {
                Node node = headList.tail;
                // 删除最头部的桶中的最上面的节点
                headList.deleteNode(node);
                // 删除完节点判断该桶是否为空，如果为空，还需要将这个桶删除
                modifyHeadList(headList);
                // 将该节点的数据在Map中删除
                records.remove(node.key);
                heads.remove(node);
                // 内存中的数据减1
                size--;
            }

            // 为新数据创建Node，并将其加入桶
            Node node = new Node(key, value, 1);
            // 如果此时没有桶
            if (headList == null) {
                // 新建一个桶将其加入
                headList = new NodeList(node);
                // 此时有桶
            } else {
                // 如果此时桶链表的头桶的次数是1，就将该节点加入到该桶
                if (headList.head.times.equals(node.times)) {
                    headList.addNodeFromHead(node);
                    // 此时桶链表中没有次数为1的桶（因为次数都是按照桶链表顺序递增的，所以次数为1的桶只可能是头桶，如果头桶都不是次数为1，说明此时就没有次数为1的桶）
                } else {
                    // 新建次数为1的桶，并将其加入到桶链表的头部，作为新的头桶
                    NodeList newList = new NodeList(node);
                    newList.next = headList;
                    headList.last = newList;
                    headList = newList;
                }
            }
            // 将新加入的node加入到Map中
            records.put(key, node);
            heads.put(node, headList);
            // 内存数据量加1
            size++;
        }
    }

    // 获取内存中的数据
    public int get(int key) {
        // 如果内存中没有该数据，直接返回-1
        if (!records.containsKey(key)) {
            return -1;
        }
        // 从Map中获取该数据
        Node node = records.get(key);
        // 将该数据的操作次数加1
        node.times++;
        // 获取该数据的桶
        NodeList curNodeList = heads.get(node);
        // 因为数据的次数加1，所以需要将其移动到新的桶中
        move(node, curNodeList);
        // 返回该数据的值
        return node.value;
    }
}
