package com.part.roommyapplication.NestedRecycler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.part.roommyapplication.R;
import com.part.roommyapplication.Room.Repository;
import com.part.roommyapplication.Room.Table.Chapter;
import com.part.roommyapplication.Room.Table.Content;
import com.part.roommyapplication.Room.Table.NestedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChapterLecture extends Fragment {
    RecyclerView rvChapter;
    LinearLayoutManager layoutManagerGroup;
    ChapterAd adapterChapter;
    ArrayList<Chapter> chap_list;
    ArrayList<Content> content_list;
    Repository repository;

    public ChapterLecture() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_chapter_lecture, container, false);
        rvChapter = v.findViewById(R.id.rv_chapter_lecture);

        layoutManagerGroup = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvChapter.setLayoutManager(layoutManagerGroup);
        chap_list=new ArrayList<>();
        content_list = new ArrayList<>();

        chap_list=(ArrayList<Chapter>)getActivity().getIntent().getSerializableExtra("chapterList");
        content_list=(ArrayList<Content>)getActivity().getIntent().getSerializableExtra("ContentList");
        ChapterAd adapter = new ChapterAd(getContext(),chap_list,content_list);
        adapter.notifyDataSetChanged();
        rvChapter.setAdapter(adapter);

        return v;
    }


//    public void getList() {
//        RequestQueue requestQueue;
//        RecyclerViewClickInterface listener;
//        listener = null;
//        ArrayList<ChapterDetail> chapterDetailsArrayList = new ArrayList<>();
//
//        ArrayList<Lecture> lecturesDetailsArray = new ArrayList<>();
//        ChapterAd adapter = new ChapterAd(getContext(), chap_list,content_list);
//        requestQueue = Volley.newRequestQueue(getContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://classroom.rkmshillong.org/classRoom/getContent?subject=" + subjectDetails.getSubjectId() + "&courseID=" + subjectDetails.getCourseId(), null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("Response", response.toString());
//                        try {
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject obj = response.getJSONObject(i);
//                                Log.d("myobject", obj.getString("title"));
//                                ChapterDetail chapterDetails = new ChapterDetail(obj.getString("title"), obj.getInt("items"));
//                                Log.d("Test", String.valueOf(chapterDetails.getItems()));
//                                chapterDetailsArrayList.add(chapterDetails);
//                                Log.d("InsideVolley", String.valueOf(chapterDetailsArrayList.get(i)));
//                                JSONArray jsonArrayLecture = new JSONArray(obj.getString("content"));
//                                for (int j = 0; j < jsonArrayLecture.length(); j++) {
//                                    JSONObject jsonObjectLecture = jsonArrayLecture.getJSONObject(j);
//                                    Lecture lecture = new Lecture(jsonObjectLecture.getString("title"), jsonObjectLecture.getInt("lectureID"), jsonObjectLecture.getString("file"));
//                                    lecturesDetailsArray.add(lecture);
//                                    Log.d("jthadded", lecturesDetailsArray.toString());
//                                }
//                                adapter.notifyDataSetChanged();
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//        Log.d("InsideVolley", chapterDetailsArrayList.toString());
//        Log.d("Adapter", chapterDetailsArrayList.toString());
//        rvChapter.setAdapter(adapter);
//    }
//
//    public ArrayList<NestedList> generateList(ArrayList<Chapter> chap_list, ArrayList<Content> content_list) {
//        ArrayList<NestedList> combineList = new ArrayList();
//        for (int i = 0; i < chap_list.size(); i++) {
//            String chapDesc = chap_list.get(i).getDesc();
//            String chapTitle = chap_list.get(i).getTitle();
//            int chapterNo = chap_list.get(i).getChapterNo();
//            int chapItems=chap_list.get(i).getItems();
//            for (int j = 0; j < content_list.size(); j++) {
//                if(chapterNo==content_list.get(j).getChapterNo()) {
//                    int contentId = content_list.get(j).getContentID();
//                    String contentDesc = content_list.get(j).getDesc();
//                    String contentTitle = content_list.get(j).getTitle();
//                    String type = content_list.get(j).getType();
//                    int id = content_list.get(j).getId();
//                    String file = content_list.get(j).getFile();
//                    NestedList nestedList = new NestedList(chapterNo,chapDesc,chapTitle,id,contentId,type,contentTitle,contentDesc,file);
//                    combineList.add(nestedList);
//                }
//            }
//        }
//        return combineList;
//    }


}