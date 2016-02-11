package jp.kuroneko.android.musicquiz.fragments;

import jp.kuroneko.android.musicquiz.controllers.BaseFragmentController;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 全てのフラグメントのベースとなる抽象クラス
 * @author kuroneko
 *
 */
public abstract class BaseFragment extends Fragment{

	/**
	 * コントローラインスタンス
	 */
	protected BaseFragmentController mController;

	/**
	 * コントローラを設定する
	 * @param controller
	 * 	コントローラ
	 */
	public void setController(BaseFragmentController controller){
		this.mController = controller;
		return;
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(this.mController != null){
			this.mController.onAttach(activity);
		}
		return;
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(this.mController != null){
			this.mController.onCreate(savedInstanceState);
		}
		return;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View result = super.onCreateView(inflater, container, savedInstanceState);
		if(this.mController != null){
			return this.mController.onCreateView(result, inflater, container, savedInstanceState);
		}
		return result;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if(this.mController != null){
			this.mController.onActivityCreated(savedInstanceState);
		}
		return;
	}

	@Override
	public void onStart(){
		super.onStart();
		if(this.mController != null){
			this.mController.onStart();
		}
		return;
	}

	@Override
	public void onResume(){
		super.onResume();
		if(this.mController != null){
			this.mController.onResume();
		}
		return;
	}

	@Override
	public void onPause(){
		super.onPause();
		if(this.mController != null){
			this.mController.onPause();
		}
		return;
	}

	@Override
	public void onStop(){
		super.onStop();
		if(this.mController != null){
			this.mController.onStop();
		}
		return;
	}

	@Override
	public void onDestroyView(){
		super.onDestroyView();
		if(this.mController != null){
			this.mController.onDestroyView();
		}
		return;
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		if(this.mController != null){
			this.mController.onDestroy();
		}
		return;
	}

	@Override
	public void onDetach(){
		super.onDetach();
		if(this.mController != null){
			this.mController.onDetach();
		}
		return;
	}

}
