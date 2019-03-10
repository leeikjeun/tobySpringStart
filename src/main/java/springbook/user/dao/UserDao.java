package springbook.user.dao;

import springbook.user.domain.User;

import java.util.List;

/**
 * Created by adaeng on 10/03/2019.
 * 기술에 독립적인 UserDao만들기
 *
 */
public interface UserDao {
    public void add(User user);
    public User get(String id);
    public List<User> getAll();
    public void deleteAll();
    public int getCount();
    public void update(User user);
}
