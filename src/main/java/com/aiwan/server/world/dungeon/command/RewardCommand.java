package com.aiwan.server.world.dungeon.command;

import com.aiwan.server.base.executor.account.impl.AbstractAccountCommand;
import com.aiwan.server.monster.resource.DropBean;
import com.aiwan.server.monster.resource.MonsterResource;
import com.aiwan.server.publicsystem.common.Session;
import com.aiwan.server.publicsystem.service.SessionManager;
import com.aiwan.server.util.GetBean;
import com.aiwan.server.util.MonsterGenerateUtil;

import java.util.List;

/**
 * 奖励命令
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
public class RewardCommand extends AbstractAccountCommand {

    /**
     * 角色id
     */
    private Long rId;

    /**
     * 掉落物列表
     */
    private List<DropBean> list;

    /**
     * 经验值
     */
    private int experience;

    public RewardCommand(String accountId, Long rId, List<DropBean> list, int experience) {
        super(accountId);
        this.rId = rId;
        this.list = list;
        this.experience = experience;
    }

    @Override
    public void active() {
        //添加经验
        GetBean.getRoleService().addExperience(rId, experience);
        if (list.size() == 0) {
            //没有掉落物
            return;
        }
        //获取drop随机
        DropBean dropBean = list.get(MonsterGenerateUtil.getRandomNum(list.size()));
        //添加物品
        Session session = SessionManager.getSessionByAccountId(getAccountId());
        GetBean.getBackpackService().addPropToBack(getAccountId(), dropBean.getPropId(), dropBean.getNum(), session);
    }
}
