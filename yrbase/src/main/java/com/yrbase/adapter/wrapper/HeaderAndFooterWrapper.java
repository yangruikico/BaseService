package com.yrbase.adapter.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yrbase.adapter.CommonAdapter;
import com.yrbase.adapter.DataIO;
import com.yrbase.adapter.MultiItemTypeAdapter;
import com.yrbase.adapter.base.ViewHolder;
import com.yrbase.adapter.utils.WrapperUtils;

import java.util.List;


/**
 * Created by zhy on 16/6/23
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataIO {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public RecyclerView.Adapter mInnerAdapter;
    private final List mDatas;

    public HeaderAndFooterWrapper(CommonAdapter adapter) {
        mInnerAdapter = adapter;

        mDatas = adapter.getDatas();

    }


    public HeaderAndFooterWrapper(MultiItemTypeAdapter adapter) {
        mInnerAdapter = adapter;
        mDatas = adapter.getDatas();

    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;

        } else if (mFootViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

    @Override
    public void addAt(int location, Object elem) {

    }


    @Override
    public void addAllAt(int location, List elements) {
        if (location >= 0) {
            mDatas.addAll(location, elements);
            notifyDataSetChanged();
        }

    }

    @Override
    public void remove(Object elem) {
        if (null != elem && null != mDatas && mDatas.contains(elem)) {
            mDatas.remove(elem);
            notifyDataSetChanged();
        }
    }

    @Override
    public void deleteAll(List elements) {

    }

    @Override
    public void removeAt(int index) {
        if (index >= 0 && null != mDatas && mDatas.size() > index) {
            mDatas.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public void clear() {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void replace(Object oldElem, Object newElem) {

    }

    @Override
    public void replaceAt(int index, Object elem) {

    }

    @Override
    public void add(Object elem) {
        mDatas.add((T) elem);
        notifyDataSetChanged();
    }


    public void add(Object elem, boolean only) {
        if (only && !mDatas.contains(elem)) {
            mDatas.add((T) elem);
        }
        notifyDataSetChanged();
    }


    @Override
    public void addAll(List elements) {
        if (null != elements) {
            mDatas.addAll(elements);
            notifyDataSetChanged();
        }
    }

    @Override
    public void replaceAll(List elements) {
        if (null != elements) {
            if (mDatas != null && mDatas.size() > 0) {
                mDatas.clear();
            }
            mDatas.addAll(elements);
            notifyDataSetChanged();
        }

    }

    @Override
    public List<T> getAll() {
        return mDatas;
    }

    @Override
    public Object get(int position) {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getSize() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public boolean contains(Object elem) {
        return false;
    }
}
