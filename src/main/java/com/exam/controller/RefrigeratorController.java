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
       MemberDTO memberDTO = (MemberDTO)auth.getPrincipal();
      String userid = memberDTO.getUserid();
      
       List<RefrigeratorDTO> refList = refrigeratorService.refrigeratorList(userid);
       m.addAttribute("refList", refList);
       
       List<GoodsDTO> goodsList = goodsService.getAllStock();
       m.addAttribute("goodsList", goodsList);
       logger.info("LIST: {}", goodsList);
       
      return "refrigerator";
   }

    // 식재료 추가
    @GetMapping("refrigeratorAdd")
    public String refrigeratorAdd(@RequestParam String gCode,
    							  @RequestParam String gCategory,
    							  @RequestParam String gName,
    						  	
    				              @Size(min = 1, max = 2)
    							  @RequestParam String rStock,
    							
    							  Model m
    							  ) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	MemberDTO memberDTO = (MemberDTO)auth.getPrincipal();
    	
    	String userid = memberDTO.getUserid();
    	
    	RefrigeratorDTO refrigeratorDTO = new RefrigeratorDTO();
    	refrigeratorDTO.setUserid(userid);
    	refrigeratorDTO.setgCode(gCode);
    	refrigeratorDTO.setgCategory(gCategory);
    	refrigeratorDTO.setgName(gName);
    	refrigeratorDTO.setrStock(Integer.parseInt(rStock));
    	
    	int n = refrigeratorService.refrigeratorAdd(refrigeratorDTO);
    	
    	return "/refrigeratorAddSuccess";
    }
    
    // 식재료 삭제
    @GetMapping("refrigeratorDelete")
    public String refrigeratorDelete(@RequestParam Integer num) {
       
       int n = refrigeratorService.refrigeratorDelete(num);
       
       return "redirect:refrigeratorList";
    }
    
    // 식재료 보유 현황 조회
    @GetMapping("getRefrigeratorStock")
    public String getRefrigeratorStock(@RequestParam String gCode, Model m) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       MemberDTO memberDTO = (MemberDTO)auth.getPrincipal();
       
       String userid = memberDTO.getUserid();

        int stock = refrigeratorService.getRefrigeratorStock(userid, gCode);
        m.addAttribute("stock", stock);

        return "/refrigerator";
    }
    
    // 식재료 보유 현황 업데이트
    @GetMapping("updateRefrigeratorStock")
    public String updateRefrigeratorStock(@RequestParam String gCode,
                                 @RequestParam int amount) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       MemberDTO memberDTO = (MemberDTO)auth.getPrincipal();
       
       String userid = memberDTO.getUserid();

        int n = refrigeratorService.updateRefrigeratorStock(userid, gCode, amount);
       
       return "redirect:refrigeratorList";
       
    }


}
