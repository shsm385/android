package jp.kuroneko.android.musicquiz.parameters;

import static android.provider.MediaStore.Audio.AudioColumns.ALBUM;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM_ID;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM_KEY;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST_ID;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST_KEY;
import static android.provider.MediaStore.Audio.AudioColumns.BOOKMARK;
import static android.provider.MediaStore.Audio.AudioColumns.COMPOSER;
import static android.provider.MediaStore.Audio.AudioColumns.DURATION;
import static android.provider.MediaStore.Audio.AudioColumns.IS_ALARM;
import static android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC;
import static android.provider.MediaStore.Audio.AudioColumns.IS_NOTIFICATION;
import static android.provider.MediaStore.Audio.AudioColumns.IS_PODCAST;
import static android.provider.MediaStore.Audio.AudioColumns.IS_RINGTONE;
import static android.provider.MediaStore.Audio.AudioColumns.TITLE_KEY;
import static android.provider.MediaStore.Audio.AudioColumns.TRACK;
import static android.provider.MediaStore.Audio.AudioColumns.YEAR;
import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.BOOLEAN;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.INTEGER;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.LONG;
import static jp.kuroneko.android.musicquiz.parameters.AutoColumnData.ColumnType.STRING;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

/**
 * オーディオ情報
 * @author kuroneko
 *
 */
public class Audio extends Media{

	/**
	 * データベースから情報の自動取得する場合のカラムと格納するフィールド
	 */
	private static final AutoColumnData[] COLUMN_DATA= {
		new AutoColumnData(ALBUM, "mAlbum", STRING),
		new AutoColumnData(ALBUM_ID, "mAlbumId", LONG),
		new AutoColumnData(ALBUM_KEY, "mAlbumKey", STRING),
		new AutoColumnData(ARTIST, "mArtist", STRING),
		new AutoColumnData(ARTIST_ID, "mArtistId", LONG),
		new AutoColumnData(ARTIST_KEY, "mArtistKey", STRING),
		new AutoColumnData(BOOKMARK, "mBookmark", LONG),
		new AutoColumnData(COMPOSER, "mComporse", STRING),
		new AutoColumnData(DURATION, "mDuration", LONG),
		new AutoColumnData(IS_ALARM, "mAlarmFlag", BOOLEAN),
		new AutoColumnData(IS_MUSIC, "mMusicFlag", BOOLEAN),
		new AutoColumnData(IS_NOTIFICATION, "mNotificationFlag", BOOLEAN),
		new AutoColumnData(IS_PODCAST, "mPodcastFlag", BOOLEAN),
		new AutoColumnData(IS_RINGTONE, "mRingtoneFlag", BOOLEAN),
		new AutoColumnData(TITLE_KEY, "mTitleKey", STRING),
		new AutoColumnData(TRACK, "mTrack", INTEGER),
		new AutoColumnData(YEAR, "mYear", INTEGER)
	};

	/**
	 * アルバム
	 */
	protected String mAlbum;

	/**
	 * アルバム ID
	 */
	protected long mAlbumId;

	/**
	 * アルバムから算出された人以外が読み取り可能なキーで、検索、ソート、グルーピングに使用される
	 */
	protected String mAlbumKey;

	/**
	 * アーティスト
	 */
	protected String mArtist;

	/**
	 * アーティスト ID
	 */
	protected long mArtistId;

	/**
	 * アーティストから算出された人以外が読み取り可能なキーで、検索、ソート、グルーピングに使用される
	 */
	protected String mArtistKey;

	/**
	 * ファイルが最後に再生を停止した位置[ms]
	 */
	protected long mBookmark;

	/**
	 * 作曲者
	 */
	protected String mComporse;

	/**
	 * 持続時間
	 */
	protected long mDuration;

	/**
	 * アラーム使用可能フラグ
	 */
	protected boolean mAlarmFlag;

	/**
	 * ミュージックフラグ
	 */
	protected boolean mMusicFlag;

	/**
	 * 通知音使用可能フラグ
	 */
	protected boolean mNotificationFlag;

	/**
	 * ポッドキャストフラグ
	 */
	protected boolean mPodcastFlag;

	/**
	 * 着信音使用可能フラグ
	 */
	protected boolean mRingtoneFlag;

