package com.ellfors.dagger2.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.ellfors.dagger2.R;
import com.ellfors.dagger2.base.BaseActivity;
import com.ellfors.dagger2.model.Girl;
import com.ellfors.dagger2.presenter.GirlPresenter;
import com.ellfors.dagger2.ui.adapter.MainRcvAdapter;
import com.ellfors.dagger2.view.GirlView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * MVP + Dagger2 + RxJava + Retrofit 封装
 */
public class MainActivity extends BaseActivity implements GirlView
{
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_btn)
    Button btn_touch_me;
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;

    @Inject
    GirlPresenter girlPresenter;

    @Override
    public int getLayout()
    {
        return R.layout.activity_main;
    }

    @Override
    public void initInject()
    {
        /* 注入 */
        getActivityComponent().inject(this);
        girlPresenter.attachView(this);
    }

    @Override
    public void initEventAndData()
    {
        /* ToolBar */
        mToolbar.setTitle("This is Title");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        /* RecyclerView */
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        /* Destroy方法中取消RxJava订阅 */
        girlPresenter.detachView();
    }

    @Override
    public void showList(List<Girl> list)
    {
        /* 显示获取到的List */
        MainRcvAdapter adapter = new MainRcvAdapter(mContext,list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public Context getContext()
    {
        return mContext;
    }

    /**
     * 点击获取列表
     */
    @OnClick(R.id.main_btn) void doGetList()
    {
        girlPresenter.getGirlList();
    }

    @Override
    public void showError(String msg)
    {
        showToast(msg);
    }


    //----------------------------下面是笔记---------------------------------------------------------

    /**
     * Dagger2 通过注解来生成代码，定义不同的角色，主要的注解有：
     * @Inject、@Module、@Component、@Provides、@Scope、@SubComponent 等。
     *
     * @Inject:     通常在需要依赖的地方使用这个注解。
     *              换句话说，你用它告诉Dagger这个类或者字段需要依赖注入。
     *              这样，Dagger就会构造一个这个类的实例并满足他们的依赖。
     *
     * @Module:     Modules类里面的方法专门提供依赖，所以我们定义一个类，
     *              用@Module注解，这样Dagger在构造类的实例的时候，
     *              就知道从哪里去找到需要的依赖。
     *              modules的一个重要特征是它们设计为分区并组合在一起
     *              （比如说，在我们的app中可以有多个组成在一起的modules）。
     *
     * @Provides:   在modules中，我们定义的方法是用这个注解，
     *              以此来告诉Dagger我们想要构造对象并提供这些依赖。
     *
     * @Component:  Components从根本上来说就是一个注入器，
     *              也可以说是@Inject和@Module的桥梁，
     *              它的主要作用就是连接这两个部分。
     *              Components可以提供所有定义了的类型的实例，
     *              比如：我们必须用@Component注解一个接口然后列出所有的 @Modules组成该组件，
     *              如果缺失了任何一块都会在编译的时候报错。所有的组件都可以通过它的modules知道依赖的范围。
     *
     * @Scope:      Scopes可是非常的有用，Dagger2可以通过自定义注解限定注解作用域，
     *              即可以限定依赖类实例的生命周期，比如有的跟activity生命周期一样，
     *              有的是app生命周期等等。这是一个非常强大的特点，因为就如前面说的一样,
     *              没必要让每个对象都去了解如何管理他们的实例。
     *
     * @Qualifier:  当两个依赖类型一样，无法鉴别他们的时候，我们就可以使用这个注解标示。
     *              相当如给依赖打上不同的“tag”,例如：在Android中，我们会需要不同类型的context，
     *              所以我们就可以定义 @qualifier注解“@ForApplication”和“@ForActivity”，
     *              这样当注入一个context的时候，我们就可以告诉 Dagger我们想要哪种类型的context。
     */

}
