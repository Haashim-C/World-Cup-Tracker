package ca.sheridancollege.demo.controllers;


import ca.sheridancollege.demo.database.DatabaseAccess;
import ca.sheridancollege.demo.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TeamController {
    @Autowired
    DatabaseAccess da;

    ModelAndView mv;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/deleteTeam")
    public ModelAndView teamDelete(@ModelAttribute Team team){
        mv = new ModelAndView("/deleteTeam","displayTeams",da.getTeams());
        return mv;
    }

    @GetMapping("/editTeam")
    public ModelAndView teamEdit(@ModelAttribute Team team){
        mv = new ModelAndView("/editTeam","displayTeams",da.getTeams());
        return mv;
    }

    @PostMapping("/insertTeam")
    public String processInsertTeam(@ModelAttribute Team team){
        da.insertTeam(team);
        return "addTeam";
    }

    @GetMapping("/addTeam")
    public ModelAndView processAddTeam(){
        return mv = new ModelAndView("addTeam", "team", new Team());
    }

    @GetMapping("/displayResults")
    public String displayTeams(Model model){
        model.addAttribute("displayTeams",da.getTeams());
        return "displayTeam";
    }

    @GetMapping("/deleteTeamById/{id}")
    public ModelAndView deleteTeam(@PathVariable Long  id){
        da.deleteTeamById(id);
        mv = new ModelAndView("redirect:/deleteTeam","displayTeams",da.getTeams());
        return mv;
    }

    @PostMapping("/modifyTeam")
    public ModelAndView teamModify(@ModelAttribute Team team){

        da.editTeamById(team);
        mv = new ModelAndView("redirect:/editTeam", "team",da.getTeams());
        return mv;

    }

    @GetMapping("/updateTeamById/{id}")
    public ModelAndView teamUpdate(@PathVariable Long  id){

        Team team;
        team = da.getTeamById(id).get(0);
        mv = new ModelAndView("modifyTeam", "team",da.getTeams());
        mv.addObject("team",team);
        return mv;
    }

    @GetMapping("/deleteSearch")
    public ModelAndView searchDelete(@RequestParam String search){
        if(search != null){
            mv = new ModelAndView("/deleteTeam","displayTeams",da.getSearchTeam(search));
            return mv;
        }
        mv = new ModelAndView("/deleteTeam","displayTeams",da.getTeams());
        return mv;
    }

    @GetMapping("/editSearch")
    public ModelAndView searchEdit(@RequestParam String search){
        if(search != null){
            mv = new ModelAndView("/editTeam","displayTeams",da.getSearchTeam(search));
            return mv;
        }
        mv = new ModelAndView("/editTeam","displayTeams",da.getTeams());
        return mv;
    }

    @GetMapping("/teamSort")
    public ModelAndView sortTeam(@RequestParam String sort){
        mv = new ModelAndView("/displayTeam","displayTeams",da.getSortTeams(sort));
        return mv;
    }

}