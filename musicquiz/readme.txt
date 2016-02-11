本番環境の設定
	~musicquiz/res/values/strings.xml の banner_ad_unit_id を本番環境に変更

テスト環境の設定
	~musicquiz/res/values/strings.xml の banner_ad_unit_id をデバッグ環境に変更

Android SDK Manager でインストール
	・Android 4.0.4
	・Android 2.3.3
	・google-play-service_lib

google-lay-service_lib をインポート
	ファイル > インポート > 既存プロジェクトをワークスペースへ > google-play-service_lib を選択
	musicquiz プロジェクトを右クリック > プロパティー > Android > google-play-service_lib をライブラリに追加

Lint エラー対策
	Eclipse > 環境設定 > Android > Lint エラー検査 > 下記項目の Security を ignore に変更
		google-play-service_lib 関係
			・MissingTranslation
			・IconDuplicates
			・IconDuplicatesConfig
			・IconDensities
			・IconDipSize
		musicquiz 関係
			・UnusedResources
			・Overdraw
