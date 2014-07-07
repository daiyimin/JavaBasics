package com.learning.cxf.server.simple;

public class CourseManagerImpl implements CourseManager {

    public void addCourse(Course course) {
        System.out.println("Course code: " + course.getCode());
        System.out.println("Course name: " + course.getName());
    }
}