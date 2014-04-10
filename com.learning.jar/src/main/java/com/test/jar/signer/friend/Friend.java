package com.test.jar.signer.friend;

import java.security.AccessController;  
import java.security.PrivilegedAction;  

import com.test.security.allinone.Doer;

  
  
public class Friend implements Doer{  
    private Doer next;  
    private boolean direct;  
      
    public Friend(Doer next,boolean direct){  
        this.next=next;  
        this.direct=direct;  
    }

    public void doYourThing() {  
        System.out.println("Im a Friend");  
        if (direct) {  
            next.doYourThing();  
        } else {  
            AccessController.doPrivileged(new PrivilegedAction() {  
                public Object run() {  
                    next.doYourThing();  
                    return null;  
                }  
  
            });  
  
        }  
    }  
}  