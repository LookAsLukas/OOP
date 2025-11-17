package ru.nsu.nmashkin.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HashTableTest {

    @Test
    void add() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertThrows(HashTableException.class, () -> ht.add(2, 3));
    }

    @Test
    void remove_was_removed() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        ht.remove(2);
        assertThrows(HashTableException.class, () -> ht.remove(2));
    }

    @Test
    void remove_never_existed() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertThrows(HashTableException.class, () -> ht.remove(3));
    }

    @Test
    void get() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertEquals(2, ht.get(2));
    }

    @Test
    void get_non_existent() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertThrows(HashTableException.class, () -> ht.get(3));
    }

    @Test
    void update() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        ht.update(1, 1);
        HashTable<Integer, Integer> e = new HashTable<>();
        e.add(1, 1);
        e.add(2, 2);
        assertEquals(e, ht);
    }

    @Test
    void update_non_existent() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertThrows(HashTableException.class, () -> ht.update(3, 3));
    }

    @Test
    void contains() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertTrue(ht.contains(1));
        assertFalse(ht.contains(3));
    }

    @Test
    void equals_null() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertFalse(ht.equals(null));
    }

    @Test
    void equals() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        HashTable<Integer, Integer> htt = new HashTable<>();
        htt.add(1, 2);
        htt.add(2, 2);
        assertTrue(ht.equals(htt));
    }

    @Test
    void equals_not() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        HashTable<Integer, Integer> htt = new HashTable<>();
        htt.add(1, 2);
        assertFalse(ht.equals(htt));
    }

    @Test
    void equals_different() {
        HashTable<Integer, String> ht = new HashTable<>();
        ht.add(0, "");
        HashTable<Integer, Integer> htt = new HashTable<>();
        htt.add(0, 0);
        assertFalse(ht.equals(htt));
    }

    @Test
    void to_string() {
        HashTable<Integer, Integer> ht = new HashTable<>();
        ht.add(1, 2);
        ht.add(2, 2);
        assertTrue(ht.toString().equals("(1, 2)\n(2, 2)\n")
                || ht.toString().equals("(2, 2)\n(1, 2)\n"));
    }
}