package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Sku;

public interface SkuService {
	
	//商品ID 查询 库存结果集
	public List<Sku> selectSkuListByProductId(Long productId);
	
	//修改
	public void updateSkuById(Sku sku);

}
