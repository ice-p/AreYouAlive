package none.areyoualive;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.lang.String;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
public class Activity3 extends ActionBarActivity {
    ListView listView;
    SoldierServices ss;
    String test3 = "";
    String test4 = "";
    String status = "";
    String status1 = "";
    String[] values = {""};
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3);
        listView = (ListView) findViewById(R.id.list);
        ss = ServiceGenerator.createService(SoldierServices.class, this);
        ss.getSoldiers(new Callback<SoldierResponse>() {
            @Override
            public void success(SoldierResponse soldierResponse, Response response) {
                System.out.println("soldierResponse.soldiers.size() = " + soldierResponse.soldiers.size());
                test3 = soldierResponse.soldiers.get(0).Name;
                test4 = soldierResponse.soldiers.get(1).Name;
                System.out.println(MainActivity.userstring);
                if(MainActivity.userstring.equals(test3)) test3 = test3 + " (you)";
                if(MainActivity.userstring.equals(test4)) test4 = test4 + " (you)";
                values = new String[]{test3, test4};
                status = soldierResponse.soldiers.get(0).Status;
                status1 = soldierResponse.soldiers.get(1).Status;
                dealwithit();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = " + error);
            }

        });
    }
    public void dealwithit() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String itemStatus = "";
                String itemMessage = "";

                // ListView Clicked item index
                int itemPosition = position + 1;

                // ListView Clicked item value
                if (itemPosition == 1) {
                    itemStatus = MainActivity.statuses[0];
                    itemMessage = MainActivity.messages[0];
                }
                if (itemPosition == 2) {
                    itemStatus = MainActivity.statuses[1];
                    itemMessage = MainActivity.messages[1];
                }
                System.out.println(itemStatus);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Id : " + itemPosition + "," + "  Status : " + itemStatus + "," + "  Message: " + itemMessage, Toast.LENGTH_LONG)
                        .show();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

