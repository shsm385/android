package jp.kuroneko.android.musicquiz.ayncs;

import static jp.kuroneko.android.musicquiz.configs.Config.TIMER_INTERVAL;
import jp.kuroneko.android.musicquiz.configs.Config;
import jp.kuroneko.android.musicquiz.controllers.BaseController;

/**
 * タイマとなるスレッドクラス<br/>
 * このタイマスレッドを実行した際に渡される情報は<br/>
 * {@link BaseController#onHandleMessage} に渡される情報は以下の通りに成る<br/>
 * 	<table>
 * 		<tr><th>第一引数</th><th>第二引数</th><th>第三引数</th></tr>
 * 		<tr><td>指定したリクエストコード</td><td>{@link TimerThread#REMAINING_TIME}</td><td>{@link Long} 型で残り時間 [ms]</td></tr>
 * 		<tr><td>指定したリクエストコード</td><td>{@link TimerThread#TIMER_FINISHED}</td><td>{@link Long} 型で 0</td></tr>
 * 	</table>
 * @author kuroneko
 *
 */
public class TimerThread extends BaseControlThread {

	/**
	 * タイマの残り時間のリザルトコード
	 */
	public static final int REMAINING_TIME = 1;

	/**
	 * タイマが終了した場合のリザルトコード
	 */
	public static final int TIMER_FINISHED = 2;

	/**
	 * タイマに設定された時間 [ms]
	 */
	private long mTimer;

	/**
	 * インターバル時間 [ms]
	 */
	private long mInterval = TIMER_INTERVAL;

	/**
	 * タイマを設定するコンストラクタ<br/>
	 * インターバルタイムはデフォルト値である {@link Config#TIMER_INTERVAL} を使用する
	 * @param timer
	 * 	タイマに設定する時間 [ms]
	 */
	public TimerThread(long timer) {
		super();
		if(timer <= 0){
			throw new RuntimeException("illegal time exception: timer=" + timer);
		}
		this.mTimer = timer;
		return;
	}

	/**
	 * タイマを設定するコンストラクタ
	 * @param timer
	 * 	タイマに設定する時間 [ms]
	 * @param interval
	 *  タイマの経過時間を確認するインターバルタイム
	 */
	public TimerThread(long timer, long interval) {
		this(timer);
		if(interval <= 0){
			throw new RuntimeException("illegal time exception: interval=" + interval);
		}
		this.mInterval = interval;
		return;
	}

	@Override
	public void run() {
		super.run();
		long current = System.currentTimeMillis();
		long end = current + this.mTimer;
		while(!this.mFinishFlag && current < end){
			this.sendMessage(REMAINING_TIME, Long.valueOf(end - current));
			try {
				Thread.sleep(this.mInterval);
			} catch (InterruptedException e) {
				break;
			}
			current = System.currentTimeMillis();
		}
		this.sendMessage(TIMER_FINISHED, Long.valueOf(0L));
		this.free();
		return;
	}

}
