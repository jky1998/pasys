package ntu.jky.business;

import ntu.jky.authority.LoginStaff;
import ntu.jky.bean.*;
import ntu.jky.enums.RoleName;
import ntu.jky.enums.ScoreType;
import ntu.jky.form.ScoreForm;
import ntu.jky.service.ScoreService;
import ntu.jky.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
public class ScoreBusiness {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StaffService staffService;

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

    // 显示已经评分的分数
    public Score showDepartmentScore(ScoreForm form) {
        Score score = new Score();
        // 检查是否已经评过分
        if (form.getStaffId() != null && form.getMonthly() != null) {
            Staff staff = new Staff();
            staff.setId(form.getStaffId());
            score.setMonthly(form.getMonthly());
            score.setType(ScoreType.DEPARTMENT);
            score.setStaff(staff);
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
}
