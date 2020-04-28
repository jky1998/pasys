package ntu.jky.dao;

import ntu.jky.bean.Score;

import java.util.List;

public interface ScoreDao {
    List<Score> findAll(Score score);
    void add(Score score);
    void update(Score score);
}
