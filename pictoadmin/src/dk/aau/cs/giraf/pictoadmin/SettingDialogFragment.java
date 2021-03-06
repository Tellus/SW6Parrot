package dk.aau.cs.giraf.pictoadmin;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import dk.aau.cs.giraf.categorylib.PARROTCategory;

@SuppressLint("ValidFragment")
public class SettingDialogFragment extends DialogFragment{
	AdminCategory startActivity;
	private PARROTCategory category;
	private int pos;
	private boolean isCategory;
	
	public SettingDialogFragment(AdminCategory activity, PARROTCategory cat, int position, boolean isCategory) {
		this.startActivity = activity;
		this.category = cat;
		this.pos = position;
		this.isCategory = isCategory;
	}
	
	public interface SettingDialogListener {
		public void onDialogSettingPositiveClick(DialogFragment dialog, int position);
		public void onDialogSettingNegativeClick(DialogFragment dialog);
	}
	
	SettingDialogListener listenerSetting;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Rediger kategori")
        	   .setItems(R.array.dialog_options, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Change title
					if(which == 0) {
						TitleDialogFragment titelDialog = new TitleDialogFragment(startActivity, pos, isCategory);
						titelDialog.show(getFragmentManager(), "changeTitle");
					}
					//Change color
					if(which == 1) {
						AmbilWarnaDialog colorDialog = new AmbilWarnaDialog(startActivity, category.getCategoryColor(), new OnAmbilWarnaListener() {	
							@Override
							public void onOk(AmbilWarnaDialog dialog, int color) {
								if(!isCategory){
									category = category.getSuperCategory();
								}
								category.setCategoryColor(color);
								for(PARROTCategory sc : category.getSubCategories()){
									sc.setCategoryColor(color);
								}
								startActivity.updateSettings(category, pos, isCategory, "color");
							}
							
							@Override
							public void onCancel(AmbilWarnaDialog dialog) {
								// Do nothing
							}
						});
						colorDialog.show();
					}
					//Change icon
					if(which == 2) {
						IconDialogFragment iconDialog = new IconDialogFragment(startActivity, category, pos, isCategory);
						iconDialog.show(getFragmentManager(), "changeIcon");
					}
				}
			})
               .setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
