package com.Phronesis.Service;

import org.springframework.stereotype.Service;

@Service
public class ServiceImplementation implements ServiceInterface {

    @Override
    public boolean checkHealth() {
        return true;
    }

}
