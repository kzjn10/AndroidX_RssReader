package dev.anhndt.gobear.viewmodel;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dev.anhndt.gobear.cache.NewsLruCache;
import dev.anhndt.gobear.entities.NewsEntity;

/**
 * Handle data for News list
 */

public class NewsViewModel extends ViewModel {
    private static final String TAG = NewsViewModel.class.getSimpleName();
    private String mRssUrl = "http://feeds.bbci.co.uk/news/world/asia/rss.xml";
    private MutableLiveData<List<NewsEntity>> dataList;

    public LiveData<List<NewsEntity>> getNewsData() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            loadData();
        }

        return dataList;
    }

    public LiveData<List<NewsEntity>> refreshNewsData() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
        }

        NewsLruCache.getInstance().clear();
        loadData();

        return dataList;
    }


    /**
     * Do fetch data from network if  not available in cache
     */
    private void loadData() {

        AsyncTask<Void, Void, List<NewsEntity>> task = new AsyncTask<Void, Void, List<NewsEntity>>() {
            @Override
            protected List<NewsEntity> doInBackground(Void... voids) {
                List<NewsEntity> ret = null;
                do {
                    try {
                        //Get data from cache
                        ret = NewsLruCache.getInstance().getNewsList();
                        if (ret != null) {
                            Log.e(TAG, "Get data from cache");
                            break;
                        }

                        //Request via network
                        Log.e(TAG, "Get data from network");
                        URL url = new URL(mRssUrl);
                        InputStream inputStream = url.openConnection().getInputStream();
                        ret = parseFeed(inputStream);
                    } catch (IOException e) {
                        Log.e(TAG, "Error", e);
                    } catch (XmlPullParserException e) {
                        Log.e(TAG, "Error", e);
                    }
                } while (false);
                return ret;
            }

            @Override
            protected void onPostExecute(List<NewsEntity> newsEntities) {
                super.onPostExecute(newsEntities);
                if (dataList == null) {
                    dataList = new MutableLiveData<>();
                }

                if (newsEntities != null && newsEntities.size() != 0) {
                    NewsLruCache.getInstance().putNewsList((ArrayList<NewsEntity>) newsEntities);
                    dataList.setValue(newsEntities);
                }
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    /**
     * Process raw data
     *
     * @param inputStream
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List<NewsEntity> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String thumb = null;
        String publishTime = null;
        String description = null;
        boolean isItem = false;
        List<NewsEntity> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("pubDate")) {
                    publishTime = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                } else if (name.equalsIgnoreCase("media:thumbnail")) {
                    result = xmlPullParser.getAttributeValue("", "url");
                    thumb = result;
                }


                if (title != null && publishTime != null && link != null && description != null && thumb != null) {
                    if (isItem) {
                        NewsEntity item = new NewsEntity();
                        item.title = title;
                        item.description = description;
                        item.publishTime = publishTime;
                        item.thumb = thumb;
                        item.link = link;
                        items.add(item);
                    }

                    title = null;
                    publishTime = null;
                    link = null;
                    description = null;
                    thumb = null;

                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }
}
