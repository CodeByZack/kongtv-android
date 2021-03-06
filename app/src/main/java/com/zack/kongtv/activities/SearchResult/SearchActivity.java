package com.zack.kongtv.activities.SearchResult;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zack.kongtv.Adapter.MovieListAdapter;
import com.zack.kongtv.R;
import com.zack.kongtv.activities.MovieDetail.MovieDetailActivity;
import com.zack.kongtv.bean.MovieDetailBean;
import com.zack.kongtv.bean.SearchResultBean;
import com.zack.kongtv.util.CountEventHelper;
import com.zack.kongtv.view.GridSpacingItemDecoration;
import com.zackdk.Utils.SPUtil;
import com.zackdk.Utils.ToastUtil;
import com.zackdk.base.BaseMvpActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements ISearchView{

    private ImageView icBack,icSearch;
    private EditText searchText;
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TagFlowLayout flowLayout;
    private LinearLayout recentSearchList;
    private List<MovieDetailBean> data = new LinkedList<>();
    private String searchtext;
    private List<String> searchHistoryList = new LinkedList<>();

    @Override
    public int setView() {
        return R.layout.activity_search;
    }

    @Override
    public void initBasic(Bundle savedInstanceState) {
        initView();
        initLogic();
        requestData();
    }

    private void requestData() {
        SPUtil.loadArray(this,searchHistoryList,"searchHistory");
        flowLayout.getAdapter().notifyDataChanged();

    }

    private void initLogic() {
        adapter = new MovieListAdapter(this,R.layout.movie_item,data);
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity,MovieDetailActivity.class).putExtra("url",data.get(position).getTargetUrl()));
            }
        });
        icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtext = searchText.getText().toString();
                if(TextUtils.isEmpty(searchtext)){
                    ToastUtil.showToast("输入点什么再搜索吧!");
                    return;
                }
                search();
            }
        });
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString())){
                    data.clear();
                    adapter.notifyDataSetChanged();
                    searchtext = "";
                    presenter.clear();
                    if(recentSearchList.getVisibility() == View.GONE){
                        recentSearchList.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchtext = searchText.getText().toString();
                    search();
                }
                return false;
            }
        });
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,getResources().getDimensionPixelSize(R.dimen.little_margin),true));
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                search();
            }
        },recyclerView);

        flowLayout.setAdapter(new TagAdapter<String>(searchHistoryList) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.search_item,
                        flowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                searchtext = searchHistoryList.get(position);
                searchText.setText(searchHistoryList.get(position));
                search();
                return false;
            }
        });
    }

    private void search() {
        if(recentSearchList.getVisibility() == View.VISIBLE){
            recentSearchList.setVisibility(View.GONE);
        }
        CountEventHelper.countMovieSearch(this,searchtext);
        if(!searchHistoryList.contains(searchtext)){
            searchHistoryList.add(searchtext);
            if(searchHistoryList.size()>20){
                searchHistoryList.remove(0);
            }
            SPUtil.deleteArray(this,"searchHistory");
            SPUtil.saveArray(this,searchHistoryList,"searchHistory");
            flowLayout.getAdapter().notifyDataChanged();
        }

        presenter.search(searchtext);
    }

    private void initView() {
        icBack = findViewById(R.id.ic_back);
        icSearch = findViewById(R.id.ic_search);
        searchText = findViewById(R.id.search);
        swipeRefreshLayout = findViewById(R.id.sw_refresh);
        recyclerView = findViewById(R.id.recycleview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        flowLayout = findViewById(R.id.id_flowlayout);
        recentSearchList = findViewById(R.id.ll_recnet_search);
    }

    @Override
    protected void initImmersionBar() {
        immersionBar.titleBar(findViewById(R.id.toolbar)).statusBarColor(R.color.colorPrimaryDark).init();
    }

    @Override
    protected SearchPresenter setPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void updateView(List<MovieDetailBean> data) {
        adapter.addData(data);
    }

    @Override
    public void loadMoreComplete() {
        adapter.loadMoreComplete();
    }

    @Override
    public void loadMoreEnd() {
        adapter.loadMoreEnd();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
