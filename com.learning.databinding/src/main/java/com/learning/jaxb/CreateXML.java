package com.learning.jaxb;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
public class CreateXML {

    public static void main(String[] args) {
        ObjectFactory factory = new ObjectFactory();
        GolfCountryClub gcc = factory.createGolfCountryClub();
        GolfCourseType gctype = factory.createGolfCourseType();
        gctype.setName("The best course");
        gctype.setNumberOfHoles(BigInteger.valueOf(18));
        gcc.golfCourse = new ArrayList<GolfCourseType>();
        gcc.golfCourse.add(0, gctype);
        System.out.println(marshall(gcc));
    }

    public static String marshall(Object jaxbObject) {
        try {
            JAXBContext jc = JAXBContext.newInstance("com.learning.jaxb");
            Marshaller marshaller = jc.createMarshaller();
            Writer outputWriter = new StringWriter();
            marshaller.marshal(jaxbObject, outputWriter);
            return outputWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}