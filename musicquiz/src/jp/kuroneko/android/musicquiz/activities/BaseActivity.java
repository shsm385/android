package jp.kuroneko.android.musicquiz.activities;

import static android.view.KeyEvent.KEYCODE_BACK;
import jp.kuroneko.android.musicquiz.controllers.BaseController;
import jp.kuroneko.android.musicquiz.controllers.BaseFragmentController;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 全てのアクティビティのベースとなる抽象クラス
 * @author kuroneko
 *
 */
public abstract class BaseActivity extends Activity{

	/**
	 * コントローラインスタンス
	 */
	protected BaseController mController;

	/**
	 * フラグメントが保持しているコントローラインスタンス
	 */
	protected BaseFragmentController mFragmentController;

	/**
	 * コントローラを設定する
	 * @param controller
	 * 	コントローラ
	 */
	public void setController(BaseController controller){
		this.mController = controller;
		return;
	}

	/**
	 * フラグメントが保持しているコントローラを設定する
	 * @param controller
	 * 	コントローラ
	 */
	public void setFragmentController(BaseFragmentController controller){
		this.mFragmentController = controller;
		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(this.mController != null){
			this.mController.onCreate(savedInstanceState);
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onActivityCreate(savedInstanceState);
		}
		return;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(this.mController != null){
			if(this.mController.isEnabledForBack() || keyCode != KEYCODE_BACK){
				return this.mController.onKeyDown(super.onKeyDown(keyCode, event), keyCode, event);
			}
			else{
				return this.mController.onKeyDown(false, keyCode, event);
			}
		}
		else if(this.mFragmentController != null){
			if(this.mFragmentController.isEnabledForBack() || keyCode != KEYCODE_BACK){
				return this.mFragmentController.onKeyDown(super.onKeyDown(keyCode, event), keyCode, event);
			}
			else{
				return this.mFragmentController.onKeyDown(false, keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void finish(){
		if(this.mController != null){
			this.mController.finish();
		}
		if(this.mFragmentController != null){
			this.mFragmentController.finish();
		}
		super.finish();
		return;
	}

	@Override
	protected void onDestroy(){
		if(this.mController != null){
			this.mController.onDestroy();
			this.mController = null;
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onActivityDestroy();
			this.mController = null;
		}
		super.onDestroy();
		return;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(this.mController != null){
			this.mController.onActivityResult(requestCode, resultCode, data);
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onActivityResult(requestCode, resultCode, data);
		}
		return;
	}

	@Override
	protected void onPause(){
		super.onPause();
		if(this.mController != null){
			this.mController.onPause();
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onActivityPause();
		}
		return;
	}

	@Override
	protected void onRestart(){
		super.onRestart();
		if(this.mController != null){
			this.mController.onRestart();
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onRestart();
		}
		return;
	}

	@Override
	protected void onResume(){
		super.onResume();
		BaseController.setCurrentActivity(this);
		if(this.mController != null){
			this.mController.onResume();
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onActivityResume();
		}
		return;
	}

	@Override
	protected void onStart(){
		super.onStart();
		if(this.mController != null){
			this.mController.onStart();
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onActivityStart();
		}
		return;
	}

	@Override
	protected void onStop(){
		super.onStop();
		if(this.mController != null){
			this.mController.onStop();
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onActivityStop();
		}
		return;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		if(this.mController != null){
			this.mController.onSaveInstanceState(outState);
		}
		if(this.mFragmentController != null){
			this.mFragmentController.onSaveInstanceState(outState);
		}
		return;
	}

}
