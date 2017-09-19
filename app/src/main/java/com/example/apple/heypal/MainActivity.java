package com.example.apple.heypal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

   // private static final String URL_DATA = "https://simplifiedcoding.net/demos/marvel/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    EditText usrInput;
    private List<Msg> messages;
    private ConversationService service;
    private MessageRequest newMsg;
    private Map<String,Object> mp;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messages = new ArrayList<Msg>();
        mp = null;




        Button button = (Button) findViewById(R.id.buttonsend);

        adapter = new MyAdapter(messages,this);
        recyclerView.setAdapter(adapter);

        service = new ConversationService("2017-05-26");
        service.setUsernameAndPassword("b69b65f7-7d5b-4b56-a3d6-de2bbc0910c6", "AtexVFm44Ezk");

        // listItems =new ArrayList<>();
      /*  for(int i=0;i<10;i++)
        {
            ListItem listItem =new ListItem(
                    "heading"+(i+1),
                    "Lorum Ipsum Dummy Text"

            );
            listItems.add(listItem);
        }
        adapter = new MyAdapter(listItems,this);
        recyclerView.setAdapter(adapter); */

    }


    public String curTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        return date.format(currentLocalTime);
    }




    public void sendMessage(View view) {
        Msg newMessage = new Msg("sent",usrInput.getText().toString(),messages.size(),curTime());
        messages.add(newMessage);
        usrInput.setText("");
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
        new Task().execute(newMessage.messageBody);
    }



    private class Task extends AsyncTask<String,Void,String> {
        String data;
        public Task() {
            data = "";
        }

        @Override
        protected String doInBackground(String... params) {
            newMsg = new MessageRequest.Builder()
                    .inputText(params[0])
                    .context(mp)
                    .build();

            String workspaceId = "3587c8ee-4ef0-4371-a538-5a9365c1b5f3";
            MessageResponse response = service.message(workspaceId, newMsg).execute();
            data = response.toString();

            String result = "";
            Map<String,Object> output = response.getOutput();
            List<String> values = (List<String>) output.get("text");
            if(values.size()!=0)
                result = values.get(0);
            mp = response.getContext();
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.length()==0)
                s="Sorry Come again Please!";
            Msg newMessage = new Msg("recieved",s,messages.size(),curTime());
            messages.add(newMessage);
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(adapter.getItemCount()-1);
        }
    }

}



