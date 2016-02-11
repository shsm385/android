package jp.kuroneko.android.musicquiz.utilities;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM_ID;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST_ID;
import static android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC;
import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.TITLE;
import static jp.kuroneko.android.musicquiz.R.string.question_introduction_age;
import static jp.kuroneko.android.musicquiz.R.string.question_introduction_album;
import static jp.kuroneko.android.musicquiz.R.string.question_introduction_music;
import static jp.kuroneko.android.musicquiz.R.string.question_introduction_singer;
import static jp.kuroneko.android.musicquiz.R.string.question_text_age;
import static jp.kuroneko.android.musicquiz.R.string.question_text_album;
import static jp.kuroneko.android.musicquiz.R.string.question_text_singer;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_CHOICES;
import static jp.kuroneko.android.musicquiz.configs.Config.MAXIMUM_QUESTION;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionKind.KIND_MUSIC;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionKind.KIND_SINGER;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_INTRODUCTION;
import static jp.kuroneko.android.musicquiz.configs.Config.QuestionType.TYPE_JACKET;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import jp.kuroneko.android.musicquiz.configs.Config;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionKind;
import jp.kuroneko.android.musicquiz.configs.Config.QuestionType;
import jp.kuroneko.android.musicquiz.parameters.Audio;
import jp.kuroneko.android.musicquiz.parameters.Media;
import jp.kuroneko.android.musicquiz.parameters.Question;
import android.annotation.SuppressLint;
import android.app.Application;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

/**
 * ユーティリティクラス
 * @author shimadahiroshi
 * @author kuroneko
 *
 */
public class Utility extends Application{

	/**
	 * 読込みを行うオーディオ情報のカラム
	 */
	private static final String[] AUDIO_COLUMNS = new String[]{
		"*"
	};

	/**
	 * メディアのメタデータを取得するために使用
	 */
	private static final MediaMetadataRetriever RETRIEVER = new MediaMetadataRetriever();

	/**
	 * データベースにアクセスする際に distinct を指定した URI
	 */
	private static final Uri DISTINCT_CONTENT_URI = EXTERNAL_CONTENT_URI.buildUpon().appendQueryParameter("distinct", "true").build();

	/**
	 * シングルトンパターンとして使用するためのユーティリティインスタンス
	 */
	private static Utility sUtility;

	/**
	 * オーディオ情報のIDのリスト
	 */
	private static ArrayList<Long> sAudioIds;

	/**
	 * オーディオ情報取得時に無視する情報のマップ
	 */
	private static HashMap<String, String> sIgnores = new HashMap<String, String>();

	/**
	 * 初期化フラグ
	 */
	private static boolean sInitializeFlag;

	/**
	 * 乱数生成器
	 */
	private static Random sRandom = new Random();

	/**
	 * ユーティリティを使用するための初期設定を行うコンストラクタ<br/>
	 * 用途はシングルトンパターンと同じだが Android の Application クラスを継承した<br/>
	 * データ共有時の仕様上 private に出来ないため public になっているが<br/>
	 * 基本的には new することなく {@link Utility#getInstance} で取得することを推奨する
	 */
	@Deprecated
	public Utility(){
		super();
		Utility.sUtility = this;
		return;
	}

	/**
	 * シングルトンパターンでユーティリティインスタンスを取得する
	 * @return
	 * 	ユーティリティインスタンス
	 */
	public static Utility getInstance(){
		return Utility.sUtility;
	}

	/**
	 * オーディオ情報のIDをリスト形式で取得する
	 * @param ignores
	 * 	無視する情報を Map<カラム名, 値> の形で指定する
	 * @return
	 * 	オーディオ情報のIDのリスト
	 */
	public static ArrayList<Long> getAudioIds(Map<String, String> ignores){
		StringBuffer where = new StringBuffer(IS_MUSIC).append(" != 0 and ").append(TITLE).append(" != ''");
		String[] parameters = null;
		int index = 0;
		if(ignores != null){
			parameters= new String[ignores.size()];
			for(Entry<String, String> ignore: ignores.entrySet()){
				where.append(" and ").append(ignore.getKey()).append(" != ?");
				parameters[index++] = ignore.getValue();
			}
		}
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(EXTERNAL_CONTENT_URI, new String[]{_ID}, where.toString(), parameters, null);
		ArrayList<Long> list = new ArrayList<Long>();
		while(cursor.moveToNext()){
			list.add(cursor.getLong(0));
		}
		return list;
	}

