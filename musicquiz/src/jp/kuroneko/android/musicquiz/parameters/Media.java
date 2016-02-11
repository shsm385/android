package jp.kuroneko.android.musicquiz.parameters;

import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.DATE_MODIFIED;
import static android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.MediaColumns.TITLE;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.LONG;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.STRING;
import android.database.Cursor;

/**
 * メディア情報のパラメータクラス
 * @author kuroneko
 *
 */
public class Media extends ProviderData {

	/**
	 * データベースから情報の自動取得する場合のカラムと格納するフィールド
	 */
	private static final AutoColumnData[] COLUMN_DATA = new AutoColumnData[]{
		new AutoColumnData(DATA, "mData", STRING),
		new AutoColumnData(DATE_ADDED, "mDateAdded", LONG),
		new AutoColumnData(DATE_MODIFIED, "mDateModified", LONG),
		new AutoColumnData(DISPLAY_NAME, "mDisplayName", STRING),
		new AutoColumnData(MIME_TYPE, "mMimeType", STRING),
		new AutoColumnData(SIZE, "mSize", LONG),
		new AutoColumnData(TITLE, "mTitle", STRING)
	};

	/**
	 * 実データのパス
	 */
	protected String mData;

	/**
	 * メディアプロバイダに追加された時間。1970年からの秒数(UnixTime)
	 */
	protected long mDateAdded;

	/**
	 * 最後の更新された時間。1970年からの秒数(UnixTime)
	 */
	protected long mDateModified;

	/**
	 * ディスプレイ名
	 */
	protected String mDisplayName;

	/**
	 * MIME タイプ
	 */
	protected String mMimeType;

	/**
	 * サイズ
	 */
	protected long mSize;

	/**
	 * タイトル
	 */
	protected String mTitle;

	/**
	 * 初期設定を行わないコンストラクタ
	 */
	public Media(){
		super();
		return;
	}

	/**
	 * 各カラムの情報から自動的にフィールドを埋めるためのコンストラクタ
	 * @param cursor
	 * 	各カラム情報
	 */
	public Media(Cursor cursor){
		super(cursor);
		try {
			this.setFieldsFromCursor(Media.class, cursor, COLUMN_DATA);
		} catch (IllegalArgumentException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		return;
	}

	/**
	 * 実データのパスを取得する
	 * @return
	 * 	実データのパス
	 */
	public String getData() {
		return this.mData;
	}

	/**
	 * 実データのパスを設定する
	 * @param data
	 * 	実データのパス
	 */
	public void setData(String data) {
		this.mData = data;
		return;
	}

	/**
	 * メディアプロバイダに追加された時間を取得する
	 * @return
	 * 	1970年からの秒数(UnixTime)
	 */
	public long getDateAdded() {
		return this.mDateAdded;
	}

	/**
	 * メディアプロバイダに追加された時間を取得する
	 * @param time
	 * 	1970年からの秒数(UnixTime)
	 */
	public void setDateAdded(long time) {
		this.mDateAdded = time;
		return;
	}

	/**
	 * 最後の更新された時間を取得する
	 * @return
	 * 	1970年からの秒数(UnixTime)
	 */
	public long getDateModified() {
		return this.mDateModified;
	}

	/**
	 * 最後の更新された時間を設定する
	 * @param time
	 * 	1970年からの秒数(UnixTime)
	 */
	public void setDateModified(long time) {
		this.mDateModified = time;
		return;
	}

	/**
	 * ディスプレイ名を取得する
	 * @return
	 * 	ディスプレイ名
	 */
	public String getDisplayName() {
		return this.mDisplayName;
	}

	/**
	 * ディスプレイ名を設定する
	 * @param name
	 * 	ディスプレイ名
	 */
	public void setDisplayName(String name) {
		this.mDisplayName = name;
		return;
	}

	/**
	 * MIME タイプを取得する
	 * @return
	 * 	MIME タイプ
	 */
	public String getMimeType() {
		return this.mMimeType;
	}

	/**
	 * MIME タイプを設定する
	 * @param type
	 * 	MIME タイプ
	 */
	public void setMimeType(String type) {
		this.mMimeType = type;
		return;
	}

	/**
	 * ファイルサイズを取得する
	 * @return
	 * 	ファイルサイズ
	 */
	public long getSize() {
		return this.mSize;
	}

	/**
	 * ファイルサイズを設定する
	 * @param size
	 * 	ファイルサイズ
	 */
	public void setSize(long size) {
		this.mSize = size;
		return;
	}

	/**
	 * タイトルを取得する
	 * @return
	 * 	タイトル
	 */
	public String getTitle() {
		return this.mTitle;
	}

	/**
	 * タイトルを設定する
	 * @param title
	 * 	タイトル
	 */
	public void setTitle(String title) {
		this.mTitle = title;
		return;
	}

}
