package com.ellfors.dagger2.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ellfors.dagger2.R;
import com.ellfors.dagger2.model.Girl;
import com.ellfors.extools.adapter.ExBaseRcvAdapter;
import com.ellfors.extools.base.ExBaseViewHolder;

import java.util.List;

import javax.inject.Inject;

/**
 * 显示福利的Adapter
 */
public class MainRcvAdapter extends ExBaseRcvAdapter
{
    private Context context;
    private List<Girl> list;

    @Inject
    public MainRcvAdapter(Context context, List<Girl> list)
    {
        super(false, false);
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup viewGroup, int i)
    {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_main,viewGroup,false));
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        if(viewHolder instanceof ItemViewHolder)
        {
            Glide.with(context)
                    .load(list.get(i).getUrl())
                    .into(((ItemViewHolder) viewHolder).iv_item);
        }
    }

    @Override
    public int getItemSize()
    {
        return list.size() == 0 || list == null ? 0 : list.size();
    }

    private class ItemViewHolder extends ExBaseViewHolder
    {
        ImageView iv_item;

        public ItemViewHolder(View itemView)
        {
            super(itemView);

            iv_item = $(R.id.item_img);
        }
    }
}
