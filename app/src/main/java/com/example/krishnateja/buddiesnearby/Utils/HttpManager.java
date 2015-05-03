package com.example.krishnateja.buddiesnearby.Utils;

import android.util.Log;

import com.example.krishnateja.buddiesnearby.Models.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by krishnateja on 5/3/2015.
 */
public class HttpManager {

    private final static String TAG = "HttpManager";

    public static String sendUserData(RequestParams params) {
        URL url = null;

        try {

                url = new URL(params.getURI());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestMethod(params.getMethod());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        if (params.getMethod() == "POST") {
            OutputStreamWriter writer = null;
            try {
                writer = new OutputStreamWriter(con.getOutputStream());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                writer.write(params.getEncodedParams());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        try {
            while (reader != null && (line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, sb.toString());
        return sb.toString();


    }
}

