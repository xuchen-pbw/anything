package cn.itcast.core.service;

import cn.itcast.common.page.Pagination;

public interface SearchService {
	
	
	//全文检索
	//全文检索
	public Pagination selectPaginationByQuery(Integer pageNo,String keyword,Long brandId,String price) throws Exception;
	//保存商品信息到Solr服务器
	public void insertProductToSolr(Long id);

}
