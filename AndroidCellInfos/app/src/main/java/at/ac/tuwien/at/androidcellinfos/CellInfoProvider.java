package at.ac.tuwien.at.androidcellinfos;

import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

public class CellInfoProvider implements Serializable {

    private static final String TAG = "CellInfoProvider";

    private static final int INTERVAL_SEC = 5;

    private final Observable<CellInfo> cellInfoObservable;
    private Action1<CellInfo> callback;

    public CellInfoProvider(TelephonyManager telephonyManager) {
        //noinspection Convert2Lambda,Anonymous2MethodRef
        cellInfoObservable = Observable.fromEmitter(emitter ->
                callback = new Action1<CellInfo>() {
                    @Override
                    public void call(CellInfo cellInfo) {
                        emitter.onNext(cellInfo);
                    }
                }, Emitter.BackpressureMode.BUFFER);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                () -> {
                    List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
                    Log.i(TAG, "Found " + allCellInfo.size() + " cells");
                    for (CellInfo cellInfo : allCellInfo) {
                        callback.call(cellInfo);
                    }
                },
                0,
                INTERVAL_SEC,
                TimeUnit.SECONDS
        );
    }

    public Observable<CellInfo> getObservable() {
        return cellInfoObservable;
    }

}
