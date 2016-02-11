package jp.kuroneko.android.musicquiz.activities;

import static jp.kuroneko.android.musicquiz.R.id.adView;
import static jp.kuroneko.android.musicquiz.R.id.container;
import static jp.kuroneko.android.musicquiz.R.layout.activity_result;
import jp.kuroneko.android.musicquiz.fragments.ResultFragment;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

/**
 * 結果表示画面のアクティビティ
 * @author kuroneko
 *
 */
public class ResultActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			this.getFragmentManager()
				.beginTransaction()
				.add(container , new ResultFragment())
				.commit();
		}
		this.setContentView(activity_result);
		((AdView)this.findViewById(adView)).loadAd(new Builder().build());
		return;
	}

}
