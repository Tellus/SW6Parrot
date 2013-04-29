package dk.aau.cs.giraf.pictoadmin;

import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.pictogram.Pictogram;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class DeleteDialogFragment extends DialogFragment {
	
	AdminCategory startActivity;
	PARROTCategory category;
	Pictogram pictogram = null;
	int pos;
	boolean isCategory;
	
	
	public DeleteDialogFragment(AdminCategory activity, PARROTCategory cat, int position, boolean yesOrNo) {
		this.startActivity = activity;
		this.category = cat;
		this.pos = position;
		this.isCategory = yesOrNo;
	}
	
	public DeleteDialogFragment(AdminCategory activity, Pictogram pic, int position, boolean yesOrNo) {
		this.startActivity = activity;
		this.pictogram = pic;
		this.pos = position;
		this.isCategory = yesOrNo;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Er du sikker p�, at du vil slette?")
               .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                   @Override
				public void onClick(DialogInterface dialog, int id) {
                	   if(pictogram == null){
                		   startActivity.updateSettings(category, pos, isCategory, "delete");
                	   }
                	   else{
                		   startActivity.updateSettings(category, pos, isCategory, "deletepictogram");
                	   }
                   }
               })
               .setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                   @Override
				public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
