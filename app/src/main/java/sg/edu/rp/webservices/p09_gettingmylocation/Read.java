package sg.edu.rp.webservices.p09_gettingmylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Read extends AppCompatActivity {

    Button btnRefresh;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        btnRefresh = findViewById(R.id.btnRefresh);
        lv = findViewById(R.id.lv);

        ArrayList<String> arrayList = new ArrayList<>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) lv.getItemAtPosition(position);
                Toast.makeText(Read.this,clickedItem,Toast.LENGTH_SHORT).show();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();

                // Folder creation
                String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                File folder = new File(folderLocation_I);
                if (folder.exists() == false){
                    boolean result = folder.mkdir();
                    if (result == true){
                        Log.d("File Read/Write", "Folder created");
                    }
                }

                // File creation and writing
                File targetFile = new File(folderLocation_I, "data.txt");
                if (targetFile.exists() == true){
                    String data ="";
                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);String line = br.readLine();
                        while (line != null){
                            line = br.readLine();
                            data += line + "\n";
                            arrayList.add(line + "");
                        }br.close();
                        reader.close();
                    } catch (Exception e) {
                        Toast.makeText(Read.this, "Failed to read!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    Log.d("Content", data);}

                arrayAdapter.notifyDataSetChanged();
            }
        });

        btnRefresh.performClick();
    }
}