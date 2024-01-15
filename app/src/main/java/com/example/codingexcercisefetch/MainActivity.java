package com.example.codingexcercisefetch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.codingexcercisefetch.adapter.ItemAdapter;
import com.example.codingexcercisefetch.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Item> itemList;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://fetch-hiring.s3.amazonaws.com/hiring.json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertStreamToString(in);

                itemList = parseJson(response);

                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            displayResults();
        }
    }

    private String convertStreamToString(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Item> parseJson(String json) {
        List<Item> itemList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonItem = jsonArray.getJSONObject(i);

                int id = jsonItem.getInt("id");
                int listId = jsonItem.getInt("listId");
                String name = jsonItem.optString("name", ""); // Handle null or missing name

                if (!name.isEmpty()) {
                    Item item = new Item(id, listId, name);
                    itemList.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    private void displayResults() {
        // Implement the logic to display the results as per your requirements
        List<Item> filteredList = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getName() != null && !item.getName().trim().isEmpty()) {
                filteredList.add(item);
            }
        }

        // Group the items by listId
        Map<Integer, List<Item>> groupedItems = new HashMap<>();
        for (Item item : filteredList) {
            int listId = item.getListId();
            if (!groupedItems.containsKey(listId)) {
                groupedItems.put(listId, new ArrayList<>());
            }
            groupedItems.get(listId).add(item);
        }

        // Sort the items within each group by name
        for (List<Item> items : groupedItems.values()) {
            Collections.sort(items, Comparator.comparing(Item::getName));
        }

        // Combine the sorted groups into a single list
        List<Item> sortedList = new ArrayList<>();
        for (List<Item> items : groupedItems.values()) {
            sortedList.addAll(items);
        }

        // Set up the RecyclerView with the sorted list
        itemAdapter = new ItemAdapter(sortedList);
        recyclerView.setAdapter(itemAdapter);
    }
}