package org.bazinga.domain;

/**
 * teacher
 *
 * @author liguolin
 * @create 2018-10-24 10:30
 **/
public class Teacher {

    private int id;

    private String name;

    private String course;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
