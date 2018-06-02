package com.carpi.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.carpi.api.pojo.Bminer;
import com.carpi.api.pojo.FensMiner;

public interface FensMinerMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(FensMiner record);

	int insertSelective(FensMiner record);

	FensMiner selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(FensMiner record);

	int updateByPrimaryKey(FensMiner record);

	// 根据粉丝id查询矿机
	List<FensMiner> selectMiner(Integer fensUserId);

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
	
	//查询矿机的算力（根据粉丝id）
	Double sum(@Param("fensUserId") Integer fensUserId);

	//转入运行池
	int updateyxc(FensMiner record);
}