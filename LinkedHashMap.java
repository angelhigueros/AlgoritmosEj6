import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

class MyKeyValueEntry1<K, V> {
    private K key;
    private V value;

    public MyKeyValueEntry1(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // getters & setters
    public K getKey() {
        return this.key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return this.value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    // hashCode & equals
}

class MyMapBucket1 {
    private List<MyKeyValueEntry1> entries;

    public MyMapBucket1() {
        if (entries == null) {
            entries = new LinkedList<>();
        }
    }

    public List<MyKeyValueEntry1> getEntries() {
        return entries;
    }

    public void addEntry(MyKeyValueEntry1 entry) {
        this.entries.add(entry);
    }

    public void removeEntry(MyKeyValueEntry1 entry) {
        this.entries.remove(entry);
    }
}

public class LinkedHashMap<K, V> implements IMap<K, V> {

    private int CAPACITY = 10;
    private MyMapBucket1[] bucket;
    private int size = 0;

    public LinkedHashMap() {
        this.bucket = new MyMapBucket1[CAPACITY];
    }

    private int getHash(K key) {
        return (key.hashCode() & 0xfffffff) % CAPACITY;
    }

    private MyKeyValueEntry1 getEntry(K key) {
        int hash = getHash(key);
        for (int i = 0; i < bucket[hash].getEntries().size(); i++) {
            MyKeyValueEntry1 myKeyValueEntry1 = bucket[hash].getEntries().get(i);
            if (myKeyValueEntry1.getKey().equals(key)) {
                return myKeyValueEntry1;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        if (containsKey(key)) {
            MyKeyValueEntry1 entry = getEntry(key);
            entry.setValue(value);
        } else {
            int hash = getHash(key);
            if (bucket[hash] == null) {
                bucket[hash] = new MyMapBucket1();
            }
            bucket[hash].addEntry(new MyKeyValueEntry1<>(key, value));
            size++;
        }
    }

    public V get(K key) {
        return containsKey(key) ? (V) getEntry(key).getValue() : null;
    }

    public boolean containsKey(K key) {
        int hash = getHash(key);
        return !(Objects.isNull(bucket[hash]) || Objects.isNull(getEntry(key)));
    }

    public void delete(K key) {
        if (containsKey(key)) {
            int hash = getHash(key);
            bucket[hash].removeEntry(getEntry(key));
            size--;
        }
    }

    public int size() {
        return size;
    }
}