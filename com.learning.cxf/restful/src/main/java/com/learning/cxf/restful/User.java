package com.learning.cxf.restful;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="User")  
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlType(name="User")  
public class User {  
      
    @XmlElement(nillable=true)  
    private Long id;  
      
    @XmlElement(nillable=true)  
    private String name;  
      
    @XmlElement(nillable=true)  
    private int age;  
  
/*    @XmlMimeType("application/octet-stream")  
    private DataHandler imageData;  //ÈËÔ±µÄÍ¼Ïñ  
*/      
    public User() {}  
      
    public User(Long id, String name, int age) {  
        super();  
        this.id = id;  
        this.name = name;  
        this.age = age;  
    }  
  
    public Long getId() {  
        return id;  
    }  
  
    public void setId(Long id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public int getAge() {  
        return age;  
    }  
  
    public void setAge(int age) {  
        this.age = age;  
    }  
  
/*    public DataHandler getImageData() {  
        return imageData;  
    }  
  
    public void setImageData(DataHandler imageData) {  
        this.imageData = imageData;  
    }  
*/      
}  