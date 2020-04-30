package ntu.jky.controller;

import ntu.jky.bean.Message;
import ntu.jky.bean.Progress;
import ntu.jky.bean.Score;
import ntu.jky.bean.Staff;
import ntu.jky.business.ScoreBusiness;
import ntu.jky.business.StaffBusiness;
import ntu.jky.form.ScoreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private StaffBusiness staffBusiness;
    @Autowired
    private ScoreBusiness scoreBusiness;

    @RequestMapping("/self")
    public String self() {
        return "/score/self";
    }

    @RequestMapping("/department")
    public String department(Model model) {
        List<Staff> staffs = staffBusiness.findEvaluationStaffs();
        model.addAttribute("staffs", staffs);
        return "/score/department";
    }

    // 部门评分
    @RequestMapping(value = "/department/add", method = RequestMethod.POST)
    @ResponseBody
    public Message addScore(@RequestBody ScoreForm form) {
        return scoreBusiness.addDepartmentScore(form);
    }

    // 显示已经评过的分数
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public Score showScores(@RequestBody ScoreForm form) {
        return scoreBusiness.showScore(form);
    }

    // 查看进度
    @RequestMapping(value = "/department/progress/{monthly}", method = RequestMethod.POST)
    @ResponseBody
    public Progress showDepartmentProgress(@PathVariable Date monthly) {
        return scoreBusiness.showDepartmentProgress(monthly);
    }

    // 考核组考核
    @RequestMapping("/assessor")
    public String assessor() {
        return "/score/assessor";
    }

    // 部门评分
    @RequestMapping(value = "/assessor/add", method = RequestMethod.POST)
    @ResponseBody
    public Message addAssessorScore(@RequestBody ScoreForm form) {
        return scoreBusiness.addAssessorScore(form);
    }

    // 查看进度
    @RequestMapping(value = "/assessor/progress/{monthly}", method = RequestMethod.POST)
    @ResponseBody
    public List<Progress> showAssessorProgress(@PathVariable Date monthly) {
        return scoreBusiness.showAssessorProgress(monthly);
    }
}
