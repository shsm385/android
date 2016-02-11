package jp.kuroneko.android.musicquiz.ayncs;

import jp.kuroneko.android.musicquiz.controllers.BaseController;
import android.os.Handler;
import android.os.Message;

/**
 * スレッドの処理をコントローラに伝えるためのハンドラクラス
 * @author kuroneko
 *
 */
public final class ControlHandler extends Handler {

	/**
	 * スレッドでの処理を伝えるためのコントローラ
	 */
	private BaseController mController;

	/**
	 * スレッドでの処理を伝えるコントローラを初期設定するコンストラクタ
	 * @param controller
	 * 	処理を伝えるためのコントローラ
	 */
	public ControlHandler(BaseController controller){
		super();
		this.mController = controller;
		return;
	}

	/**
	 * スレッドとこのハンドラを紐付ける
	 * @param thread
	 * 	コントローラに処理結果を伝えるスレッド
	 * @param requestCode
	 * 	リクエストコード
	 */
	public void relation(BaseControlThread thread, int requestCode){
		thread.setHandler(this);
		thread.setRequestCode(requestCode);
		return;
	}

	/**
	 * 保持しているコントローラを開放する
	 */
	public void free(){
		this.mController = null;
		return;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if(this.mController != null){
			this.mController.onHandleMessage(msg.arg1, msg.arg2, msg.obj);
		}
		return;
	}

}