	/**
	 * 重複したタイトルを除いたオーディオ情報のIDをリスト形式で取得する
	 * @param ignores
	 * 	無視する情報を Map<カラム名, 値> の形で指定する
	 * @return
	 * 	重複したタイトルを除いたオーディオ情報のIDのリスト
	 */
	public static ArrayList<Long> getTitleIds(Map<String, String> ignores){
		StringBuffer where = new StringBuffer(IS_MUSIC).append(" != 0 and ").append(TITLE).append(" != ''");
		String[] parameters = null;
		int index = 0;
		if(ignores != null){
			parameters= new String[ignores.size()];
			for(Entry<String, String> ignore: ignores.entrySet()){
				where.append(" and ").append(ignore.getKey()).append(" != ?");
				parameters[index++] = ignore.getValue();
			}
		}
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(DISTINCT_CONTENT_URI, new String[]{_ID, TITLE}, where.toString(), parameters, null);
		HashMap<String, Long> map = new HashMap<String, Long>();
		while(cursor.moveToNext()){
			map.put(cursor.getString(1), cursor.getLong(0));
		}
		ArrayList<Long> list = new ArrayList<Long>();
		for(Entry<String, Long> entry: map.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}

	/**
	 * アルバムのIDをリスト形式で取得する
	 * @param ignores
	 * 	無視する情報を Map<カラム名, 値> の形で指定する
	 * @return
	 * 	アルバム情報のIDのリスト
	 */
	public static ArrayList<Long> getAlbumIds(Map<String, String> ignores){
		StringBuffer where = new StringBuffer(IS_MUSIC).append(" != 0 and ").append(ALBUM).append(" != ''");
		String[] parameters = null;
		int index = 0;
		if(ignores != null){
			parameters= new String[ignores.size()];
			for(Entry<String, String> ignore: ignores.entrySet()){
				where.append(" and ").append(ignore.getKey()).append(" != ?");
				parameters[index++] = ignore.getValue();
			}
		}
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(DISTINCT_CONTENT_URI, new String[]{ALBUM_ID}, where.toString(), parameters, null);
		ArrayList<Long> list = new ArrayList<Long>();
		while(cursor.moveToNext()){
			list.add(cursor.getLong(0));
		}
		return list;
	}

	/**
	 * アーティストのIDをリスト形式で取得する
	 * @param ignores
	 * 	無視する情報を Map<カラム名, 値> の形で指定する
	 * @return
	 * 	アーティスト情報のIDのリスト
	 */
	public static ArrayList<Long> getArtistIds(Map<String, String> ignores){
		StringBuffer where = new StringBuffer(IS_MUSIC).append(" != 0 and ").append(ARTIST).append(" != ''");
		String[] parameters = null;
		int index = 0;
		if(ignores != null){
			parameters= new String[ignores.size()];
			for(Entry<String, String> ignore: ignores.entrySet()){
				where.append(" and ").append(ignore.getKey()).append(" != ?");
				parameters[index++] = ignore.getValue();
			}
		}
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(DISTINCT_CONTENT_URI, new String[]{ARTIST_ID}, where.toString(), parameters, null);
		ArrayList<Long> list = new ArrayList<Long>();
		while(cursor.moveToNext()){
			list.add(cursor.getLong(0));
		}
		return list;
	}

	/**
	 * オーディオ情報の数を取得する
	 * @return
	 * 	オーディオ情報の数
	 */
	public static int getAudioCount(){
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(EXTERNAL_CONTENT_URI, new String[]{TITLE}, IS_MUSIC + " != 0", null, null);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while(cursor.moveToNext()){
			String key = cursor.getString(0);
			map.put(key, map.containsKey(key) ? map.get(key) + 1 : 0);
		}
		return map.size();
	}

	/**
	 * 全てのオーディオ情報をリスト形式で取得する
	 * @return
	 * 	オーディオ情報のリスト
	 */
	public static ArrayList<Audio> getAudios(){
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(EXTERNAL_CONTENT_URI, AUDIO_COLUMNS, IS_MUSIC + " != 0", null, null);
		ArrayList<Audio> list = new ArrayList<Audio>();
		while(cursor.moveToNext()){
			list.add(new Audio(cursor));
		}
		return list;
	}

	/**
	 * オーディオ情報をリスト形式で取得する
	 * @param ids
	 * 	取得するオーディオ情報のID
	 * @return
	 * 	オーディオ情報のリスト
	 */
	public static ArrayList<Audio> getAudios(String... ids){
		StringBuilder builder = new StringBuilder(_ID + " in(");
		int length = ids.length;
		for(int index = 0; index < length; index++){
			builder.append("?,");
		}
		length = builder.length();
		builder.delete(length - 1, length);
		builder.append(")");
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(EXTERNAL_CONTENT_URI, AUDIO_COLUMNS, builder.toString(), ids, null);
		ArrayList<Audio> list = new ArrayList<Audio>();
		while(cursor.moveToNext()){
			list.add(new Audio(cursor));
		}
		return list;
	}

	/**
	 * オーディオのタイトルをリスト形式で取得する
	 * @param ids
	 * 	取得するタイトルを保持するオーディオ情報のID
	 * @return
	 * 	オーディオのタイトルのリスト
	 */
	public static ArrayList<String> getTitle(Long... ids){
		int length = ids.length;
		String[] parameters = new String[length];
		for(int index = 0; index < length; index++){
			parameters[index] = ids[index].toString();
		}
		return Utility.getTitles(parameters);
	}

	/**
	 * オーディオのタイトルをリスト形式で取得する
	 * @param ids
	 * 	取得するタイトルを保持するオーディオ情報のID
	 * @return
	 * 	オーディオのタイトルのリスト
	 */
	public static ArrayList<String> getTitles(String... ids){
		StringBuilder builder = new StringBuilder(_ID + " in(");
		int length = ids.length;
		for(int index = 0; index < length; index++){
			builder.append("?,");
		}
		length = builder.length();
		builder.delete(length - 1, length);
		builder.append(")");
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(EXTERNAL_CONTENT_URI, new String[]{TITLE}, builder.toString(), ids, null);
		ArrayList<String> list = new ArrayList<String>();
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		return list;
	}

	/**
	 * アルバム名をリスト形式で取得する
	 * @param ids
	 * 	取得するアルバムのID
	 * @return
	 * 	アルバム名のリスト
	 */
	public static ArrayList<String> getAlbums(Long... ids){
		int length = ids.length;
		String[] parameters = new String[length];
		for(int index = 0; index < length; index++){
			parameters[index] = ids[index].toString();
		}
		return Utility.getAlbums(parameters);
	}

	/**
	 * アルバム名をリスト形式で取得する
	 * @param ids
	 * 	取得するアルバムのID
	 * @return
	 * 	アルバム名のリスト
	 */
	public static ArrayList<String> getAlbums(String... ids){
		StringBuilder builder = new StringBuilder(ALBUM_ID + " in(");
		int length = ids.length;
		for(int index = 0; index < length; index++){
			builder.append("?,");
		}
		length = builder.length();
		builder.delete(length - 1, length);
		builder.append(")");
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(DISTINCT_CONTENT_URI, new String[]{ALBUM, ALBUM_ID}, builder.toString(), ids, null);
		ArrayList<String> list = new ArrayList<String>();
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		return list;
	}

	/**
	 * アーティスト名をリスト形式で取得する
	 * @param ids
	 * 	取得するアーティストのID
	 * @return
	 * 	アーティスト名のリスト
	 */
	public static ArrayList<String> getArtist(Long... ids){
		int length = ids.length;
		String[] parameters = new String[length];
		for(int index = 0; index < length; index++){
			parameters[index] = ids[index].toString();
		}
		return Utility.getArtist(parameters);
	}

	/**
	 * アーティスト名をリスト形式で取得する
	 * @param ids
	 * 	取得するアーティストのID
	 * @return
	 * 	アーティスト名のリスト
	 */
	public static ArrayList<String> getArtist(String... ids){
		StringBuilder builder = new StringBuilder(ARTIST_ID + " in(");
		int length = ids.length;
		for(int index = 0; index < length; index++){
			builder.append("?,");
		}
		length = builder.length();
		builder.delete(length - 1, length);
		builder.append(")");
		Cursor cursor = Utility.sUtility
			.getContentResolver()
			.query(DISTINCT_CONTENT_URI, new String[]{ARTIST, ARTIST_ID}, builder.toString(), ids, null);
		ArrayList<String> list = new ArrayList<String>();
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		return list;
	}

	/**
	 * オーディオ情報をリスト形式で取得する
	 * @param ids
	 * 	取得するオーディオ情報のID
	 * @return
	 * 	オーディオ情報のリスト
	 */
	public static ArrayList<Audio> getAudios(Long... ids){
		int length = ids.length;
		String[] parameters = new String[length];
		for(int index = 0; index < length; index++){
			parameters[index] = ids[index].toString();
		}
		return Utility.getAudios(parameters);
	}

	/**
	 * メディアのアートワークのビットマップイメージを取得する
	 * @param audio
	 * 	オーディオ情報
	 * @return
	 * 	アートワークのビットマップイメージ
	 */
	public static Bitmap getArtWork(Media media){
		byte[] data;
		synchronized (RETRIEVER) {
			RETRIEVER.setDataSource(media.getData());
			data = RETRIEVER.getEmbeddedPicture();
		}
		return data == null ? null : BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	/**
	 * ランダムに問題情報を取得する
	 * @return
	 * 	ランダムに選ばれた問題情報の配列<br/>
	 *  問題数は {@link Config#MAXIMUM_QUESTION} で指定された数
	 */
	public static Question[] getRandomQuestion(){
		Utility.initializeIds();
		Question[] questions = new Question[MAXIMUM_QUESTION];
		Audio[] audios = Utility.getRandomAudios();
		QuestionType[] types = QuestionType.values();
		QuestionKind[] kinds = QuestionKind.values();
		int force = Utility.sRandom.nextInt(MAXIMUM_QUESTION);
		for(int index = 0; index < MAXIMUM_QUESTION; ++index){
			Question question = new Question();
			question.setType(index == force ? TYPE_INTRODUCTION : types[Utility.sRandom.nextInt(types.length)]);
			if(question.getType() == TYPE_JACKET){
				// ジャケット問題が追加されるまではイントロ問題にする
				question.setType(TYPE_INTRODUCTION);
			}
			question.setKind(kinds[Utility.sRandom.nextInt(kinds.length)]);
			question.setAnswerData(audios[index]);
			question.setAnswerNumber(Utility.sRandom.nextInt(MAXIMUM_CHOICES));
			Utility.fillQuestion(question);
			questions[index] = question;
		}
		return questions;
	}

	/**
	 * 配列をシャッフルする
	 * @param items
	 * 	シャッフルする配列
	 */
	public static void shuffle(Object[] items){
		for(int index = 0; index < items.length; ++index){
			int change = Utility.sRandom.nextInt(items.length);
			Object temporary = items[index];
			items[index] = items[change];
			items[change] = temporary;
		}
		return;
	}

	/**
	 * リストをシャッフルする
	 * @param items
	 * 	シャッフルするリスト
	 */
	public static void shuffle(ArrayList<? super Object> items){
		int size = items.size();
		for(int index = 0; index < size; ++index){
			int change = Utility.sRandom.nextInt(size);
			Object temporary = items.get(index);
			items.set(index, items.get(change));
			items.set(change, temporary);
		}
		return;
	}

	/**
	 * 文字列からリソースIDを取得する
	 * @param name
	 * 	リソース ID の名前<br/>
	 *  e.g.) R.string.first_button_name の場合 first_button_name
	 * @param type
	 * 	リソース ID のタイプ<br/>
	 * 	e.g.) R.string.first_button_name の場合 string
	 * @return
	 * 	取得したリソース ID
	 */
	public static int getResouoceId(String name, String type){
		return Utility.sUtility.getResources().getIdentifier(name, type, Utility.sUtility.getPackageName());
	}

	/**
	 * R.string のリソース ID から文字列を取得する
	 * @param id
	 * 	リソース ID
	 * @return
	 * 	リソース ID に紐付いている文字列
	 */
	public static String getResouceString(int id){
		return Utility.sUtility.getResources().getString(id);
	}

	/**
	 * 問題タイプ毎に選択肢を埋める
	 * @param question
	 * 	選択肢を埋める問題情報
	 */
	private static void fillQuestion(Question question){
		switch(question.getKind()){
			case KIND_ALBUM:
				Utility.fillAlbumQuestion(question);
				break;

			case KIND_SINGER:
				Utility.fillArtistQuestion(question);
				break;

			case KIND_MUSIC:
				Utility.fillMusicQuestion(question);
				break;

			case KIND_AGE:
				Utility.fillAgeQuestion(question);
				break;

			default:
				break;
		}
		Utility.fillQuestionText(question);
		return;
	}

	/**
	 * アルバム名問題の選択肢を埋める
	 * @param question
	 * 	選択肢を埋める問題情報
	 */
	private static void fillAlbumQuestion(Question question){
		String answer = question.getAnswerData().getAlbum();
		long id = question.getAnswerData().getAlbumId();
		Utility.sIgnores.clear();
		Utility.sIgnores.put(ALBUM_ID, Long.toString(id));
		Utility.sIgnores.put(ALBUM, answer);
		ArrayList<Long> ids;
		if(answer == null || answer.isEmpty() || (ids = Utility.getAlbumIds(Utility.sIgnores)).size() < MAXIMUM_CHOICES){
			question.setKind(KIND_MUSIC);
			Utility.fillMusicQuestion(question);
			return;
		}
		Utility.fillChoices(question, Utility.getAlbums(Utility.getRandomIds(ids)), answer);
		return;
	}

	/**
	 * アーティスト名問題の選択肢を埋める
	 * @param question
	 * 	選択肢を埋める問題情報
	 */
	private static void fillArtistQuestion(Question question){
		String answer = question.getAnswerData().getArtist();
		long id = question.getAnswerData().getArtistId();
		Utility.sIgnores.clear();
		Utility.sIgnores.put(ARTIST_ID, Long.toString(id));
		Utility.sIgnores.put(ARTIST, answer);
		if(question.getType() == TYPE_JACKET && question.getKind() == KIND_SINGER){
			Utility.sIgnores.put(ALBUM_ID, Long.toString(question.getAnswerData().getAlbumId()));
		}
		ArrayList<Long> ids;
		if(answer == null || answer.isEmpty() || (ids = Utility.getArtistIds(Utility.sIgnores)).size() < MAXIMUM_CHOICES){
			question.setKind(KIND_MUSIC);
			Utility.fillMusicQuestion(question);
			return;
		}
		Utility.fillChoices(question, Utility.getArtist(Utility.getRandomIds(ids)), answer);
		return;
	}

	/**
	 * 曲名問題の選択肢を埋める
	 * @param question
	 * 	選択肢を埋める問題情報
	 */
	private static void fillMusicQuestion(Question question){
		String answer = question.getAnswerData().getTitle();
		Utility.sIgnores.clear();
		Utility.sIgnores.put(TITLE, answer);
		Utility.sIgnores.put(_ID, Long.toString(question.getAnswerData().getId()));
		if(question.getType() == TYPE_JACKET && question.getKind() == KIND_MUSIC){
			Utility.sIgnores.put(ALBUM_ID, Long.toString(question.getAnswerData().getAlbumId()));
		}
		ArrayList<Long> choices = Utility.getAudioIds(Utility.sIgnores);
		if(MAXIMUM_CHOICES < choices.size()){
			String[] ids = Utility.getRandomIds(Utility.getAudioIds(Utility.sIgnores));
			Utility.fillChoices(question, Utility.getTitles(ids), answer);
		}
		return;
	}

	/**
	 * 年代問題の選択肢を埋める
	 * @param question
	 * 	選択肢を埋める問題情報
	 */
	private static void fillAgeQuestion(Question question){
		int answer = question.getAnswerData().getYear();
		if(answer == 0){
			question.setKind(KIND_MUSIC);
			Utility.fillMusicQuestion(question);
			return;
		}
		int maximum = MAXIMUM_CHOICES - 1;
		String[] choices = new String[maximum];
		int offset = Utility.sRandom.nextInt(MAXIMUM_CHOICES);
		for(int index = 0; index < maximum; ++index){
			int year = answer - offset + index;
			if(year == answer){
				++year;
				--offset;
			}
			choices[index] = Integer.toString(year);
		}
		Utility.shuffle(choices);
		Utility.fillChoices(question, choices, Integer.toString(answer));
		return;
	}

	/**
	 * 問題の選択肢情報を埋める
	 * @param question
	 * 	選択肢を埋める問題情報
	 * @param choices
	 * 	選択肢情報の配列
	 * @param answer
	 * 	答え
	 */
	private static void fillChoices(Question question, String[] choices, String answer){
		int index = 0;
		question.setChoice(question.getAnswerNumber(), answer);
		for(String choice: choices){
			if(index == question.getAnswerNumber()){
				if(MAXIMUM_CHOICES <= ++index){
					break;
				}
			}
			question.setChoice(index++, choice);
		}
		return;
	}

	/**
	 * 問題の選択肢情報を埋める
	 * @param question
	 * 	選択肢を埋める問題情報
	 * @param choices
	 * 	選択肢情報のリスト
	 * @param answer
	 * 	答え
	 */
	private static void fillChoices(Question question, ArrayList<String> choices, String answer){
		int index = 0;
		question.setChoice(question.getAnswerNumber(), answer);
		for(String choice: choices){
			if(index == question.getAnswerNumber()){
				if(MAXIMUM_CHOICES <= ++index){
					break;
				}
			}
			question.setChoice(index++, choice);
		}
		return;
	}

	/**
	 * 問題文を埋める
	 * @param question
	 * 	問題文を埋める問題情報
	 */
	private static void fillQuestionText(Question question){
		switch(question.getType()){
			case TYPE_INTRODUCTION:
				Utility.fillIntroductionQuestion(question);
				break;

			case TYPE_TEXT:
				Utility.fillTextQuestion(question);
				break;

			default:
				question.setType(TYPE_INTRODUCTION);
				Utility.fillQuestion(question);
				break;
		}
		return;
	}

	/**
	 * 文章問題の問題文を埋める
	 * @param question
	 * 	問題文を埋める問題情報
	 */
	private static void fillTextQuestion(Question question){
		int id = 0;
		switch(question.getKind()){
			case KIND_ALBUM:
				id = question_text_album;
				break;

			case KIND_SINGER:
				id = question_text_singer;
				break;

			case KIND_AGE:
				id = question_text_age;
				break;

			default:
				question.setType(TYPE_INTRODUCTION);
				return;
		}
		String text = Utility.getResouceString(id);
		question.setQuestionText(String.format(text, question.getAnswerData().getTitle()));
		return;
	}

	/**
	 * イントロ問題の問題文を埋める
	 * @param question
	 * 	問題文を埋める問題情報
	 */
	private static void fillIntroductionQuestion(Question question){
		switch(question.getKind()){
			case KIND_ALBUM:
				question.setQuestionText(Utility.getResouceString(question_introduction_album));
				break;

			case KIND_SINGER:
				question.setQuestionText(Utility.getResouceString(question_introduction_singer));
				break;

			case KIND_MUSIC:
				question.setQuestionText(Utility.getResouceString(question_introduction_music));
				break;

			case KIND_AGE:
				question.setQuestionText(Utility.getResouceString(question_introduction_age));
				break;

			default:
				break;
		}
		return;
	}

	/**
	 * ランダムに選ばれたIDを取得する
	 * @param choices
	 * 	IDの選択肢<br/>
	 * 	この中からIDが選ばれる
	 * @return
	 * 	ランダムに選ばれたIDの配列<br/>
	 * 	IDの数は{@link Config#MAXIMUM_CHOICES} - 1の数
	 */
	@SuppressWarnings("unchecked")
	private static String[] getRandomIds(ArrayList<? super Long> choices){
		int maximum = MAXIMUM_CHOICES - 1;
		Utility.shuffle((ArrayList<Object>)choices);
		String[] ids = new String[maximum];
		int offset = Utility.sRandom.nextInt(choices.size() - maximum);
		for(int index = 0; index < maximum; index++){
			ids[index] = choices.get(offset + index).toString();
		}
		return ids;
	}

	/**
	 * ランダムにオーディオ情報を取得する
	 * @return
	 * 	ランダムに選ばれたオーディオ情報<br/>
	 *  問題数は {@link Config#MAXIMUM_QUESTION} で指定された数
	 */
	@SuppressLint("UseSparseArrays")
	private static Audio[] getRandomAudios(){
		String[] ids = new String[MAXIMUM_QUESTION];
		Long[] checker = new Long[MAXIMUM_QUESTION];
		Audio[] audios = new Audio[MAXIMUM_QUESTION];
		HashMap<Long, Audio> map = new HashMap<Long, Audio>();
		int maximum = Utility.sAudioIds.size();
		for(int index = 0; index < MAXIMUM_QUESTION; ++index){
			ids[index] = (checker[index] = Utility.sAudioIds.get(Utility.sRandom.nextInt(maximum))).toString();
		}
		for(Audio audio: Utility.getAudios(ids)){
			map.put(audio.getId(), audio);
		}
		for(int index = 0; index < MAXIMUM_QUESTION; index++){
			audios[index] = map.get(checker[index]);
		}
		return audios;
	}

	/**
	 * IDの初期設定を行う
	 */
	private static void initializeIds(){
		if(!Utility.sInitializeFlag){
			Utility.sAudioIds = Utility.getAudioIds(null);
			Utility.sInitializeFlag = true;
		}
		return;
	}

}
