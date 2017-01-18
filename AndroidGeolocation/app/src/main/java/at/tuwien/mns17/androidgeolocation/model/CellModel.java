package at.tuwien.mns17.androidgeolocation.model;

import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;

public class CellModel {

    public static final String UNKNOWN = "unknown";

    public enum CellType {
        GSM, CDMA, UMTS, LTE
    }

    public enum CellState {
        ACTIVE, NEIGHBORING
    }

    private final String cellId;
    private final CellType type;
    private final CellState state;
    private final String mobileCountryCode;
    private final String mobileNetworkId;
    private final String locationAreaCode;
    private final int signalStrengthDbm;
    private final String radioType;

    public static CellModel from(CellInfo info) {
        if (info instanceof CellInfoGsm) {
            return from((CellInfoGsm) info);
        } else if (info instanceof CellInfoCdma) {
            return from((CellInfoCdma) info);
        } else if (info instanceof CellInfoWcdma) {
            return from((CellInfoWcdma) info);
        } else if (info instanceof CellInfoLte) {
            return from((CellInfoLte) info);
        }

        throw new RuntimeException("Unknown cell info");
    }

    private static CellModel from(CellInfoGsm info) {
        int cid = info.getCellIdentity().getCid();
        int mcc = info.getCellIdentity().getMcc();
        int mnc = info.getCellIdentity().getMnc();
        int lac = info.getCellIdentity().getLac();

        return new CellModel(
                cid == Integer.MAX_VALUE ? UNKNOWN : (cid + ""),
                CellType.GSM,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                mcc == Integer.MAX_VALUE ? UNKNOWN : (mcc + ""),
                mnc == Integer.MAX_VALUE ? UNKNOWN : (mnc + ""),
                lac == Integer.MAX_VALUE ? UNKNOWN : (lac + ""),
                info.getCellSignalStrength().getDbm(),
                "gsm"
        );
    }

    private static CellModel from(CellInfoCdma info) {
        return new CellModel(
                info.getCellIdentity().getBasestationId() == Integer.MAX_VALUE ? UNKNOWN : (info.getCellIdentity().getBasestationId() + ""),
                CellType.CDMA,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                UNKNOWN,
                info.getCellIdentity().getNetworkId() == Integer.MAX_VALUE ? UNKNOWN : (info.getCellIdentity().getNetworkId() + ""),
                UNKNOWN,
                info.getCellSignalStrength().getDbm(),
                "cdma"
        );
    }

    private static CellModel from(CellInfoWcdma info) {
        int cid = info.getCellIdentity().getCid();
        int mcc = info.getCellIdentity().getMcc();
        int mnc = info.getCellIdentity().getMnc();
        int lac = info.getCellIdentity().getLac();

        return new CellModel(
                cid == Integer.MAX_VALUE ? UNKNOWN : (cid + ""),
                CellType.UMTS,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                mcc == Integer.MAX_VALUE ? UNKNOWN : (mcc + ""),
                mnc == Integer.MAX_VALUE ? UNKNOWN : (mnc + ""),
                lac == Integer.MAX_VALUE ? UNKNOWN : (lac + ""),
                info.getCellSignalStrength().getDbm(),
                "wcdma"
        );
    }

    private static CellModel from(CellInfoLte info) {
        int cid = info.getCellIdentity().getCi();
        int mcc = info.getCellIdentity().getMcc();
        int mnc = info.getCellIdentity().getMnc();
        int lac = info.getCellIdentity().getTac();

        return new CellModel(
                cid == Integer.MAX_VALUE ? UNKNOWN : (cid + ""),
                CellType.LTE,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                mcc == Integer.MAX_VALUE ? UNKNOWN : (mcc + ""),
                mnc == Integer.MAX_VALUE ? UNKNOWN : (mnc + ""),
                lac == Integer.MAX_VALUE ? UNKNOWN : (lac + ""),
                info.getCellSignalStrength().getDbm(),
                "lte"
        );
    }

    private CellModel(String cellId, CellType type, CellState state, String mobileCountryCode, String mobileNetworkId, String locationAreaCode, int signalStrengthDbm, String radioType) {
        this.cellId = cellId;
        this.type = type;
        this.state = state;
        this.mobileCountryCode = mobileCountryCode;
        this.mobileNetworkId = mobileNetworkId;
        this.locationAreaCode = locationAreaCode;
        this.signalStrengthDbm = signalStrengthDbm;
        this.radioType = radioType;
    }

    public boolean isValid() {
        return this.getMobileCountryCode().matches("^-?\\d+$") && this.getMobileNetworkId().matches("^-?\\d+$") && this.getLocationAreaCode().matches("^-?\\d+$") && this.getCellId().matches("^-?\\d+$");
    }

    public String getCellId() {
        return cellId;
    }

    public CellType getType() {
        return type;
    }

    public CellState getState() {
        return state;
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public String getMobileNetworkId() {
        return mobileNetworkId;
    }

    public String getLocationAreaCode() {
        return locationAreaCode;
    }

    public int getSignalStrengthDbm() {
        return signalStrengthDbm;
    }

    public String getRadioType() {
        return radioType;
    }

    @Override
    public String toString() {
        return "CellModel{" +
                "cellId='" + cellId + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", mobileCountryCode='" + mobileCountryCode + '\'' +
                ", mobileNetworkId='" + mobileNetworkId + '\'' +
                ", locationAreaCode='" + locationAreaCode + '\'' +
                ", signalStrengthDbm=" + signalStrengthDbm +
                ", radioType='" + radioType + '\'' +
                '}';
    }
}
