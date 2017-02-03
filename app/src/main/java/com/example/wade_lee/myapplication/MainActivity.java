package com.example.wade_lee.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	TextView tv_trending, tv_follow;
	FlingContainer linearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_follow = (TextView)findViewById(R.id.bc_home_following_text);
		tv_follow.setOnClickListener(followClick);
		tv_follow.setSelected(false);
		tv_trending = (TextView)findViewById(R.id.bc_home_trending_text);
		tv_trending.setOnClickListener(trendingClick);
		tv_trending.setSelected(true);

		linearLayout = (FlingContainer) findViewById(R.id.test);
		linearLayout.setOnSwipeListener(new FlingContainer.OnSwipeListener() {
			@Override
			public void onSwipeView(int eventType) {
				System.out.println("~~~~eventType:"+eventType);
			}
		});
	}

	private View.OnClickListener trendingClick = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
//			Toast.makeText(MainActivity.this, "Trending", Toast.LENGTH_LONG).show();
			System.out.println("~~~trending click");
			tv_trending.setSelected(true);
			tv_follow.setSelected(false);
		}
	};

	private View.OnClickListener followClick = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
//			Toast.makeText(MainActivity.this, "Following", Toast.LENGTH_LONG).show();
			System.out.println("~~~follow click");
			tv_trending.setSelected(false);
			tv_follow.setSelected(true);
		}
	};
}
