/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtr.zxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.beiair.ui.base.BaseMultiPartActivity;
import com.beiair.utils.Settings;
import com.beiair.widget.Toast;
import com.beiair.R;
import com.dtr.zxing.encode.QRCodeEncode;

/**
 * This class encodes data from an Intent into a QR code, and then displays it
 * full screen so that another person can scan it with their device.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class EncodeActivity extends BaseMultiPartActivity {
	public static final String CONTENT = "text_content";
	private Bitmap shareBitmap;

	private ImageView qrview;

	private QRCodeEncode mEncoder;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.encode);

		setMenuEnable(false);

		initView();
		init();
	}

	private void initView() {
		qrview = (ImageView) findViewById(R.id.image_view);

		findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (shareBitmap == null) {
					Toast.show(mActivity, "没有生成二维码");
					return;
				}
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		final int width = Settings.P_WIDTH;
		int dimension = width * 3 / 4;
		mEncoder = new QRCodeEncode.Builder()
				.setBackgroundColor(0xFFFFFF)
				.setCodeColor(0xFF000000)
				.setOutputBitmapPadding(0)
				.setOutputBitmapWidth(dimension)
				.setOutputBitmapHeight(dimension)
				.build();
	}

	@Override
	protected void onResume() {
		super.onResume();

		Intent intent = getIntent();
		if (!intent.hasExtra(CONTENT)) {
			Toast.show(mActivity, "没有编码数据");
			finish();
			return;
		}

		String content = intent.getStringExtra(CONTENT);
		new DecodeTask().execute(content);
	}

	class DecodeTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            return mEncoder.encode(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
        	qrview.setImageBitmap(bitmap);
        	shareBitmap = bitmap;
        }
    }

}