package ntu.jky.service;

import ntu.jky.bean.Score;
import ntu.jky.dao.ScoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    @Autowired
    private ScoreDao scoreDao;

    public List<Score> findAll(Score score) {
        return scoreDao.findAll(score);
    }

    public Score findByStaff(Score score) {
        List<Score> scores = scoreDao.findAll(score);
        if (scores.size() != 0) {
            return scores.get(0);
        } else {
            return null;
        }
    }

    public void add(Score score) {
        scoreDao.add(score);
    }

    public void update(Score score) {
        scoreDao.update(score);
    }
}
