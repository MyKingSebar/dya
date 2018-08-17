package cn.v1.unionc_user.ui.home.health;

public interface IRespCallBack {

    public final int DO=1;

    public boolean callback(int callCode, Object... param);
}
