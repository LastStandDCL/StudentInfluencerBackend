package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.mapper.SchoolMapper;
import com.backend.last_stand.mapper.TeamMapper;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/18 12:19
 */
@Service
public class TeamServiceImpl  extends ServiceImpl<TeamMapper, Team> implements TeamService {

    private static final Integer ROLE_CAPTAIN = 1;

    private static final Integer ROLR_MEMBER = 0;

    private final TeamMapper teamMapper;

    private final SchoolMapper schoolMapper;

    @Autowired
    @SuppressWarnings("all")
    public TeamServiceImpl(TeamMapper teamMapper, SchoolMapper schoolMapper){
        this.schoolMapper = schoolMapper;
        this.teamMapper = teamMapper;
    }

    @Override
    public ResponseResult getTeamMembers(Long id) {
        List<User> teamMembers = teamMapper.getTeamMembers(id);
        if (teamMembers == null) {
            throw new RuntimeException("队伍中人员为空");
        }
        return new ResponseResult(200, "获取队伍成员成功", teamMembers);
    }

    @Override
    public ResponseResult getSchool(Long id) {
        School school = teamMapper.getSchool(id);
        if (school == null) {
            throw new RuntimeException("队伍所属学校为空");
        }
        return new ResponseResult(200, "获取队伍所属成功", school);
    }

    @Override
    public ResponseResult getTeamInfo(Long id) {
        Team team = teamMapper.selectById(id);
        School school = teamMapper.getSchool(id);
        List<User> teamMembers = teamMapper.getTeamMembers(id);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("teamName", team.getTeamName());
        hashMap.put("school", school.getSchoolName());
        hashMap.put("members", teamMembers.toString());
//        hashMap.put("createTime", team.getCreateTime().toString());

        return new ResponseResult(200, "返回队伍相关信息", hashMap);
    }

    @Override
    public ResponseResult createTeam(Team team) {
        if (team == null) {
            throw new RuntimeException("插入队伍信息为空");
        }
        team.setCreateTime(new Date());
        int insert = teamMapper.insert(team);
        if (insert != 1) {
            throw new RuntimeException("插入队伍信息失败");
        }
        return new ResponseResult(200, "创建队伍成功");
    }

    @Override
    public ResponseResult deleteTeam(Team team) {
        int i = teamMapper.deleteById(team);
        if (i != 1) {
            throw new RuntimeException("删除队伍失败");
        }
        return new ResponseResult(200, "删除队伍成功");
    }

    @Override
    public ResponseResult updateTeam(Team team) {
        int i = teamMapper.updateById(team);
        if (i != 1) {
            throw new RuntimeException("更新队伍失败");
        }
        return new ResponseResult(200, "更新队伍成功");
    }

    @Override
    public ResponseResult getTeamBySchoolName(String schoolName) {
        JSONObject jsonObject = JSONObject.parseObject(schoolName);
        String string = jsonObject.get("schoolName").toString();
        StringBuilder newname = new StringBuilder("%");
        for (int i = 0; i < string.length(); i++) {
            newname.append(string.charAt(i)).append("%");
        }
        List<Team> teamBySchoolName = teamMapper.getTeamBySchoolName(newname.toString());
        if (teamBySchoolName == null) {
            throw new RuntimeException("返回队伍值为空");
        }
        HashMap<String, String> hashMap = new HashMap<>();
        int total = teamBySchoolName.size();
        hashMap.put("total", Integer.toString(total));

        List<HashMap<String, Object>> list = new ArrayList<>();
        for (Team team : teamBySchoolName) {
            HashMap<String,Object> mp = new HashMap<>();
            School school = teamMapper.getSchool(team.getId());

            mp.put("teamId", team.getId());//队伍ID
            mp.put("year", team.getYear());//队伍年份
            mp.put("province", school.getProvince());//队伍省份
            mp.put("teamName", team.getTeamName());//队伍名称
            mp.put("schoolName", school.getSchoolName());//队伍的学校名称
            mp.put("captainId", teamMapper.getTeamCaptainIdByTeamId(team.getId()));//队长ID


            //队伍成员
            List<User> teamMembers = teamMapper.getTeamMembers(team.getId());
            List<HashMap<String, String>> list1 = new ArrayList<>();

            for (User user : teamMembers) {
                HashMap<String, String> hashMap1 = new HashMap<>();
                hashMap1.put("name", user.getName());//成员名
                hashMap1.put("avatar", user.getAvatar());//成员头像
                hashMap1.put("role", getUserTeamRole(user.getId(), team.getId()));//成员的队伍内角色
                list1.add(hashMap1);
            }
            mp.put("members", list1.toString());

            list.add(mp);
        }
        hashMap.put("teams", list.toString());
        ResponseResult result = new ResponseResult();
        result.setCode(200);
        result.setMsg("根据学校返回队伍名称成功");
        result.setData(hashMap);
        return result;
    }

    /**
     * 私有方法用于获取队内用户角色
     * @param userId
     * @param teamId
     * @return
     */
    private String getUserTeamRole(Long userId, Long teamId) {
        Integer role = teamMapper.getUserTeamRole(userId, teamId);
        if(role == ROLE_CAPTAIN){
            return "captain";
        }else {
            return "member";
        }
    }

