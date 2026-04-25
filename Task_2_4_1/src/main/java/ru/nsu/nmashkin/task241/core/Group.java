package ru.nsu.nmashkin.task241.core;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @param name .
 * @param students .
 */
public record Group(String name, List<Student> students) {
    /**
     * .
     *
     * @param name .
     */
    public Group(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * .
     *
     * @param student .
     */
    public void addStudent(Student student) {
        students.add(student);
    }
}
