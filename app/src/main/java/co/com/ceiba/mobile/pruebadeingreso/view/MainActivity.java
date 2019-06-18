package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import static co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.GET_USERS;
import static co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.URL_BASE;
import static co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.getData;

public class MainActivity extends Activity {


    ArrayList<ListAdapter.UserElement> listUsers;
    RecyclerView recycler;
    ListAdapter adapter;
    ProgressBar progressBar;
    RelativeLayout layout;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.content);

        progressBar = new ProgressBar(getApplicationContext(),null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar,params);

        recycler = findViewById(R.id.recyclerViewSearchResults);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listUsers = new ArrayList<>();

        adapter = new ListAdapter(getApplicationContext(), listUsers);
        recycler.setAdapter(adapter);


        search = findViewById(R.id.editTextSearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<ListAdapter.UserElement> newList = new ArrayList<>();

                for(ListAdapter.UserElement user: listUsers){
                    if(user.getUserName().contains(s.toString())){
                        newList.add(user);
                    }

                }
                adapter.refreshFilterList(newList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ServiceTask ws = new ServiceTask(URL_BASE+GET_USERS);
        ws.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class ServiceTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection;
        private String urlString;

        public ServiceTask(String urlString) {
            this.urlString = urlString;
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            try{
                result=getData(URL_BASE+GET_USERS);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            parseDataUsers(result);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void parseDataUsers(String json){

        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(json);
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                listUsers.add(new ListAdapter.UserElement(jsonObject.optInt("id"), jsonObject.optString("name"),jsonObject.optString("email"),jsonObject.optString("phone")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}