package my_jihe.my_map;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TransferQueue;

public class StudyJavaTreeMap<K,V> {
    //TreeMap中每个结点的内部属性
    K key ;
    V value;
    Entry<K,V> left;
    Entry<K,V> right;
    Entry<K,V> parent;
    boolean color;

    //TreeMap类中要知道的一些成员变量
    //比较器对象
    private final Comparator<? super K> comparator;
    //根节点
    private transient Entry<K,V> root;
    private transient int size = 0;

    //空参构造 --- 没有传递比较器对象
    public TreeMap(){
        comparator = null;
    }
    //带参构造 --- 传递了比较器对象
    public TreeMap(Comparator<? super K> comparator){
        this.comparator = comparator;
    }
    //添加元素
    public V put(K key, V value) {
        return put(key, value, true);
    }
    //参数3 --- 当键重复的时候，是否需要覆盖值 true时覆盖 false时不覆盖，与HashMap刚好相反
    private V put(K key, V value, boolean replaceOld) {
        //获得根节点的地址值，赋值给局部变量t
        Entry<K,V> t = root;
        //判断根节点是否为null 是则为 第一次添加，会把当前添加的元素当做根节点
        if (t == null) {
            //方法核心为创建一个Entry对象，然后赋值给root
            addEntryToEmptyMap(key, value);
            //null表示此时没有覆盖任何元素
            return null;
        }
        //表示两个元素的键比较之后的结果
        int cmp;
        //当前要添加节点的父节点
        Entry<K,V> parent;
        // split comparator and comparable paths
        //表示当前比较规则
        //如果采取默认的自然排序，那么此时comparator记录的是null，cpr记录的也是null
        //如果我们采取的是比较器排序方式，那么comparator记录的就是比较器
        Comparator<? super K> cpr = comparator;
        //判断当前是否有比较器对象
        //如果有执行if里的代码，此时以比较器的规则为准
        //如果没有，就执行else里面的代码，以自然排序规则为准

        if (cpr != null) {
            do {
                parent = t;
                cmp = cpr.compare(key, t.key);
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else {
                    V oldValue = t.value;
                    //覆盖判断逻辑
                    if (replaceOld || oldValue == null) {
                        t.value = value;
                    }
                    return oldValue;
                }
            } while (t != null);
        } else {
            Objects.requireNonNull(key);
            @SuppressWarnings("unchecked")
            //将键强转成Comparable类型 要求键必须实现Comparable接口
            //如果没有实现，强转的时候就会报错
            Comparable<? super K> k = (Comparable<? super K>) key;

            do {
                //把根节点当做当前节点的父节点
                parent = t;
                //调用compareTo方法，比较根节点和当前节点的大小关系
                cmp = k.compareTo(t.key);
                if (cmp < 0)
                    //如果是负数，到根节点的左侧找
                    t = t.left;
                else if (cmp > 0)
                    //如果是正数，到右侧去找
                    t = t.right;
                else {
                    //为0会覆盖
                    V oldValue = t.value;
                    if (replaceOld || oldValue == null) {
                        t.value = value;
                    }
                    return oldValue;
                }
            } while (t != null);
        }
        //当连续传进键为1,2,3的元素会发现树很显然不符合红黑树规则，所以调用addEntry进行调整
        addEntry(key, value, parent, cmp < 0);
        return null;
    }
    private void addEntry(K key, V value, Entry<K, V> parent, boolean addToLeft) {
        Entry<K,V> e = new Entry<>(key, value, parent);
        //添加节点至对应位置
        if (addToLeft)
            parent.left = e;
        else
            parent.right = e;
        //调用方法进行调整
        fixAfterInsertion(e);
        size++;
        modCount++;
    }

    private void fixAfterInsertion(Entry<K,V> x) {
        //第一件事就是把结点变成红色
        x.color = RED;
        //按照红黑规则进行调整
        //循环判断，如果x是根，循环结束，走最后一行将颜色变黑

        while (x != null && x != root && x.parent.color == RED) {
            //parentOf表示获取x的父节点
            //那么parentOf(parentOf(x))表示获取x的爷爷节点
            //那么leftOf(parentOf(parentOf(x))表示获取爷爷左子节点
            //所以这个判断意在确认x的父结点是爷爷节点的左子结点还是右子节点
            //目的是获取当前节点的叔叔节点
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                //确认父节点对于爷爷节点的位置
                //并获取叔叔节点
                Entry<K,V> y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    //叔叔节点为红色
                    setColor(parentOf(x), BLACK);//父设为黑
                    setColor(y, BLACK);//叔叔设为黑
                    setColor(parentOf(parentOf(x)), RED);//设爷爷为红
                    x = parentOf(parentOf(x));//转到爷爷节点继续进行判断
                } else {
                    //叔叔节点为黑色
                    //判断当前节点是父亲节点的左结点还是右节点
                    if (x == rightOf(parentOf(x))) {
                        //当前节点为右节点
                        //将父节点作为当前节点
                        x = parentOf(x);
                        //进行左旋，然后再进行判断
                        rotateLeft(x);
                    }
                    //将父节点设置为黑色
                    setColor(parentOf(x), BLACK);
                    //祖父结点变为红色
                    setColor(parentOf(parentOf(x)), RED);
                    //以祖父为支点进行右旋
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                //同理
                Entry<K,V> y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        //结点转为黑色
        root.color = BLACK;
    }
    //阅读源码后问题思考
    //1 TreeMap添加元素的时候，键是否需要重写hashCode和equals方法
            不需要 因为TreeMap方法在put的时候并没有使用hashCode和equals方法

    //2 HashMap是哈希表结构，JDK8开始由数组，链表，红黑树组成
    //  a 既然有红黑树，HashMap是否需要利用Comparable指定排序规则？
    //  b 是否需要传递比较器Comparator指定比较规则？
            都是不需要的，因为在HashMap的底层默认是用哈希值的大小关系创建红黑树
            源码中如果待添加结点是树节点，那么就会使用putTreeVal方法添加
            这个方法中有一个find方法，阅读就会发现实际是用哈希值比较的
    //3 TreeMap和HashMap谁的效率更高？
            如果是最坏情况，添加了八个元素，这八个元素形成了链表，此时TreeMap效率更高
            但是这种情况几率很小
            一般而言，还是HashMap效率更高 

    //4 你觉得在Map集合中 Java会提供一个如果键重复了，不会覆盖的put方法吗
            会的会的 就是因为java既然设置了一个开关onlyIfAbsent，就是一定可以被扳动的
            事实上也确实有一个名为putIfAbsent的方法可以在建重复时不进行覆盖
            同时也传递一个思想，代码中的逻辑都有两面性，如果我们只知道其中的A面，而且代码中还有变量可以控制两面性
            那么该逻辑一定会有B面
            习惯： 如果使用boolean类型的变量进行控制，一般只有AB两面，因为boolean只有两个值
                    如果使用int类型进行控制，一般至少有三面，因为int有多个值
    //5 三种双列集合，以后如何选择
            HashMap 默认使用，效率 最高
            LinkedHashMap 需要保证存取有序的时候用
            TreeMap 需要进行排序的时候使用

}
