package com.example.calendarquickstart;

        import android.app.ListActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.HashMap;

public class Main2Activity extends ListActivity  implements android.view.View.OnClickListener{

    Button btnAdd,btnGetAll;
    TextView even_Id;

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.btnAdd)){

            Intent intent = new Intent(Main2Activity.this,evenDetail.class);
            intent.putExtra("even_Id",0);
            startActivity(intent);

        }else {

            EvenRepo repo = new EvenRepo(this);

            ArrayList<HashMap<String, String>> evenList =  repo.getEvenList();
            if(evenList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        even_Id = (TextView) view.findViewById(R.id.event_Id);
                        String eventId = even_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),evenDetail.class);
                        objIndent.putExtra("even_Id", Integer.parseInt( eventId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( Main2Activity.this,evenList, R.layout.view_event_entry, new String[] { "id","name"}, new int[] {R.id.event_Id, R.id.event_name});
                setListAdapter(adapter);
            }else{
                Toast.makeText(this,"No Events!",Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

    }




}