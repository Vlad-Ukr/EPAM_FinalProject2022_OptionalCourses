package com.example.optionalcoursesfp.entity;

import java.io.Serializable;

public class Course implements Serializable{
    private int id;
    private String name;
    private int duration;
    private int amountOfStudent;
    private int maxAmountOfStudent;
    private String topic;
    private int teacherId;
    private String status;
    private  String teacherFullName;

    public Course() {
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public Course(int id, String name, int duration, int amountOfStudent, int maxAmountOfStudent, String topic, int teacherId, String status, String teacherFullName) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.amountOfStudent = amountOfStudent;
        this.maxAmountOfStudent = maxAmountOfStudent;
        this.topic = topic;
        this.teacherId = teacherId;
        this.status = status;
        this.teacherFullName = teacherFullName;
    }

    public Course(int id, String name, int duration, int amountOfStudent, int maxAmountOfStudent, String topic, int teacher_id, String status) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.amountOfStudent = amountOfStudent;
        this.maxAmountOfStudent=maxAmountOfStudent;
        this.topic = topic;
        this.teacherId = teacher_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmountOfStudent() {
        return amountOfStudent;
    }

    public String getTopic() {
        return topic;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getStatus() {
        return status;
    }

    public int getMaxAmountOfStudent() {
        return maxAmountOfStudent;
    }

    public void setMaxAmountOfStudent(int maxAmountOfStudent) {
        this.maxAmountOfStudent = maxAmountOfStudent;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", amountOfStudent=" + amountOfStudent +
                ", topic='" + topic + '\'' +
                ", teacher_id=" + teacherId +
                ", status='" + status + '\'' +
                '}';
    }

}
