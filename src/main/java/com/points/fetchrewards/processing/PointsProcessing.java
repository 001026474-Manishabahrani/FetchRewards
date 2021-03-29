package com.points.fetchrewards.processing;

import com.points.fetchrewards.beans.Transaction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PointsProcessing {

    public static JSONParser parser = new JSONParser();

    public static TreeMap<DateTime, Transaction> allTransactions = new TreeMap<>();

    private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public static synchronized boolean verifyAndAddPoints(String body)  {
        if(body == null || body.length() == 0) {
            return false;
        }

        JSONObject bodyObject;

        try {
            bodyObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        String payer = (String) bodyObject.get("payer");
        Integer points = ((Long) bodyObject.get("points")).intValue();

        DateTime timestamp = dtf.parseDateTime(bodyObject.get("timestamp").toString());

        Transaction transaction = new Transaction(payer, points, timestamp);

        allTransactions.put(timestamp, transaction);

        return true;
    }


    public static synchronized JSONArray spend(String body) {
        HashMap<String, Integer> result = new HashMap<>();
        TreeMap<DateTime, Transaction> allTransactionsCopy = allTransactions;
        JSONObject bodyObject;

        try {
            bodyObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


        Integer pointsToSpend = ((Long) bodyObject.get("points")).intValue();

        if(pointsToSpend <= 0) {
            return null;
        }


        for (Map.Entry<DateTime, Transaction> entry : allTransactions.entrySet()) {
                Transaction curPartner = entry.getValue();
                int curPartnerPoints = curPartner.getPoints();
                int reducePointsBy = 0;
                if (curPartnerPoints > pointsToSpend) {
                    reducePointsBy = pointsToSpend;
                } else {
                    reducePointsBy = curPartnerPoints;
                }
                pointsToSpend -= reducePointsBy;

                String curPayer = curPartner.getPayer();
                int curPoints = Math.negateExact(reducePointsBy);

                curPartner.setPoints(curPartner.getPoints()-reducePointsBy);

                allTransactions.put(curPartner.getTimestamp(), curPartner);

                if(result.containsKey(curPayer)) {
                    int resultPoints = result.get(curPayer);
                    result.put(curPayer, curPoints + resultPoints);
                } else {
                    result.put(curPayer, curPoints);
                }
            }

        if (pointsToSpend > 0) {
            allTransactions = allTransactionsCopy;
            return null;
        }

        JSONArray resultArray = new JSONArray();
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            JSONObject payer = new JSONObject();
            payer.put("payer", entry.getKey());
            payer.put("points", entry.getValue());

            resultArray.add(payer);
        }

        return resultArray;
    }


    public static JSONArray calculateBalacePoints() {
        HashMap<String, Integer> result = new HashMap<>();

        for (Map.Entry<DateTime, Transaction> entry : allTransactions.entrySet()) {
            Transaction curPartner = entry.getValue();
            String curPayer = curPartner.getPayer();
            int curPoints = curPartner.getPoints();

            if(result.containsKey(curPayer)) {
                int resultPoints = result.get(curPayer);
                result.put(curPayer, curPoints + resultPoints);
            } else {
                result.put(curPayer, curPoints);
            }
        }

        JSONArray resultArray = new JSONArray();
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            JSONObject payer = new JSONObject();
            payer.put("payer", entry.getKey());
            payer.put("points", entry.getValue());

            resultArray.add(payer);
        }

        return resultArray;
    }
}
