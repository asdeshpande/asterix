package com.example.asterix;

import android.widget.ImageView;

public class ImageDownloader {
	
	public void download(String url, ImageView imageView){
		BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
		task.execute(url);
	}
}
