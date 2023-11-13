package com.atguigu.spzx.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import com.atguigu.spzx.model.entity.user.UserCollect;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.model.vo.product.ProductSkuVO;
import com.atguigu.spzx.user.mapper.UserBrowseHistoryMapper;
import com.atguigu.spzx.user.mapper.UserCollectMapper;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

// 业务接口实现
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserCollectMapper userCollectMapper;

	@Autowired
	private UserBrowseHistoryMapper userBrowseHistoryMapper;

	@Autowired
	private ProductFeignClient productFeignClient;

	@Autowired
	private RedisTemplate<String , String> redisTemplate;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void register(UserRegisterDto userRegisterDto) {

		// 1、UserRegisterDto获取数据
		String username = userRegisterDto.getUsername();
		String password = userRegisterDto.getPassword();
		String nickName = userRegisterDto.getNickName();
		String code = userRegisterDto.getCode();


		if(StringUtils.isEmpty(username) ||
				StringUtils.isEmpty(password) ||
				StringUtils.isEmpty(nickName) ||
				StringUtils.isEmpty(code)) {
			throw new GuiguException(ResultCodeEnum.DATA_ERROR);
		}
		//2、验证码校验
		//2.1 从redis获取验证码
		String codeValueRedis = redisTemplate.opsForValue().get("phone:code:" + username);
		//2.2 校验验证码
		if(!code.equals(codeValueRedis)) {
			throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
		}

		//3、检验用户名不能重复
		UserInfo userInfo = userInfoMapper.getByUsername(username);
		if(null != userInfo) {
			throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
		}

		//4、保存用户信息
		userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setNickName(nickName);
		userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
		userInfo.setPhone(username);
		userInfo.setStatus(1);
		userInfo.setSex(0);
		userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
		userInfoMapper.save(userInfo);

		// 删除Redis中的数据
		redisTemplate.delete("phone:code:" + username) ;
	}


	//业务接口实现
	@Override
	public String login(UserLoginDto userLoginDto) {
		String username = userLoginDto.getUsername();
		String password = userLoginDto.getPassword();

		//校验参数
		if(StringUtils.isEmpty(username) ||
				StringUtils.isEmpty(password)) {
			throw new GuiguException(ResultCodeEnum.DATA_ERROR);
		}

		UserInfo userInfo = userInfoMapper.getByUsername(username);
		if(null == userInfo) {
			throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
		}

		//校验密码
		String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!md5InputPassword.equals(userInfo.getPassword())) {
			throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
		}

		//校验是否被禁用
		if(userInfo.getStatus() == 0) {
			throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
		}

		String token = UUID.randomUUID().toString().replaceAll("-", "");
		redisTemplate.opsForValue().set("user:spzx:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
		System.out.println(token);
		return token;
	}


//	@Override
//	public UserInfoVo getCurrentUserInfo(String token) {
//		String userInfoJSON = redisTemplate.opsForValue().get("user:spzx:" + token);
//		if(StringUtils.isEmpty(userInfoJSON)) {
//			throw new GuiguException(ResultCodeEnum.LOGIN_AUTH) ;
//		}
//		UserInfo userInfo = JSON.parseObject(userInfoJSON , UserInfo.class) ;
//		UserInfoVo userInfoVo = new UserInfoVo();
//		BeanUtils.copyProperties(userInfo, userInfoVo);
//		System.out.println(userInfoVo);
//		return userInfoVo ;
//	}

	@Override
	public UserInfoVo getCurrentUserInfo(String token) {
		UserInfo userInfo = AuthContextUtil.getUserInfo();
		UserInfoVo userInfoVo = new UserInfoVo();
		BeanUtils.copyProperties(userInfo, userInfoVo);
		return userInfoVo ;
	}

	@Override
	public void saveUserCollect(Long id) {
		UserInfo userInfo = AuthContextUtil.getUserInfo();
		UserCollect userCollect = new UserCollect();
		userCollect.setUserId(userInfo.getId());
		userCollect.setCreateTime(new Date());
		userCollect.setUpdateTime(new Date());
		userCollect.setSkuId(id);
		userCollect.setIsDeleted(0);
		userCollectMapper.saveUserCollect(userCollect);
	}

