package com.xmy.service.dao;

import com.xmy.bean.bean.Article;
import com.xmy.bean.vo.ArticleInfo;
import org.springframework.util.StringUtils;

import java.util.Map;

public class SqlProvider {

	boolean isEmpty(Object o){
		if(null==o||"".equals(o)){
			return true;
		}
		return false;
	}

	public String getCount(Map<String, Object> map){
		String sql = "select count(1) from article where 1=1";
		if(!isEmpty(map.get("nowdays"))){
			if("week".equals(map.get("nowdays").toString()))
				sql += " and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time)";
			else
				sql += " and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(create_time)";

		}
		if(!isEmpty(map.get("plate"))){
			sql += " and plate = '"+ map.get("plate").toString()+"'";
		}
		if(!isEmpty(map.get("approve"))){
			sql += " and approve = '" + map.get("approve").toString()+"'";
		}
		return sql;
	}

	public String getArticleInfo(Map<String, Object> map){
		String sql = "select a.id,a.title,a.content,a.pics,a.create_time createTime, a.address,a.zanNum,a.plate,a.user_id userId,b.nickname,b.headPic,b.sex from article a, user b where a.user_id = b.id";
		if(!isEmpty(map.get("nowdays"))){
			if("week".equals(map.get("nowdays").toString()))
				sql += " and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(a.create_time)";
			else
				sql += " and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(a.create_time)";

		}
		if(!isEmpty(map.get("plate"))){
			sql += " and a.plate = '"+ map.get("plate").toString()+"'";
		}
		if(!isEmpty(map.get("approve"))){
			sql += " and a.approve = '" + map.get("approve").toString()+"'";
		}
		sql += " limit "+map.get("currentResult").toString()+","+map.get("pageSize");
		return sql;
	}

	public String updateInfo(Map<String,Object> user){
		String sql = "update user set ";
		if(null!=user.get("nickname")) {
			sql += "nickname='"+user.get("nickname").toString()+"',";
		}
		if(null!=user.get("email")) {
			sql += "email='"+user.get("email").toString()+"',";
		}
		if(null!=user.get("phone")) {
			sql += "phone='"+user.get("phone").toString()+"',";
		}
		if(null!=user.get("sex")) {
			sql += "sex='"+user.get("sex").toString()+"'";
		}
		sql += " where id='"+user.get("id").toString()+"'";
		return sql;
	}

	public String insertArticle(Map<String,Object> article){
		String sql = "insert into article" +
				"(title,content,pics,create_time,address,zanNum,plate,user_id)" +
				"values('" +
				article.get("title")+"','" +
				article.get("content")+"','"+
				article.get("pics")+"',"+
				"now(),'"+
				article.get("address")+"',"+
				"0,"+
				article.get("plate")+","+
				article.get("userId")+
				")";
		return sql;
	}

