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

    private List<String> interests;

    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
