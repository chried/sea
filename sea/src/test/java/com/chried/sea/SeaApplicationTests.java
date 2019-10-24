package com.chried.sea;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeaApplicationTests {

    @Test
    public void contextLoads() {

        String encode = new BCryptPasswordEncoder().encode("123456");
        String encode1 = new BCryptPasswordEncoder().encode("1234sdey2w673478382356");
        System.out.println(encode1.length());
        System.out.println(encode);
    }

}
