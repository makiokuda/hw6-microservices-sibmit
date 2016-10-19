package io.spring.cloud.samples.commerce.ui.services;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CommerceService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackCommerce")
    public String getAllItems() {
        Map<String, String> priceInfo = restTemplate.getForObject("http://price/prices", Map.class);
        Iterable<LinkedHashMap> items = restTemplate.getForObject("http://item/items", Iterable.class);

        StringBuilder result = new StringBuilder();

        Iterator<LinkedHashMap> it = items.iterator();
        while (it.hasNext()) {
            LinkedHashMap curr = it.next();
            String id = String.valueOf(curr.get("id"));
            String price = priceInfo.get(id);
            result.append("Item: ")
                    .append(curr.toString())
                    .append("Price: ")
                    .append(price)
                    .append("<br>");
        }

        result.append("# of items: ").append(priceInfo.size());
        return result.toString();
    }

    public String getCategoryItem(String category) {

        Map<String, String> priceInfo = restTemplate.getForObject("http://price/prices", Map.class);
        Iterable<LinkedHashMap> items = restTemplate.getForObject("http://item/category/{cat}", Iterable.class, category);

        StringBuilder result = new StringBuilder();

        Iterator<LinkedHashMap> it = items.iterator();
        while (it.hasNext()) {
            LinkedHashMap curr = it.next();
            String id = String.valueOf(curr.get("id"));
            String price = priceInfo.get(id);
            result.append("Item: ")
                    .append(curr.toString())
                    .append("Price: ")
                    .append(price)
                    .append("<br>");
        }

        return result.toString();
    }

    public String getIDItem(String id) {

        Map<String, String> priceInfo = restTemplate.getForObject("http://price/price/{item}", Map.class, id);
        Item curr = restTemplate.getForObject("http://item/item/{item}", Item.class, id);

        StringBuilder result = new StringBuilder();

        String i = curr.getId();
        String price = priceInfo.get("price");
        result.append("Item: ")
                .append(curr.toString())
                .append("Price: ")
                .append(price)
                .append("<br>");

        return result.toString();
    }

    public String fallbackCommerce() {
        return "Cannot fulfill your requests now. Please try again.";
    }

}
