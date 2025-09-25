package my_jihe.my_map;

import javax.swing.tree.TreeNode;

import org.w3c.dom.Node;

public class StudyJavaHashMap {
    // Map的底层原理
    // HashMap内部有一个内部类Node 每一个元素都是一个Node对象
    // Node类实现了Entry接口，所以称一个元素是一个Entry对象
    //     包含
    //     int hash //键的哈希值
    //     final K key
    //     V value
    //     Node<K,V> next

    // Node中第一个成员变量就是hash，即通过键计算出的哈希值，接着是键和值
    // 第四个成员变量是next，由于键不同但哈希值相同的元素会挂在老元素的下面，那么老元素就通过next记录下一个链节的地址值
    // 如果是红黑树，内部节点叫TreeNode，同样是HashMap的内部类
    //     包含
    //     int hash
    //     final K key
    //     V value
    //     TreeNode<K,V> parent
    //     TreeNode<K,V> left
    //     TreeNode<K,V> right
    //     boolean red

    // TreeNode中有parent left和right，还有prev和布尔类型的red（用来记录颜色，红true黑false）
    // TreeNode继承LinkedHashMap中的Entry，这个Entry又继承了HashMap里的Node

    // HashMap内部有一个Node数组table
    // 默认长度DEFAULT_INITIAL_CAPACITY 16
    // 默认加载因子DEFAULT_LOAD_FACTOR 0.75

    // 加载因子决定了table的扩容时机，当超过百分之七十五的时候需要扩容，扩容为原先的两倍
    // table最大容量为1<<30

    // HashMap的空参构造只会把加载因子赋给成员变量loadFactor,数组也不存在，为null

    // put方法：
    //     put方法的参数为key和value 返回覆盖元素的值，没有覆盖则返回null
    //     put方法会调用putVal，传入hash(key),key,value,onlyIfAbsent（决定键重复的数据是否会保留 ，默认为false，表示不会保留，会覆盖）,evict（默认true）
    //     hash(key)函数通过键计算出对应哈希值，并对哈希值进行一定处理，具体可参考源码
        
    //     putVal内定义了一个Node数组tab，用来记录哈希表中数组的地址值
    //     还有临时的第三方变量，Node类型的p，用来记录键值对对象的地址值
    //         当前数组长度n，索引i

    //添加元素时至少考虑三种情况
    // 数组位置为null
    // 数组位置不为null，键不重复，挂在下面形成链表或者红黑树
    // 数组位置不为null，键重复，元素覆盖

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; //用来记录哈希表中数组的地址值
        Node<K,V> p;     //用来记录键值对对象的地址值
        int n, i;       //数组长度与索引

        tab = table;    //把哈希表中数组的地址赋值给局部变量tab
        if (tab == null || (n = tab.length) == 0)
            //如果当前是第一次添加数据 底层会创建一个默认长度为16，加载因子为0.75的数组
            //如果不是第一次添加，会看数组中元素是否达到扩容条件
            //如果达到，会将数组扩容为原先的两倍，并将数据全部转到新的哈希表中
            tab = resize();
            //把当前数组的长度赋值给n
            n = tab.length;

            //拿着数组的长度跟键的哈希值进行计算，计算出当前键值对对象在数组中应存入的位置
            //然后再获取中对应元素的数据
        if ((p = tab[i = (n - 1) & hash]) == null)
            //如果数组中的对应元素是null 调用newNode
            //底层会创建一个键值对对象，直接放到数组当中
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            //p.hash == hash
            //等号的左边：数组中键值对的哈希值
            //等号的右边：当前要添加的键值对的哈希值
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
                //判断数组中获取出来的键值对是不是红黑树中的节点
                //如果是，调用putTreeVal，把当前节点按照红黑树的规则添加到树当中
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                //如果数组中获取的键值对不是红黑树，则是链表
                for (int binCount = 0; ; ++binCount) {
                    //持续循环
                    if ((e = p.next) == null) { //获取元素下面的结点，如果为空
                        //则会创建一个新的结点，挂在原节点下面
                        p.next = newNode(hash, key, value, null);
                        //判断长度是否超过8，超过会调用treeifyBin
                        //treeifyBin的底层还会继续判断
                        //判断数组的长度是否大于64
                        //如果同时满足条件，会将链表转为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //如果e为null，表示当前不需要覆盖任何元素
            //如果e不为null，表示当前的键是一样的，需要覆盖
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    //用新的值覆盖老的值
                    //说明覆盖仅改变值
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        //与并发修改异常有关
        //threshold: 记录的是数组的长度*0.75，也就是哈希表的扩容时机
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        //表示当前没有覆盖任何元素，返回null
        return null;
    }
    






}
