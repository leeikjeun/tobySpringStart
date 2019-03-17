package springbook.user.dao;

import springbook.user.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adaeng on 17/03/2019.
 */
public class MockUserDao implements UserDao {

    private List<User> users;
    private List<User> updated;

    public MockUserDao(List<User> users){
        this.users = users;
        this.updated = new ArrayList<>();
    }

    @Override
    public void add(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User get(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAll() {
        return this.users;
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User user) {
        updated.add(user);
    }

    public List<User> getUpdated(){
        return this.updated;
    }
}
