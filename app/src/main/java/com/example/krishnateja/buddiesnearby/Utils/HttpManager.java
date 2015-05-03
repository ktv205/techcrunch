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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by krishnateja on 5/3/2015.
 */
public class HttpManager {

    private final static String TAG = "HttpManager";

    public static String sendUserData(RequestParams params) {
        URL url = null;

        try {
            if (params.getMethod() == "GET") {
                url = new URL(params.getURI());
                Log.d(TAG, params.getURI());
            } else {
                url = new URL(params.getURI());
            }
            if (!params.getProtocol().equals("https")) {
                HttpURLConnection con = null;


                con = (HttpURLConnection) url.openConnection();


                con.setRequestMethod(params.getMethod());
                OutputStreamWriter writer = null;

                writer = new OutputStreamWriter(con.getOutputStream());


                writer.write(params.getEncodedParams());


                writer.flush();


                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));


                String line;

                while (reader != null && (line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                Log.d(TAG, sb.toString());
                return sb.toString();
            } else {
                HttpsURLConnection con = null;


                con = (HttpsURLConnection) url.openConnection();


                con.setRequestMethod(params.getMethod());
                OutputStreamWriter writer = null;

                writer = new OutputStreamWriter(con.getOutputStream());


                writer.write(params.getEncodedParams());


                writer.flush();


                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));


                String line;

                while (reader != null && (line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                Log.d(TAG, sb.toString());
                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

