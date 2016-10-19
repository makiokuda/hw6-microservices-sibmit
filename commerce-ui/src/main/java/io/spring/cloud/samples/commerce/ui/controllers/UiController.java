package io.spring.cloud.samples.commerce.ui.controllers;

import io.spring.cloud.samples.commerce.ui.services.CommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UiController {

    @Autowired
    CommerceService service;

    // List all items with prices (/items)
    @RequestMapping("/items")
    public String getAllItems() {
        return service.getAllItems();
    }

    // List all items and price based on category (/category/shoes or /category/shirts)
    @RequestMapping("/category/{cat}")
    public String getCategoryItem(@PathVariable("cat") String category) {
        return service.getCategoryItem(category);
    }

    // List all items and price based on item id (/item/itemId)
    @RequestMapping("/item/{id}")
    public String getIDItem(@PathVariable("id") String id) {
        return service.getIDItem(id);
    }
}
