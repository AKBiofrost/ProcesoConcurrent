package com.portafolio.libraryconcurrendjava.ClassThread;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.portafolio.libraryconcurrendjava.CallBack.Procedure;
import com.portafolio.libraryconcurrendjava.CallBack.Result;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcesoConcurrent extends Thread {

    private static int sleepT;
    private static Context contextT;
    private static Procedure callBackPro;
    private static Result callBackResult;
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static ProcesoConcurrent init(Context context, int sleep, Procedure callback, Result result) {
        contextT = context;
        callBackPro = callback;
        callBackResult = result;
        sleepT = sleep;
        return new ProcesoConcurrent();
    }

    @Override
    public void run() {
        boolean result=false;
        // Ejemplo de tarea que devuelve un resultado
        Callable<Boolean> callableTask = () -> {
            try {
                Thread.sleep(sleepT); // Simular trabajo
                return callBackPro.Procedure(contextT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        };

        try {
            Future<Boolean> future = executor.submit(() -> callableTask.call());
             result = future.get();
            Log.d(TAG, "Resultado de la tarea: " + result);
            // Esperar a que todas las tareas terminen


        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Error ejecutando tarea", e);
        } finally {
            executor.shutdown(); // Asegúrate de cerrar el executor
        }
        while (!executor.isTerminated()) {
            try {
                callBackResult.Result(result);
                Thread.sleep(sleepT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }


}
