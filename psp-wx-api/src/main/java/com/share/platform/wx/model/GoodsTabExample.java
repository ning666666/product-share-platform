package com.share.platform.wx.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodsTabExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GoodsTabExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Integer value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Integer value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Integer value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Integer> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Integer> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeIsNull() {
            addCriterion("goods_code is null");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeIsNotNull() {
            addCriterion("goods_code is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeEqualTo(String value) {
            addCriterion("goods_code =", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotEqualTo(String value) {
            addCriterion("goods_code <>", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeGreaterThan(String value) {
            addCriterion("goods_code >", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("goods_code >=", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeLessThan(String value) {
            addCriterion("goods_code <", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeLessThanOrEqualTo(String value) {
            addCriterion("goods_code <=", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeLike(String value) {
            addCriterion("goods_code like", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotLike(String value) {
            addCriterion("goods_code not like", value, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeIn(List<String> values) {
            addCriterion("goods_code in", values, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotIn(List<String> values) {
            addCriterion("goods_code not in", values, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeBetween(String value1, String value2) {
            addCriterion("goods_code between", value1, value2, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andGoodsCodeNotBetween(String value1, String value2) {
            addCriterion("goods_code not between", value1, value2, "goodsCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeIsNull() {
            addCriterion("shop_code is null");
            return (Criteria) this;
        }

        public Criteria andShopCodeIsNotNull() {
            addCriterion("shop_code is not null");
            return (Criteria) this;
        }

        public Criteria andShopCodeEqualTo(String value) {
            addCriterion("shop_code =", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeNotEqualTo(String value) {
            addCriterion("shop_code <>", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeGreaterThan(String value) {
            addCriterion("shop_code >", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeGreaterThanOrEqualTo(String value) {
            addCriterion("shop_code >=", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeLessThan(String value) {
            addCriterion("shop_code <", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeLessThanOrEqualTo(String value) {
            addCriterion("shop_code <=", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeLike(String value) {
            addCriterion("shop_code like", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeNotLike(String value) {
            addCriterion("shop_code not like", value, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeIn(List<String> values) {
            addCriterion("shop_code in", values, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeNotIn(List<String> values) {
            addCriterion("shop_code not in", values, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeBetween(String value1, String value2) {
            addCriterion("shop_code between", value1, value2, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopCodeNotBetween(String value1, String value2) {
            addCriterion("shop_code not between", value1, value2, "shopCode");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNull() {
            addCriterion("shop_name is null");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNotNull() {
            addCriterion("shop_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopNameEqualTo(String value) {
            addCriterion("shop_name =", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotEqualTo(String value) {
            addCriterion("shop_name <>", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThan(String value) {
            addCriterion("shop_name >", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_name >=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThan(String value) {
            addCriterion("shop_name <", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThanOrEqualTo(String value) {
            addCriterion("shop_name <=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLike(String value) {
            addCriterion("shop_name like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotLike(String value) {
            addCriterion("shop_name not like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameIn(List<String> values) {
            addCriterion("shop_name in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotIn(List<String> values) {
            addCriterion("shop_name not in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameBetween(String value1, String value2) {
            addCriterion("shop_name between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotBetween(String value1, String value2) {
            addCriterion("shop_name not between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleIsNull() {
            addCriterion("goods_title is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleIsNotNull() {
            addCriterion("goods_title is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleEqualTo(String value) {
            addCriterion("goods_title =", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleNotEqualTo(String value) {
            addCriterion("goods_title <>", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleGreaterThan(String value) {
            addCriterion("goods_title >", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleGreaterThanOrEqualTo(String value) {
            addCriterion("goods_title >=", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleLessThan(String value) {
            addCriterion("goods_title <", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleLessThanOrEqualTo(String value) {
            addCriterion("goods_title <=", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleLike(String value) {
            addCriterion("goods_title like", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleNotLike(String value) {
            addCriterion("goods_title not like", value, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleIn(List<String> values) {
            addCriterion("goods_title in", values, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleNotIn(List<String> values) {
            addCriterion("goods_title not in", values, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleBetween(String value1, String value2) {
            addCriterion("goods_title between", value1, value2, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andGoodsTitleNotBetween(String value1, String value2) {
            addCriterion("goods_title not between", value1, value2, "goodsTitle");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNull() {
            addCriterion("original_price is null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIsNotNull() {
            addCriterion("original_price is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceEqualTo(String value) {
            addCriterion("original_price =", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotEqualTo(String value) {
            addCriterion("original_price <>", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThan(String value) {
            addCriterion("original_price >", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceGreaterThanOrEqualTo(String value) {
            addCriterion("original_price >=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThan(String value) {
            addCriterion("original_price <", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLessThanOrEqualTo(String value) {
            addCriterion("original_price <=", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceLike(String value) {
            addCriterion("original_price like", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotLike(String value) {
            addCriterion("original_price not like", value, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceIn(List<String> values) {
            addCriterion("original_price in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotIn(List<String> values) {
            addCriterion("original_price not in", values, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceBetween(String value1, String value2) {
            addCriterion("original_price between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andOriginalPriceNotBetween(String value1, String value2) {
            addCriterion("original_price not between", value1, value2, "originalPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceIsNull() {
            addCriterion("discounted_price is null");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceIsNotNull() {
            addCriterion("discounted_price is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceEqualTo(String value) {
            addCriterion("discounted_price =", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceNotEqualTo(String value) {
            addCriterion("discounted_price <>", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceGreaterThan(String value) {
            addCriterion("discounted_price >", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceGreaterThanOrEqualTo(String value) {
            addCriterion("discounted_price >=", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceLessThan(String value) {
            addCriterion("discounted_price <", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceLessThanOrEqualTo(String value) {
            addCriterion("discounted_price <=", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceLike(String value) {
            addCriterion("discounted_price like", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceNotLike(String value) {
            addCriterion("discounted_price not like", value, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceIn(List<String> values) {
            addCriterion("discounted_price in", values, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceNotIn(List<String> values) {
            addCriterion("discounted_price not in", values, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceBetween(String value1, String value2) {
            addCriterion("discounted_price between", value1, value2, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountedPriceNotBetween(String value1, String value2) {
            addCriterion("discounted_price not between", value1, value2, "discountedPrice");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeIsNull() {
            addCriterion("opening_time is null");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeIsNotNull() {
            addCriterion("opening_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeEqualTo(Date value) {
            addCriterion("opening_time =", value, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeNotEqualTo(Date value) {
            addCriterion("opening_time <>", value, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeGreaterThan(Date value) {
            addCriterion("opening_time >", value, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("opening_time >=", value, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeLessThan(Date value) {
            addCriterion("opening_time <", value, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeLessThanOrEqualTo(Date value) {
            addCriterion("opening_time <=", value, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeIn(List<Date> values) {
            addCriterion("opening_time in", values, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeNotIn(List<Date> values) {
            addCriterion("opening_time not in", values, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeBetween(Date value1, Date value2) {
            addCriterion("opening_time between", value1, value2, "openingTime");
            return (Criteria) this;
        }

        public Criteria andOpeningTimeNotBetween(Date value1, Date value2) {
            addCriterion("opening_time not between", value1, value2, "openingTime");
            return (Criteria) this;
        }

        public Criteria andGoodsLabIsNull() {
            addCriterion("goods_lab is null");
            return (Criteria) this;
        }

        public Criteria andGoodsLabIsNotNull() {
            addCriterion("goods_lab is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsLabEqualTo(String value) {
            addCriterion("goods_lab =", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabNotEqualTo(String value) {
            addCriterion("goods_lab <>", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabGreaterThan(String value) {
            addCriterion("goods_lab >", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabGreaterThanOrEqualTo(String value) {
            addCriterion("goods_lab >=", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabLessThan(String value) {
            addCriterion("goods_lab <", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabLessThanOrEqualTo(String value) {
            addCriterion("goods_lab <=", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabLike(String value) {
            addCriterion("goods_lab like", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabNotLike(String value) {
            addCriterion("goods_lab not like", value, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabIn(List<String> values) {
            addCriterion("goods_lab in", values, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabNotIn(List<String> values) {
            addCriterion("goods_lab not in", values, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabBetween(String value1, String value2) {
            addCriterion("goods_lab between", value1, value2, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsLabNotBetween(String value1, String value2) {
            addCriterion("goods_lab not between", value1, value2, "goodsLab");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIsNull() {
            addCriterion("goods_type is null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIsNotNull() {
            addCriterion("goods_type is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeEqualTo(String value) {
            addCriterion("goods_type =", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotEqualTo(String value) {
            addCriterion("goods_type <>", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThan(String value) {
            addCriterion("goods_type >", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeGreaterThanOrEqualTo(String value) {
            addCriterion("goods_type >=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThan(String value) {
            addCriterion("goods_type <", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLessThanOrEqualTo(String value) {
            addCriterion("goods_type <=", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeLike(String value) {
            addCriterion("goods_type like", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotLike(String value) {
            addCriterion("goods_type not like", value, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeIn(List<String> values) {
            addCriterion("goods_type in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotIn(List<String> values) {
            addCriterion("goods_type not in", values, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeBetween(String value1, String value2) {
            addCriterion("goods_type between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andGoodsTypeNotBetween(String value1, String value2) {
            addCriterion("goods_type not between", value1, value2, "goodsType");
            return (Criteria) this;
        }

        public Criteria andMainImgIsNull() {
            addCriterion("main_img is null");
            return (Criteria) this;
        }

        public Criteria andMainImgIsNotNull() {
            addCriterion("main_img is not null");
            return (Criteria) this;
        }

        public Criteria andMainImgEqualTo(String value) {
            addCriterion("main_img =", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgNotEqualTo(String value) {
            addCriterion("main_img <>", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgGreaterThan(String value) {
            addCriterion("main_img >", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgGreaterThanOrEqualTo(String value) {
            addCriterion("main_img >=", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgLessThan(String value) {
            addCriterion("main_img <", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgLessThanOrEqualTo(String value) {
            addCriterion("main_img <=", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgLike(String value) {
            addCriterion("main_img like", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgNotLike(String value) {
            addCriterion("main_img not like", value, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgIn(List<String> values) {
            addCriterion("main_img in", values, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgNotIn(List<String> values) {
            addCriterion("main_img not in", values, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgBetween(String value1, String value2) {
            addCriterion("main_img between", value1, value2, "mainImg");
            return (Criteria) this;
        }

        public Criteria andMainImgNotBetween(String value1, String value2) {
            addCriterion("main_img not between", value1, value2, "mainImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgIsNull() {
            addCriterion("details_img is null");
            return (Criteria) this;
        }

        public Criteria andDetailsImgIsNotNull() {
            addCriterion("details_img is not null");
            return (Criteria) this;
        }

        public Criteria andDetailsImgEqualTo(String value) {
            addCriterion("details_img =", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgNotEqualTo(String value) {
            addCriterion("details_img <>", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgGreaterThan(String value) {
            addCriterion("details_img >", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgGreaterThanOrEqualTo(String value) {
            addCriterion("details_img >=", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgLessThan(String value) {
            addCriterion("details_img <", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgLessThanOrEqualTo(String value) {
            addCriterion("details_img <=", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgLike(String value) {
            addCriterion("details_img like", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgNotLike(String value) {
            addCriterion("details_img not like", value, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgIn(List<String> values) {
            addCriterion("details_img in", values, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgNotIn(List<String> values) {
            addCriterion("details_img not in", values, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgBetween(String value1, String value2) {
            addCriterion("details_img between", value1, value2, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andDetailsImgNotBetween(String value1, String value2) {
            addCriterion("details_img not between", value1, value2, "detailsImg");
            return (Criteria) this;
        }

        public Criteria andVideoIsNull() {
            addCriterion("video is null");
            return (Criteria) this;
        }

        public Criteria andVideoIsNotNull() {
            addCriterion("video is not null");
            return (Criteria) this;
        }

        public Criteria andVideoEqualTo(String value) {
            addCriterion("video =", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotEqualTo(String value) {
            addCriterion("video <>", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThan(String value) {
            addCriterion("video >", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoGreaterThanOrEqualTo(String value) {
            addCriterion("video >=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThan(String value) {
            addCriterion("video <", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLessThanOrEqualTo(String value) {
            addCriterion("video <=", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoLike(String value) {
            addCriterion("video like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotLike(String value) {
            addCriterion("video not like", value, "video");
            return (Criteria) this;
        }

        public Criteria andVideoIn(List<String> values) {
            addCriterion("video in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotIn(List<String> values) {
            addCriterion("video not in", values, "video");
            return (Criteria) this;
        }

        public Criteria andVideoBetween(String value1, String value2) {
            addCriterion("video between", value1, value2, "video");
            return (Criteria) this;
        }

        public Criteria andVideoNotBetween(String value1, String value2) {
            addCriterion("video not between", value1, value2, "video");
            return (Criteria) this;
        }

        public Criteria andQualificationsIsNull() {
            addCriterion("qualifications is null");
            return (Criteria) this;
        }

        public Criteria andQualificationsIsNotNull() {
            addCriterion("qualifications is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationsEqualTo(String value) {
            addCriterion("qualifications =", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotEqualTo(String value) {
            addCriterion("qualifications <>", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsGreaterThan(String value) {
            addCriterion("qualifications >", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsGreaterThanOrEqualTo(String value) {
            addCriterion("qualifications >=", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLessThan(String value) {
            addCriterion("qualifications <", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLessThanOrEqualTo(String value) {
            addCriterion("qualifications <=", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLike(String value) {
            addCriterion("qualifications like", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotLike(String value) {
            addCriterion("qualifications not like", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsIn(List<String> values) {
            addCriterion("qualifications in", values, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotIn(List<String> values) {
            addCriterion("qualifications not in", values, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsBetween(String value1, String value2) {
            addCriterion("qualifications between", value1, value2, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotBetween(String value1, String value2) {
            addCriterion("qualifications not between", value1, value2, "qualifications");
            return (Criteria) this;
        }

        public Criteria andAfterSalesIsNull() {
            addCriterion("after_sales is null");
            return (Criteria) this;
        }

        public Criteria andAfterSalesIsNotNull() {
            addCriterion("after_sales is not null");
            return (Criteria) this;
        }

        public Criteria andAfterSalesEqualTo(String value) {
            addCriterion("after_sales =", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesNotEqualTo(String value) {
            addCriterion("after_sales <>", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesGreaterThan(String value) {
            addCriterion("after_sales >", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesGreaterThanOrEqualTo(String value) {
            addCriterion("after_sales >=", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesLessThan(String value) {
            addCriterion("after_sales <", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesLessThanOrEqualTo(String value) {
            addCriterion("after_sales <=", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesLike(String value) {
            addCriterion("after_sales like", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesNotLike(String value) {
            addCriterion("after_sales not like", value, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesIn(List<String> values) {
            addCriterion("after_sales in", values, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesNotIn(List<String> values) {
            addCriterion("after_sales not in", values, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesBetween(String value1, String value2) {
            addCriterion("after_sales between", value1, value2, "afterSales");
            return (Criteria) this;
        }

        public Criteria andAfterSalesNotBetween(String value1, String value2) {
            addCriterion("after_sales not between", value1, value2, "afterSales");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("created_by like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("created_by not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}