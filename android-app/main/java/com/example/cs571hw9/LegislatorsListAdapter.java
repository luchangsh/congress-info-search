package com.example.cs571hw9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs571hw9.model.Legislator;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class LegislatorsListAdapter extends ArrayAdapter<Legislator> {

    private List<Legislator> legislators;
    private LruCache<String, Bitmap> imageCache;

    public LegislatorsListAdapter(Context context, int resource, List<Legislator> objects) {
        super(context, resource, objects);
        legislators = objects;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_legislators, parent, false);
        }

        Legislator legislator = legislators.get(position);

        TextView nameText = (TextView) convertView.findViewById(R.id.textView_legislators_name);
        nameText.setText(legislator.getLastName() + ", " + legislator.getFirstName());

        TextView partyText = (TextView) convertView.findViewById(R.id.textView_legislators_party);
        partyText.setText("(" + legislator.getParty() + ")" + legislator.getStateName()
                + " - District " + legislator.getDistrict());

        Bitmap bitmap = imageCache.get(legislator.getBioguideId());
        if (bitmap != null) {
            ImageView image = (ImageView) convertView.findViewById(R.id.imageView_legislators_photo);
            image.setImageBitmap(legislator.getBitmap());
        } else {
            LegislatorAndView container = new LegislatorAndView();
            container.legislator = legislator;
            container.view = convertView;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);
        }

        return convertView;
    }

    class LegislatorAndView {
        public Legislator legislator;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<LegislatorAndView, Void, LegislatorAndView> {

        @Override
        protected LegislatorAndView doInBackground(LegislatorAndView... params) {

            LegislatorAndView container = params[0];
            Legislator legislator = container.legislator;

            try {
                String imageUrl = MainActivity.PHOTOS_BASE_URL + legislator.getBioguideId() + ".jpg";
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                legislator.setBitmap(bitmap);
                in.close();
                container.bitmap = bitmap;
                return container;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(LegislatorAndView result) {
            ImageView image = (ImageView) result.view.findViewById(R.id.imageView_legislators_photo);
            image.setImageBitmap(result.bitmap);
            imageCache.put(result.legislator.getBioguideId(), result.bitmap);
        }
    }
}
