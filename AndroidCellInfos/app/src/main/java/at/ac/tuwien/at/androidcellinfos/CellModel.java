package at.ac.tuwien.at.androidcellinfos;

import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;

public class CellModel {

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
        return new CellModel(
                info.getCellIdentity().getCid() + "",
                CellType.GSM,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                info.getCellIdentity().getMcc() + "",
                info.getCellIdentity().getMnc() + "",
                info.getCellIdentity().getLac() + "",
                info.getCellSignalStrength().getDbm()
        );
    }

    private static CellModel from(CellInfoCdma info) {
        return new CellModel(
                "N/A",
                CellType.CDMA,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                "N/A",
                info.getCellIdentity().getNetworkId() + "",
                "N/A",
                info.getCellSignalStrength().getDbm()
        );
    }

    private static CellModel from(CellInfoWcdma info) {
        return new CellModel(
                info.getCellIdentity().getCid() + "",
                CellType.UMTS,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                info.getCellIdentity().getMcc() + "",
                info.getCellIdentity().getMnc() + "",
                info.getCellIdentity().getLac() + "",
                info.getCellSignalStrength().getDbm()
        );
    }

    private static CellModel from(CellInfoLte info) {
        return new CellModel(
                info.getCellIdentity().getCi() + "",
                CellType.LTE,
                info.isRegistered() ? CellState.ACTIVE : CellState.NEIGHBORING,
                info.getCellIdentity().getMcc() + "",
                info.getCellIdentity().getMnc() + "",
                info.getCellIdentity().getTac() + "",
                info.getCellSignalStrength().getDbm()
        );
    }


    private CellModel(String cellId, CellType type, CellState state, String mobileCountryCode, String mobileNetworkId, String locationAreaCode, int signalStrengthDbm) {
        this.cellId = cellId;
        this.type = type;
        this.state = state;
        this.mobileCountryCode = mobileCountryCode;
        this.mobileNetworkId = mobileNetworkId;
        this.locationAreaCode = locationAreaCode;
        this.signalStrengthDbm = signalStrengthDbm;
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
                '}';
    }
}
