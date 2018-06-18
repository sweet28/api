package com.carpi.api.controllerNew;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.arttraining.api.dao.SMSCheckCodeMapper;
import com.arttraining.api.dao.TokenMapper;
import com.arttraining.commons.util.TimeUtil;
import com.carpi.api.dao.APoolMapper;
import com.carpi.api.dao.BPoolMapper;
import com.carpi.api.dao.BankCardMapper;
import com.carpi.api.dao.FensAuthenticationMapper;
import com.carpi.api.dao.FensComputingPowerMapper;
import com.carpi.api.dao.FensEarnMapper;
import com.carpi.api.dao.FensLoginStateMapper;
import com.carpi.api.dao.FensMinerMapper;
import com.carpi.api.dao.FensTeamMapper;
import com.carpi.api.dao.FensTransactionMapper;
import com.carpi.api.dao.FensUserMapper;
import com.carpi.api.dao.FensWalletMapper;
import com.carpi.api.pojo.FensComputingPower;
import com.carpi.api.pojo.FensEarn;
import com.carpi.api.pojo.FensMiner;
import com.carpi.api.pojo.FensUser;

@Controller
@EnableScheduling
public class TimerController {
	@Autowired
	private FensUserMapper fensUserMapper;

	@Autowired
	private SMSCheckCodeMapper smsCheckCodeDao;

	@Autowired
	private TokenMapper tokenDao;

	@Autowired
	private FensAuthenticationMapper fensAuthenticationMapper;

	@Autowired
	private FensTeamMapper fensTeamMapper;

	@Autowired
	private FensComputingPowerMapper fensComputingPowerMapper;

	@Autowired
	private FensLoginStateMapper fensLoginStateMapper;

	@Autowired
	private FensMinerMapper fensMinerMapper;

	@Autowired
	private APoolMapper apoolMapper;

	@Autowired
	private BPoolMapper bpoolMapper;

	@Autowired
	private FensWalletMapper fensWalletMapper;

	@Autowired
	private FensTransactionMapper fensTransactionMapper;

	@Autowired
	private BankCardMapper bankcardMapper;
	
	@Autowired
	private FensEarnMapper fensEarnMapper;
	
