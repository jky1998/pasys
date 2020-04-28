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
    @RequestMapping(value = "/department/score", method = RequestMethod.POST)
    @ResponseBody
    public Message addDepartmentScore(@RequestBody ScoreForm form) {
        return scoreBusiness.addDepartmentScore(form);
    }

    // 显示已经评过的分数
    @RequestMapping(value = "/department/show", method = RequestMethod.POST)
    @ResponseBody
    public Score showDepartmentScores(@RequestBody ScoreForm form) {
        return scoreBusiness.showDepartmentScore(form);
    }

    // 查看进度
    @RequestMapping(value = "/department/progress/{monthly}", method = RequestMethod.POST)
    @ResponseBody
    public Progress showDepartmentProgress(@PathVariable Date monthly) {
        return scoreBusiness.showDepartmentProgress(monthly);
    }
}
