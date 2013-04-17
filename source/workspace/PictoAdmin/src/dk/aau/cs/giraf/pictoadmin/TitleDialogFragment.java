package dk.aau.cs.giraf.pictoadmin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import dk.aau.cs.giraf.categorylib.PARROTCategory;
import dk.aau.cs.giraf.pictogram.Pictogram;

@SuppressLint("ValidFragment")
public class TitleDialogFragment extends DialogFragment{
	private AdminCategory startActiviy;
	private PARROTCategory changedCategory;
	private int pos;
	private boolean isCategory;
	
	public TitleDialogFragment(AdminCategory activity, PARROTCategory cat, int position, boolean isCategory) {
		this.startActiviy =  activity;
		this.changedCategory = cat;
		this.pos = position;
		this.isCategory = isCategory;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_title, null);
        final EditText titel = (EditText) layout.findViewById(R.id.titelEdit);
        
        builder.setView(layout)
        	   .setTitle("Ændre titel")
               .setPositiveButton("Færdig", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   changedCategory.setCategoryName(titel.getText().toString());
                   }
               })
               .setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
