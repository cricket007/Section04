package com.meetingninja.csse.extras;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AlertDialogUtil {
	public static void displayDialog(Context context, String title,
			String message, String buttonText,
			DialogInterface.OnClickListener positiveListener) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton(buttonText, positiveListener).show();
	}

	public static void deleteDialog(Context context,
			DialogInterface.OnClickListener positiveListener,
			OnClickListener negativeListener) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog.setTitle("Confirm Delete...");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want delete this?");

		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("Ok", positiveListener);

		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("Cancel", negativeListener);

		// Showing Alert Message
		alertDialog.show();
	}

	public static void showErrorDialog(Context context,
			String message) {
		displayDialog(context, "Error", message, "OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
	}
}