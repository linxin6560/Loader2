package me.levylin.loader;

import android.support.v7.app.AppCompatActivity;

import com.levylin.loader.DataLoader;
import com.levylin.loader.ILoaderView;

/**
 * Created by LinXin on 2016/12/28 15:36.
 */
public class BaseActivity extends AppCompatActivity implements ILoaderView {

    private DataLoader mLoader;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLoader();
    }

    @Override
    public void destroyLoader() {
        if (mLoader != null) {
            mLoader.onDestroy();
        }
    }

    @Override
    public void setDataLoader(DataLoader loader) {
        mLoader = loader;
    }
}
