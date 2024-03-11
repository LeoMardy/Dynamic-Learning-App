package com.example.drawerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.drawerapp.databinding.FragmentSecondTabBinding;
import com.example.drawerapp.databinding.ItemRvLayoutBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Second_TabFragment extends Fragment {

    FragmentSecondTabBinding binding;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> myArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSecondTabBinding.inflate(getLayoutInflater(), container, false);

        loadData();


        return binding.getRoot();
    }

    public void loadData() {

        myArrayList=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        binding.progressBar.setVisibility(View.VISIBLE);
        String url = "https://testserverleo.000webhostapp.com/apps/DemoLoadDataTwo.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                binding.progressBar.setVisibility(View.GONE);

                for (int x = 0; x < response.length(); x++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(x);
                        String id = jsonObject.getString("id");
                        String itemList = jsonObject.getString("itemlist");
                        String description = jsonObject.getString("description");
                        String imageLink = jsonObject.getString("imagelink");

                        hashMap = new HashMap<>();
                        hashMap.put("id_key", id);
                        hashMap.put("itemList_key", itemList);
                        hashMap.put("description_key", description);
                        hashMap.put("imageLink_key", imageLink);
                        myArrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

                Second_TabFragment.MyAdapter myAdapter = new Second_TabFragment.MyAdapter();
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                binding.firtRV.setLayoutManager(layoutManager);
                binding.firtRV.setAdapter(myAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Network Error!")
                        .setMessage("Please Check Internet Connection")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                binding.noInternet.setVisibility(View.VISIBLE);

                            }
                        })
                        .show();
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private class MyViewHolder extends RecyclerView.ViewHolder {
            ItemRvLayoutBinding binding;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = ItemRvLayoutBinding.bind(itemView);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.item_rv_layout, parent, false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            hashMap = myArrayList.get(position);
            String id = hashMap.get("id_key");
            String itemList = hashMap.get("itemList_key");
            String description = hashMap.get("description_key");
            String imageLink = hashMap.get("imageLink_key");

            holder.binding.itemTextID.setText(itemList);

            holder.binding.rvItemID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(getActivity(), DescriptionActivity.class);
                    intent.putExtra("keyItem", itemList);
                    intent.putExtra("keyDes", description);
                    intent.putExtra("keyImage", imageLink);
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return myArrayList.size();
        }

    }
}