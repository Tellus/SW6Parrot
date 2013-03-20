package dk.aau.cs.giraf.parrot;




import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;



public class OptionsFragment extends Fragment
{
	private CheckBox checkboxGradient;

	private Activity parrent;
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		this.parrent = activity;
	}
	@Override
	public void onResume() {
		
		super.onResume();
		parrent.setContentView(R.layout.options_layout);
		initColorButtons();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		parrent.setContentView(R.layout.options_layout);
	}

	private void initColorButtons() {

		Button ccc = (Button) parrent.findViewById(R.id.changecategorycolor);// these are two different buttons

		Button csc = (Button) parrent.findViewById(R.id.changesentencecolor);


		ccc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						PARROTActivity.getUser().getCategoryColor(), new OnAmbilWarnaListener() {
					@Override
					public void onCancel(AmbilWarnaDialog dialog) {
					}

					@Override
					public void onOk(AmbilWarnaDialog dialog, int color) {
						PARROTProfile user = PARROTActivity.getUser();
						user.setCategoryColor(color);
						PARROTActivity.setUser(user);
					}
				});
				dialog.show();
			}
		});

		csc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
						PARROTActivity.getUser().getSentenceBoardColor(), new OnAmbilWarnaListener() {
					@Override
					public void onCancel(AmbilWarnaDialog dialog) {
					}

					@Override
					public void onOk(AmbilWarnaDialog dialog, int color) {
						PARROTProfile user = PARROTActivity.getUser();
						user.setSentenceBoardColor(color);
						PARROTActivity.setUser(user);
					}
				});
				dialog.show();

			}
		});
	}
}
