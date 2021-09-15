package com.farasatnovruzov.survivorbird;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.farasatnovruzov.survivorbird.SurvivorBird;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import static android.content.ContentValues.TAG;

public class AndroidLauncher extends AndroidApplication{
//	private AdView adView;
	private InterstitialAd mInterstitialAd;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
//		test
//		ca-app-pub-3940256099942544/1033173712

//		main
//		ca-app-pub-7490906318526375/1493829000
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SurvivorBird(), config);

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		AdRequest adRequest = new AdRequest.Builder().build();

		InterstitialAd.load(this, "ca-app-pub-7490906318526375/1493829000", adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						mInterstitialAd = interstitialAd;
						Log.i(TAG, "onAdLoaded");
						if (mInterstitialAd != null) {
							mInterstitialAd.show(AndroidLauncher.this);
						} else {
							Log.d("TAG", "The interstitial ad wasn't ready yet.");
						}


					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error
						Log.i(TAG, loadAdError.getMessage());
						mInterstitialAd = null;
					}
				});


	}
}






