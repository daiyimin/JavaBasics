package com.test.jar.signer.strainger;

import java.security.AccessController;  
import java.security.PrivilegedAction;  

import com.test.security.allinone.Doer;
  
public class Stranger implements Doer {  
  
    private Doer next;  
    private boolean direct;  
  
    public Stranger(Doer next, boolean direct) {  
        this.next = next;  
        this.direct = direct;  
    }  
  
    public void doYourThing() {  
        System.out.println("Im a Stranger");  
  
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