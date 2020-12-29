package com.example.QLSV_Pro;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<HashMap<String, String>> al_contact = new ArrayList<HashMap<String, String>>();
    SimpleAdapter sa;
    DBHelper dbh;
    SQLiteDatabase db;
    Cursor cursor;
    String[]from={"name","email","gender","phone"};
    int [] to={R.id.tv_name,R.id.tv_email,R.id.tv_gender,R.id.tv_phone};
    AlertDialog.Builder ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh=new DBHelper(this);
        db=dbh.getReadableDatabase();
        cursor = dbh.getContacs(db);
        listView=(ListView)findViewById(R.id.listview);
        sa=new SimpleAdapter(this,al_contact,R.layout.custom_list,from,to);
        listView.setAdapter(sa);
        while (cursor.moveToNext())
        {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("name",cursor.getString(0));
            hm.put("email",cursor.getString(1));
            hm.put("gender",cursor.getString(2));
            hm.put("phone",cursor.getString(3));
            al_contact.add(hm);
        }

        sa.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String num = ((TextView)view.findViewById(R.id.tv_phone)).getText().toString();
                final String name=((TextView)view.findViewById(R.id.tv_name)).getText().toString();
                final String email=((TextView)view.findViewById(R.id.tv_email)).getText().toString();
                final String gender=((TextView)view.findViewById(R.id.tv_gender)).getText().toString();
                //Toast.makeText(ShowContac.this,""+num,Toast.LENGTH_LONG).show();
                ab=new AlertDialog.Builder(MainActivity.this);
                ab.setTitle("Thông tin sinh viên");
                ab.setMessage(" MSSV: "+num + "\n Họ và tên : "+ name +"\n Email : "+email +"\n Giới tính : "+gender);
                ab.show();
            }

        });

    }



    // set navigation menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.nav_search){
            Intent intent =new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
            return true;

        }else if (id==R.id.nav_add){
            Intent intent =new Intent(MainActivity.this,AddContact.class);
            startActivity(intent);
            return true;

        }else if (id==R.id.nav_delete){
            Intent intent =new Intent(MainActivity.this,delete.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
