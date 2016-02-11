package jp.kuroneko.android.musicquiz.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 全てのフラグメントコントローラのベースとなる抽象クラス
 * @author kuroneko
 *
 */
public class BaseFragmentController extends BaseController {

	/**
	 * フラグメント
	 */
	protected Fragment mFragment;

	/**
	 * 継承時にフラグメントの初期設定を自動で行いたくない場合に用いるコンストラクタ
	 */
	protected BaseFragmentController(){
		super();
		return;
	}

	/**
	 * フラグメントのコントローラのコンストラクタ
	 * @param fragment
	 * 	フラグメント
	 */
	public BaseFragmentController(Fragment fragment){
		super();
		this.setFragment(fragment);
		this.initialize();
		return;
	}

	/**
	 * フラグメントを設定する
	 * @param fragment
	 * 	フラグメント
	 */
	public void setFragment(Fragment fragment){
		this.mFragment = fragment;
		return;
	}

	/**
	 * {@link Fragment#onCreate} が呼び出された時に実行する
	 * @param savedInstanceState
	 * 	{@link Fragment#onCreate} の第一引数
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		return;
	}

	/**
	 * {@link Activity#onCreate} が呼び出された時に実行する
	 * @param savedInstanceState
	 * 	{@link Activity#onCreate} の第一引数
	 */
	public void onActivityCreate(Bundle savedInstanceState){
		return;
	}

	/**
	 * {@link Fragment#onDestroy} が呼び出された時に実行する
	 */
	@Override
	public void onDestroy(){
		return;
	}

	/**
	 * {@link Activity#onDestroy} が呼び出された時に実行する
	 */
	public void onActivityDestroy(){
		super.onDestroy();
		return;
	}

	@Override
	public void finish(){
		return;
	}

	/**
	 * {@link Fragment#onPause} が呼び出された時に実行する
	 */
	@Override
	public void onPause(){
		return;
	}

	/**
	 * {@link Activity#onPause} が呼び出された時に実行する
	 */
	public void onActivityPause(){
		super.onPause();
		return;
	}

	/**
	 * {@link Fragment#onResume} が呼び出された時に実行する
	 */
	public void onResume(){
		return;
	}

	/**
	 * {@link Activity#onResume} が呼び出された時に実行する
	 */
	public void onActivityResume(){
		super.onResume();
		return;
	}

	/**
	 * {@link Fragment#onStart} が呼び出された時に実行する
	 */
	@Override
	public void onStart(){
		return;
	}

	/**
	 * {@link Activity#onStart} が呼び出された時に実行する
	 */
	public void onActivityStart(){
		super.onStart();
		return;
	}

	/**
	 * {@link Fragment#onStop} が呼び出された時に実行する
	 */
	@Override
	public void onStop(){
		return;
	}

	/**
	 * {@link Activity#onStop} が呼び出された時に実行する
	 */
	public void onActivityStop(){
		super.onStop();
		return;
	}

	/**
	 * {@link Fragment#onAttach} が呼び出された時に実行する
	 * @param activity
	 * 	{@link Fragment#onAttach} の第一引数
	 */
	public void onAttach(Activity activity){
		this.setActivity(activity);
		return;
	}

	/**
	 * {@link Fragment#onCreateView} が呼び出された時に実行する
	 * @param result
	 * 	{@link Fragment#onCreateView} の結果値
	 * @param inflater
	 * 	{@link Fragment#onCreateView} の第一引数
	 * @param container
	 * 	{@link Fragment#onCreateView} の第二引数
	 * @param instanceState
	 * 	{@link Fragment#onCreateView} の第三引数
	 */
	public View onCreateView(View result, LayoutInflater inflater, ViewGroup container, Bundle instanceState){
		return result;
	}

	/**
	 * {@link Fragment#onActivityCreated} が呼び出された時に実行する
	 * @param anInstanceState
	 * 	{@link Fragment#onActivityCreated} の第一引数
	 */
	public void onActivityCreated(Bundle savedInstanceState){
		return;
	}

	/**
	 * {@link Fragment#onDestroyView} が呼び出された時に実行する
	 */
	public void onDestroyView(){
		return;
	}

	/**
	 * {@link Fragment#onDetach} が呼び出された時に実行する
	 */
	public void onDetach(){
		super.finish();
		this.mFragment = null;
		return;
	}

}
