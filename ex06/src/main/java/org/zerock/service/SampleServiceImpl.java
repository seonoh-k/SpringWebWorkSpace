package org.zerock.service;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService{
    @Override
    public void doAdd(String str1, String str2) throws Exception {
        System.out.println(Integer.parseInt(str1) + Integer.parseInt(str2));
    }
}
