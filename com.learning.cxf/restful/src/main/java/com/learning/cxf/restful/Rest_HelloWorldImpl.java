package com.learning.cxf.restful;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("rest_HelloWorldImpl")  
public class Rest_HelloWorldImpl implements Rest_HelloWorld {  
  
    public String say(String name) {  
            return name+"YuanYuan";  
    }  
  
    public String sayHello(User user) {  
        return user.getName()+"JueJue";  
    }  
      
    public List<User> getList(Long id){  
        List<User> list = new ArrayList<User>();  
          
        Long sid=1L;  
        User user = new User(sid,"QinQing"+sid,21);  
        list.add(user);  
          
        sid=2L;  
        user = new User(sid,"JieJie"+sid,21);  
        list.add(user);  
          
        sid=3L;  
        user = new User(sid,"MinMin"+sid,21);  
        list.add(user);  
        return list;  
    }  
}  