	/**
	 * タイトルから算出された人以外が読み取り可能なキーで、検索、ソート、グルーピングに使用される
	 */
	protected String mTitleKey;

	/**
	 * トラック名
	 */
	protected int mTrack;

	/**
	 * 年
	 */
	protected int mYear;

	/**
	 * オーディオ情報の URI
	 */
	protected Uri mUri;

	/**
	 * 初期設定を行わないコンストラクタ
	 */
	public Audio(){
		super();
		return;
	}

	/**
	 * 各カラムの情報から自動的にフィールドを埋めるためのコンストラクタ
	 * @param cursor
	 * 	各カラム情報
	 */
	public Audio(Cursor cursor){
		super(cursor);
		try {
			this.setFieldsFromCursor(Audio.class, cursor, COLUMN_DATA);
		} catch (IllegalArgumentException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		this.setUri(ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, this.mId));
		return;
	}

	/**
	 * アルバムを取得する
	 * @return
	 *	アルバム
	 */
	public String getAlbum(){
		return this.mAlbum;
	}

	/**
	 * アルバムを設定する
	 * @param album
	 *	アルバム
	 */
	public void setAlbum(String album){
		this.mAlbum = album;
		return;
	}

	/**
	 * アルバム IDを取得する
	 * @return
	 *	アルバム ID
	 */
	public long getAlbumId(){
		return this.mAlbumId;
	}

	/**
	 * アルバム IDを設定する
	 * @param albumId
	 *	アルバム ID
	 */
	public void setAlbumId(long albumId){
		this.mAlbumId = albumId;
		return;
	}

	/**
	 * アルバムから算出された人以外が読み取り可能なキーを取得する。<br/>
	 * 検索、ソート、グルーピングに使用される
	 * @return
	 *	アルバムから算出された人以外が読み取り可能なキー
	 */
	public String getAlbumKey(){
		return this.mAlbumKey;
	}

	/**
	 * アルバムから算出された人以外が読み取り可能なキーを取得する<br/>
	 * 検索、ソート、グルーピングに使用される
	 * @param albumKey
	 *	アルバムから算出された人以外が読み取り可能なキー
	 */
	public void setAlbumKey(String albumKey){
		this.mAlbumKey = albumKey;
		return;
	}

	/**
	 * アーティストを取得する
	 * @return
	 *	アーティスト
	 */
	public String getArtist(){
		return this.mArtist;
	}

	/**
	 * アーティストを設定する
	 * @param artist
	 *	アーティスト
	 */
	public void setArtist(String artist){
		this.mArtist = artist;
		return;
	}

	/**
	 * アーティスト IDを取得する
	 * @return
	 *	アーティスト ID
	 */
	public long getArtistId(){
		return this.mArtistId;
	}

	/**
	 * アーティスト IDを設定する
	 * @param artistId
	 *	アーティスト ID
	 */
	public void setArtistId(long artistId){
		this.mArtistId = artistId;
		return;
	}

	/**
	 * アーティストから算出された人以外が読み取り可能なキーを設定する<br/>
	 * 検索、ソート、グルーピングに使用される
	 * @return
	 *	アーティストから算出された人以外が読み取り可能なキー
	 */
	public String getArtistKey(){
		return this.mArtistKey;
	}

	/**
	 * アーティストから算出された人以外が読み取り可能なキーを設定する<br/>
	 * 検索、ソート、グルーピングに使用される
	 * @param artistKey
	 *	アーティストから算出された人以外が読み取り可能なキー
	 */
	public void setArtistKey(String artistKey){
		this.mArtistKey = artistKey;
		return;
	}

	/**
	 * ファイルが最後に再生を停止した位置[ms]を取得する
	 * @return
	 *	ファイルが最後に再生を停止した位置[ms]
	 */
	public long getBookmark(){
		return this.mBookmark;
	}

	/**
	 * ファイルが最後に再生を停止した位置[ms]を設定する
	 * @param bookmark
	 *	ファイルが最後に再生を停止した位置[ms]
	 */
	public void setBookmark(long bookmark){
		this.mBookmark = bookmark;
		return;
	}

	/**
	 * 作曲者を取得する
	 * @return
	 *	作曲者
	 */
	public String getComporse(){
		return this.mComporse;
	}

