package ntu.jky.business;

import ntu.jky.authority.LoginStaff;
import ntu.jky.bean.*;
import ntu.jky.enums.RoleName;
import ntu.jky.enums.ScoreType;
import ntu.jky.form.ScoreForm;
import ntu.jky.form.StaffPlanRelationQueryForm;
import ntu.jky.service.DepartmentService;
import ntu.jky.service.ScoreService;
import ntu.jky.service.StaffPlanRelationService;
import ntu.jky.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScoreBusiness {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PlanBusiness planBusiness;
    @Autowired
    private StaffPlanRelationService relationService;

    // 部门评分
    public Message addDepartmentScore(ScoreForm form) {
        LoginStaff loginStaff = LoginStaff.getInstance();
        Integer assessorId = loginStaff.getId();
        if (loginStaff.getRoleId() == RoleName.ROLE_DEPARTMENT_LEADER) {
            Score score = new Score();
            // 检查是否已经评过分
            if (form.getStaffId() != null && form.getMonthly() != null) {
                Staff staff = new Staff();
                staff.setId(form.getStaffId());
                score.setMonthly(form.getMonthly());
                score.setType(ScoreType.DEPARTMENT);
                score.setStaff(staff);
                Score existScore = scoreService.findByStaff(score);
                // 没有，添加记录
                if (existScore == null) {
                    Staff assessor = new Staff();
                    assessor.setId(assessorId);
                    score.setAssessor(assessor);
                    score.setScore(form.getScore());
                    scoreService.add(score);
                } else {
                    // 有，更新分数
                    Score updateScore = new Score();
                    updateScore.setScore(form.getScore());
                    updateScore.setId(existScore.getId());
                    scoreService.update(updateScore);
                }
            }
            return new Message(true, "评分成功！");
        } else {
            return new Message(false, "没有权限！");
        }
    }

    // 考核组评分
    public Message addAssessorScore(ScoreForm form) {
        LoginStaff loginStaff = LoginStaff.getInstance();
        Integer assessorId = loginStaff.getId();
        if (loginStaff.getRoleId() == RoleName.ROLE_ASSESSOR) {
            Score score = new Score();
            // 检查是否已经评过分
            if (form.getStaffId() != null && form.getMonthly() != null) {
                Staff staff = new Staff();
                staff.setId(form.getStaffId());
                Staff assessor = new Staff();
                assessor.setId(assessorId);
                score.setMonthly(form.getMonthly());
                score.setType(ScoreType.ASSESSOR);
                score.setAssessor(assessor);
                score.setStaff(staff);
                Score existScore = scoreService.findByStaff(score);
                // 没有，添加记录
                if (existScore == null) {
                    score.setScore(form.getScore());
                    scoreService.add(score);
                } else {
                    // 有，更新分数
                    Score updateScore = new Score();
                    updateScore.setScore(form.getScore());
                    updateScore.setId(existScore.getId());
                    scoreService.update(updateScore);
                }
            }
            return new Message(true, "评分成功！");
        } else {
            return new Message(false, "没有权限！");
        }
    }

    // 显示已经评分的分数
    public Score showScore(ScoreForm form) {
        Score score = new Score();
        LoginStaff loginStaff = LoginStaff.getInstance();
        // 检查是否已经评过分
        if (form.getStaffId() != null && form.getMonthly() != null) {
            Staff staff = new Staff();
            staff.setId(form.getStaffId());
            Staff assessor = new Staff();
            assessor.setId(loginStaff.getId());
            score.setMonthly(form.getMonthly());
            score.setType(form.getType());
            score.setStaff(staff);
            score.setAssessor(assessor);
            Score exist = scoreService.findByStaff(score);
            return exist;
        } else {
            return null;
        }
    }

    // 展示完成进度（部门评分）
    public Progress showDepartmentProgress(Date monthly) {
        Progress progress = new Progress();
        progress.setMonthly(monthly);
        // 获取部门人数
        LoginStaff loginStaff = LoginStaff.getInstance();
        Staff findStaff = new Staff();
        Department findDepartment = new Department();
        findDepartment.setId(loginStaff.getDepartmentId());
        findStaff.setDepartment(findDepartment);
        List<Staff> staffs = staffService.findAll(findStaff);
        // 获取已经评分的人数
        Score findScore = new Score();
        findScore.setMonthly(monthly);
        Staff assessor = new Staff();
        assessor.setId(loginStaff.getId());
        findScore.setAssessor(assessor);
        List<Score> scores = scoreService.findAll(findScore);
        // 百分比
        float percent;
        float s = staffs.size();
        float sc = scores.size();
        if (s != 0) {
            percent = sc / s;
        } else {
            percent = 0f;
        }
        progress.setPercent(percent);
        return progress;
    }

    // 展示完成进度（考核组评分）
    public List<Progress> showAssessorProgress(Date monthly) {
        List<Progress> progresses = new ArrayList<>();
        // 查该考核人员已经考核的数量
        LoginStaff loginStaff = LoginStaff.getInstance();
        Staff assessor = new Staff();
        assessor.setId(loginStaff.getId());
        Score score = new Score();
        score.setAssessor(assessor);
        score.setMonthly(monthly);
        List<Score> scores = scoreService.findAll(score);

        List<Department> departments = departmentService.findAll();
        for (Department department : departments) {
            Progress progress = new Progress();
            progress.setDepartment(department);
            // 根据部门查人数
            Staff staff = new Staff();
            staff.setDepartment(department);
            List<Staff> staffs = staffService.findAll(staff);
            float staffSum = staffs.size();
            float scoreSum = 0;
            // 查各部门已经评分人数
            for (Score sc : scores) {
                Staff s = sc.getStaff();
                List<Staff> staffList = staffService.findAll(s);
                if (staffList != null) {
                    s = staffList.get(0);
                }
                if (s.getDepartment().getId().equals(department.getId())) {
                    scoreSum++;
                }
            }
            float percent;
            if (staffSum != 0) {
                percent = scoreSum / staffSum;
            } else {
                percent = 0;
            }
            progress.setPercent(percent);
            progresses.add(progress);
        }
        return progresses;
    }

    // 月统计报表显示
    public List<MonthlyStatistic> showFinalScores(Date monthly) {
        List<MonthlyStatistic> list = new ArrayList<>();

        StaffPlanRelationQueryForm form = new StaffPlanRelationQueryForm();
        Calendar c = Calendar.getInstance();
        c.setTime(monthly);
        form.setYyyy(c.get(Calendar.YEAR));
        form.setMm(c.get(Calendar.MONTH) + 1);
        List<StaffPlanRelation> relations = planBusiness.showPlanQuery(form);
        for (StaffPlanRelation relation : relations) {
            MonthlyStatistic monthlyStatistic = new MonthlyStatistic();
            monthlyStatistic.setRelation(relation);

            // selfScore
            StaffPlanRelation findRelation = new StaffPlanRelation();
            findRelation.setStaff(relation.getStaff());
            findRelation.setPlan(relation.getPlan());
            List<StaffPlanRelation> selfs = relationService.findAll(findRelation);
            float selfScore = 0;
            if (selfs.size() != 0) {
                for (StaffPlanRelation r : selfs) {
                    if (r.getScore() != null) {
                        selfScore += r.getScore();
                    }
                }
            }
            if (selfScore == 0) {
                selfScore = -1;
            }
            monthlyStatistic.setSelfScore(selfScore);

            // departScore
            Score findScore = new Score();
            findScore.setStaff(relation.getStaff());
            findScore.setType(ScoreType.DEPARTMENT);
            List<Score> scoreList = scoreService.findAll(findScore);
            float departScore = -1;
            if (scoreList.size() != 0) {
                departScore = scoreList.get(0).getScore();
            }
            monthlyStatistic.setDepartScore(departScore);

            // assessorScore
            findScore.setType(ScoreType.ASSESSOR);
            scoreList = scoreService.findAll(findScore);
            float assessorScore = -1; // 表示没有评分
            if (scoreList.size() != 0) {
                assessorScore = 0;
                for (Score score : scoreList) {
                    assessorScore += score.getScore();
                }
                assessorScore = assessorScore / scoreList.size();
            }
            monthlyStatistic.setAssessorScore(assessorScore);

            // 绩效得分
            float average = 0;
            float times = 0;
            if (selfScore != -1) {
                average += selfScore;
                times += 1;
            }
            if (departScore != -1) {
                average += departScore;
                times += 1;
            }
            if (assessorScore != -1) {
                average += assessorScore;
                times += 1;
            }
            if (times != 0) {
                average = average / times;
                monthlyStatistic.setAverage(average);
            } else {
                monthlyStatistic.setAverage((float) -1);
            }

            list.add(monthlyStatistic);
        }
        return list;
    }
}
