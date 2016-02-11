package jp.kuroneko.android.musicquiz.fragments;

import static jp.kuroneko.android.musicquiz.R.id.btnHistry;
import static jp.kuroneko.android.musicquiz.R.id.btnStart;
import static jp.kuroneko.android.musicquiz.R.layout.fragment_main;
import jp.kuroneko.android.musicquiz.controllers.MainFragmentController;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * メイン画面のフラグメント
 * @author shimadahiroshi
 *
 */
public class MainFragment extends BaseFragment {

	/**
	 * コントローラの初期設定を行うコンストラクタ
	 */
	public MainFragment(){
		super();
		this.mController = new MainFragmentController(this);
		return;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate( fragment_main , container , false );
		MainFragmentController controller = (MainFragmentController)this.mController;
		view.findViewById(btnStart).setOnClickListener(controller);
		view.findViewById(btnHistry).setOnClickListener(controller);
		return view;
	}



}
