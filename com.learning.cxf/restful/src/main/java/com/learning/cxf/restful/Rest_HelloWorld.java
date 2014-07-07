package com.learning.cxf.restful;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface Rest_HelloWorld {  
	  
	  
    @GET   
    @Produces (MediaType.TEXT_PLAIN)   
    @Path("/say/{name}")   
    public String say(@PathParam("name")String name);  
      
    @GET   
    @Produces (MediaType.TEXT_PLAIN)   
    @Path("/sayHello/{name}")   
    public String sayHello(@PathParam("user")User user);  
      
    @GET   
    @Produces ({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})   
    @Path("/getList/{id}")   
    public List<User> getList(@PathParam("id")Long id);  
      
}