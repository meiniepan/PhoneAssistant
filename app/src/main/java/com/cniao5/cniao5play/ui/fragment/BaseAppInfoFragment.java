package com.cniao5.cniao5play.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cniao5.cniao5play.R;
import com.cniao5.cniao5play.bean.AppInfo;
import com.cniao5.cniao5play.bean.PageBean;
import com.cniao5.cniao5play.di.component.AppComponent;
import com.cniao5.cniao5play.presenter.AppInfoPresenter;
import com.cniao5.cniao5play.presenter.contract.AppInfoContract;
import com.cniao5.cniao5play.ui.adapter.AppInfoAdapter;
import com.cniao5.cniao5play.ui.widget.DividerItemDecoration;

import butterknife.BindView;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.ui.fragment
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public abstract class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenter>  implements AppInfoContract.AppInfoView,
        BaseQuickAdapter.RequestLoadMoreListener{



    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;


    protected AppInfoAdapter mAdapter;

    int page =0;



    @Override
    public void init() {

        mPresenter.requestData(type(),page);

        initRecyclerView();


    }


    protected void initRecyclerView(){


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) );

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);
        mAdapter = buildAdapter();

        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    abstract  int type();

    abstract AppInfoAdapter buildAdapter();

    @Override
    public int setLayout() {
        return R.layout.template_recycler_view;
    }



    @Override
    public void showResult(PageBean<AppInfo> pageBean) {

        mAdapter.addData(pageBean.getDatas());

        if(pageBean.isHasMore()){
            page++;
        }
        mAdapter.setEnableLoadMore(pageBean.isHasMore());
    }

    @Override
    public void onLoadMoreComplete() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreRequested() {

        mPresenter.requestData(type(),page);
    }
}