//	UserInfo userInfo = AuthContextUtil.getUserInfo();
	//		//根据条件查询所有数据
//		int offset = (page - 1) * limit;
//		List<UserCollect> userCollects = userCollectMapper.findUserBrowseHistoryPage(userInfo.getId(), offset, limit);
//		//远程调用查询商品的suk信息
//		List<ProductSku> productSkus = new ArrayList<>();
//		for (UserCollect userCollect : userCollects) {
//			ProductSku productSku = productFeignClient.getBySkuId(userCollect.getSkuId());
//			productSkus.add(productSku);
//		}
	@Override
	public PageInfo<UserCollect> findUserBrowseHistoryPage(Integer page, Integer limit) {
		PageHelper.startPage(page , limit) ;
		UserInfo userInfo = AuthContextUtil.getUserInfo();
		//根据条件查询所有数据
		List<UserCollect> userCollects = userCollectMapper.findUserBrowseHistoryPage(userInfo.getId()) ;
		//查询商品的suk信息
		List<ProductSkuVO> productSkus = new ArrayList<>();

		for (UserCollect userCollect : userCollects) {
			ProductSkuVO productSkuVO = new ProductSkuVO();
			ProductSku productSku = productFeignClient.getBySkuId(userCollect.getSkuId());
			BeanUtils.copyProperties(productSku, productSkuVO);
			productSkuVO.setSukId(userCollect.getSkuId());
			productSkus.add(productSkuVO);
		}
		PageInfo<UserCollect> pageInfo = new PageInfo(productSkus);
		return pageInfo;
	}

	@Override
	public PageInfo<UserBrowseHistory> findUserCollectPage(Integer page, Integer limit) {
		PageHelper.startPage(page , limit) ;
		UserInfo userInfo = AuthContextUtil.getUserInfo();
		//根据条件查询所有数据
		List<UserBrowseHistory> userBrowseHistories = userCollectMapper.findUserCollectPage(userInfo.getId()) ;
		//查询商品的suk信息
		List<ProductSkuVO> productSkus = new ArrayList<>();
		for (UserBrowseHistory userBrowseHistory : userBrowseHistories) {
			ProductSkuVO productSkuVO = new ProductSkuVO();
			ProductSku productSku = productFeignClient.getBySkuId(userBrowseHistory.getSkuId());
			BeanUtils.copyProperties(productSku, productSkuVO);
			productSkuVO.setSukId(userBrowseHistory.getSkuId());
			productSkus.add(productSkuVO);
		}
		PageInfo<UserBrowseHistory> pageInfo = new PageInfo(productSkus);
		return pageInfo;
	}

	//取消收藏
	@Transactional
	@Override
	public void updatecancelCollect(Long skuId) {
		UserInfo userInfo = AuthContextUtil.getUserInfo();
		userBrowseHistoryMapper.updatecancelCollect(skuId , userInfo.getId());

	}

	//添加收藏
	@Override
	@Transactional
	public void savecollect(Long skuId) {
		UserInfo userInfo = AuthContextUtil.getUserInfo();
		if (userInfo != null) {
			UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.selectcollect(skuId, userInfo.getId());
			if (userBrowseHistory == null) {
				userBrowseHistoryMapper.savecollect(skuId, userInfo.getId());
			} else {
				userBrowseHistoryMapper.updatecollect(skuId, userInfo.getId());
			}
		} else {
			throw new GuiguException(ResultCodeEnum.DATA_ERROR);
		}
	}

	//查询商品是否已经收藏
	@Override
	public Boolean findUserisCollect(Long skuId) {
		UserInfo userInfo = AuthContextUtil.getUserInfo();
		if (userInfo != null) {
			UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.selectusercollect(skuId, userInfo.getId());
			if (userBrowseHistory == null) {
				return false;
			} else {
				return true;
			}
		} else {
			throw new GuiguException(ResultCodeEnum.DATA_ERROR);
		}
	}

	@Override
	public UserBrowseHistory getMostFrequentSkuId() {
		UserBrowseHistory userBrowseHistory = userBrowseHistoryMapper.getMostFrequentSkuId();
		return userBrowseHistory;
	}

}