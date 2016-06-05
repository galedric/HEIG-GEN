package ch.heigvd.gen.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import ch.heigvd.gen.R;
import ch.heigvd.gen.communications.RequestPOST;
import ch.heigvd.gen.communications.RequestPUT;
import ch.heigvd.gen.interfaces.ICallback;
import ch.heigvd.gen.interfaces.IJSONKeys;
import ch.heigvd.gen.interfaces.IRequests;
import ch.heigvd.gen.models.Group;
import ch.heigvd.gen.models.User;
import ch.heigvd.gen.utilities.Utils;

public class GroupCreateActivity extends AppCompatActivity implements IRequests, IJSONKeys {

    ArrayAdapter adapter = null;

    private final static String TAG = GroupCreateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);

        // enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create adapter
        adapter = new ArrayAdapter<User>(this, R.layout.group_contacts_list_item, User.users);

        final ListView listView = (ListView) findViewById(R.id.group_contact_list);

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);


    }

    /**
     * TODO Méthode de création du groupe
     *
     * @param view
     */
    public void createGroup(final View view){
        final EditText text = (EditText) findViewById(R.id.group_name);
        if(TextUtils.isEmpty(text.getText())) {
            text.setError("Group name is empty !");
            return;
        } else if(Group.exists(text.getText().toString())){
            text.setError("Group name already exists !");
            return;
        }
        final ListView listView = (ListView) findViewById(R.id.group_contact_list);
        try {
            String[] keys = new String[]{KEY_TITLE};
            String[] values = new String[]{text.getText().toString()};
            String content = Utils.createJSONObject(keys, values);
            RequestPOST post = new RequestPOST(new ICallback<String>() {
                @Override
                public void success(String result) {
                    try{
                        JSONObject json = new JSONObject(result);

                        SparseBooleanArray checked = listView.getCheckedItemPositions();
                        for (int i = 0; i < listView.getCount(); i++) {
                            if (checked.get(i)) {
                                User user = (User)listView.getItemAtPosition(i);
                                inviteContact(json.getInt("id"), user.getId());
                            }
                        }
                    } catch (Exception ex){
                        Log.e(TAG, ex.getMessage());
                    }
                    Log.i(TAG, "Success : " + result);
                }

                @Override
                public void failure(Exception ex) {
                    try {
                        Utils.showAlert(GroupCreateActivity.this, new JSONObject(ex.getMessage()).getString("err"));
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    Log.e(TAG, ex.getMessage());
                }
            }, Utils.getToken(this), BASE_URL + GET_GROUPS, content);
            post.execute();
            post.get();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

        finish();
    }

    /**
     * TODO
     *
     * @param groupId
     */
    public void inviteContact(int groupId, int userId){
        try {
            Log.i(TAG, "Token : " + Utils.getToken(GroupCreateActivity.this));
            new RequestPUT(new ICallback<String>() {
                @Override
                public void success(String result) {
                    Log.i(TAG, "Success : " + result);
                }

                @Override
                public void failure(Exception ex) {
                    try {
                        Utils.showAlert(GroupCreateActivity.this, new JSONObject(ex.getMessage()).getString("err"));
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    Log.e(TAG, ex.getMessage());
                }
            }, Utils.getToken(GroupCreateActivity.this), BASE_URL + GET_GROUP + groupId + GET_MEMBER + userId).execute();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * TODO
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}