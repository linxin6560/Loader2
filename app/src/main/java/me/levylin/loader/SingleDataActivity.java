package me.levylin.loader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.levylin.lib.loader.base.DataLoader;
import com.levylin.lib.loader.base.listener.OnLoadSuccessListener;

import me.levylin.loader.helper.LoadStateHelper;
import me.levylin.loader.helper.RefreshHelper;
import me.levylin.loader.model.SingleDataModel;
import me.levylin.loader.model.SingleDataModel2;

/**
 * 单页数据Activity
 * Created by LinXin on 2016/12/28 15:51.
 */
public class SingleDataActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_single_data);
        SwipeRefreshLayout layout = (SwipeRefreshLayout) findViewById(R.id.act_single_data_sfl);
        final TextView textView = (TextView) findViewById(R.id.act_single_data_tv);
        SingleDataModel2 model = new SingleDataModel2();
        DataLoader<String> loader = new DataLoader<>(this, model);
        loader.setLoadStateHelper(new LoadStateHelper(layout));
        loader.setRefreshViewHelper(new RefreshHelper(layout));
        loader.setOnLoadSuccessListener(new OnLoadSuccessListener<String>() {
            @Override
            public void onSuccess(boolean isRefreshing, String response) {
                textView.setText(response);
            }
        });
        loader.load();
    }
}
