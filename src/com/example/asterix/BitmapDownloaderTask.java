package com.example.asterix;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap>{
	//private String url;
	private final WeakReference<ImageView> imageViewReference;
	
	public BitmapDownloaderTask(ImageView imageView){
		imageViewReference = new WeakReference<ImageView>(imageView);
	}
	
	@Override // Actual download method, run in the task thread
	protected Bitmap doInBackground(String... params){
		//params comes from the execute() call: params[0] has the url
		return downloadBitmap(params[0]);
	}
	
	@Override
	//Associate the downloaded image with imageView
	protected void onPostExecute(Bitmap bitmap){
		if(isCancelled()){
			bitmap = null;
		}
		
		if(imageViewReference != null){
			ImageView imageView = imageViewReference.get();
			if(imageView != null){
				imageView.setImageBitmap(bitmap);
			}
		}
	}
	
	static Bitmap downloadBitmap(String url){
		final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);
		
		try{
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode != HttpStatus.SC_OK){
				Log.w("ImageDownloader", "Error:"+statusCode+" Failed to download image from "+ url);
				return null;
			}
		
			final HttpEntity entity = response.getEntity();
			if(entity != null){
				InputStream inputStream = null;
				try{
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				} finally {
					if(inputStream != null){
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch(Exception e){
			getRequest.abort();
			Log.w("ImageLoader", "Error while retrieving bitmap from "+url);
			} finally {
				if(client != null){
					client.close();
				}
			}
		
		return null;
	}
}
