package com.piglin.analyzer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.nio.*;
import java.nio.file.*;
import java.lang.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.lang.Exception;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

@Controller
public class MahoutTest {

    private static int counter = 0;
    private static final String VIEW_INDEX = "index";
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(MahoutTest.class);

    private static String data1 = "empty";
    private static String data2 = "empty";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {
        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();
        PreferenceArray prefsForUser1 = new GenericUserPreferenceArray(2);

        prefsForUser1.setUserID(0, 1L);

        prefsForUser1.setItemID(0, 101L);
        prefsForUser1.setValue(0, 3.0f);

        prefsForUser1.setItemID(1, 102L);
        prefsForUser1.setValue(1, 4.5f);

        Preference pref = prefsForUser1.get(1);
        System.out.println(pref);

        preferences.put(1L, prefsForUser1);

        DataModel dataModel = new GenericDataModel(preferences);
        System.out.println(dataModel);

        model.addAttribute("message", "Welcome");

        model.addAttribute("data1", pref.toString());
        model.addAttribute("data2", dataModel.toString());

        model.addAttribute("counter", ++counter);
        logger.debug("[welcome] counter : {}", counter);

        // Spring uses InternalResourceViewResolver and return back indexOLD.jsp
        return VIEW_INDEX;

    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute("message", "Welcome " + name);
        model.addAttribute("counter", ++counter);
        logger.debug("[welcomeName] counter : {}", counter);
        return VIEW_INDEX;

    }

    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("/Users/swyna/Sites/UniversityAnalyzer/src/main/resources/2014ComputerScience.json"));
        return new String(encoded, StandardCharsets.UTF_8);
    }

}