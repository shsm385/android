package jp.kuroneko.android.musicquiz.fragments;

import static jp.kuroneko.android.musicquiz.R.layout.fragment_history;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_HISTORY;
import jp.kuroneko.android.musicquiz.controllers.HistoryFragmentController;
import jp.kuroneko.android.musicquiz.utilities.ScoreCache;
import jp.kuroneko.android.musicquiz.utilities.Utility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 結果履歴画面のフラグメント
 * @author kuroneko
 *
 */
public class HistoryFragment extends BaseFragment {

	/**
	 * コントローラの初期設定を行うコンストラクタ
	 */
	public HistoryFragment(){
		super();
		this.mController = new HistoryFragmentController(this);
		return;
	}

	/**
	 * スコア履歴のテキストを設定する
	 * @param view
	 * 	スコア履歴を親として持っているビュー
	 */
	public void setHistoryText(View view){
		int[] history = ScoreCache.getHistory();
		for(int index = 0; index < MAXIMUM_HISTORY; ++index){
			((TextView)view.findViewById(Utility.getResouoceId("rank_num" + (index + 1), "id")))
				.setText(Integer.toString(history[index]));
		}
		return;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate( fragment_history , container , false );
		this.setHistoryText(view);
		return view;
	}



}
