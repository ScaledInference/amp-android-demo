package com.scaledinference.amped.store;

import android.content.Context;

import com.google.gson.Gson;
import com.scaledinference.amped.store.lists.HeaderListItem;
import com.scaledinference.amped.store.lists.ProductListItem;
import com.scaledinference.amped.store.model.Category;
import com.scaledinference.amped.store.lists.ListItem;
import com.scaledinference.amped.store.model.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub data provider that reads product list from local JSON.
 */
class DataProvider {

    private static Category[] categories;
    private static List<ListItem> listItems;

    static List<ListItem> getCompositeItems(Context context) {
        if (listItems == null) {
            listItems = new ArrayList<>();
            for (Category c: getCategories(context)) {
                listItems.add(new HeaderListItem(c.getTitle()));

                for (Product p: c.getProducts()) {
                    listItems.add(new ProductListItem(p));
                }
            }
        }

        return listItems;
    }

    private static Category[] getCategories(Context context) {
        if (categories == null) {
            categories = loadData(context);
        }
        return categories;
    }

    private static Category[] loadData(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("sample_data.json");
            Reader reader = new InputStreamReader(inputStream);
            return new Gson().fromJson(reader, Category[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
