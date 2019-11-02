package service;

import dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginService {
    @Autowired
    public  LoginDao loginDao;
    public boolean isValidUser(String name, String password) throws IOException {
        return loginDao.isValidUser(name,password);
    }
}
