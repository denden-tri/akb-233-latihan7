package com.denztri.catviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.denztri.catviewer.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ProgressBar progressBar = binding.mainPb;
        progressBar.setVisibility(View.VISIBLE);

        MainActivity main = this;

        new Handler().postDelayed(() -> {
            try {
                viewModel.getList().observe(main,
                        catLists -> {
                            if (recyclerView.getVisibility() == View.GONE) recyclerView.setVisibility(View.VISIBLE);
                            mainAdapter.setCatModels(catLists);
                            progressBar.setVisibility(View.GONE);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        },1000);

        initRecycle();


        setContentView(view);
    }

    private void initRecycle(){
        recyclerView = binding.mainRv;
        recyclerView.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(),
                3));

        mainAdapter = new MainAdapter(binding.getRoot().getContext());
        mainAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recyclerView.setAdapter(mainAdapter);
    }
}