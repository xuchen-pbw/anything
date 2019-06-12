package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.service.product.BrandService;
import cn.itcast.core.service.product.ProductService;

/**
 * 商品管理
 * 列表
 * 添加
 * 上架
 * @author lx
 *
 */
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	
	//查询
	@RequestMapping(value = "/product/list.do")
	public String list(Integer pageNo,String name,Long brandId,Boolean isShow,Model model){
		//品牌的结果集
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		model.addAttribute("brands", brands);
		
		Pagination pagination = productService.selectPaginationByQuery(pageNo, name, brandId, isShow);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		if(null != isShow){
			model.addAttribute("isShow", isShow);
		}else{
			model.addAttribute("isShow", false);
		}
		return "product/list";
	}
	
	//去商品添加页面
	@RequestMapping(value = "/product/toAdd.do")
	public String toAdd(Model model){
		//品牌的结果集
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		model.addAttribute("brands", brands);
		
		List<Color> colors = productService.selectColorList();
		model.addAttribute("colors", colors);
		return "product/add";
	}
	//商品保存
	@RequestMapping(value = "/product/add.do")
	public String add(Product product){
		productService.insertProduct(product);
		
		return "redirect:/product/list.do";
	}
	//上架 批量
	@RequestMapping(value = "/product/isShow.do")
	public String isShow(Long[] ids){
		productService.isShow(ids);
		return "forward:/product/list.do";
	}
	@RequestMapping(value = "/type/list.do")
	public String tlist(){
		return "type/list";
	}
	
	
	
}
