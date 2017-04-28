# 介绍
Loader是一个方便Activity控制加载状态，加载缓存的工具类
# 下载

    compile 'com.github.linxin6560:loader-base:1.3.0'
    
# 调用前的工作
1.修改BaseActivity使之实现INetWorkView:

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
2.根据产品UI，实现ILoadStateHelper,IRefreshViewHelper,IListViewHelper
3.编写Model:
    
    public class SingleDataModel extends Model<String> {

        private String response;

        @Override
        public boolean isEmpty() {
            return TextUtils.isEmpty(response);
        }

        @Override
        public void setData(boolean isRefreshing, String response) {
            this.response = response;
        }

        @Override
        public void load(final OnLoadListener<String> listener) {
            listener.onStart();
            MainApi api = ApiManager.getInstance().getMainApi();
            Call call = api.getTestCall();
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    listener.onSuccess(response.body());
                }
            });
        }
    }
或者

    public class ListDataModel extends ListModel<String, String> {

        public ListDataModel(List<String> list) {
            super(list);
        }

        @Override
        protected boolean ensureHasNext(String response, List<String> mapList) {
            return page < 10;
        }

        @Override
        protected List<String> map(String response) {
            List<String> list = new ArrayList<>();
            int size = mList.size();
            for (int i = 0; i < 20; i++) {
                list.add((i + size) + ":XXXXXXXXXXXXXXXXXXXXXXXXXX");
            }
            return list;
        }

        @Override
        public void load(final OnLoadListener<List<String>> listener) {
            listener.onStart();
            MainApi api = ApiManager.getInstance().getMainApi();
            Call call = api.getTestCall();
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    listener.onSuccess(response.body());
                }
            });
        }
    }
    
4.调用：

    public class SingleDataActivity extends BaseActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_single_data);
            SwipeRefreshLayout layout = (SwipeRefreshLayout) findViewById(R.id.act_single_data_sfl);
            final TextView textView = (TextView) findViewById(R.id.act_single_data_tv);
            SingleDataModel model = new SingleDataModel();
            DataLoader<String> loader = new DataLoader<>(this, model);
            loader.setLoadStateHelper(new LoadStateHelper(layout));//控制加载中，加载失败，加载成功
            loader.setRefreshViewHelper(new RefreshHelper(layout));//控制刷新
            loader.setOnLoadSuccessListener(new OnLoadSuccessListener<String>() {
                @Override
                public void onSuccess(boolean isRefreshing, String response) {
                    textView.setText(response);
                }
            });
            loader.load();
        }
    }
    
或者

    public class ListDataActivity extends BaseActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_list_data);
            SwipeRefreshLayout layout = (SwipeRefreshLayout) findViewById(R.id.act_list_data_sfl);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.act_list_data_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<String> list = new ArrayList<>();
            MyAdapter adapter = new MyAdapter(list);
            recyclerView.setAdapter(adapter);

            ListDataModel model = new ListDataModel(list);
            ListLoader<String, String> loader = new ListLoader<>(this, model);
            loader.setLoadStateHelper(new LoadStateHelper(layout));//控制加载中，加载失败，加载成功
            loader.setRefreshViewHelper(new RefreshHelper(layout));//控制刷新
            loader.setListViewHelper(new RecyclerViewHelper(recyclerView));//控制自动加载下一页
            loader.load();
        }
    }
这样子就可以了。
