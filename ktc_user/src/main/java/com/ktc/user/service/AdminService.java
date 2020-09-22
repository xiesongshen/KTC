package com.ktc.user.service;

import com.ktc.user.dao.AdminDao;
import com.ktc.user.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 管理员 服务层
 * @author admin
 * @date 2020-08-24 16:33:29
*/
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	* 查询全部列表
	* @return
	*/
	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	/**
	* 条件查询+分页
	* @param whereMap
	* @param page
	* @param size
	* @return
	*/
	public Page<Admin> findSearch(Map whereMap, int page, int size) {
		Specification<Admin> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return adminDao.findAll(specification, pageRequest);
	}

	/**
 	* 条件查询
	* @param whereMap
	* @return
	*/
	public List<Admin> findSearch(Map whereMap) {
		Specification<Admin> specification = createSpecification(whereMap);
		return adminDao.findAll(specification);
	}

	/**
	* 根据ID查询实体
	* @param id
	* @return
	*/
	public Admin findById(String id) {
		return adminDao.findById(id).get();
	}

	/**
	* 增加
	* @param admin
	*/
	public void add(Admin admin) {
		admin.setId( idWorker.nextId()+"" );

		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));

		adminDao.save(admin);
	}

	/**
	* 修改
	* @param admin
	*/
	public void update(Admin admin) {
		adminDao.save(admin);
	}

	/**
	* 删除
	* @param id
	*/
	public void deleteById(String id) {
		adminDao.deleteById(id);
	}

	/**
	* 动态条件构建
	* @param searchMap
	* @return
	*/
	private Specification<Admin> createSpecification(Map searchMap) {

		return new Specification<Admin>() {

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//登陆名称
				if (searchMap.get("loginname")!=null && !"".equals(searchMap.get("loginname"))) {
					predicateList.add(cb.like(root.get("loginname").as(String.class), "%"+(String)searchMap.get("loginname")+"%"));
				}
				//密码
				if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
					predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
				}
				//状态
				if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
					predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
				}

				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	/*
	 * @author Xss
	 * @date 2020/8/25
	 * @param [loginname, password]
	 * @return com.ktc.user.pojo.Admin
	 * @description 管理员登录
	 */
	public Admin login(String loginname, String password) {
		Admin admin = adminDao.findByLoginname(loginname);

		if(admin!=null && bCryptPasswordEncoder.matches(password,admin.getPassword())){
			return admin;
		}

		return null;
	}
}
