package com.yrbase.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yrbase.adapter.base.ItemViewDelegate;
import com.yrbase.adapter.base.ItemViewDelegateManager;
import com.yrbase.adapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 16/4/9
 * <p>
 * MultiItemTypeAdapter adapter = new MultiItemTypeAdapter(this,mDatas);
 * adapter.addItemViewDelegate(new MsgSendItemDelagate());
 * adapter.addItemViewDelegate(new MsgComingItemDelagate());
 * 每种Item类型对应一个ItemViewDelegete，例如：
 * <p>
 * public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessage>
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements DataIO<T> {
    protected Context mContext;
    protected List<T> mDatas = new ArrayList<>();


    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        if (datas != null) {
            mDatas = datas;
        }
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public void addAt(int location, T elem) {
        if (location >= 0) {
            mDatas.add(location, elem);
            notifyDataSetChanged();
        }
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
        if (null != elem && null != mDatas) {
            mDatas.remove(elem);
            notifyDataSetChanged();
        }
    }

    @Override
    public void deleteAll(List elements) {

    }

    @Override
    public void removeAt(int index) {
        if (mDatas != null && mDatas.size() > index) {
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
            synchronized (MultiItemTypeAdapter.class) {
                if (mDatas != null && mDatas.size() > 0) {
                    mDatas.clear();
                    notifyDataSetChanged();
                }
                mDatas.addAll(elements);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public List<T> getAll() {
        return mDatas;
    }

    @Override
    public T get(int position) {
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
