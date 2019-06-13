package com.aiwan.server.user.account.protocol;

/**
 * 注册返回协议
 * @author dengzebiao
 * @since 2019.6.13
 * */
public class SM_Register {

    /** 注册状态
     * 1.成功
     * 0.失败
     * */
    private int status;

    /** 注册账号 */
    private String accountId;

    public static SM_Register valueOf(int status,String accountId){
       SM_Register sm_register = new SM_Register();
       sm_register.setAccountId(accountId);
       sm_register.setStatus(status);
       return sm_register;
    }
    public int getStatus() {
        return status;
    }

    public SM_Register setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public SM_Register setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }
}
