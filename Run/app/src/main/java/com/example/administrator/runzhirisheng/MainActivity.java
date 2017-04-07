package com.example.administrator.runzhirisheng;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tjyh)
    Button tjyh;
    @Bind(R.id.lssc)
    Button lssc;
    @Bind(R.id.yjsc)
    Button yjsc;
    @Bind(R.id.run1)
    TextView run1;
    @Bind(R.id.run2)
    TextView run2;
    @Bind(R.id.run3)
    TextView run3;
    @Bind(R.id.bt_ok)
    Button btOk;
    @Bind(R.id.et_tjyh)
    EditText etTjyh;
    private List<String> list = new ArrayList<String>();
    private String[] chengyuan = {"孙国勋", "李睿", "孙书博", "张晓驰", "佟仁",
            "冯亚楠", "曹康凯", "衣丽莉", "窦新平", "冯笑颜", "苏成厚", "孙文平",
            "单冬冬", "祝旭枫", "王思文", "严相和", "肖榜", "周振振"};
    private ArrayAdapter adapter;
    private SQLiteDatabase db;
    private Boolean diyici = true;
    private Random random = new Random();
    private String r;
    private String r1;
    private String r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db = openOrCreateDatabase("ren",MODE_PRIVATE,null);
        //创建表
        db.execSQL("create table if not exists user(_id integer primary key autoincrement,name text not null)");
        Cursor cursor = db.rawQuery("select name from user", null);
        String[] columnNames = cursor.getColumnNames();
        if (cursor!=null){
            while (cursor.moveToNext()){
                for (String name:columnNames) {
                    list.add(cursor.getString(cursor.getColumnIndex(name)));
                }
            }

    }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);//添加适配器
        adapter.setDropDownViewResource(R.layout.item);//设置适配器
        lv.setAdapter(adapter);//开启适配器
    }

    @OnClick({R.id.tjyh, R.id.lssc, R.id.yjsc, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tjyh:
                db.execSQL("insert into user(name)values("+"'"+etTjyh.getText().toString().trim()+"'"+")");
                adapter.add(etTjyh.getText().toString().trim());
                lv.setAdapter(adapter);
                etTjyh.setText("");
                break;
            case R.id.lssc:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                View view1 = View.inflate(this, R.layout.item_dialog, null);
                final EditText et_sc = (EditText) view1.findViewById(R.id.et_sc);
                dialog.setView(view1);
                dialog.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(et_sc.getText().toString().trim());
                        adapter.remove(et_sc.getText().toString().trim());
                    }
                });
                dialog.show();
                lv.setAdapter(adapter);
                break;
            case R.id.yjsc:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                View view2 = View.inflate(this, R.layout.item_dialog, null);
                final EditText yongjiu = (EditText) view2.findViewById(R.id.et_sc);
                alertDialog.setView(view2);
                alertDialog.setPositiveButton("永久删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.execSQL("delete from user where name="+"'"+yongjiu.getText().toString().trim()+"'");
                        list.remove(yongjiu.getText().toString().trim());
                        adapter.remove(yongjiu.getText().toString().trim());
                    }
                });
                alertDialog.show();
                lv.setAdapter(adapter);
                break;
            case R.id.bt_ok:
                r = list.get(random.nextInt(list.size()));
                r1 = list.get(random.nextInt(list.size()));
                r2 = list.get(random.nextInt(list.size()));
                Boolean i = true;
                while (i){
                    if (r == r1){
                        continue;
                    }else if (r1 == r2){
                        continue;
                    }else if (r2 == r){
                        continue;
                    }
                    i=false;
                }
                run1.setText(r);
                run2.setText(r1);
                run3.setText(r2);
                break;
        }
    }
}