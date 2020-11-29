package com.example.rseu;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JsonRead {

    static ArrayList<String> readLogin(Context context) throws IOException, JSONException {

        String jsonText = readText(context, R.raw.login);

        JSONObject jsonRoot = new JSONObject(jsonText);

        JSONArray jsonArray = jsonRoot.getJSONArray("Лист1");

        ArrayList<String> logins = new ArrayList<>();

        for(int i=0; i < jsonArray.length(); i++) {

            JSONObject obj_t = jsonArray.getJSONObject(i);
            String login = obj_t.getString("login");

            logins.add(login);
        }

        return logins;
    }

    static ArrayList<String> readPassword(Context context) throws IOException, JSONException {

        String jsonText = readText(context, R.raw.login);

        JSONObject jsonRoot = new JSONObject(jsonText);

        JSONArray jsonArray = jsonRoot.getJSONArray("Лист1");

        ArrayList<String> passwords = new ArrayList<>();

        for(int i=0; i < jsonArray.length(); i++) {

            JSONObject obj_t = jsonArray.getJSONObject(i);
            String login = obj_t.getString("password");

            passwords.add(login);
        }

        return passwords;
    }

    static ArrayList<Long> readID(Context context) throws IOException, JSONException {

        String jsonText = readText(context, R.raw.login);

        JSONObject jsonRoot = new JSONObject(jsonText);

        JSONArray jsonArray = jsonRoot.getJSONArray("Лист1");

        ArrayList<Long> ids = new ArrayList<>();

        for(int i=0; i < jsonArray.length(); i++) {

            JSONObject obj_t = jsonArray.getJSONObject(i);
            long id = obj_t.getLong("id");

            ids.add(id);
        }

        return ids;
    }

    static ArrayList<String> readType(Context context) throws IOException, JSONException {

        String jsonText = readText(context, R.raw.login);

        JSONObject jsonRoot = new JSONObject(jsonText);

        JSONArray jsonArray = jsonRoot.getJSONArray("Лист1");

        ArrayList<String> types = new ArrayList<>();

        for(int i=0; i < jsonArray.length(); i++) {

            JSONObject obj_t = jsonArray.getJSONObject(i);
            String id = obj_t.getString("type");

            types.add(id);
        }

        return types;
    }


    private static String readText(Context context, int resId) throws IOException {

        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}