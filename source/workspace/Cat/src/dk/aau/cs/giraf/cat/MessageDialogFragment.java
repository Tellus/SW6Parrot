package dk.aau.cs.giraf.cat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class MessageDialogFragment extends DialogFragment {
	
	public String message = "default message";
	
	public MessageDialogFragment(String msg) {
		this.message = msg;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