    @Override
    public ResponseResult getTeamByYear(String year) {
        //获取前端传过来的年份
        JSONObject jsonObject = JSON.parseObject(year);
        String year1 = jsonObject.get("year").toString();

        System.out.println(year1);

        //根据年份查找队伍
        List<Team> teamByYear = teamMapper.getTeamByYear(year1);

        //设置返回信息
        HashMap<String, Object> hashMap = new HashMap<>();
        int total = teamByYear.size();
        hashMap.put("total", Integer.toString(total));
        //存储数组结果
        List<HashMap<String, Object>> array = new ArrayList<>();
        for (Team team : teamByYear) {
            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("teamName", team.getTeamName());
            hashMap1.put("teamId", team.getId());//队伍ID
            hashMap1.put("captainId", teamMapper.getTeamCaptainIdByTeamId(team.getId()));//队长ID

            Long schoolId = team.getSchoolId();
            //学校信息，省份，年份
            School school = schoolMapper.selectById(schoolId);
            hashMap1.put("schoolName", school.getSchoolName());
            hashMap1.put("province", school.getProvince());
            hashMap1.put("year", team.getYear());

            //成员信息
            List<User> teamMembers = teamMapper.getTeamMembers(team.getId());
            //存储学生信息的数组
            List<HashMap<String, String>> studentinfos = new ArrayList<>();
            for (User user : teamMembers) {
                //用户姓名
                String name = user.getName();
                //用户头像url
                String url = user.getAvatar();
                HashMap<String, String> student = new HashMap<>();
                student.put("name", name);
                student.put("avatar", url);
                student.put("role", getUserTeamRole(user.getId(), team.getId()));//成员的队伍内角色
                studentinfos.add(student);
            }
            hashMap1.put("members", studentinfos);
            array.add(hashMap1);
        }

        hashMap.put("info", array);

        ResponseResult result = new ResponseResult();
        result.setCode(200);
        result.setMsg("根据年份返回队伍名称成功");
        result.setData(hashMap);
        return result;
    }

    @Override
    public ResponseResult countByYear(Map<String,Object> map) {

//        JSONObject jsonObject = JSON.parseObject(year);
//        System.out.println(jsonObject + "----------------");
//        //获取年份
//        String years = jsonObject.get("year").toString();
//        JSONArray data = jsonObject.getJSONArray("data");
//
//        List<Map> javaList = data.toJavaList(Map.class);
//        List<HashMap<String, String>> list = new ArrayList<>();
//        for (Map m : javaList) {
//            //获取省份名字
//            String provinceName = m.get("provinceName").toString();
//            //根据年份返回队伍列表
//            List<Team> teamByYear = teamMapper.getTeamByYearAndProvince(years, provinceName);
//            Integer total = teamByYear.size();
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("total", total.toString());
//            hashMap.put("province", provinceName);
//            list.add(hashMap);
//        }

        List<HashMap<String, String>> list = new ArrayList<>();
        String years = map.get("year").toString();
        Object data = map.get("data");
        List<Object> provinces = JSON.parseArray(JSON.toJSONString(data));

        for (Object o : provinces) {
            String province = o.toString();
            JSONObject jsonObject = JSON.parseObject(province);
            String newpro = jsonObject.get("provinceName").toString();
            List<Team> teamByYear = teamMapper.getTeamByYearAndProvince(years, newpro);
            int total = teamByYear.size();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("total", Integer.toString(total));
            hashMap.put("province", newpro);
            list.add(hashMap);
        }


        return new ResponseResult(200, "返回参与队伍数量统计成功", list);
    }

    /**
     * 通过年份和学校名称查找队伍
     * @param info
     * @return
     */
    @Override
    public ResponseResult getTeamByYearAndSchoolName(HashMap<String,Object> info) {

        //获取前端传过来的年份和学校名称
        String year = info.get("year").toString();
        String schoolName = info.get("schoolName").toString();
        StringBuilder newName = new StringBuilder("%");
        for (int i = 0; i < schoolName.length(); i++) {
            newName.append(schoolName.charAt(i)).append("%");
        }
        List<Team> teamByYearAndSchoolName = teamMapper.getTeamByYearAndSchoolName(year, newName.toString());

        //设置返回信息
        HashMap<String, Object> hashMap = new HashMap<>();
        int total = teamByYearAndSchoolName.size();
        hashMap.put("total", Integer.toString(total));
        //存储数组结果
        List<HashMap<String, Object>> array = new ArrayList<>();
        for (Team newTeam : teamByYearAndSchoolName) {
            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("teamName", newTeam.getTeamName());
            hashMap1.put("teamId", newTeam.getId());//队伍ID
            hashMap1.put("captainId", teamMapper.getTeamCaptainIdByTeamId(newTeam.getId()));//队长ID

            Long schoolId = newTeam.getSchoolId();
            //学校信息，省份，年份
            School school = schoolMapper.selectById(schoolId);
            hashMap1.put("schoolName", school.getSchoolName());
            hashMap1.put("province", school.getProvince());
            hashMap1.put("year", newTeam.getYear());

            //成员信息
            List<User> teamMembers = teamMapper.getTeamMembers(newTeam.getId());
            //存储学生信息的数组
            List<HashMap<String, Object>> studentinfos = new ArrayList<>();
            for (User user : teamMembers) {
                //用户姓名
                String name = user.getName();
                //用户头像url
                String url = user.getAvatar();
                HashMap<String, Object> student = new HashMap<>();
                student.put("name", name);
                student.put("avatar", url);
                student.put("role", getUserTeamRole(user.getId(), newTeam.getId()));//成员的队伍内角色
                studentinfos.add(student);
            }
            hashMap1.put("members", studentinfos);
            array.add(hashMap1);
        }

        hashMap.put("info", array);

        System.out.println(hashMap.size());

        return new ResponseResult(200,"根据年份和学校名称返回队伍名称成功",hashMap);
    }
}
