package ru.nsu.nmashkin.task122;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Vector;

/**
 * HashTable class.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class HashTable<K, V> implements Iterable<HashTable.Pair<K, V>> {
    /**
     * Entry type for HashTable.
     *
     * @param <K> key type
     * @param <V> value type
     */
    public static class Pair<K, V> {
        K el1;
        V el2;

        /**
         * Make a pair.
         *
         * @param el1 first element
         * @param el2 second element
         */
        public Pair(K el1, V el2) {
            this.el1 = el1;
            this.el2 = el2;
        }

        /**
         * Check for equality.
         *
         * @param o the reference object with which to compare.
         * @return true if equal
         */
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(el1, pair.el1);
        }

        /**
         * Get the hash code.
         *
         * @return the hash code
         */
        @Override
        public int hashCode() {
            return Objects.hash(el1);
        }

        /**
         * Get the string representation.
         *
         * @return the string representation
         */
        @Override
        public String toString() {
            return "(" + el1 + ", " + el2 + ")";
        }
    }

    private class HashTableIterator implements Iterator<Pair<K, V>> {
        private int churchhellaInd;
        private int oreshekInd;
        private final int expectedModCount;

        /**
         * Initialize an iterator.
         */
        public HashTableIterator() {
            churchhellaInd = 0;
            oreshekInd = 0;
            expectedModCount = modCount;

            while (churchhellaInd < verevka.capacity()
                    && (verevka.elementAt(churchhellaInd) == null
                    || verevka.elementAt(churchhellaInd).isEmpty())) {
                churchhellaInd++;
            }
            if (churchhellaInd == verevka.capacity()) {
                churchhellaInd = -1;
            }
        }

        /**
         * Check if there is a next element.
         *
         * @return true if there is
         */
        @Override
        public boolean hasNext() {
            return churchhellaInd != -1;
        }

        /**
         * Get next element.
         *
         * @return the next element
         */
        @Override
        public Pair<K, V> next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException("");
            }
            if (churchhellaInd == -1) {
                throw new NoSuchElementException("");
            }

            Pair<K, V> result = verevka.elementAt(churchhellaInd).get(oreshekInd);

            oreshekInd++;
            if (oreshekInd < verevka.elementAt(churchhellaInd).size()) {
                return result;
            }

            oreshekInd = 0;
            do {
                churchhellaInd++;
            } while (churchhellaInd < verevka.capacity()
                    && (verevka.elementAt(churchhellaInd) == null
                    || verevka.elementAt(churchhellaInd).isEmpty()));
            if (churchhellaInd == verevka.capacity()) {
                churchhellaInd = -1;
            }

            return result;
        }
    }

    private Vector<List<Pair<K, V>>> verevka;
    private int count;
    private int modCount;

    private int getChurchhellaInd(K key) {
        return key.hashCode() % verevka.capacity();
    }

    private int getChurchhellaInd(K key, Vector<List<Pair<K, V>>> verevka) {
        return key.hashCode() % verevka.capacity();
    }

    private void resize() {
        if (count < 0.69 * verevka.capacity()) {
            return;
        }

        Vector<List<Pair<K, V>>> verevkaPokrepche = new Vector<>(verevka.capacity() * 2);
        for (int i = 0; i < verevkaPokrepche.capacity(); i++) {
            verevkaPokrepche.add(null);
        }
        for (List<Pair<K, V>> churchhella : verevka) {
            if (churchhella == null) {
                continue;
            }
            for (Pair<K, V> oreshek : churchhella) {
                int churchhellaPovkusneeInd = getChurchhellaInd(oreshek.el1, verevkaPokrepche);
                if (verevkaPokrepche.elementAt(churchhellaPovkusneeInd) == null) {
                    verevkaPokrepche.set(churchhellaPovkusneeInd, new LinkedList<>());
                }
                verevkaPokrepche.elementAt(churchhellaPovkusneeInd).add(oreshek);
            }
        }
        verevka = verevkaPokrepche;
    }

    /**
     * Initialize the hash table.
     */
    public HashTable() {
        verevka = new Vector<>(1);
        verevka.add(null);
        count = 0;
        modCount++;
    }

    /**
     * Add a value with a key.
     * Key must not exist in the table.
     *
     * @param key the said key
     * @param value the said value
     */
    public void add(K key, V value) {
        int churchhellaInd = getChurchhellaInd(key);
        if (verevka.elementAt(churchhellaInd) == null) {
            verevka.set(churchhellaInd, new LinkedList<>());
        } else if (contains(key)) {
            throw new HashTableException("Key " + key + "already exists");
        }

        verevka.elementAt(churchhellaInd).add(new Pair<>(key, value));
        count++;
        modCount++;
        resize();
    }

    /**
     * Remove a key with its value.
     * Key must exist in the table.
     *
     * @param key the said key
     */
    public void remove(K key) {
        int churchhellaInd = getChurchhellaInd(key);
        if (!contains(key)) {
            throw new HashTableException("Key " + key + " does not exist");
        }

        verevka.elementAt(churchhellaInd).remove(new Pair<>(key, null));
        count--;
        modCount++;
    }

    /**
     * Get the value associated with a key.
     * Key must exist in the table.
     *
     * @param key the said key
     * @return the said value
     */
    public V get(K key) {
        int churchhellaInd = getChurchhellaInd(key);
        if (!contains(key)) {
            throw new HashTableException("Key " + key + " does not exist");
        }

        int oreshekInd = verevka.elementAt(churchhellaInd).indexOf(new Pair<>(key, null));
        return verevka.elementAt(churchhellaInd).get(oreshekInd).el2;
    }

    /**
     * Update the value associated with a key.
     * Key must exist in the table.
     *
     * @param key the said key
     * @param value the new value
     */
    public void update(K key, V value) {
        int churchhellaInd = getChurchhellaInd(key);
        if (!contains(key)) {
            throw new HashTableException("Key " + key + " does not exist");
        }

        int oreshekInd = verevka.elementAt(churchhellaInd).indexOf(new Pair<>(key, null));
        verevka.elementAt(churchhellaInd).set(oreshekInd, new Pair<>(key, value));
        modCount++;
    }

    /**
     * Check if the table has an entry with a key.
     *
     * @param key the said key
     * @return true if it has
     */
    public boolean contains(K key) {
        int churchhellaInd = getChurchhellaInd(key);
        if (verevka.elementAt(churchhellaInd) == null) {
            return false;
        }
        for (Pair<K, V> oreshek : verevka.elementAt(churchhellaInd)) {
            if (oreshek.el1.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get an iterator over the table.
     *
     * @return the said iterator
     */
    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Check for equality.
     * Tables are considered equal if each entry of one table
     * has a matching entry in the other.
     *
     * @param o the reference object with which to compare.
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HashTable<K, V> hashTable = (HashTable<K, V>) o;
        if (count != hashTable.count) {
            return false;
        }

        for (Pair<K, V> oreshek : this) {
            if (!hashTable.contains(oreshek.el1)
                || hashTable.get(oreshek.el1) != oreshek.el2) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the hash code.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = Objects.hash(count);
        for (Pair<K, V> oreshek : this) {
            hash ^= Objects.hash(oreshek);
        }
        return hash;
    }

    /**
     * Get the string representation.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pair<K, V> oreshek : this) {
            sb.append(oreshek).append("\n");
        }
        return sb.toString();
    }
}
