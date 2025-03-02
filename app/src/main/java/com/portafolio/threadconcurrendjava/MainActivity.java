package com.portafolio.threadconcurrendjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.portafolio.libraryconcurrendjava.CallBack.Procedure;
import com.portafolio.libraryconcurrendjava.CallBack.Result;
import com.portafolio.libraryconcurrendjava.ClassThread.ProcesoConcurrent;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i("ThreadConcurrend", "Procedure: " + Thread.currentThread().getName());
        for (int i = 0; i < 1000; i++) {

            Log.i("ThreadConcurrend", "Procedure: " + i);
        }

        ProcesoConcurrent.init(this, 100, new Procedure() {
            @Override
            public boolean Procedure(Context context) {
                Log.i("ThreadConcurrend", "Procedure: " + Thread.currentThread().getName());

                for (int i = 0; i < 100000; i++) {
                    if (i % 2 == 0) {
                        Log.i("ThreadConcurrend", "Procedure: " + i);
                    } else {
                        Log.i("ThreadConcurrend", "Procedure: " + i);
                    }
                    if (i == 99999) return true;
                }

                return false;
            }
        }, new Result() {
            @Override
            public boolean Result(boolean result) {
                Log.i("ThreadConcurrend", "Procedure: " + Thread.currentThread().getName() + "-result " + result);
                return false;
            }
        }).start();
    }
}