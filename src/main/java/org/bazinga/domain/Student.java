package org.bazinga.domain;

import java.util.List;

/**
 * student
 *
 * @author liguolin
 * @create 2018-10-24 10:33
 **/
public class Student{

    private String name;

    private String interest;

    private int score;

    public Student(String name, String interest, int score) {
        this.name = name;
        this.interest = interest;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", interests=" + interest +
                ", score=" + score +
                '}';
    }
}
