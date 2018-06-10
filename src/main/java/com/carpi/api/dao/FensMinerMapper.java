package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.FensMiner;

public interface FensMinerMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(FensMiner record);

	int insertSelective(FensMiner record);

	FensMiner selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(FensMiner record);

	int updateByPrimaryKey(FensMiner record);

	// 根据粉丝id修改
	int updateFen(FensMiner record);

	// 根据粉丝id查询矿机
	List<FensMiner> selectMiner(Integer fensUserId);

	// 根据粉丝id查询矿机
	List<FensMiner> selectMiner3(Integer fensUserId);

	// 根据粉丝id查询运行矿机
	List<FensMiner> selectMiner2(Integer fensUserId);

	// 根据粉丝id查询A矿机
	List<FensMiner> selectAMiner(Integer fensUserId);

	// 根据粉丝id查询A矿机矿池
	List<FensMiner> selectAMinerKC(Integer fensUserId);

	// 根据粉丝id查询A矿机总数
	int selectASum(Integer fensUserId);

	// 根据粉丝id查询B矿机
	List<FensMiner> selectBMiner(Integer fensUserId);

	// 根据粉丝id查询B矿机矿池
	List<FensMiner> selectBMinerKC(Integer fensUserId);

	List<FensMiner> selectEranMiner(Integer fensUserId);

	// 粉丝的b矿机列表
	int selectUserMiner(FensMiner record);

	// 查询购买矿机类型的数量
	int selectSum(@Param("bak1") String bak1, @Param("fensUserId") Integer fensUserId);

	// 查询矿机的算力（根据粉丝id）
	Double sum(@Param("fensUserId") Integer fensUserId);

	// 查询矿机的算力列表（根据粉丝id）
	List<FensMiner> geRenList(@Param("fensUserId") Integer fensUserId);

	// 转入运行池
	int updateyxc(FensMiner record);

	// 根据粉丝id查询AB矿机库存
	List<FensMiner> selectABMinnerKC(Integer fensUserId);

	// 粉丝算力和（亲友团算力和）
	Double selectSuanLiHe(@Param("phone") String phone);

	// 粉丝算力列表（或者收益列表）
	List<FensMiner> suanLiList(@Param("phone") String phone);

	// 粉丝算力列表（或者收益列表）
	List<FensMiner> suanLiList2(@Param("phone") String phone);

	// 亲友团收益（矿机价格的1%）
	Double shouYiHe(@Param("phone") String phone);

	// 粉丝矿机的总价值
	Double kjJz(@Param("phone") String phone);

	// 查询所有运行矿机
	List<FensMiner> allMinerList();

	// 矿机总量
	int minerSum(Integer fensUserId);
	
	// 矿机的总价格
	Double minerPrice(Integer fensUserId);
	
	// 矿机的提取收益
	Double minerEarn(Integer fensUserId);

}