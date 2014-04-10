package com.test.security.allinone;

import com.test.jar.signer.friend.Friend;
import com.test.jar.signer.strainger.Stranger;

public class TestSecurityPolicy2 {
    /**
     * 1. add following VM arguments<BR/>
     * -Djava.security.manager  -Djava.security.policy=src/main/java/com/test/security/allinone/java.policy<BR/>
     * 2. put the keystore in the root directory of this project<BR/>
     * for example: C:\Ericsson\maworkspace\com.learning.java\mykey.store<BR/>
     * 3. don't test the read authority of files that put under the root directory of this project<BR/>
     * By default, JVM will grant your application the authority to read files under the project root directory<BR/>
     * So you always can read them no matter how you change your policy file. I waste a lot of time on it.
     * @param args
     */
    public static void main(String[] args) {
        TextFileDisplayer tfd = new TextFileDisplayer("c:/temp/answer.txt");  
        tfd.doYourThing();
        Friend friend = new Friend(tfd, true);
        friend.doYourThing();
        Stranger stranger = new Stranger(friend,true);  
        stranger.doYourThing();
    }  

}
