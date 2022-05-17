package com.example.optionalcoursesfp.entity;

public enum UserRole {
    ADMIN,STUDENT,TEACHER;

    public  static UserRole determineUserRole(int userRoleNumber){
        if(userRoleNumber==1){
            return UserRole.ADMIN;
        }
        else if(userRoleNumber==2){
            return UserRole.STUDENT;
        }
        else {
            return UserRole.TEACHER;
        }

    }
    public  static int determineUserRoleNumber(UserRole userRole){
       if(userRole ==ADMIN){
           return 1;
       }else if(userRole==STUDENT){
           return 2;
       }else {
           return 3;
       }

    }

}
