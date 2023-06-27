package com.backend.last_stand.service.impl;

import cn.hutool.core.lang.hash.Hash;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.mapper.SchoolMapper;
import com.backend.last_stand.mapper.TeamMapper;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.ptg.AreaErrPtg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/18 12:19
 */
@Service
public class TeamServiceImpl  extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public ResponseResult getTeamMembers(Long id) {
        List<User> teamMembers = teamMapper.getTeamMembers(id);
        if (teamMembers == null) {
            throw new RuntimeException("队伍中人员为空");
        }
        return new ResponseResult<>(200, "获取队伍成员成功", teamMembers);
    }

    @Override
    public ResponseResult getSchool(Long id) {
        School school = teamMapper.getSchool(id);
        if (school == null) {
            throw new RuntimeException("队伍所属学校为空");
        }
        return new ResponseResult<>(200, "获取队伍所属成功", school);
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

        return new ResponseResult<>(200, "返回队伍相关信息", hashMap);
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
        return new ResponseResult<>(200, "创建队伍成功");
    }

    @Override
    public ResponseResult deleteTeam(Team team) {
        int i = teamMapper.deleteById(team);
        if (i != 1) {
            throw new RuntimeException("删除队伍失败");
        }
        return new ResponseResult<>(200, "删除队伍成功");
    }

    @Override
    public ResponseResult updateTeam(Team team) {
        int i = teamMapper.updateById(team);
        if (i != 1) {
            throw new RuntimeException("更新队伍失败");
        }
        return new ResponseResult<>(200, "更新队伍成功");
    }

    @Override
    public ResponseResult getTeamBySchoolName(String schoolName) {
        JSONObject jsonObject = JSONObject.parseObject(schoolName);
        String string = jsonObject.get("schoolName").toString();
        String newname = "%";
        for (int i = 0; i < string.length(); i++) {
            newname += string.charAt(i) + "%";
        }
        List<Team> teamBySchoolName = teamMapper.getTeamBySchoolName(newname);
        if (teamBySchoolName == null) {
            throw new RuntimeException("返回队伍值为空");
        }
        HashMap<String, String> hashMap = new HashMap<>();
        Integer total = teamBySchoolName.size();
        hashMap.put("total", total.toString());

        List<HashMap<String, String>> list = new ArrayList<>();
        for (Team team : teamBySchoolName) {
            HashMap<String,String> mp = new HashMap<>();
            School school = teamMapper.getSchool(team.getId());

            mp.put("year", team.getYear());
            mp.put("province", school.getProvince());
            mp.put("teamName", team.getTeamName());
            mp.put("schoolName", school.getSchoolName());

            //队伍成员
            List<User> teamMembers = teamMapper.getTeamMembers(team.getId());
            List<HashMap<String, String>> list1 = new ArrayList<>();

            for (User user : teamMembers) {
                HashMap<String, String> hashMap1 = new HashMap<>();
                hashMap1.put("name", user.getName());
                hashMap1.put("avatar", user.getAvatar());
                list1.add(hashMap1);
            }
            mp.put("members", list1.toString());

            list.add(mp);
        }
        hashMap.put("teams", list.toString());
        ResponseResult result = new ResponseResult<>();
        result.setCode(200);
        result.setMsg("根据学校返回队伍名称成功");
        result.setData(hashMap);
        return result;
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
        HashMap<String, String> hashMap = new HashMap<>();
        Integer total = teamByYear.size();
        hashMap.put("total", total.toString());
        //存储数组结果
        List<HashMap<String, String>> array = new ArrayList<>();
        for (Team team : teamByYear) {
            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("teamName", team.getTeamName());

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
                studentinfos.add(student);
            }
            hashMap1.put("members", studentinfos.toString());
            array.add(hashMap1);
        }

        hashMap.put("info", array.toString());

        ResponseResult result = new ResponseResult<>();
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
            Integer total = teamByYear.size();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("total", total.toString());
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
        String newName = "%";
        for (int i = 0; i < schoolName.length(); i++) {
            newName += schoolName.charAt(i) + "%";
        }
        List<Team> teamByYearAndSchoolName = teamMapper.getTeamByYearAndSchoolName(year, newName);

        //设置返回信息
        HashMap<String, String> hashMap = new HashMap<>();
        Integer total = teamByYearAndSchoolName.size();
        hashMap.put("total", total.toString());
        //存储数组结果
        List<HashMap<String, String>> array = new ArrayList<>();
        for (Team newTeam : teamByYearAndSchoolName) {
            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("teamName", newTeam.getTeamName());

            Long schoolId = newTeam.getSchoolId();
            //学校信息，省份，年份
            School school = schoolMapper.selectById(schoolId);
            hashMap1.put("schoolName", school.getSchoolName());
            hashMap1.put("province", school.getProvince());
            hashMap1.put("year", newTeam.getYear());

            //成员信息
            List<User> teamMembers = teamMapper.getTeamMembers(newTeam.getId());
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
                studentinfos.add(student);
            }
            hashMap1.put("members", studentinfos.toString());
            array.add(hashMap1);
        }

        hashMap.put("info", array.toString());


        return new ResponseResult<>(200,"根据年份和学校名称返回队伍名称成功",hashMap);
    }
}
