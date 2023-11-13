package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.feign.user.UserFeignClient;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value="/api/product")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProductController {
	
	@Autowired
	private ProductService productService;


	@Autowired
	private UserFeignClient userFeignClient;

	@Operation(summary = "分页查询")
	@GetMapping(value = "/{page}/{limit}")
	public Result<PageInfo<ProductSku>> findByPage(@Parameter(name = "page", description = "当前页码", required = true) @PathVariable Integer page,
												   @Parameter(name = "limit", description = "每页记录数", required = true) @PathVariable Integer limit,
												   @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false) ProductSkuDto productSkuDto) {
		PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
		return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
	}

	@Operation(summary = "商品详情")
	@GetMapping("item/{skuId}")
	public Result<ProductItemVo> item(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable String skuId) {
		ProductItemVo productItemVo;
		if (!"undefined".equals(skuId)) {
			// 如果没有传入有效的skuId，可以返回收藏最多的商品信息
			//远程调用查询收藏最多的商品
			productItemVo = productService.item(skuId);
		}else {
			//远程调用获取浏览量最多的商品
			UserBrowseHistory browseHistory = userFeignClient.getByBrowseHistory();
			Long id = browseHistory.getSkuId();
			String skuidString = String.valueOf(id);
			productItemVo = productService.item(skuidString);
		}

		return Result.build(productItemVo , ResultCodeEnum.SUCCESS);
	}

	/**
	 * @Description: 远程调用：根据商品skuId获取商品sku信息
	 * @param skuId
	 */
	@Operation(summary = "获取商品sku信息")
	@GetMapping("getBySkuId/{skuId}")
	public ProductSku getBySkuId(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
		ProductSku productSku = productService.getBySkuId(skuId);
		return productSku;
	}

	@Operation(summary = "更新商品sku销量")
	@PostMapping("updateSkuSaleNum")
	public Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList) {
		return productService.updateSkuSaleNum(skuSaleDtoList);
	}

}