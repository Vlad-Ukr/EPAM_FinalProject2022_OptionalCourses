
package com.example.optionalcoursesfp.entity;

import java.util.List;

public class Teacher extends User {
    private int id;
    private String fullName;
    private List<Course> courseList;

    public Teacher(){}
    public Teacher(String fullName) {
        this.fullName = fullName;
    }

    public Teacher(String login, String password, UserRole role, String fullName) {
        super(login, password, role);
        this.fullName = fullName;
    }

    public Teacher(int id, String login, String fullName) {
        super(login);
        this.id = id;
        this.fullName = fullName;
    }

    public Teacher(int id, String fullName, List<Course> courseList) {
        this.id = id;
        this.fullName = fullName;
        this.courseList = courseList;
    }


    public Teacher(String login, int id, String fullName, List<Course> courseList) {
        super(login);
        this.id = id;
        this.fullName = fullName;
        this.courseList = courseList;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public  void setId(int id){
        this.id=id;
    }
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
