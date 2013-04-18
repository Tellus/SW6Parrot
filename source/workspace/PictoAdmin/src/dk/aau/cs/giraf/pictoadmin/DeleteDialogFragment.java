package dk.aau.cs.giraf.pictoadmin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteDialogFragment extends DialogFragment {
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Er du sikker på, at du vil slette?")
               .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       MessageDialogFragment message = new MessageDialogFragment("Not implemented");
                       message.show(getFragmentManager(), "NotImplementMessage");
                   }
               })
               .setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
