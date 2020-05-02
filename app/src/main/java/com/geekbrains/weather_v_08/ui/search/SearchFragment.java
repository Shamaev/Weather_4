package com.geekbrains.weather_v_08.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.geekbrains.weather_v_08.R;
import com.geekbrains.weather_v_08.ui.home.HomeFragment;

import java.util.ArrayList;

public class SearchFragment extends Fragment  {
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageButton imageButton;
    private String cityName;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        ArrayList<SearchModel> al = new ArrayList<>();
        String[] arrCity = getResources().getStringArray(R.array.city);
        for (String o : arrCity) {
            al.add(new SearchModel(o));
        }
        final int size = al.size();
        Log.i("eerr56", "size - " + size);

        SearchAdapter adapter = new SearchAdapter(al);
        recyclerView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // текст будет изменен
//                Log.i("eerr56", "beforeTextChanged - " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<SearchModel> al = new ArrayList<>();
                String[] arrCity = getResources().getStringArray(R.array.city);
                for (String o : arrCity) {
                    al.add(new SearchModel(o));
                }

                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(llm);


                String alEdit = s.toString();
                StringBuilder stringBuilder = new StringBuilder(alEdit);
                if (!alEdit.equals("")) {
                    stringBuilder.setCharAt(0, Character.toUpperCase(alEdit.charAt(0)));
                }
                alEdit = String.valueOf(stringBuilder);

                int count = alEdit.length();

                ArrayList<SearchModel> al2 = new ArrayList<>();

                for (int i = 0; i < al.size(); i++) {
                    String alStr = al.get(i).getCity();
                    String alStrSub;

                    if (count <= alStr.length()) {
                        alStrSub = alStr.substring(0,count);
                    } else {
                        alStrSub = alStr;
                    }


                    if (alEdit.equals(alStrSub)) {
                        al2.add(al.get(i));
                    }
                }
                al.clear();
                al.addAll(al2);
                al2.clear();

                SearchAdapter adapter = new SearchAdapter(al);
                recyclerView.setAdapter(adapter);
            }
        });

//        getTextEditKey();
        getTextEditEnter();


        view.findViewById(R.id.searchEnter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName = editText.getText().toString();
                HomeFragment homeFragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("city", cityName);
                homeFragment.setArguments(bundle);

                NavHostFragment.findNavController(SearchFragment.this)
                        .navigate(R.id.action_nav_search_to_nav_home, bundle);
            }
        });


    }

    public void onClickSearch(String str) {
                HomeFragment homeFragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("city", str);
                homeFragment.setArguments(bundle);

                NavHostFragment.findNavController(SearchFragment.this)
                        .navigate(R.id.action_nav_search_to_nav_home, bundle);
    }


    private void getTextEditEnter() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName = editText.getText().toString();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment(cityName);
                fragmentTransaction.add(homeFragment, "thread_manager");
                fragmentTransaction.commit();
            }
        });
    }

    private void getTextEditKey() {
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    cityName = editText.getText().toString();
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("city", cityName);
                    homeFragment.setArguments(bundle);

                    NavHostFragment.findNavController(SearchFragment.this)
                            .navigate(R.id.action_nav_search_to_nav_home, bundle);
                    return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        recyclerView = getActivity().findViewById(R.id.searchRecycler);
        editText = getActivity().findViewById(R.id.searchEditText);
        imageButton = getActivity().findViewById(R.id.searchEnter);
    }
}
