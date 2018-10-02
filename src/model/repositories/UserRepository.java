package model.repositories;

import model.datastructures.Occupation;
import model.datastructures.User;
import model.mappers.UserMapper;

import java.util.List;

public class UserRepository implements Repository<User> {
    private UserMapper userMapper = new UserMapper();

    @Override
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public void update(User user) {}

    @Override
    public void remove(User item) {}

    @Override
    public User get(int id) {
        return userMapper.get(id);
    }

    @Override
    public List<User> query() {
        return userMapper.getAll();
    }

    public User get(String login, String password) {
        return userMapper.get(login, password);
    }

    public List<User> getAll(Occupation occupation) {
        return userMapper.getAll(occupation);
    }

    public List<User> getAllActorsNotInPerformance(int performanceId) {
        return userMapper.getAllActorsNotInPerformance(performanceId);
    }

    public List<User> getAllActorsInPerformance(int performanceId) {
        return userMapper.getAllActorsInPerformance(performanceId);
    }
}
