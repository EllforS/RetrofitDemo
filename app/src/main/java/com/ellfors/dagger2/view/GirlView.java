package com.ellfors.dagger2.view;


import android.content.Context;

import com.ellfors.dagger2.base.BaseView;
import com.ellfors.dagger2.model.Girl;

import java.util.List;

public interface GirlView extends BaseView
{
    void showList(List<Girl> list);
    Context getContext();
}
