package com.xss.service;

import com.xss.dao.LabelDao;
import com.xss.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author XSS
 * @date 2020/8/18
 * @desc
 */
@Service
public class LabelService {

    @Autowired
    LabelDao dao;

    @Autowired
    IdWorker idWorker;

    /*
     * @author Xss
     * @date 2020/8/18
     * @param [label]
     * @return void
     * @description  添加标签
     */
    public void add(Label label) {
        label.setId(idWorker.nextId() + "");
        dao.save(label);
    }

    /*
     * @author Xss
     * @date 2020/8/18
     * @param [id]
     * @return void
     * @description 根据id删除标签
     */
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    /*
     * @author Xss
     * @date 2020/8/18
     * @param [label]
     * @return void
     * @description 修改标签
     */
    public void update(Label label) {
        dao.save(label);
    }

    /*
     * @author Xss
     * @date 2020/8/18
     * @param [id]
     * @return com.xss.pojo.Label
     * @description  根据id查单个标签
     */
    public Label findById(String id) {

        return dao.findById(id).get();
    }

    /*
     * @author Xss
     * @date 2020/8/18
     * @param []
     * @return java.util.List<com.xss.pojo.Label>
     * @description 查询全部标签
     */
    public List<Label> findAll() {
        return dao.findAll();
    }

    /*
     * @author Xss
     * @date 2020/8/19
     * @param [searchMap]
     * @return java.util.List<com.xss.pojo.Label>
     * @description 根据条件查询标签
     */
    public List<Label> search(Map searchMap) {
        Specification<Label> spec = createSpecification(searchMap);
        return dao.findAll(spec);
    }

    private Specification<Label> createSpecification(Map searchMap) {
        Specification<Label> spec = new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                ArrayList<Predicate> pres = new ArrayList<>();
                if (searchMap.get("labelname") != null && !searchMap.get("labelname").equals("")) {
                    pres.add(cb.like(root.get("labelname").as(String.class), "%" + searchMap.get("labelname") + "%"));
                }
                if (searchMap.get("state") != null && !searchMap.get("state").equals("")) {
                    pres.add(cb.equal(root.get("state").as(String.class), searchMap.get("state")));
                }
                if (searchMap.get("recommend") != null && !searchMap.get("recommend").equals("")) {
                    pres.add(cb.equal(root.get("recommend").as(String.class), searchMap.get("recommend")));
                }

                Predicate[] predicates = new Predicate[pres.size()];

                return cb.and(pres.toArray(predicates));
            }

        };

        return spec;
    }

    /*
     * @author Xss
     * @date 2020/8/19
     * @param [searchMap, page, size]
     * @return org.springframework.data.domain.Page<com.xss.pojo.Label>
     * @description 条件+查询分页信息
     */
    public Page<Label> findPage(Map searchMap, Integer page, Integer size) {
        Specification<Label> spec = createSpecification(searchMap);
        return dao.findAll(spec, PageRequest.of(page-1,size));
    }
}
