package dk.aau.cs.giraf.oasis.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;

/**
 * Fragment which is used for listing all applications in the system
 * 
 * @author Oasis
 *
 */
public class ChildProfileFrag extends Fragment {

	private Button bMedia, bApps, bSettings;
	private TextView tvName, tvPhone;
	private EditText tName, tPhone;
	private ImageView pPic, iProfileImg;
	private String name, imgPath, tmpPath;
	private Helper helper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.myprofile_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity());

		pPic = (ImageView) getView().findViewById(R.id.ivProfilePicture);
		imgPath = MainActivity.child.getPicture();
		if (imgPath != null) {
			Bitmap bm = BitmapFactory.decodeFile(imgPath);
			pPic.setImageBitmap(bm);
		} else {
			pPic.setImageResource(R.drawable.no_profile_pic);
		}


		tvName = (TextView) getView().findViewById(R.id.tvMyProfileName);

		name = MainActivity.child.getFirstname();
		if (MainActivity.child.getMiddlename() != null) {
			name += " " + MainActivity.child.getMiddlename();
		}
		name += " " + MainActivity.child.getSurname();
		tvName.setText(name);
		tvPhone = (TextView) getView().findViewById(R.id.tvMyProfilePhone);
		tvPhone.setText("" + MainActivity.child.getPhone());

		bMedia = (Button) getView().findViewById(R.id.bMyProfileMedia);
		bMedia.setText(R.string.childmedia);
		bMedia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragParentTab.switchTab(FragParentTab.TABCHILDMEDIA);
			}
		});

		bApps = (Button) getView().findViewById(R.id.bMyProfileApps);
		bApps.setText(R.string.childapps);
		bApps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragParentTab.switchTab(FragParentTab.TABCHILDAPP);
			}
		});

		bSettings = (Button) getView().findViewById(R.id.bMyProfileSettings);
		bSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setCancelable(true);
				builder.setTitle(R.string.mySettingsButton);
				LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = mInflater.inflate(R.layout.modifyprofile_view, null, false);
				tName = (EditText) view.findViewById(R.id.etProfileName);
				tName.setText(name);
				tPhone = (EditText) view.findViewById(R.id.etProfilePhone);
				tPhone.setText(""+MainActivity.child.getPhone());
				iProfileImg = (ImageView) view.findViewById(R.id.iProfileImg);
				if (imgPath != null) {
					Bitmap bm = BitmapFactory.decodeFile(imgPath);
					iProfileImg.setImageBitmap(bm);
				} else {
					iProfileImg.setImageResource(R.drawable.no_profile_pic);
				}
				
				Button bImg = (Button) view.findViewById(R.id.bProfileImg);
				bImg.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						List<Media> valueList = helper.mediaHelper.getMyPictures(MainActivity.child);
						List<String> items = new ArrayList<String>();
						for (Media m : valueList) {
							items.add(m.getMPath());
						}
						
						final tmpListAdapter adapter = new tmpListAdapter(getActivity(), R.layout.children_list_childitem, items);
						
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			            builder.setTitle("Vælg et Profil Billede");
			            builder.setAdapter(adapter,
			                    new DialogInterface.OnClickListener() {
			                        @Override
			                        public void onClick(DialogInterface dialog, int item) {
										tmpPath = adapter.getItem(item);
										if (tmpPath != null) {
											Bitmap bm = BitmapFactory.decodeFile(tmpPath);
											iProfileImg.setImageBitmap(bm);
										} else {
											iProfileImg.setImageResource(R.drawable.no_profile_pic);
										}
			                        	
			                            dialog.dismiss();
			                        }
			                    });
			            builder.setNegativeButton("Tilbage", null);
			            AlertDialog alert = builder.create();
			            alert.show();
					}
				});
				
				builder.setView(view);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String newName = tName.getText().toString();
						String tmp[] = newName.split(" ");
						String first = tmp[0];
						String middle = "";
						for (int i = 1; i < tmp.length-1; i++) {
							middle += tmp[i] + " ";
						}
						if (middle.equals("")) {
							middle = null;
						}
						String last = "";
						if (tmp.length > 1) {
							last = tmp[tmp.length-1];
						}
						String newPhone = tPhone.getText().toString();
						MainActivity.child.setFirstname(first);
						MainActivity.child.setSurname(last);
						MainActivity.child.setMiddlename(middle);
						MainActivity.child.setPhone(Long.parseLong(newPhone));
						MainActivity.child.setPicture(tmpPath);
						helper.profilesHelper.modifyProfile(MainActivity.child);

						name = newName;
						tvName.setText(newName);
						tvPhone.setText(newPhone);
						imgPath = tmpPath;
						if (imgPath != null) {
							Bitmap bm = BitmapFactory.decodeFile(imgPath);
							pPic.setImageBitmap(bm);
						} else {
							pPic.setImageResource(R.drawable.no_profile_pic);
						}
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("Afbryd", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
}
