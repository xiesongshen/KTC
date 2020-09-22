package com.ktc.user.service;

import com.ktc.user.dao.UserDao;
import com.ktc.user.pojo.User;
import com.xss.entity.Result;
import com.xss.entity.StatusCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description 用户 服务层
 * @author admin
 * @date 2020-08-24 16:33:58
*/
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	* 查询全部列表
	* @return
	*/
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	* 条件查询+分页
	* @param whereMap
	* @param page
	* @param size
	* @return
	*/
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	/**
 	* 条件查询
	* @param whereMap
	* @return
	*/
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	* 根据ID查询实体
	* @param id
	* @return
	*/
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	* 增加
	* @param user
	*/
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		userDao.save(user);
	}

	/**
	* 修改
	* @param user
	*/
	public void update(User user) {
		userDao.save(user);
	}

	/**
	* 删除
	* @param id
	*/
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	* 动态条件构建
	* @param searchMap
	* @return
	*/
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//手机号码
				if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
					predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
				}
				//密码
				if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
					predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
				}
				//昵称
				if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
					predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
				}
				//性别
				if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
					predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
				}
				//出生年月日
				if (searchMap.get("birthday")!=null && !"".equals(searchMap.get("birthday"))) {
					predicateList.add(cb.like(root.get("birthday").as(String.class), "%"+(String)searchMap.get("birthday")+"%"));
				}
				//头像
				if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
					predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
				}
				//E-Mail
				if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
					predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
				}
				//注册日期
				if (searchMap.get("regdate")!=null && !"".equals(searchMap.get("regdate"))) {
					predicateList.add(cb.like(root.get("regdate").as(String.class), "%"+(String)searchMap.get("regdate")+"%"));
				}
				//修改日期
				if (searchMap.get("updatedate")!=null && !"".equals(searchMap.get("updatedate"))) {
					predicateList.add(cb.like(root.get("updatedate").as(String.class), "%"+(String)searchMap.get("updatedate")+"%"));
				}
				//最后登陆日期
				if (searchMap.get("lastdate")!=null && !"".equals(searchMap.get("lastdate"))) {
					predicateList.add(cb.like(root.get("lastdate").as(String.class), "%"+(String)searchMap.get("lastdate")+"%"));
				}
				//在线时长（分钟）
				if (searchMap.get("online")!=null && !"".equals(searchMap.get("online"))) {
					predicateList.add(cb.like(root.get("online").as(String.class), "%"+(String)searchMap.get("online")+"%"));
				}
				//兴趣
				if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
					predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
				}
				//个性
				if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
					predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
				}
				//粉丝数
				if (searchMap.get("fanscount")!=null && !"".equals(searchMap.get("fanscount"))) {
					predicateList.add(cb.like(root.get("fanscount").as(String.class), "%"+(String)searchMap.get("fanscount")+"%"));
				}
				//关注数
				if (searchMap.get("followcount")!=null && !"".equals(searchMap.get("followcount"))) {
					predicateList.add(cb.like(root.get("followcount").as(String.class), "%"+(String)searchMap.get("followcount")+"%"));
				}

				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	/*
	 * @author Xss
	 * @date 2020/8/24
	 * @param [mobile]
	 * @return void
	 * @description 发送验证码给MQ
	 */
    public void sendSms(String mobile) {

    	//生成6位随机数
		String code = RandomStringUtils.randomNumeric(6);
		System.out.println(code);

		//把手机号和验证码存入redis
		redisTemplate.opsForValue().set("sms_"+mobile,code,60, TimeUnit.SECONDS);

		//把手机号和验证码发送给MQ
		HashMap<String, String> dataMap = new HashMap<>();
		dataMap.put("mobile",mobile);
		dataMap.put("code",code);

		rabbitMessagingTemplate.convertAndSend("sms",dataMap);
	}

	/*
	 * @author Xss
	 * @date 2020/8/24
	 * @param [code]
	 * @return com.xss.entity.Result
	 * @description 用户注册
	 */
	public Result register(User user,String code) {
		Object checkCode = redisTemplate.opsForValue().get("sms_" + user.getMobile());

		if (checkCode == null){
			//验证码过期
			return new Result(false, StatusCode.ERROR,"验证码过期");
		}else {
			checkCode = checkCode.toString();

			if (code.equals(checkCode)){
				user.setId(idWorker.nextId()+"" );
				user.setFollowcount(0);//关注数
				user.setFanscount(0);//粉丝数
				user.setOnline(0L);//在线时长
				user.setRegdate(new Date());//注册日期
				user.setUpdatedate(new Date());//更新日期
				user.setLastdate(new Date());//最后登陆日期

				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				userDao.save(user);

				redisTemplate.delete("sms_"+user.getMobile());
			}else {
				//验证码错误
				return new Result(false,StatusCode.ERROR,"验证码错误");
			}
		}
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/*
	 * @author Xss
	 * @date 2020/8/25
	 * @param [mobile, password]
	 * @return com.ktc.user.pojo.User
	 * @description 用户登录
	 */
	public User login(String mobile, String password) {
		User user = userDao.findByMobile(mobile);

		if (user != null&&bCryptPasswordEncoder.matches(password,user.getPassword())){
			return user;
		}

		return null;
	}

	/*
	 * @author Xss
	 * @date 2020/8/26
	 * @param [userid, count]
	 * @return void
	 * @description  更新粉丝数
	 */
	@Transactional
	public void updateFansCount(String userid, Integer count) {
		userDao.updateFansCount(userid,count);
	}

	/*
	 * @author Xss
	 * @date 2020/8/26
	 * @param [userid, count]
	 * @return void
	 * @description  更新关注数
	 */
	public void updateFollowCount(String userid, Integer count) {

		userDao.updateFollowCount(userid, count);
	}
}
