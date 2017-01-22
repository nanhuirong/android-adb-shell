package com.panglei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncApp {
    private Map<String , List<String>> map = new HashMap<String, List<String>>();

    public FuncApp() {
        addShowAnimation();
        addShowArticle();
        addShowBook();
        addShowHotel();
        addShowMovie();
        addShowMovieInfo();
        addShowMusicAlbum();
        addShowMusicArtist();
        addShowMusicRecording();
        addShowTvShow();
        addShowTvSeries();
        addShowRestaurant();
        addShowProduct();
        addShowPodcast();
        addShowDeals();
    }

    private void addShowRestaurant(){
        List<String> list = new ArrayList<String>();
        list.add("饿了么");
        list.add("美团外卖");
        list.add("百度外卖");
        map.put("showRestaurant", list);
    }
    private void addShowProduct() {
        List<String> list = new ArrayList<String>();
        list.add("京东");
        list.add("苏宁易购");
        map.put("showProduct", list);
    }
    private void addShowPodcast(){
        List<String> list = new ArrayList<String>();
        list.add("喜马拉雅");
        map.put("showPodcast", list);
    }

    private void addShowDeals(){
        List<String> list = new ArrayList<String>();
        list.add("大众点评");
        list.add("美团");
        map.put("showDeals", list);
    }
    private void addShowBook(){
        List<String> list = new ArrayList<String>();
        list.add("掌阅");
        list.add("QQ阅读");
        list.add("起点读书");
//        list.add("书旗小说");
        map.put("showBook", list);
    }

    private void addShowMovieInfo(){
        List<String> list = new ArrayList<String>();
        list.add("时光网");
        list.add("猫眼电影");
        list.add("爱奇艺");
        list.add("豆瓣电影");
        map.put("showMovieInfo", list);
    }
    private void addShowMovie(){
        List<String> list = new ArrayList<String>();
        list.add("优酷视频");
        list.add("搜狐视频");
        list.add("爱奇艺");
        list.add("腾讯视频");
        map.put("showMovie", list);
    }
    private void addShowTvSeries(){
        List<String> list = new ArrayList<String>();
        list.add("优酷视频");
        list.add("搜狐视频");
        list.add("爱奇艺");
        list.add("腾讯视频");
        map.put("showTvSeries", list);
    }
    private void addShowTvShow(){
        List<String> list = new ArrayList<String>();
        list.add("优酷视频");
        list.add("搜狐视频");
        list.add("爱奇艺");
        list.add("腾讯视频");
        map.put("showTvShow", list);
    }
    private void addShowAnimation(){
        List<String> list = new ArrayList<String>();
        list.add("哔哩哔哩");
        list.add("AcFun");
        list.add("爱奇艺");
        map.put("showAnimation", list);
    }
    private void addShowArticle(){
        List<String> list = new ArrayList<String>();
        list.add("什么值得买");
        list.add("知乎日报");
        map.put("showArticle", list);
    }
    private void addShowHotel(){
        List<String> list = new ArrayList<String>();
        list.add("携程旅游");
        map.put("showHotel", list);
    }
    private void addShowMusicRecording(){
        List<String> list = new ArrayList<String>();
        list.add("QQ音乐");
        list.add("酷狗音乐");
        list.add("网易云音乐");
        list.add("虾米音乐");
        map.put("showMusicRecording", list);
    }
    private void addShowMusicAlbum(){
        List<String> list = new ArrayList<String>();
        list.add("QQ音乐");
        list.add("酷狗音乐");
        list.add("网易云音乐");
        list.add("虾米音乐");
        map.put("showMusicAlbum", list);
    }

    private void addShowMusicArtist(){
        List<String> list = new ArrayList<String>();
        list.add("QQ音乐");
        list.add("酷狗音乐");
        list.add("网易云音乐");
        list.add("虾米音乐");
        map.put("showMusicArtist", list);
    }

    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder();
        sb.append("---------------------------------------------\n");
        for (String key : map.keySet()){
            sb.append(key + ";");
            sb.append(map.get(key).toString());
            sb.append("\n");
        }
        sb.append("---------------------------------------------\n");
        return sb.toString();
    }

    public Map<String, List<String>> getMap() {
        return map;
    }
}
