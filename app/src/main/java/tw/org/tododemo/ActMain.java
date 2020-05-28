package tw.org.tododemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ActMain extends AppCompatActivity {

    private View.OnClickListener btnAdd_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences table = getSharedPreferences("tTodo",0);
            int sn = table.getInt("COUNT",0);
            sn++;
            table.edit().putInt("COUNT",sn).commit();

            String keyT = "T"+String.valueOf(sn);
            String keyD = "D"+String.valueOf(sn);
            table.edit().putString(keyT,txtTodo.getText().toString()).commit();
            table.edit().putString(keyD,txtDate.getText().toString()).commit();

            txtTodo.setText("");
            txtDate.setText("");

            Toast.makeText(ActMain.this,"寫入資料成功!",Toast.LENGTH_SHORT).show();


        }
    };
    private View.OnClickListener btnList_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences table = getSharedPreferences("tTodo",0);
            int sn = table.getInt("COUNT",0);
            if (sn==0){
                Toast.makeText(ActMain.this,"沒有待辦的事項",Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<String> list = new ArrayList<String>();
            for (int i=1;i<=sn;i++){
                String keyT = "T"+String.valueOf(i);
                String keyD = "D"+String.valueOf(i);
                if (table.contains(keyT)){
                    list.add(table.getString(keyT,"")+"\r\n"+table.getString(keyD,""));
                }
            }
            String[] items = list.toArray(new String[list.size()]);

            AlertDialog.Builder build = new AlertDialog.Builder(ActMain.this);
            build.setTitle("未完成工作項目").setItems(items,null).create().show();

            Toast.makeText(ActMain.this, "讀取資料成功!", Toast.LENGTH_SHORT).show();


        }
    };
    private View.OnClickListener btnDate_Click = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            Calendar l_calendar=Calendar.getInstance();
            Dialog l_dialog=new DatePickerDialog(
                    ActMain.this,
                    new DatePickerDialog.OnDateSetListener()
                    {
                        public void onDateSet(DatePicker arg0, int arg1,
                                              int arg2, int arg3)
                        {
                            String date = arg1+"-"+arg2+"-"+arg3;
                            txtDate.setText(date);
                        }

                    },
                    l_calendar.get(Calendar.YEAR),
                    l_calendar.get(Calendar.MONTH),
                    l_calendar.get(Calendar.DATE)
            );
            l_dialog.show();


        }
    };
    private View.OnClickListener btnClear_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences table = getSharedPreferences("tTodo",0);
            int sn = 0;
            table.edit().putInt("COUNT",sn).commit();
            Toast.makeText(ActMain.this, "清除成功!", Toast.LENGTH_SHORT).show();

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialComponent();
    }

    private void InitialComponent() {
        txtTodo = findViewById(R.id.txtTode);
        txtDate = findViewById(R.id.txtDate);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(btnAdd_Click);
        btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(btnList_Click);

        btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(btnDate_Click);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(btnClear_Click);


    }
    TextView txtTodo;
    TextView txtDate;
    Button btnAdd;
    Button btnList;
    Button btnDate;
    Button btnClear;
}
