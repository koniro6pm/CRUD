package com.example.user.crud2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllEmployee extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_employee);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        //取得資料
        getJSON();
    }

    /***取得資料****/
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {

                //loading圈圈  不用管他
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAllEmployee.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                //變數s 是 透過php檔返回的json字串
                //可以把s toast出來看看有沒有取得成功
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL);
                //URL_GET_ALL 是 在  Config.java 設定的網址字串,也就是getAllEmp.php
                //getAllEmp.php可在ftp上看到

                return s;
            }
        }

        //這兩行不用管
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    //對返回的json字串做處理
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {

            //將JSON_STRING(也就是s)  轉object再轉array 先不管他
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            //從array的第0個跑到最後一個
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);


                //這裡的id,name就是每個取到的資料,可以把它toast出來看看
                String id = jo.getString("id");
                String name = jo.getString("name");

                //listview的部分 先不管他
                HashMap<String,String> employees = new HashMap<>();
                employees.put("id",id);
                employees.put("name",name);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewAllEmployee.this, list, R.layout.list_item,
                new String[]{"id","name"},
                new int[]{R.id.id, R.id.name});

        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewEmployee.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get("id").toString();
        intent.putExtra(Config.EMP_ID,empId);
        startActivity(intent);
    }
}
