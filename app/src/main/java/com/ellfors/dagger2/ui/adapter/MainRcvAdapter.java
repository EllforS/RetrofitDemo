package com.ellfors.dagger2.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ellfors.dagger2.R;
import com.ellfors.dagger2.model.Girl;
import com.ellfors.extools.base.recyclerview.BaseRecyclerAdapter;
import com.ellfors.extools.base.recyclerview.BaseRecyclerViewHolder;
import com.ellfors.extools.utils.ExImageLoader;
import com.kingstarit.tjxs_ent.base.recyclerview.BaseRecyclerData;

import java.util.List;


/**
 * 显示福利的Adapter
 */
public class MainRcvAdapter extends BaseRecyclerAdapter
{
    public MainRcvAdapter(Context mContext, List<BaseRecyclerData> items)
    {
        super(mContext, items);
    }

    @Override
    protected BaseRecyclerViewHolder onCreate(ViewGroup viewGroup, int i)
    {
        return new ItemViewHolder(getItemView(R.layout.listitem_main, viewGroup), this);
    }

    @Override
    protected void onBind(BaseRecyclerViewHolder baseRecyclerViewHolder, int i)
    {
        ItemViewHolder itemHolder = (ItemViewHolder) baseRecyclerViewHolder;
        Girl bean = (Girl) items.get(i).getData();
        ExImageLoader.load(mContext, bean.getUrl(), itemHolder.iv_item);
    }

    private class ItemViewHolder extends BaseRecyclerViewHolder
    {
        ImageView iv_item;

        public ItemViewHolder(View itemView, BaseRecyclerAdapter adapter)
        {
            super(itemView, adapter);
            iv_item = $(R.id.item_img);
        }
    }
}
