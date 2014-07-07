package com.learning.cxf.client.simple;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import com.learning.cxf.server.simple.Course;
import com.learning.cxf.server.simple.CourseManager;

public class CourseClient {
    public static void main(String...args) {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        factory.setServiceClass(CourseManager.class);
        factory.setAddress("http://localhost:9000/CourseManager");
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.getServiceFactory().setDataBinding(new AegisDatabinding());
        CourseManager client = (CourseManager) factory.create();
        Course course = new Course();
        course.setCode("C01");
        course.setName("English Grammer");
        client.addCourse(course);
    }
}
