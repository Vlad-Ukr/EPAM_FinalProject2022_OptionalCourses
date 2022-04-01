package com.example.optionalcoursesfp.entity;

public class FinishedCourse {
    private int id;
    private String name;
    private int duration;
    private String topic;
    private String status;
    private String teacherFullName;
    private String StudentFullName;
    private int mark;


    public FinishedCourse(int id, String name, int duration, String topic, String status, String teacherFulName, int mark) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.topic = topic;
        this.status = status;
        this.teacherFullName = teacherFulName;
        this.mark = mark;
    }

    public FinishedCourse(int id, String name, int duration, String topic, String status, String teacherFullName, String studentFullName, int mark) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.topic = topic;
        this.status = status;
        this.teacherFullName = teacherFullName;
        StudentFullName = studentFullName;
        this.mark = mark;
    }

    public String getStudentFullName() {
        return StudentFullName;
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

    public String getTopic() {
        return topic;
    }


    public String getStatus() {
        return status;
    }


    public String getTeacherFullName() {
        return teacherFullName;
    }

    public int getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "FinishedCourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", topic='" + topic + '\'' +
                ", status='" + status + '\'' +
                ", teacherFullName='" + teacherFullName + '\'' +
                ", StudentFullName='" + StudentFullName + '\'' +
                ", mark=" + mark +
                '}';
    }
}
