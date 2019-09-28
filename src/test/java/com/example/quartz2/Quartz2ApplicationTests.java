package com.example.quartz2;

import com.example.quartz2.utils.JobInvokeUtils;
import com.example.quartz2.utils.SpringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Quartz2ApplicationTests {

    @Test
    public void contextLoads() {

        Object bean = SpringUtils.getBean("com.example.quartz2.model.JobEntity");
        System.out.println(bean);

        JobInvokeUtils utils=new JobInvokeUtils();
        JobInvokeUtils.invokeMethod("HelloTask","run");
    }

}
