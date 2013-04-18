package dk.aau.cs.giraf.pictoadmin;

import dk.aau.cs.giraf.categorylib.PARROTCategory;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class CreateDialogFragment extends DialogFragment{
	
	String message = "";
	int position;
	boolean isCategory;
	
	public CreateDialogFragment(boolean isCategory, int pos, String msg) {
		this.isCategory = isCategory;
		this.position = pos;
		this.message = msg;
	}
	
	public interface CreateDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog, String titel, int pos, boolean isCategory);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	
	CreateDialogListener listener; 
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (CreateDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
		}
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_create, null);
        
        final EditText titelText = (EditText) layout.findViewById(R.id.username);
        
        builder.setView(layout)
        	   .setTitle("Opret ny " + message)
               .setPositiveButton("Færdig", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   listener.onDialogPositiveClick(CreateDialogFragment.this, titelText.getText().toString(), position, isCategory);
                   }
               })
               .setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       listener.onDialogNegativeClick(CreateDialogFragment.this);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	

}
