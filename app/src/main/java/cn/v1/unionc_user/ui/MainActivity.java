package cn.v1.unionc_user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.LogOutEventData;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.discover.DiscoverFragment;
import cn.v1.unionc_user.ui.discover.DiscoverFragment2;
import cn.v1.unionc_user.ui.discover.DiscoverFragment3;
import cn.v1.unionc_user.ui.find.FindFragment;
import cn.v1.unionc_user.ui.me.PersonalFragment;
import cn.v1.unionc_user.ui.home.MessageFragment;

public class MainActivity extends BaseActivity {

    private Unbinder unbinder;

    boolean logout=false;

    @BindView(R.id.rg)
    RadioGroup rg;

    private Fragment mCurrentfragment;//记录选中的fragment
    private int mCurrentCheckedId;//记录选中的id
    private MessageFragment messageFragment;
    private DiscoverFragment3 discoverFragment3;
    private PersonalFragment personalFragment;
    private FindFragment findFragment;
    private final String MESSAGE = "message";
    private final String DISCOVER = "discover";
    private final String PERSONAL = "personal";
//    private String[] tags = new String[]{MESSAGE, DISCOVER, PERSONAL};

    private long exitTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("linshi","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder=ButterKnife.bind(this);
        BusProvider.getInstance().register(this);
//        getRongToken();
//        initLocation();
        initView();
    }

    @Override
    protected void onResume() {
        if (!isLogin()) {
            mCurrentCheckedId=R.id.message;
            rg.check(mCurrentCheckedId);
            Log.d("linshi","onresume");
        }
        super.onResume();
    }

    private void initView() {
        // 在页面重启时，Fragment会被保存恢复，而此时再加载Fragment会重复加载，导致重叠 ;
        if(outState == null){
            // 或者 if(findFragmentByTag(mFragmentTag) == null)
            // 正常情况下去 加载根Fragment
            Log.d("linshi","outState==null");
            messageFragment = new MessageFragment();
            mCurrentfragment = messageFragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.ll_fragment_container, messageFragment).commit();
        }else{
//            Log.d("linshi","outState!=null");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (messageFragment != null)
//            transaction.remove(messageFragment);
//        if (discoverFragment3 != null)
//            transaction.remove(discoverFragment3);
//        if (personalFragment != null)
//            transaction.remove(personalFragment);
//
            messageFragment = new MessageFragment();
            mCurrentfragment = messageFragment;
            transaction.add(R.id.ll_fragment_container, messageFragment).commit();

        }
        mCurrentCheckedId = R.id.message;
//        stateCheck(outState);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("linshi","onCheckedChanged:"+checkedId);
                switch (checkedId) {
                    case R.id.message:
                        mCurrentCheckedId = R.id.message;
                        if (null == messageFragment) {
                            messageFragment = new MessageFragment();
                        }
                        switchContent(messageFragment, 0);
                        break;
                    case R.id.discover:
                        mCurrentCheckedId = R.id.discover;
                        if (null == discoverFragment3) {
                            discoverFragment3 = new DiscoverFragment3();
                        }
                        switchContent(discoverFragment3, 1);
                        break;
                    case R.id.find:
                        mCurrentCheckedId = R.id.find;
                        if (null == findFragment) {
                            findFragment = new FindFragment();
                        }
                        switchContent(findFragment, 2);

                        break;
                    case R.id.personal:
                        if (null == personalFragment) {
                            personalFragment = new PersonalFragment();
                        }
                        if (isLogin()) {
                            switchContent(personalFragment, 3);
                        } else {
                            if(!logout){
                                goNewActivity(LoginActivity.class);
                            }else {
                                logout=false;
                            }
                            Log.d("linshi","LoginActivity:Main");
                            mCurrentCheckedId=R.id.message;
                            rg.check(mCurrentCheckedId);
                        }
                        break;
                }
            }
        });
        rg.check(R.id.message);
    }

    /**
     * fragment 切换
     *
     * @param to
     */
    public void switchContent(Fragment to, int position) {
        Log.d("linshi","switchContent:"+position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentfragment != to) {
            if (!to.isAdded()) { // 先判断是否被add过
                Log.d("linshi","!to.isAdded()");
                transaction.hide(mCurrentfragment)
                        .add(R.id.ll_fragment_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                Log.d("linshi","isAdded()");
                transaction.hide(mCurrentfragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
        mCurrentfragment = to;
    }

//    /**
//     * 状态检测 用于内存不足时的时候保证fragment不会重叠
//     */
//    private void stateCheck(Bundle saveInstanceState) {
//        Logger.i(new Gson().toJson(saveInstanceState));
//        if (null != saveInstanceState) {
//            Log.d("linshi","null != saveInstanceState");
//            //通过tag找回失去引用但是存在内存中的fragment.id相同
//            MessageFragment messageFragment = (MessageFragment) getSupportFragmentManager().findFragmentByTag(tags[0]);
//            DiscoverFragment discoverFragment = (DiscoverFragment) getSupportFragmentManager().findFragmentByTag(tags[1]);
//            PersonalFragment personalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentByTag(tags[2]);
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .show(messageFragment)
//                    .hide(discoverFragment)
//                    .hide(personalFragment)
//                    .commit();
//        } else {
//            Log.d("linshi","null == saveInstanceState");
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            if (null == messageFragment) {
//                messageFragment = new MessageFragment();
//            }
//            transaction.add(R.id.ll_fragment_container, messageFragment, tags[0]).commit();
//        }
//        mCurrentfragment = messageFragment;
//    }
    @Subscribe
    public void logoutReturn(LogOutEventData data){
        logout=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("linshi","onSaveInstanceState");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (messageFragment != null)
            transaction.remove(messageFragment);
        if (discoverFragment3 != null)
            transaction.remove(discoverFragment3);
        if (findFragment != null)
            transaction.remove(findFragment);
        if (personalFragment != null)
            transaction.remove(personalFragment);
//        goNewActivity(StartActivity.class);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 1000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}
