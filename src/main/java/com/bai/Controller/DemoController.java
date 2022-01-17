package com.bai.Controller;

import com.jayway.jsonpath.JsonPath;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;


/**
 * @Author: Hualin Bai
 * @Date: 2022-01-17 13:49
 * @Version: 1.0
 */
@RestController
@RequestMapping("/touchtunes/tech-assignment")
public class DemoController {

    // recall readJsonFile
    private String str = readJsonFile("D:\\GitHub\\Restful_API_Demo\\src\\main\\resources\\static\\db.json");

    // method to get jukes
    @GetMapping("/jukes")
    public String getJuke(){

        // find all jukes, using JsonPath package
        List<String> list = JsonPath.read(str,"$.jukes.*");
        return list.toString();
    }

    // method to get settings
    @GetMapping("/settings")
    public String getSetting(){
        // find all settings
        List<String> list = JsonPath.read(str,"$.settings.*");
        return list.toString();
    }

    // method to get settings by ID
    @GetMapping("/settings/{id}")
    public String getSettingsById(@PathVariable String id){
        // find setting by id eg.(e9869bbe-887f-4d0a-bb9d-b81eb55fbf0a)
        List<String> list = JsonPath.read(str,"$..settings[?(@.id =~ /" + id + "/i)]");
        return list.toString();
    }

    // method to get jukes by Model
    @GetMapping("/jukes/{model}")
    public String getJukesByModel(@PathVariable String model){
        // find setting by model eg.(fusion)
        List<String> list = JsonPath.read(str,"$..[?(@.model =~ /" + model + "/i)]");
        return list.toString();
    }

    // method to offset - specifies at what index start the page
    @GetMapping("/jukes/offset/{offset}")
    public String getJukesByOffset(@PathVariable String offset){
        // find jukes by offset eg.(start index is 2)
        List<String> list = JsonPath.read(str,"$.jukes[" + offset + ":]");
        return list.toString();
    }

    @GetMapping("/settings/offset/{offset}")
    public String getSettingsByOffset(@PathVariable String offset){
        // find setting by offset eg.(start index is 3)
        List<String> list = JsonPath.read(str,"$..settings[" + offset + ":]");
        return list.toString();
    }


    private static String readJsonFile(String fileName){
        /**
         * The method is to readJsonFile then return a string.
         * @Param: fileName
         * @Return: String
         * */
        String jsonStr = "";
        try{
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile));
            int ch = 0;
            StringBuffer sb = new StringBuffer();

            while((ch = reader.read()) != -1){
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
