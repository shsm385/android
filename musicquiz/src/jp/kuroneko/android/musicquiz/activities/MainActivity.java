package jp.kuroneko.android.musicquiz.activities;


import static jp.kuroneko.android.musicquiz.R.id.adView;
import static jp.kuroneko.android.musicquiz.R.id.container;
import static jp.kuroneko.android.musicquiz.R.layout.activity_main;
import jp.kuroneko.android.musicquiz.fragments.MainFragment;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

/**
 * メイン画面のアクティビティ
 * @author kuroneko
 *
 */
public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			this.getFragmentManager()
				.beginTransaction()
				.add(container , new MainFragment())
				.commit();
		}
		this.setContentView(activity_main);
		((AdView)this.findViewById(adView)).loadAd(new Builder().build());
		return;
	}

}
