package at.tuwien.mns17.androidgeolocation.location_report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

import at.tuwien.mns17.androidgeolocation.R;
import at.tuwien.mns17.androidgeolocation.model.GPSModel;
import at.tuwien.mns17.androidgeolocation.model.MozillaResponse;

public class LocationReportEmailSenderImpl implements LocationReportEmailSender {

    private static final String TAG = LocationReportRepositoryImpl.class.getName();

    private static final String MIME_TYPE = "text/plain";
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public void sendEmail(Context context, LocationReport report) {
        Log.d(TAG, "Sending location report per email: " + report);
        Uri attachmentUri = getAttachmentUri(createEmailText(context, report));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(MIME_TYPE);
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.location_report_email_subject));
        intent.putExtra(Intent.EXTRA_STREAM, attachmentUri);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            Log.d(TAG, "Location report sent: " + report);
        } else {
            Log.w(TAG, "No application found to handle Intent.ACTION_SEND");
        }
    }

    private Uri getAttachmentUri(String content) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            throw new RuntimeException("Media not mounted");
        } else {
            File attachmentDir = new File(
                    Environment.getExternalStorageDirectory(),
                    "Android/data/at.tuwien.mns17.androidgeolocation/attachments"
            );
            if (!attachmentDir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                attachmentDir.mkdir();
            }

            try {
                File attachmentFile = new File(attachmentDir, UUID.randomUUID().toString() + ".txt");
                FileUtils.writeStringToFile(attachmentFile, content, UTF_8);
                return Uri.fromFile(attachmentFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String createEmailText(Context context, LocationReport report) {
        GPSModel gps = report.getGPS();
        MozillaResponse mozillaResponse = report.getMozillaResponse();
        String time = report.getTime();
        float distanceBetweenMLSandGPS = report.getDistanceBetweenMLSandGPS();

        String gpsText = String.format("%s %s %s",
                nullSafeString(context, gps.getLongitude()),
                nullSafeString(context, gps.getLatitude()),
                nullSafeString(context, gps.getAccuracy())
        );

        return String.format("%s: %s", context.getString(R.string.location_report_email_text_name), report.getName()) + "\n" +
                String.format("%s: %s", context.getString(R.string.location_report_email_text_time), time) + "\n" +
                String.format("%s: %s", context.getString(R.string.location_report_email_text_gps), gpsText) + "\n" +
                String.format("%s: %s", context.getString(R.string.location_report_email_text_mlp_response), mozillaResponse.toString()) + "\n" +
                String.format("%s: %s", context.getString(R.string.location_report_email_text_distance_mlp_gps), distanceBetweenMLSandGPS);
    }

    private String nullSafeString(Context context, Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return context.getString(R.string.location_report_email_text_not_available);
        }
    }
}
