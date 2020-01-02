package id.ac.poliban.mi.nia.sqlitetesting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.ac.poliban.mi.nia.sqlitetesting.dao.impl.FriendDaoImplSQLite;
import id.ac.poliban.mi.nia.sqlitetesting.domain.Friend;

public class MainActivity extends AppCompatActivity {
    private List<Friend> data = new ArrayList<>();
    {
        data.add(new Friend("Vita Susanti", "Dago Utara, Bandung", "0812223"));
        data.add(new Friend("Juniadi Abdi", "Paiton, Probolinggo", "0812226"));
        data.add(new Friend("M. Yusuf", "Telawang, Banjarmasin", "0812222"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar()!=null)getSupportActionBar().setTitle("SQLite Demo");

        EditText edId = findViewById(R.id.edId);
        Button btUpgrade = findViewById(R.id.btUpgrade);
        Button btInsert = findViewById(R.id.btInsert);
        Button btUpdate = findViewById(R.id.btUpdate);
        Button btDelete = findViewById(R.id.btDelete);
        Button btGetAFriend = findViewById(R.id.btGetAFriend);
        Button btGetAllFriends = findViewById(R.id.btGetAllFriend);

        FriendDaoImplSQLite db = new FriendDaoImplSQLite(this);

        btUpgrade.setOnClickListener(v ->{
            db.onUpgrade(db.getReadableDatabase(), 1, 2);
            Toast.makeText(this, "upgrade succed!", Toast.LENGTH_SHORT).show();
        });

        btInsert.setOnClickListener(v -> {
            for (Friend o : data) {
                db.insert(o);
            }
            Toast.makeText(this, "inserted ok", Toast.LENGTH_SHORT).show();
        });

        btGetAllFriends.setOnClickListener(v -> {
            for (Friend o : db.getAllFriends()) {
                System.out.println(o);
            }
            Toast.makeText(this, "showing data ok", Toast.LENGTH_SHORT).show();
        });

        btUpdate.setOnClickListener(v -> {
            int id = Integer.parseInt(edId.getText().toString());
            db.update(new Friend(id, "XXX", "XXX", "XXX"));
            Toast.makeText(this, "update success!", Toast.LENGTH_SHORT).show();
        });

        btDelete.setOnClickListener(v -> {
            int id = Integer.parseInt(edId.getText().toString());
            db.delete(id);
            Toast.makeText(this, "delete success!, chech in run monitor!", Toast.LENGTH_SHORT).show();
        });

        btGetAFriend.setOnClickListener(v -> {
            int id = Integer.parseInt(edId.getText().toString());
            Friend f = db.getAFriendById(id);
            Toast.makeText(this, "get a friend success!, check run in monitor!", Toast.LENGTH_SHORT).show();
            System.out.println(f);
        });

    }
}
