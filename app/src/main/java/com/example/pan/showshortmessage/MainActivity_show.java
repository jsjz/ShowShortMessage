package com.example.pan.showshortmessage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class MainActivity_show extends Activity {
    private ListView Msglist;
    private MyAdapter adapter;

    private TextView number;
    private TextView content;
    private TextView time;

    private List<Msg> mData=new ArrayList<>();
    private IntentFilter intentFilter;
    private MsgReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MsgReceiver();
        registerReceiver(messageReceiver, intentFilter);

        number = (TextView) findViewById(R.id.number);
        content = (TextView) findViewById(R.id.content);
        time = (TextView) findViewById(R.id.time);
        Msglist = (ListView) findViewById(R.id.listView);
        mData = new ArrayList<>();
        adapter = new MyAdapter(MainActivity_show.this, mData,Msglist);
        Msglist.setAdapter(adapter);


        readShortMessage();
        String send = number.getText().toString();//转换：textview-->string!!
//            String send1 = content.getText().toString();
//            String send2 = number.getText().toString();

        Msg asd = new Msg("");
        String[] temp;
        temp = send.split(";");//在最后加";"作为分隔符，短信中若有";"则出现错误
        for (int i = 0; i < temp.length; i++){//拆分数据源split方法
            asd.content = content.getText().toString();
            asd.number = number.getText().toString();
            asd.time = time.getText().toString();
            mData.add(new Msg(temp[i]));
        }
        readShortMessage();
        adapter.notifyDataSetChanged();


//        Msglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Msg ss=mData.get(position);
//                Intent intent = new Intent(MainActivity.this, huifu.class);
//                intent.putExtra("extra_data[i]",ss.getTime()+ss.getNumber()+ss.getContent());
//                startActivity(intent);
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }


    //短信获取
    public void readShortMessage() {
        ContentResolver cr = getContentResolver();//用于操作ContentProvider中的数据
        Cursor cursor = cr.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        String msg = "";
        String msg2 = "";
        String msg3 = "";
        while(cursor.moveToNext()) {
            int phoneColumn = cursor.getColumnIndex("address");
            int smsColumn = cursor.getColumnIndex("body");
            int dateColumn = cursor.getColumnIndex("date");//格式转换
//int>>string
            Date date=new Date(Long.parseLong(cursor.getString(dateColumn)));
            Log.d(TAG, "readShortMessage: "+dateColumn);
            String strs="";
            SimpleDateFormat sdf=new SimpleDateFormat("   "+"yyyy年MM月dd日HH时mm分ss秒", Locale.getDefault());
            strs = sdf.format(date);
            msg += cursor.getString(phoneColumn) + "\n" +
                    cursor.getString(smsColumn) +"\n"+ strs+ ";";
        }
        number.setText(msg);
        content.setText(msg2);
        time.setText(msg3);
    }

    class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Object[] pduses = (Object[]) intent.getExtras().get("pdus");
            for (Object pdus : pduses) {
                byte[] pdusmessage = (byte[]) pdus;
            }
            Toast.makeText(MainActivity_show.this,"收到", Toast.LENGTH_LONG).show();
        }
    }
}
