package com.panglei;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {
    public static final String [] FUNC = {
            "func://quixey.com/searchInside-showTvShows",
            "func://quixey.com/searchInside-showMusicGroup",
            "func://quixey.com/searchInside-showMusicRecording",
            "func://quixey.com/searchInside-showBooks",
            "func://quixey.com/searchInside-showMovie",
            "func://quixey.com/searchInside-showTvSeries"

    };
    public static final String URL_PREFIX = "http://223.6.252.216/v5.0/dvcs/search?log=0&product=holaverse&search_id=022377f3-f191-4bb2-bccf-b54fb59de9f5&disable_cache=1&partner_id=2819515675&locale=zh_CN&q=";
    public static final String URL_SUFFIX = "&partner_secret=9hwtmm62us3fps2bc8fgre13kd124tbe&dv_limit=7";

    public static void main(String[] args)throws Exception {
        String path = "c:\\deeplink\\querySet.txt";
        String[] queryArray = getQueryFromLocal(path);
        System.out.println(Arrays.toString(queryArray));
        System.out.println("-------------------------------");
        for (int i = 0; i < queryArray.length; i++){
            System.out.println(queryArray[i]);
        }
        List<Source> list = getSource(queryArray);
        FuncApp funcApp = new FuncApp();
        List<Source> disList = distinctAnd(list);
        PipelineCmd(disList);

    }

    public static final String[] getQueryFromLocal(String path)throws IOException{
        File file = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        String line = null;
        List<String> query = new ArrayList<String>();
        while ((line = br.readLine()) != null){
            if (line.length() > 0){
                String function = line.split("\\:")[0];
                String[] split = line.split("\\:")[1].split("，");
                for (String ss : split){
                    if (ss.length() > 1){
                        query.add(function + "-" + ss);
                        System.out.println(ss);
                    }

                }
            }
        }
        String[] array = new String[query.size()];
        int count = 0;
        for (String q : query){
            array[count++] = q;
        }
        return array;
    }

    public static List<Source> getSource(String[] querys)throws Exception{
        System.out.println(Arrays.toString(querys));
        List<Source> list = new ArrayList<Source>();
        for (String query: querys){
            String[] split = query.split("\\|");
            String function = split[0];
            String q = split[1];
            URL url = new URL(URL_PREFIX + q + URL_SUFFIX);
//            System.out.println(URL_PREFIX + query + URL_SUFFIX);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String content = br.readLine();
//            while ((content = br.readLine()) != null){
//                Thread.sleep(10000);
//                System.out.println(content);
//            }
//            System.out.println(content);
            JSONObject urlObj = new JSONObject(content);
            JSONObject deepViewCards = urlObj.getJSONObject("deepViewCards");
            for (String func : FUNC){
                boolean flag = deepViewCards.has(func) && func.contains(function);
                if (flag){
//                    System.out.println(func + "\t" + function);
                    JSONObject showTvShows = deepViewCards.getJSONObject(func);
                    JSONArray jsonArray = showTvShows.getJSONObject("cardContent").getJSONArray("deepViews");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject deepView = jsonArray.getJSONObject(i);
//                        if (deepView.has("furl") && deepView.getString("furl").contains(function)){
//                            String funcUrl = null;
//                            if(deepView.has("furl")){
//                                funcUrl = deepView.getString("furl");
//                            }
//                            String accessUrl = null;
//                            if (deepView.has("stateAccessInfo") && deepView.getJSONArray("stateAccessInfo").getJSONObject(0).has("accessUrl")){
//                                accessUrl = deepView.getJSONArray("stateAccessInfo").getJSONObject(0).getString("accessUrl");
//                            }
//                            String appName = null;
//                            if (deepView.has("additionalStateAccessInfo") && deepView.getJSONArray("additionalStateAccessInfo").getJSONObject(0).has("appName")  ){
//                                appName = deepView.getJSONArray("additionalStateAccessInfo").getJSONObject(0).getString("appName");
//                            }
//                            if (funcUrl != null && funcUrl.length() > 1 && accessUrl != null && accessUrl.length() > 1 && appName != null && appName.length() > 1){
//                                Source source = new Source(query, accessUrl, funcUrl, appName);
//                                list.add(source);
//                            }
//                        }
                        String funcUrl = null;
                        if(deepView.has("furl")){
                            funcUrl = deepView.getString("furl");
                        }
                        String accessUrl = null;
                        if (deepView.has("stateAccessInfo") && deepView.getJSONArray("stateAccessInfo").getJSONObject(0).has("accessUrl")){
                            accessUrl = deepView.getJSONArray("stateAccessInfo").getJSONObject(0).getString("accessUrl");
                        }
                        String appName = null;
                        if (deepView.has("additionalStateAccessInfo") && deepView.getJSONArray("additionalStateAccessInfo").getJSONObject(0).has("appName")  ){
                            appName = deepView.getJSONArray("additionalStateAccessInfo").getJSONObject(0).getString("appName");
                        }
                        if (funcUrl != null && funcUrl.length() > 1 && accessUrl != null && accessUrl.length() > 1 && appName != null && appName.length() > 1){
                            Source source = new Source(query, accessUrl, funcUrl, appName);
                            list.add(source);
                        }
                    }
                }
            }
        }
        System.out.println("*************************************************");
        for (Source source : list){
            System.out.println(source);
        }
        System.out.println("*************************************************");
        return list;
    }

    /**
     * 判断(appName, func)是否重复,并报警是否覆盖所有的app
     * @return
     */
    public static List<Source> distinctAnd(List<Source> sources){
        System.out.println("*************************************************");
        System.out.println("初始数据量:\t" + sources.size());
        Set<Source> set = new HashSet<Source>(sources);
        List<Source> list = new ArrayList<Source>(set);
        System.out.println("去重后数据量:\t" + list.size());
        for (Source source : list){
            System.out.println(source);
        }
        Map<String, List<String>> sourceMap = new HashMap<String, List<String>>();
        System.out.println("判断缺少的APP信息");
        for (Source source : list){
            if (sourceMap.containsKey(source.getFunc())){
                sourceMap.get(source.getFunc()).add(source.getAppName());
            }else {
                List<String> temp = new ArrayList<String>();
                temp.add(source.getAppName());
                sourceMap.put(source.getFunc(), temp);
            }
        }
        FuncApp funcApp = new FuncApp();
        Map<String, List<String>> map = funcApp.getMap();
        for (String key : map.keySet()){
            if (sourceMap.containsKey(key)){
                List<String> sourceList = sourceMap.get(key);
                List<String> funcAppList = map.get(key);
                for (String str : funcAppList){
                    if (!sourceList.contains(str)){
                        System.out.println(key + "|" + str);
                    }
                }
            }
        }
        System.out.println("*************************************************");
        return list;
    }

    public static void PipelineCmd(List<Source> list) throws Exception{
        String path = "C:\\Users\\PL\\AppData\\Local\\Android\\sdk\\platform-tools\\";
        String start = "adb.exe shell \"am start ";
        String dump = "adb.exe shell uiautomator dump";
        String pull = "adb.exe pull /sdcard/window_dump.xml ";
        String pullPath = "c:\\deeplink\\";
        String fileName = null;
        BufferedWriter bw = new BufferedWriter(new FileWriter(pullPath + "result.txt"));
        String line = null;
        for (Source source : list){
            fileName = source.getQuery().replace("|", "") + "-" +source.getFunc().replaceAll("/", "")  + source.getAppName() + ".xml";
            System.out.println(fileName);
            String accessUrl = source.getAccessUrl();
            System.out.println(path + start + "'" + accessUrl + "'\"");
            Runtime.getRuntime().exec(path + start + "'" + accessUrl + "'\"");
            //等待页面跳转
            //if (source.getFunc().equals("showMovieInfo") || source.getFunc().equals("showMovie") || source.getFunc().equals("showTvSeries") || source.getFunc().equals("showTvShow") || source.getFunc().equals("showAnimation") ){
            //  Thread.sleep(120000);
            //}else {
            //  Thread.sleep(10000);
            //}
            Thread.sleep(120000);
            System.out.println(path + dump);
            Runtime.getRuntime().exec(path + dump);
            Thread.sleep(5000);
//            System.out.println(path + pull + pullPath + accessUrl + ".xml");
            Runtime.getRuntime().exec(path + pull + pullPath + fileName);
            System.out.println(path + pull + pullPath + fileName);
            Thread.sleep(2000);
            File file = new File( pullPath + fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String content = br.readLine();
            if (content.contains(source.getQuery())){
                String version = getVersionCMD( "file:///" + pullPath + fileName);
                System.out.println(source + "|" + "YES" + "|" + version);
                System.out.println(pullPath + fileName);
                line = source.toString() + "|" + "YES" + "|" + version ;
            }else {
                System.out.println(source + "\t" + "NO");
                line = source.toString() + "|" + "NO";
            }
            bw.write(line + "\n");
            System.out.println("----------------------------");
        }
        bw.close();

    }




    public static String getVersionCMD(String fileName)throws Exception{
        String app = ParseXML.getApp(fileName);
        String path = "C:\\Users\\PL\\AppData\\Local\\Android\\sdk\\platform-tools\\";
        String cmdOne = path + "adb.exe shell pm path " + app;
        System.out.println(cmdOne);
        Process process = Runtime.getRuntime().exec(cmdOne);
        BufferedInputStream in = new BufferedInputStream(process.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        String packagePath = null;
        while ((line = br.readLine()) != null){
            if (line != null && line.length() > 0) {
                packagePath = line.split(":")[1];
                System.out.println(packagePath);
            }
        }

        String cmdTwo = path + "adb.exe pull " + packagePath + " c:\\yingyong\\" + app + ".apk";
        System.out.println(cmdTwo);
        process = Runtime.getRuntime().exec(cmdTwo);
        in = new BufferedInputStream(process.getInputStream());
        br = new BufferedReader(new InputStreamReader(in));
        while ((line = br.readLine()) != null){
            if (line != null && line.length() > 0) {
                System.out.println(line);
            }
        }

        String cmdThree = "C:\\aapt\\aapt dump badging c:\\yingyong\\" + app + ".apk";
        System.out.println(cmdThree);
        process = Runtime.getRuntime().exec(cmdThree);
        in = new BufferedInputStream(process.getInputStream());
        br = new BufferedReader(new InputStreamReader(in));
        String version = null;
        while ((line = br.readLine()) != null){
            if (line != null && line.length() > 0 && line.contains("package")) {
                String[] split = line.split(" ");
//                version = line.split(" ")[3];
                if (split.length == 4){
                    version = split[3];
                }
            }
        }
        return version;
    }


}
