package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exam.dto.GoodsDTO;
import com.exam.dto.MemberDTO;
import com.exam.dto.RefrigeratorDTO;
import com.exam.service.GoodsService;
import com.exam.service.RefrigeratorService;

import jakarta.validation.constraints.Size;

@Controller
@Validated
public class RefrigeratorController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	RefrigeratorService refrigeratorService;
	GoodsService goodsService;

	public RefrigeratorController(RefrigeratorService refrigeratorService, GoodsService goodsService) {
		this.refrigeratorService = refrigeratorService;
		this.goodsService = goodsService;
	}

	// 전체 냉장고 목록 보기
	@GetMapping("/refrigerator")
	public String main(Model m) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberDTO memberDTO = (MemberDTO) auth.getPrincipal();
		String userid = memberDTO.getUserid();

		List<RefrigeratorDTO> refrigeratorList = refrigeratorService.refrigeratorList(userid);
		m.addAttribute("refrigeratorList", refrigeratorList);

		List<GoodsDTO> refGoodsList = goodsService.getAllStock();
		m.addAttribute("refGoodsList", refGoodsList);

		return "refrigerator";
	}

	// 식재료 추가
	@GetMapping("/refrigeratorAdd")
	public String refrigeratorAdd(@RequestParam String gCode,
								  @RequestParam String gCategory,
								  @RequestParam String gName,
								  @Size(min = 1, max = 2) @RequestParam String rStock,
								  Model m) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberDTO memberDTO = (MemberDTO) auth.getPrincipal();

		String userid = memberDTO.getUserid();
		logger.info("CustomLOG[REQUEST]: 냉장고에서 새로운 상품 추가를 요청받음: userid:{}, 상품명:{}, 수량:{}", userid, gName, rStock);

		RefrigeratorDTO refrigeratorDTO = new RefrigeratorDTO();
		refrigeratorDTO.setUserid(userid);
		refrigeratorDTO.setgCode(gCode);
		refrigeratorDTO.setgCategory(gCategory);
		refrigeratorDTO.setgName(gName);
		refrigeratorDTO.setrStock(Integer.parseInt(rStock));

		int n = refrigeratorService.refrigeratorAdd(refrigeratorDTO);
		logger.info("CustomLOG[SUCCESS]: 냉장고에 새로운 상품이 정상적으로 추가됨: userid:{}, 상품명:{}, 수량:{}", userid, gName, rStock);

		return "refrigerator/refrigeratorAddSuccess";
	}

	// 냉장고 상품 삭제
	@GetMapping("refrigeratorDelete")
	public String refrigeratorDelete(@RequestParam Integer num) {

		logger.info("CustomLOG[REQUEST]: 냉장고에서 상품 삭제를 요청받음");
		int n = refrigeratorService.refrigeratorDelete(num);
		logger.info("CustomLOG[SUCCESS]: 냉장고에서 상품이 정상적으로 삭제됨");

		return "redirect:refrigerator";
	}

	// 냉장고 상품 전체 삭제
	   @GetMapping("/refrigeratorDeleteAll")
	   public String refrigeratorDeleteAll(@RequestParam(name = "check", required = false) List<String> check) {
	      
	      System.out.println("check: " + check);
	      if(check != null) {
	         int n = refrigeratorService.refrigeratorDeleteAll(check);
	      }
	      
	      return "redirect:refrigerator";
	   }
	
	// 식재료 보유 현황 조회
	@GetMapping("getRefrigeratorStock")
	public String getRefrigeratorStock(@RequestParam String gCode, Model m) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberDTO memberDTO = (MemberDTO) auth.getPrincipal();

		String userid = memberDTO.getUserid();

		int stock = refrigeratorService.getRefrigeratorStock(userid, gCode);
		m.addAttribute("stock", stock);

		return "/refrigerator";
	}

	// 냉장고 상품 수량 변경
	@GetMapping("updateRefrigeratorStock")
	public String updateRefrigeratorStock(@RequestParam int num, @RequestParam int amount) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberDTO memberDTO = (MemberDTO) auth.getPrincipal();

		String userid = memberDTO.getUserid();
		logger.info("CustomLOG[REQUEST]: 냉장고 상품 수량 변경을 요청받음 userid:{}, 상품등록번호:{}, 변경할 수량:{}", userid, num, amount);

		RefrigeratorDTO refrigeratorDTO = new RefrigeratorDTO();
		refrigeratorDTO.setUserid(userid);
		refrigeratorDTO.setrStock(amount);
		refrigeratorDTO.setNum(num);

		int n = refrigeratorService.updateRefrigeratorStock(refrigeratorDTO);
		logger.info("CustomLOG[SUCCESS]: 냉장고에서 상품 수량이 정상적으로 변경됨 userid:{}, 상품등록번호:{}, 변경후 수량:{}", userid, num, amount);
		
		return "refrigerator/refrigeratorUpdateSuccess";
	}

}
