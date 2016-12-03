package at.ac.tuwien.at.androidcellinfos;

import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class CellModelWatcher implements Serializable {

    private static final String TAG = "CellModelWatcher";

    private final TelephonyManager telephonyManager;

    private volatile Thread watcherThread;
    private volatile CountDownLatch closedSignal;

    public CellModelWatcher(TelephonyManager telephonyManager) {
        this.telephonyManager = telephonyManager;
    }

    public Observable<List<CellModel>> watch() {
        return watch(30, TimeUnit.SECONDS);
    }

    public Observable<List<CellModel>> watch(long interval, TimeUnit unit) {
        return Observable.create(subscriber -> {
            Log.d(TAG, "Watch started");

            closedSignal = new CountDownLatch(1);
            watcherThread = Thread.currentThread();

            subscriber.onStart();

            while (!watcherThread.isInterrupted()) {
                List<CellModel> cells = new ArrayList<>();

                for (CellInfo info : telephonyManager.getAllCellInfo()) {
                    cells.add(CellModel.from(info));
                }

                Log.d(TAG, "Notifying cells: " + cells);
                subscriber.onNext(cells);

                try {
                    Thread.sleep(unit.toMillis(interval));
                } catch (InterruptedException e) {
                    Log.d(TAG, "Watch delay interrupted");
                    Thread.currentThread().interrupt();
                }
            }

            Log.d(TAG, "Watch finished");

            subscriber.onCompleted();
            closedSignal.countDown();
        });
    }

    public void close() {
        Log.d(TAG, "Closing watch...");
        watcherThread.interrupt();

        try {
            closedSignal.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.d(TAG, "Waiting for close signal interrupted");
            Thread.currentThread().interrupt();
        }

        Log.d(TAG, "Watch closed");
    }
}
