package service;

import dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TransactionManagerService {
    @Autowired
    private LoginDao loginDao;

    public void insertIntoDB(String name, String password, long l) throws IOException {
        loginDao.insertIntoDB(name, password, l);
    }
}
