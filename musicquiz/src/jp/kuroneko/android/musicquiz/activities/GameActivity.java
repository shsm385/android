package jp.kuroneko.android.musicquiz.activities;

import static jp.kuroneko.android.musicquiz.R.id.container;
import static jp.kuroneko.android.musicquiz.R.layout.activity_game;
import jp.kuroneko.android.musicquiz.fragments.GameFragment;
import android.os.Bundle;


/**
 * ゲーム画面のアクティビティ
 * @author kuroneko
 *
 */
public class GameActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			this.getFragmentManager()
				.beginTransaction()
				.add(container , new GameFragment())
				.commit();
		}
		this.setContentView(activity_game);


		return;
	}

}
