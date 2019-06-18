package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.R;

import static co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.GET_POST_USER;
import static co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.URL_BASE;
import static co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints.getData;

public class PostActivity extends Activity {

    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_EMAIL = "USER_EMAIL";

    TextView userName;
    TextView userPhone;
    TextView userEmail;
    Integer userId;
    ProgressBar progressBar;
    LinearLayout layout;
    private ArrayList<ListAdapterPublications.PublicationElement> listPublications;
    ListAdapterPublications adapter;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        layout = findViewById(R.id.contentCard);

        progressBar = new ProgressBar(getApplicationContext(),null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar,params);

        Intent intent = getIntent();

        userName =  findViewById(R.id.name);
        userPhone = findViewById(R.id.phone);
        userEmail = findViewById(R.id.email);

        userName.setText(intent.getStringExtra(USER_NAME));
        userPhone.setText(intent.getStringExtra(USER_PHONE));
        userEmail.setText(intent.getStringExtra(USER_EMAIL));
        userId= intent.getIntExtra(USER_ID,0);

        recycler = findViewById(R.id.recyclerViewPostsResults);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listPublications = new ArrayList<>();

        adapter = new ListAdapterPublications(listPublications);
        recycler.setAdapter(adapter);

        ServiceTask ws = new ServiceTask();
        ws.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private class ServiceTask extends AsyncTask<String, String, String> {

        public ServiceTask() {
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = new String();
            try{
                result=getData(URL_BASE + GET_POST_USER + "userId=" + userId);
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
            parseDataPublications(result);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void parseDataPublications(String json){

        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(json);
            for(int i = 0;i<jsonArr.length();i++){
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                listPublications.add(new ListAdapterPublications.PublicationElement(jsonObject.optInt("id"), jsonObject.optString("title"),jsonObject.optString("body")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
