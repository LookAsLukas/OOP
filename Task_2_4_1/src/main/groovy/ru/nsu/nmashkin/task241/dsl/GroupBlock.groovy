package ru.nsu.nmashkin.task241.dsl

import ru.nsu.nmashkin.task241.core.Group
import ru.nsu.nmashkin.task241.core.Student

class GroupBlock {
    private final Group group

    GroupBlock(Group group) { this.group = group }

    void student(String githubNick, String fullName, String repoUrl) {
        group.addStudent(new Student(githubNick, fullName, repoUrl))
    }

    def methodMissing(String name, args) {
        if (args.length == 2) {
            group.addStudent(new Student(name, args[0], args[1]));
        }
    }
}