	@Scheduled(cron= "0 * */2 * * ?")     ////0 * */2 * * ?:每两个小时执行一次；                    0 0/1 * * * ? ：每分钟执行一次
	public void fensAllTeamTempClob(){
		
        System.out.println("---->>执行");
        List<FensUser> allList = fensUserMapper.selectAllUserESPTdel();
        
        if(allList.size() > 0){
        	for(int j = 0; j < allList.size(); j++){
        		int gradeFlag = 0;// 会员等级标记，初始默认为无等级

        		List<FensUser> list = fensUserMapper.selectAllUserNoTJ();// 全部用户列表
        		List<FensUser> realList = new ArrayList<FensUser>();//全部用户 去重电话列表
        		List<String> realListOnlyPhone = new ArrayList<String>();//全部用户 去重电话列表只留电话
        		
        		System.out.println("------one size:"+list.size());
        		
        		for(int i = 0; i < list.size(); i++){
        			if(!realListOnlyPhone.contains(list.get(i).getPhone())){
        				realList.add(list.get(i));
        				realListOnlyPhone.add(list.get(i).getPhone());
        			}
        		}

        		System.out.println("------two size:"+realList.size());
        		System.out.println("------two size:"+realListOnlyPhone.size());
        		
        		List<FensMiner> mlist = fensMinerMapper.allMinerList();// 全部矿机列表

        		FensUser fm = fensUserMapper.selectByPrimaryKey(allList.get(j).getId());

        		/*
        		 * A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
        		 * B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
        		 * C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+
        		 */

        		//// 普通节点--start
        		Integer AFensNum = 0;// 直推粉丝数初始化
        		Integer AFensTeamNum = 0;// 粉丝团用户初始化
        		Integer AMinerCA2Num = 0;// 粉丝团及自己CA2矿机初始化
        		Integer AMinerCA3Num = 0;// 粉丝团及自己CA3矿机初始化
        		Integer AMinerCA4Num = 0;// 粉丝团及自己CA4矿机初始化
        		double APCPower = 0.00;// 自己及粉丝团算力初始化

        		// 1直推粉丝列表
        		List<FensUser> ztlist = fensUserMapper.selectAllUser(allList.get(j).getPhone());
        		AFensNum = ztlist.size();
        		/*
        		 * 2 粉丝团用户获取
        		 */
        		List<FensUser> listParentRecord = new ArrayList<FensUser>();// 粉丝团用户列表
        		List<Integer> listminer = new ArrayList<Integer>();// 粉丝团用户ID集合
        		getTreeChildRecord(listParentRecord, listminer, allList.get(j).getPhone(), realList);// 递归获取粉丝团列表

        		AFensTeamNum = listParentRecord.size();

        		System.out.println(listParentRecord.size() + "--fens grade--end--");

        		/*
        		 * 3、4粉丝团算力计算、矿机核算
        		 */
        		if (listParentRecord.size() > 0) {

        			if (mlist.size() > 0) {
        				for (int i = 0; i < mlist.size(); i++) {
        					if (listminer.contains(mlist.get(i).getFensUserId())) {
        						System.out.println(mlist.get(i).getFensUserId());
        						APCPower += mlist.get(i).getMinerComputingPower();
        						if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("2")
        								&& mlist.get(i).getIsDelete() == 0) {
        							AMinerCA2Num = AMinerCA2Num + 1;
        						}

        						if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("3")
        								&& mlist.get(i).getIsDelete() == 0) {
        							AMinerCA3Num = AMinerCA3Num + 1;
        						}

        						if (mlist.get(i).getMinerType() == 1 && mlist.get(i).getBak1().equals("4")
        								&& mlist.get(i).getIsDelete() == 0) {
        							AMinerCA4Num = AMinerCA4Num + 1;
        						}
        					}
        				}
        			}
        		}

        		System.out.println(listParentRecord.size());

        		// A、普通节点：1、直推粉丝10+；2、粉丝团用户500+；3、自己及粉丝团CA2矿机3+；4、自己及粉丝团算力12+
        		if (AFensNum >= 10 && AFensTeamNum >= 500 && AMinerCA2Num >= 3 && APCPower >= 12) {
        			gradeFlag = 1;
        		}
        		//// 普通节点--end

        		//// 中级节点--start
        		// B、高级节点：1、粉丝团用户3000+；2、算力80+；3、粉丝普通节点2+；4、CA3矿机5+；
        		if (gradeFlag == 1) {
        			if (AFensTeamNum >= 3000 && AMinerCA3Num >= 5 && APCPower >= 80) {
        				gradeFlag = 2;
        			}
        		}
        		//// 中级节点--end

        		//// 高级节点--start
        		// C、超级节点：1、粉丝用户10000+；2、算力300G+；3、粉丝高级节点3+；4、粉丝及自己CA4矿机5+；
        		if (gradeFlag == 2) {
        			if (AFensTeamNum >= 10000 && AMinerCA4Num >= 5 && APCPower >= 300) {
        				gradeFlag = 3;
        			}
        		}
        		//// 高级节点--end

        		/*
        		 * 进行下一节点冲击是否合格及截止时间判定
        		 */
        		String isGradeCan = "yes";//是否能够冲击下一等级
        		Date returnDate = null;//冲击下一等级的截止时间
        		Date dt = new Date();
        		Format f = new SimpleDateFormat("yyyy-MM-dd mm:hh:ss");
        		Calendar c = Calendar.getInstance();
        		int n = 28;//粉丝注册时间至冲击下一等级日期的间隔，默认为冲击普通节点
        		String nextGrade = "";
        		
        		if (gradeFlag == 0) {
        			c.setTime(fm.getCreateDate());
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        			if (c.getTime().getTime() >= dt.getTime()) {
        				isGradeCan = "yes";
        				nextGrade = "普通节点";
        				returnDate = fm.getCreateDate();
        			} else {
        				n = 70;
        				c.setTime(fm.getCreateDate());
        				c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        				if (c.getTime().getTime() >= dt.getTime()) {
        					isGradeCan = "yes";
        					nextGrade = "高级节点";
        					returnDate = fm.getCreateDate();
        				} else {
        					n = 130;
        					c.setTime(fm.getCreateDate());
        					c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        					if (c.getTime().getTime() >= dt.getTime()) {
        						isGradeCan = "yes";
        						nextGrade = "超级节点";
        						returnDate = fm.getCreateDate();
        					} else {
        						isGradeCan = "no";
        					}
        				}
        			}
        		}

        		/*
        		 *  冲击一个级别时间不够则到截止到下一个级别
        		 */
        		if (gradeFlag == 1) {
        			n = 70;
        			c.setTime(fm.getCreateDate());
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        			if (c.getTime().getTime() >= dt.getTime()) {
        				isGradeCan = "yes";
        				nextGrade = "高级节点";
        				returnDate = fm.getCreateDate();
        			} else {
        				n = 130;
        				c.setTime(fm.getCreateDate());
        				c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        				if (c.getTime().getTime() >= dt.getTime()) {
        					isGradeCan = "yes";
        					nextGrade = "超级节点";
        					returnDate = fm.getCreateDate();
        				} else {
        					isGradeCan = "no";
        				}
        			}
        		}
        		if (gradeFlag == 2) {
        			n = 130;
        			c.setTime(fm.getCreateDate());
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天

        			if (c.getTime().getTime() >= dt.getTime()) {
        				isGradeCan = "yes";
        				nextGrade = "超级节点";
        				returnDate = fm.getCreateDate();
        			} else {
        				isGradeCan = "no";
        			}
        		}
        		if (returnDate != null) {
        			c.setTime(returnDate);
        			c.add(Calendar.DAY_OF_MONTH, n);// 注册日期+n天
        		}

        		
        		/**
        		 *
        		 * 将节点等级达到的，进行分红及算力奖励
        		 * 先
        		 * 
        		 **/
        		/*分红  总量按50000为基数分  普通节点50个  高级节点 10个  超级节点 2个
        		 * 
        		 * 普通节点：当天获得前一天交易cpa总量手续费30%的收益分红，
        		 * 高级节点：当天获得前一天交易cpa总量手续费20%的收益分红，
        		 * 超级节点：当天获得前一天交易cpa总量手续费10%的收益分红，
        		 * 
        		 * 算力  只赠送一次
        		 * 普通节点：初次达到获得0.055
        		 * 高级节点：初次达到获得0.55
        		 * 超级节点：初次达到获得5.5
        		 */
        		
        		//// 分红----start
        		double cpaGiftBeishu = 60;//总量按50000为基数分  普通节点50个  高级节点 10个  超级节点 2个
        		double cpaTotal = 0.00;
        		
        		////设置时间
        		Calendar c2 = Calendar.getInstance();
        		Date dt2 = new Date();
        		c2.setTime(dt2);
        		c2.add(Calendar.DAY_OF_MONTH, -1);
        		
        		Date yesterday = c2.getTime();
        		System.out.println("昨天是:" + f.format(yesterday));
        		
        		cpaTotal = fensTransactionMapper.jylYesterdaySum();//查询昨天交易量
        		if(cpaTotal > 0){
        			
        			List<FensEarn> eranList =  fensEarnMapper.selectIsGiftFensEarn(allList.get(j).getId());
        			
        			if(eranList.size() <= 0 ){
        				Integer eranType = 0;
        				
        				if(gradeFlag == 1){
        					eranType = 41;
        					cpaGiftBeishu = 50;
        				}
        				if(gradeFlag == 2){
        					eranType = 42;
        					cpaGiftBeishu = 10;
        				}
        				if(gradeFlag == 3){
        					eranType = 43;
        					cpaGiftBeishu = 2;
        				}

        				double shijiCpa = cpaTotal*0.2/cpaGiftBeishu;
        				
        				FensEarn fEarn = new FensEarn();
        				
        				fEarn.setFensUserId(allList.get(j).getId());
        				fEarn.setCreateDate(TimeUtil.getTimeStamp());
        				fEarn.setIsDelete(0);
        				fEarn.setEarnType(eranType);
        				fEarn.setEarnDate(TimeUtil.getTimeStamp());
        				fEarn.setEarnState(2);//收益状态 1代表不锁定  2代表锁定
        				fEarn.setEarnCount(shijiCpa);
        				
        				fensEarnMapper.insertSelective(fEarn);
        			}
        			
        		}
        		
        		//// 分红----end
        		
        		//// 算力奖励----start
        		
        		if(gradeFlag == 1){
        			FensComputingPower fcp = new FensComputingPower();
        			fcp.setType(41);//41：普通节点；42：高级节点；43：超级节点
        			fcp.setFensUserId(allList.get(j).getId());
        			fcp.setCreateDate(TimeUtil.getTimeStamp());
        			fcp.setIsDelete(0);
        			
        			List<FensComputingPower> fcpList = fensComputingPowerMapper.selectFensGradePower(fcp);
        			if(fcpList.size() <= 0){
        				fcp.setComputingPower(0.055);
        				
        				int fcpResult = fensComputingPowerMapper.insertSelective(fcp);
        			}
        		}
        		
        		if(gradeFlag == 2){
        			FensComputingPower fcp = new FensComputingPower();
        			fcp.setType(42);//41：普通节点；42：高级节点；43：超级节点
        			fcp.setFensUserId(allList.get(j).getId());
        			fcp.setCreateDate(TimeUtil.getTimeStamp());
        			fcp.setIsDelete(0);
        			
        			List<FensComputingPower> fcpList = fensComputingPowerMapper.selectFensGradePower(fcp);
        			if(fcpList.size() <= 0){
        				fcp.setComputingPower(0.55);
        				
        				int fcpResult = fensComputingPowerMapper.insertSelective(fcp);
        			}
        		}
        		
        		if(gradeFlag == 3){
        			FensComputingPower fcp = new FensComputingPower();
        			fcp.setType(43);//41：普通节点；42：高级节点；43：超级节点
        			fcp.setFensUserId(allList.get(j).getId());
        			fcp.setCreateDate(TimeUtil.getTimeStamp());
        			fcp.setIsDelete(0);
        			
        			List<FensComputingPower> fcpList = fensComputingPowerMapper.selectFensGradePower(fcp);
        			if(fcpList.size() <= 0){
        				fcp.setComputingPower(5.5);
        				
        				int fcpResult = fensComputingPowerMapper.insertSelective(fcp);
        			}
        		}
        		
        		//// 算力奖励----end
        		
        		
//        		jr.put("grade", gradeFlag);//当前等级
//        		jr.put("nextgrade", nextGrade);//有希望冲击的下一等级
//        		jr.put("suanli", APCPower);
//        		jr.put("fensteamNum", AFensTeamNum);
//        		jr.put("isGradeCan", isGradeCan);
        	}
        }
        
    }
	
	/**
	 * 说明方法描述：递归查询子节点
	 * 
	 * @param listParentRecord
	 *            粉丝团集合
	 * @param parentUuid
	 *            父节点id
	 * @return
	 */

	private List<FensUser> getTreeChildRecord(List<FensUser> listParentRecord, List<Integer> listminer,
			String parentUuid, List<FensUser> allList) {
		System.out.println("parentID:" + parentUuid);
		// 遍历tmpList，找出所有的根节点和非根节点
		if (allList.size() > 0) {
			for (int i = 0; i < allList.size(); i++) {

				String refereePhone = allList.get(i).getRefereePhone();

				if (refereePhone != null && parentUuid != null) {

					if (allList.get(i).getRefereePhone().equals(parentUuid)) {

						listParentRecord.add(allList.get(i));
						if (allList.get(i).getIsDelete() == 0) {// 正常用户才计算算力
							listminer.add(allList.get(i).getId());
						}

						getTreeChildRecord(listParentRecord, listminer, allList.get(i).getPhone(), allList);

					}
				}
			}
		}
		return listParentRecord;
	}
	
	//@Scheduled(cron= "0 0/2 * * * ?")     ////0 * * /2 * * ?:每两个小时执行一次；                    0 0/1 * * * ? ：每分钟执行一次
	public void fensTeamGiftCPATempClob(){
		
		System.out.println("-----------------");
        
    }

}
