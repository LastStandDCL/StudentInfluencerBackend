package com.backend.last_stand.mapper;

import com.alibaba.fastjson.JSON;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.backend.last_stand.service.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/5/31 12:18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SchoolTest {
    @Autowired
    private SchoolMapper schoolMapper;

    @Test
    public void TestInsert() throws IOException {
        File excelFile = new File("E:\\idea_codeLibrary\\StudentInfluencerBackend\\src\\main\\resources\\s.xlsx");//读取表格文件
        FileInputStream inputStream = new FileInputStream(excelFile);
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        String province = new String("province");
        String school = new String("schoolName");

        Sheet sheet = workBook.getSheet("school");

        ArrayList<School> schoolArrayList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {

            //province
            String b = sheet.getRow(0).getCell(i).getStringCellValue();
            int j = 1;
            // school
            Cell cell = sheet.getRow(j).getCell(i);

            while(true){
                // 添加学校名称
                School school1 = new School();
                school1.setProvince(b);
                school1.setSchoolName(cell.getStringCellValue());
                school1.setCreateTime(new Date());
                System.out.println(school1);
                //加入到集合中
                schoolArrayList.add(school1);


                j++;
                cell = sheet.getRow(j).getCell(i);

                if(cell == null || cell.getCellType().equals(CellType.BLANK)){
                    break;
                }

            }
        }

        for (School school1 : schoolArrayList) {
            int insert = schoolMapper.insert(school1);
            if (insert != 1) {
                System.out.println("插入失败");
            } else {
                System.out.println("插入成功");
            }
        }


        System.out.println("Done");
    }

    @Test
    public void testUpdate() {
        List<School> list = schoolMapper.selectByProvince("浙江");
        for (School school : list) {
            school.setProvince("浙江省");
            int i = schoolMapper.updateById(school);
            if(i != 1) {
                System.out.println("更新失败");
            }
        }
    }

}
