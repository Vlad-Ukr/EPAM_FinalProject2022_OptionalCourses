package com.example.optionalcoursesfp.entity;



public class Student extends User {
    private int id;
    private String status;
    private String fullName;
    private int firstCourseId;
    private int secondCourseId;
    private int thirdCourseId;
    private int firstCourseMark;
    private int secondCourseMark;
    private int thirdCourseMark;

    public Student(){}

    public Student(String login, int id, String fullName, String status, int firstCourseId, int secondCourseId, int thirdCourseId, int firstCourseMark, int secondCourseMark, int thirdCourseMark) {
        super(login);
        this.id = id;
        this.status = status;
        this.fullName = fullName;
        this.firstCourseId = firstCourseId;
        this.secondCourseId = secondCourseId;
        this.thirdCourseId = thirdCourseId;
        this.firstCourseMark = firstCourseMark;
        this.secondCourseMark = secondCourseMark;
        this.thirdCourseMark = thirdCourseMark;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFirstCourseId() {
        return firstCourseId;
    }

    public void setFirstCourseId(int firstCourseId) {
        this.firstCourseId = firstCourseId;
    }

    public int getSecondCourseId() {
        return secondCourseId;
    }

    public void setSecondCourseId(int secondCourseId) {
        this.secondCourseId = secondCourseId;
    }

    public int getThirdCourseId() {
        return thirdCourseId;
    }

    public void setThirdCourseId(int thirdCourseId) {
        this.thirdCourseId = thirdCourseId;
    }

    public int getFirstCourseMark() {
        return firstCourseMark;
    }

    public void setFirstCourseMark(int firstCourseMark) {
        this.firstCourseMark = firstCourseMark;
    }

    public int getSecondCourseMark() {
        return secondCourseMark;
    }

    public void setSecondCourseMark(int secondCourseMark) {
        this.secondCourseMark = secondCourseMark;
    }

    public int getThirdCourseMark() {
        return thirdCourseMark;
    }

    public void setThirdCourseMark(int thirdCourseMark) {
        this.thirdCourseMark = thirdCourseMark;
    }

    public boolean isTheStudentAlreadyRegistered(int courseId){
        if(this.getFirstCourseId()==courseId){
            return true;
        }else if(this.getSecondCourseId()==courseId){
            return true;
        }else return this.getThirdCourseId() == courseId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", fullName='" + fullName + '\'' +
                ", firstCourseId=" + firstCourseId +
                ", secondCourseId=" + secondCourseId +
                ", thirdCourseId=" + thirdCourseId +
                ", firstCourseMark=" + firstCourseMark +
                ", secondCourseMark=" + secondCourseMark +
                ", thirdCourseMark=" + thirdCourseMark +
                '}';
    }
}
