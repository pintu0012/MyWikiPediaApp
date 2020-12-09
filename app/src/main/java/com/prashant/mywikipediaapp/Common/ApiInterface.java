package com.prashant.mywikipediaapp.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class ApiInterface {

    private static final String PREFS_NAME = "preferenceName";

    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "google");
    }
    public static String getSearchApi = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=";
    public static String getFeaturedImagesApi = "https://commons.wikimedia.org/w/api.php?action=query&prop=imageinfo&iiprop=timestamp%7Cuser%7Curl&generator=categorymembers&gcmtype=file&gcmtitle=Category:Featured_pictures_on_Wikimedia_Commons&format=json&utf8";
    public static String getoldRandomArticlesApi= "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&rvprop=content&grnlimit=10&prop=revisions|images|description";
//    public static String getRandomArticlesApi= "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=revisions|images&rvprop=content&grnlimit=10&rvslots=main";
    public static String getRandomArticlesApi= "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&grnnamespace=0&prop=imageinfo|extracts&rvprop=content&grnlimit=10&iiprop=timestamp%7Cuser%7Curl&explaintext&exchars=100";

    public static String getCategoryListApi="https://en.wikipedia.org/w/api.php?action=query&list=allcategories&formatversion=2&format=json&acprefix=";
}
