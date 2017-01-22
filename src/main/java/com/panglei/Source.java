package com.panglei;

public class Source {
    private String query;
    private String accessUrl;
    private String funcUrl;
    private String appName;
    private String func;

    Source(String query, String accessUrl, String funcUrl, String appName){
        this.query = query;
        this.accessUrl = accessUrl;
        this.funcUrl = funcUrl;
        this.appName = appName;
        this.func = funcUrl.split("/")[3];
    }

    public Source() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = this.funcUrl.split("/")[2];
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    @Override
    public int hashCode() {
        return this.func.hashCode() + this.appName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Source source = (Source) obj;
        return this.appName.equals(source.appName) && this.getFunc().equals(source.getFunc());
    }

    @Override
    public String toString() {
        return this.query + "|" + this.func + "|" + this.appName;
    }
}
