package com.linkly.analytics.Controller;

import com.linkly.analytics.Collection.UrlClicks;
import com.linkly.analytics.JsonObj.ShortUrlJSON;
import com.linkly.analytics.Repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.security.KeyStore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping(value = "/analytics")
@CrossOrigin
@RestController
public class AnalyticsController {

    @Autowired
    private AnalyticsRepository repo;

    @CrossOrigin
    @GetMapping(path = "/biweekly/{shortUrl}")
    public ResponseEntity<Object> getBiWeeklyAnalytics(@PathVariable String shortUrl){

        List<UrlClicks> list = repo.findByShortURL(shortUrl);

        if (list.size() == 0)
            return ResponseEntity.ok("No Clicks So Far");

        HashMap<Integer , Integer> unsortedData = new HashMap<>();

        for (UrlClicks url : list) {
            // Get the key
            LocalDateTime keyTime = url.getTimeStamp();
            int key = ( keyTime.getYear() * 10000 ) + (keyTime.getMonthValue() * 100 ) + (keyTime.getDayOfMonth());
            if (unsortedData.containsKey(key)){
                unsortedData.replace(key , unsortedData.get(key) + 1);
            }
            else {
                unsortedData.put(key , 1);
            }
        }

        // TreeMap to store values of HashMap
        TreeMap<Integer, Integer> sorted = new TreeMap<>(Collections.reverseOrder());

        // Copy all data from hashMap into TreeMap
        sorted.putAll(unsortedData);

        // Make a sorted set
        LocalDate fDate = null;
        LocalDate sDate = null;

        // Analytics Data
        int[] data = new int[14];
        int[] reverseData = new int[14];
        int count = 0;

        // Parse the Data
        for (HashMap.Entry<Integer, Integer> entry : sorted.entrySet()) {

            // Set the old fDate as sDate
            sDate = fDate;

            int year = entry.getKey() / 10000;
            int month = entry.getKey() / 100 - (year * 100);
            int day = entry.getKey() - (month * 100) - (year * 10000);

            // Get new date
            fDate = LocalDate.of(year, month , day);

            if (sDate == null) {
                data[count] = entry.getValue();
                count += 1;
                continue;
            }

            // Check if there is a gap in the dates
            int gap = fDate.until(sDate).getDays();
            for ( ; gap > 1 && count < 14 ; gap--) {
                data[count] = 0;
                count++;
            }
            data[count] = entry.getValue();
            count++;

            if (count >= 14)
                break;

            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());

        }

        for (int i = 0 ; i < 14 ; i++) {
            reverseData[i] = data[13 - i];
        }

        // Remove starting zeros
        int[] answer = new int[reverseData.length];
        count = 0;
        boolean numFound = false;
        while (!numFound) {
            if (reverseData[count] == 0) {
                count++;
            } else {
                numFound = true;
            }
        }

        for (int i = 0 ; i < reverseData.length ; i++) {
            if (i + count < reverseData.length) {
                answer[i] = reverseData[i + count];
            }
            else
                answer[i] = 0;
        }


        HashMap<String , Object> jsonData = new HashMap<>();
        jsonData.put("data" , answer);
        jsonData.put("date_from" , fDate);

        return ResponseEntity.ok(jsonData);
    }

    @CrossOrigin
    @GetMapping(path = "/monthly/{shortUrl}")
    public ResponseEntity<Object> getMonthlyAnalytics(@PathVariable String shortUrl){

        List<UrlClicks> list = repo.findByShortURL(shortUrl);
        HashMap<Integer , Integer> unsortedData = new HashMap<>();

        if (list.size() == 0)
            return ResponseEntity.ok("No Clicks So Far");

        for (UrlClicks url : list) {
            // Get the key
            LocalDateTime keyTime = url.getTimeStamp();
            int key = ( keyTime.getYear() * 100 ) + keyTime.getMonthValue() ;
            if (unsortedData.containsKey(key)){
                unsortedData.replace(key , unsortedData.get(key) + 1);
            }
            else {
                unsortedData.put(key , 1);
            }
        }

        // TreeMap to store values of HashMap
        TreeMap<Integer, Integer> sorted = new TreeMap<>(Collections.reverseOrder());

        // Copy all data from hashMap into TreeMap
        sorted.putAll(unsortedData);

        // Make a sorted set
        LocalDate fDate = null;
        LocalDate sDate = null;

        // Analytics Data
        int[] data = new int[12];
        int[] reverseData = new int[12];
        int count = 0;

        // Parse the Data
        for (HashMap.Entry<Integer, Integer> entry : sorted.entrySet()) {

            // Set the old fDate as sDate
            sDate = fDate;

            int year = entry.getKey() / 100;
            int month = entry.getKey() - (year * 100);

            // Get new date
            fDate = LocalDate.of(year, month , 1);

            if (sDate == null) {
                data[count] = entry.getValue();
                count += 1;
                continue;
            }

            // Check if there is a gap in the Months
            int gap = fDate.until(sDate).getMonths();
            for ( ; gap > 1 && count < 12 ; gap--) {
                data[count] = 0;
                count++;
            }
            data[count] = entry.getValue();
            count++;

            if (count >= 12)
                break;

            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());

        }

        for (int i = 0 ; i < 12 ; i++) {
            reverseData[i] = data[11 - i];
        }

        // Remove starting zeros
        int[] answer = new int[reverseData.length];
        count = 0;
        boolean numFound = false;
        while (!numFound) {
            if (reverseData[count] == 0) {
                count++;
            } else {
                numFound = true;
            }
        }

        for (int i = 0 ; i < reverseData.length ; i++) {
            if (i + count < reverseData.length) {
                answer[i] = reverseData[i + count];
            }
            else
                answer[i] = 0;
        }

        HashMap<String , Object> jsonData = new HashMap<>();
        jsonData.put("data" , answer);
        jsonData.put("date_from" , fDate);

        return ResponseEntity.ok(jsonData);
    }

    @CrossOrigin
    @GetMapping(path = "/all/{shortUrl}")
    public ResponseEntity<Object> getAllClicks(@PathVariable String shortUrl) {
        List<UrlClicks> list = repo.findByShortURL(shortUrl);

        return ResponseEntity.ok(list.size());
    }


}
