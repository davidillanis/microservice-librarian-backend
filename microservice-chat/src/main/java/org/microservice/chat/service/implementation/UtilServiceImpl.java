package org.microservice.chat.service.implementation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.microservice.chat.service.UtilService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UtilServiceImpl implements UtilService {
    @Override
    public String getFromDataAPI(String url) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        //HttpPost request = new HttpPost(url);
        HttpGet request = new HttpGet(url);

        try {
            request.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public OffsetDateTime getDateTimePeru(String url) {
        String json = getFromDataAPI(url);
        JSONObject jsonObject=new JSONObject(json);
        String dateTime=jsonObject.get("datetime").toString();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        return offsetDateTime;
    }

}
