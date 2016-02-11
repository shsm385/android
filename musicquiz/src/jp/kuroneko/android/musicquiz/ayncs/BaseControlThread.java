package jp.kuroneko.android.musicquiz.ayncs;

import android.os.Handler;
import android.os.Message;

/**
 * スレッドでの処理をコントローラに伝えるためのベースとなるスレッドクラス
 * @author kuroneko
 *
 */
public abstract class BaseControlThread extends Thread {

	/**
	 * 終了フラグ
	 */
	protected boolean mFinishFlag;

	/**
	 * スレッドに対して設定されているリクエストコード
	 */
	protected int mRequestCode;

	/**
	 * コントローラに伝えるためのハンドラ
	 */
	private Handler mHandler;

	/**
	 * スレッド処理を終了させるためのフラグ
	 */
	public void finish(){
		this.mFinishFlag = true;
		return;
	}

	/**
	 * このスレッドが終了しているかどうかを確認する
	 * @return
	 * 	スレッドが終了している場合は true を、していない場合は false を返す
	 */
	public boolean isFinished(){
		return this.mFinishFlag;
	}

	/**
	 * リクエストコードを設定する
	 * @param requestCode
	 * 	このリクエストコードは {@link ControlHandler#relation} でのみ設定が可能
	 */
	protected final void setRequestCode(int requestCode){
		this.mRequestCode = requestCode;
		return;
	}

	/**
	 * 処理結果を返すためのハンドラを設定する
	 * @param handler
	 * 	このハンドラは {@link ControlHandler#relation} でのみ設定が可能
	 */
	protected final void setHandler(Handler handler){
		this.mHandler = handler;
		return;
	}

	/**
	 * ハンドラにメッセージを送信する
	 * @param resultCode
	 * 	スレッド処理の種類
	 * @param data
	 * 	スレッドの処理結果
	 */
	protected final void sendMessage(int resultCode, Object data){
		if(!this.mFinishFlag){
			Message message = Message.obtain();
			message.arg1 = this.mRequestCode;
			message.arg2 = resultCode;
			message.obj = data;
			this.mHandler.sendMessage(message);
		}
		return;
	}

	/**
	 * 保持している変数を開放する<br/>
	 * {@link Thread#run} か {@link Runnable#run} メソッドの最後に呼び出す
	 */
	protected void free(){
		this.mHandler = null;
		return;
	}

}
