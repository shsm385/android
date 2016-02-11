package jp.kuroneko.android.musicquiz.controllers;

import static android.R.string.ok;
import static jp.kuroneko.android.musicquiz.R.id.btnHistry;
import static jp.kuroneko.android.musicquiz.R.id.btnStart;
import static jp.kuroneko.android.musicquiz.R.string.alert_no_music_message;
import static jp.kuroneko.android.musicquiz.R.string.alert_no_music_title;
import static jp.kuroneko.android.musicquiz.configs.Config.MINIMUM_MUSICS;
import jp.kuroneko.android.musicquiz.activities.BaseActivity;
import jp.kuroneko.android.musicquiz.activities.GameActivity;
import jp.kuroneko.android.musicquiz.activities.HistoryActivity;
import jp.kuroneko.android.musicquiz.utilities.Utility;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * メイン画面のコントローラ
 * @author kuroneko
 *
 */
public class MainFragmentController extends BaseFragmentController implements OnClickListener{

	/**
	 * 違うページに移動しているフラグ
	 */
	private boolean mMoveFlag = false;

	/**
	 * フラグメントを用いて初期設定を行うコンストラクタ
	 * @param fragment
	 * 	このコントローラを使用するフラグメント
	 */
	public MainFragmentController(Fragment fragment){
		super(fragment);
		return;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof BaseActivity){
			((BaseActivity)activity).setFragmentController(this);
		}
		return;
	}

	@Override
	public void onActivityResume() {
		super.onActivityResume();
		this.mMoveFlag = false;
		return;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case btnStart:
				if(Utility.getAudioCount() < MINIMUM_MUSICS){
					this.showAlert();
				}
				else if(!this.mMoveFlag){
					this.mMoveFlag = true;
					this.moveActivity(GameActivity.class, false);
				}
				break;

			case btnHistry:
				if(!this.mMoveFlag){
					this.mMoveFlag = true;
					this.moveActivity(HistoryActivity.class, false);
				}
				break;

			default:

				break;
		}
		return;
	}

	/**
	 * アラートを表示する
	 */
	private void showAlert(){
		String message = String.format(Utility.getResouceString(alert_no_music_message), MINIMUM_MUSICS);
		new Builder(this.mActivity)
			.setTitle(alert_no_music_title)
			.setMessage(message)
			.setPositiveButton(ok, null)
			.create()
			.show();
		return;
	}

}
