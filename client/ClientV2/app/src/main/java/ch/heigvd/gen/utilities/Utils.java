package ch.heigvd.gen.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {

    public static String getToken(final Activity a) {
        SharedPreferences prefs = a.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        return prefs.getString("Token", "");
    }

    public static void setToken(final Activity a, final String token) {
        SharedPreferences prefs = a.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Token", token);
        editor.commit();
    }

    public static String getUser(final Activity a) {
        SharedPreferences prefs = a.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        return prefs.getString("User", "");
    }

    public static void setUser(final Activity a, final String user) {
        SharedPreferences prefs = a.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("User", user);
        editor.commit();
    }

    public static String createJSONObject(String[] keys, String[] values) throws Exception {
        if (keys.length != values.length) {
            throw new Exception("Keys list and values list do not have the same number of elements !");
        }
        String result = "";
        JSONObject obj = new JSONObject();
        try {
            for (int i = 0; i < keys.length; ++i) {
                obj.put(keys[i], values[i]);
            }
            result = obj.toString();
        } catch (JSONException e) {
            throw e;
        }
        return result;
    }
}