	/**
	 * 作曲者を設定する
	 * @param comporse
	 *	作曲者
	 */
	public void setComporse(String comporse){
		this.mComporse = comporse;
		return;
	}

	/**
	 * 持続時間を取得する
	 * @return
	 *	持続時間
	 */
	public long getDuration(){
		return this.mDuration;
	}

	/**
	 * 持続時間を設定する
	 * @param duration
	 *	持続時間
	 */
	public void setDuration(long duration){
		this.mDuration = duration;
		return;
	}

	/**
	 * アラーム使用可能かどうかを取得する
	 * @return
	 *	アラーム使用可能かどうか
	 */
	public boolean isAlarm(){
		return this.mAlarmFlag;
	}

	/**
	 * アラーム使用可能かどうかを設定する
	 * @param flag
	 *	アラーム使用可能かどうか
	 */
	public void setIsAlarm(boolean flag){
		this.mAlarmFlag = flag;
		return;
	}

	/**
	 * ミュージックかどうかを取得する
	 * @return
	 *	ミュージックかどうか
	 */
	public boolean isMusic(){
		return this.mMusicFlag;
	}

	/**
	 * ミュージックかどうかを設定する
	 * @param flag
	 *	ミュージックかどうか
	 */
	public void setIsMusic(boolean flag){
		this.mMusicFlag = flag;
		return;
	}

	/**
	 * 通知音使用可能かどうかを取得する
	 * @return
	 *	通知音使用可能かどうか
	 */
	public boolean isNotification(){
		return this.mNotificationFlag;
	}

	/**
	 * 通知音使用可能かどうかを設定する
	 * @param flag
	 *	通知音使用可能かどうか
	 */
	public void setIsNotification(boolean flag){
		this.mNotificationFlag = flag;
		return;
	}

	/**
	 * ポッドキャストかどうかを取得する
	 * @return
	 *	ポッドキャストかどうか
	 */
	public boolean isPodcast(){
		return this.mPodcastFlag;
	}

	/**
	 * ポッドキャストかどうかを設定する
	 * @param flag
	 *	ポッドキャストかどうか
	 */
	public void setIsPodcast(boolean flag){
		this.mPodcastFlag = flag;
		return;
	}

	/**
	 * 着信音使用可能かどうかを取得する
	 * @return
	 *	着信音使用可能かどうか
	 */
	public boolean isRingtone(){
		return this.mRingtoneFlag;
	}

	/**
	 * 着信音使用可能かどうかを設定する
	 * @param flag
	 *	着信音使用可能かどうか
	 */
	public void setIsRingtone(boolean flag){
		this.mRingtoneFlag = flag;
		return;
	}

	/**
	 * タイトルから算出された人以外が読み取り可能なキーを取得する<br/>
	 * 検索、ソート、グルーピングに使用される
	 * @return
	 *	タイトルから算出された人以外が読み取り可能なキー
	 */
	public String getTitleKey(){
		return this.mTitleKey;
	}

	/**
	 * タイトルから算出された人以外が読み取り可能なキーを設定する<br/>
	 * 検索、ソート、グルーピングに使用される
	 * @param titleKey
	 *	タイトルから算出された人以外が読み取り可能なキー
	 */
	public void setTitleKey(String titleKey){
		this.mTitleKey = titleKey;
		return;
	}

	/**
	 * トラック名を取得する
	 * @return
	 *	トラック名
	 */
	public int getTrack(){
		return this.mTrack;
	}

	/**
	 * トラック名を設定する
	 * @param track
	 *	トラック名
	 */
	public void setTrack(int track){
		this.mTrack = track;
		return;
	}

	/**
	 * 年を取得する
	 * @return
	 * 	年
	 */
	public int getYear(){
		return this.mYear;
	}

	/**
	 * 年を設定する
	 * @param year
	 * 	年
	 */
	public void setYear(int year){
		this.mYear = year;
		return;
	}

	/**
	 * URI を取得する
	 * @return
	 * 	URI
	 */
	public Uri getUri(){
		return this.mUri;
	}

	/**
	 * URI を設定する
	 * @param uri
	 * 	URI
	 */
	public void setUri(Uri uri){
		this.mUri = uri;
		return;
	}

}
