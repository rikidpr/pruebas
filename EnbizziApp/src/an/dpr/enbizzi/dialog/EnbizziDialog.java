package an.dpr.enbizzi.dialog;

import an.dpr.enbizzi.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class EnbizziDialog extends DialogFragment {
	
	private static final String MSG = "MSG";
	private static final String TITLE = "TITLE";

	public static EnbizziDialog newInstance(String title, String msg) {
		EnbizziDialog frag = new EnbizziDialog();
		Bundle args = new Bundle();
		args.putString(TITLE, title);
		args.putString(MSG, msg);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		String msg = getArguments().getString("msg");

		AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
//		ad.setIcon(R.drawable.alert_dialog_icon);
		ad.setTitle(title);
		ad.setMessage(msg);
		return ad.create();
//		ad.setPositiveButton(R.string.alert_dialog_ok,
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int whichButton) {
//								((FragmentAlertDialog) getActivity())
//										.doPositiveClick();
//							}
//						})
//				.setNegativeButton(R.string.alert_dialog_cancel,
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int whichButton) {
//								((FragmentAlertDialog) getActivity())
//										.doNegativeClick();
//							}
//						}).create();
	}
}
