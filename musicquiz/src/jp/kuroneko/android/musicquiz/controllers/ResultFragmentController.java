package jp.kuroneko.android.musicquiz.controllers;

import static jp.kuroneko.android.musicquiz.R.id.btnRetry;
import static jp.kuroneko.android.musicquiz.R.id.btnTitle;
import jp.kuroneko.android.musicquiz.activities.BaseActivity;
import jp.kuroneko.android.musicquiz.activities.GameActivity;
import jp.kuroneko.android.musicquiz.activities.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 結果表示画面のコントローラ
 * @author kuroneko
 *
 */
public class ResultFragmentController extends BaseFragmentController implements OnClickListener{

	/**
	 * 違うページに移動しているフラグ
	 */
	private boolean mMoveFlag = false;

	/**
	 * フラグメントを用いて初期設定を行うコンストラクタ
	 * @param fragment
	 * 	このコントローラを使用するフラグメント
	 */
	public ResultFragmentController(Fragment fragment){
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
			case btnTitle:
				if(!this.mMoveFlag){
					this.mMoveFlag = true;
					this.moveActivity(MainActivity.class, true);
				}
				break;

			case btnRetry:
				if(!this.mMoveFlag){
					this.mMoveFlag = true;
					this.moveActivity(GameActivity.class, true);
				}
				break;

			default:
				break;

		}
		return;
	}

}