	/**
	 * promotionName
	 * productId
	 * bankId
	 * startTime
	 * endTime
	 * @param conditionVo
	 * @return
	 */
	public String selectPromotionList(Map<String,Object> conditionVo)
	{
		String sql = "SELECT a.id sid,a.promotionName spName,a.bankId bankId,a.productId productId,a.productName,a.bankName,a.oriPrice,a.approvestatus sastatus,a.ruleId,a.promotionId,a.approveDesc saDesc,(case \n" +
                "when a.approvestatus = 'W' and now() < b.startTime then 'W' \n" +
                "when a.approvestatus = 'W' and now() >= b.startTime  then 'O' \n" +
                "when a.approvestatus = 'R' and now() >= b.startTime then 'O' \n" +
                "when a.approvestatus = 'R' and now() < b.startTime then 'R'\n" +
                "when a.approvestatus = 'A' and now() < b.startTime then 'S'\n" +
                "when a.approvestatus = 'A' and now() >= b.startTime and now() < b.endTime then 'P' \n" +
                "when a.approvestatus = 'A' and now() > b.endTime then 'F' \n" +
                "when a.approvestatus = 'T' then 'T' end) as showStatus, b.promotionName,b.approveStatus,(case when now() between b.startTime and b.endTime then 'P' else 'T' END) as pstatus,b.startTime,b.endTime,b.pdesc,b.proConType,b.proThreshold,b.proPrice,b.approveDesc from csh.csh_sub_promotion a join csh.csh_promotion b on a.promotionId = b.id where a.enable = 0";
		
		if(!StringUtils.isEmpty(conditionVo.get("promotionName")))
		{
			sql = sql+" and b.promotionName = '" + conditionVo.get("promotionName")+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("productId")))
		{
			sql = sql+" and a.productId = '" + conditionVo.get("productId")+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("bankId")))
		{
			sql = sql+" and a.bankId = '" + conditionVo.get("bankId")+"'";
		}

		//xumy
		if(!StringUtils.isEmpty(conditionVo.get("proConType")))
		{
			sql = sql+" and b.proConType = '" + conditionVo.get("proConType")+"'";
		}

		if(!StringUtils.isEmpty(conditionVo.get("startTime")))
		{
			sql = sql+" and b.startTime >= '" + conditionVo.get("startTime")+" 00:00:00"+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("endTime")))
		{
			sql = sql+" and b.endTime <= '" + conditionVo.get("endTime")+" 23:59:59"+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("showStatus"))){
		    sql = sql+" and (case \n" +
                    "when a.approvestatus = 'W' and now() < b.startTime then 'W' \n" +
                    "when a.approvestatus = 'W' and now() >= b.startTime  then 'O' \n" +
                    "when a.approvestatus = 'R' and now() >= b.startTime then 'O' \n" +
                    "when a.approvestatus = 'R' and now() < b.startTime then 'R'\n" +
                    "when a.approvestatus = 'A' and now() < b.startTime then 'S'\n" +
                    "when a.approvestatus = 'A' and now() >= b.startTime and now() < b.endTime then 'P' \n" +
                    "when a.approvestatus = 'A' and now() > b.endTime then 'F' \n" +
                    "when a.approvestatus = 'T' then 'T' end) = '" + conditionVo.get("showStatus") + "'";
        }
			sql = sql+" ORDER BY b.createTime DESC ";
			sql = sql+" limit "+conditionVo.get("currentResult")+","+conditionVo.get("pageSize");
		return sql;
	}
	
	public String selectPromotionListCount(Map<String,Object> conditionVo)
	{
		String sql = "SELECT count(1) from csh.csh_sub_promotion a join csh.csh_promotion b on a.promotionId = b.id where a.enable = 0";
		
		if(!StringUtils.isEmpty(conditionVo.get("promotionName")))
		{
			sql = sql+" and b.promotionName = '" + conditionVo.get("promotionName")+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("productId")))
		{
			sql = sql+" and a.productId = '" + conditionVo.get("productId")+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("bankId")))
		{
			sql = sql+" and a.bankId = '" + conditionVo.get("bankId")+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("startTime")))
		{
			sql = sql+" and b.startTime >= '" + conditionVo.get("startTime")+" 00:00:00"+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("endTime")))
		{
			sql = sql+" and b.endTime <= '" + conditionVo.get("endTime")+" 23:59:59"+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("proConType")))
		{
			sql = sql+" and b.proConType = '" + conditionVo.get("proConType")+"'";
		}
		if(!StringUtils.isEmpty(conditionVo.get("showStatus"))){
			sql = sql+" and (case \n" +
					"when a.approvestatus = 'W' and now() < b.startTime then 'W' \n" +
					"when a.approvestatus = 'W' and now() >= b.startTime  then 'O' \n" +
					"when a.approvestatus = 'R' and now() >= b.startTime then 'O' \n" +
					"when a.approvestatus = 'R' and now() < b.startTime then 'R'\n" +
					"when a.approvestatus = 'A' and now() < b.startTime then 'S'\n" +
					"when a.approvestatus = 'A' and now() >= b.startTime and now() < b.endTime then 'P' \n" +
					"when a.approvestatus = 'A' and now() > b.endTime then 'F' \n" +
					"when a.approvestatus = 'T' then 'T' end) = '" + conditionVo.get("showStatus") + "'";
		}
			
		
		return sql;
	}
	
	public String updateSubPromotion(Map<String,String> subpromotion)
	{
		String sql = "UPDATE csh_sub_promotion SET approvestatus = 'W'";
		if(!StringUtils.isEmpty(subpromotion.get("promotionName")))
		{
			sql = sql+",promotionName='"+subpromotion.get("promotionName")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("productId")))
		{
			sql = sql+",productId='"+subpromotion.get("productId")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("productName")))
		{
			sql = sql+",productName='"+subpromotion.get("productName")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("oriPrice")))
		{
			sql = sql+",oriPrice='"+subpromotion.get("oriPrice")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("isSkill")))
		{
			sql = sql+",isSkill='"+subpromotion.get("isSkill")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("bankCharge")))
		{
			sql = sql+",put_bankCharge='"+subpromotion.get("bankCharge")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("promotlabel")))
		{
			sql = sql+",label='"+subpromotion.get("promotlabel")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("stockcount")))
		{
			sql = sql+",stockcount='"+subpromotion.get("stockcount")+"'";
		}
		if(!StringUtils.isEmpty(subpromotion.get("userlimit")))
		{
			sql = sql+",userlimit='"+subpromotion.get("userlimit")+"'";
		}
		sql = sql + " where id="+subpromotion.get("subId");
		return sql;
	}
	
	public String updatePromotion(Map<String,String> promotion)
	{
		String sql = "UPDATE csh_promotion SET id=id";
		
		if(!StringUtils.isEmpty(promotion.get("promotionName")))
		{
			sql = sql+",promotionName='"+promotion.get("promotionName")+"'";
		}
		if(!StringUtils.isEmpty(promotion.get("startTime")))
		{
			sql = sql+",startTime='"+promotion.get("startTime")+"'";
		}
		if(!StringUtils.isEmpty(promotion.get("endTime")))
		{
			sql = sql+",endTime='"+promotion.get("endTime")+"'";
		}
		if(!StringUtils.isEmpty(promotion.get("pdesc")))
		{
			sql = sql+",pdesc='"+promotion.get("pdesc")+"'";
		}
		if(!StringUtils.isEmpty(promotion.get("proConType")))
		{
			sql = sql+",proConType='"+promotion.get("proConType")+"'";
		}
		if(!StringUtils.isEmpty(promotion.get("proThreshold")))
		{
			sql = sql+",proThreshold='"+promotion.get("proThreshold")+"'";
		}
		if(!StringUtils.isEmpty(promotion.get("proPrice")))
		{
			sql = sql+",proPrice='"+promotion.get("proPrice")+"'";
		}
		sql = sql + " where id="+promotion.get("id");
		
		return sql;
	}
}
